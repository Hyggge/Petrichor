package front_end.AST.Exp;

import front_end.AST.Func.FuncRealParams;
import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.lexer.Token;
import front_end.symbol.FuncSymbol;
import front_end.symbol.SymbolManager;
import llvm_ir.Constant;
import llvm_ir.Function;
import llvm_ir.IRBuilder;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.instr.AluInstr;
import llvm_ir.instr.CallInstr;
import llvm_ir.instr.IcmpInstr;
import llvm_ir.instr.ZextInstr;
import llvm_ir.type.BaseType;
import utils.ErrorType;
import utils.Printer;
import utils.SyntaxVarType;
import utils.TokenType;
import utils.ValueType;

import java.util.ArrayList;

// UnaryExp ==> PrimaryExp | Ident '(' [FuncRealParams] ')' | UnaryOp UnaryExp
public class UnaryExp extends Node {
    public UnaryExp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public Integer getDim() {
        // function call;
        if (children.get(0) instanceof TokenNode) {
            String funcName = ((TokenNode) children.get(0)).getToken().getValue();
            FuncSymbol funcSymbol = (FuncSymbol) SymbolManager.getInstance().getSymbolByName(funcName);
            if (funcSymbol.getReturnType() == ValueType.INT) return 0;
            else return null;
        }
        for (Node child : children) {
            if (child.getDim() != null) return child.getDim();
        }
        return null;
    }

    @Override
    public int execute() {
        int ans = 0;
        if (children.get(0) instanceof UnaryOp) {
            TokenNode tokenNode = (TokenNode) children.get(0).getChildren().get(0);
            UnaryExp unaryExp = ((UnaryExp)children.get(1));
            if (tokenNode.getToken().getType() == TokenType.PLUS) {
                ans = unaryExp.execute();
            }
            else if (tokenNode.getToken().getType() == TokenType.MINU) {
                ans = -unaryExp.execute();
            }
            else {
                ans = unaryExp.execute() == 0 ? 1 : 0;
            }
        }
        else if (children.get(0) instanceof PrimaryExp) {
            ans = children.get(0).execute();
        }
        return ans;
    }

    @Override
    public void checkError() {
        // function call
        if (children.get(0) instanceof TokenNode) {
            Token ident = ((TokenNode)children.get(0)).getToken();
            FuncSymbol funcSymbol = (FuncSymbol)SymbolManager.getInstance().getSymbolByName(ident.getValue());
            // check Error c
            if (funcSymbol == null) {
                Printer.addErrorMsg(ident.getLineNumber(), ErrorType.c);
                super.checkError(); return;
            }
            // call the function
            ArrayList<Integer> FParamDims = funcSymbol.getFParamDims();
            ArrayList<Integer> RParamDims = new ArrayList<>();
            if (children.size() > 2 &&  children.get(2) instanceof FuncRealParams) {
                RParamDims = ((FuncRealParams) children.get(2)).getRParamDims();
            }

            System.out.println(FParamDims);
            System.out.println(RParamDims);
            // check Error d
            if (RParamDims.size() != FParamDims.size()) {
                Printer.addErrorMsg(ident.getLineNumber(), ErrorType.d);
                super.checkError(); return;
            }
            // check Error e
            for (int i = 0; i < RParamDims.size(); i++) {
                if (! FParamDims.get(i).equals(RParamDims.get(i))) {
                    Printer.addErrorMsg(ident.getLineNumber(), ErrorType.e);
                    super.checkError(); return; // only print once
                }
            }

        }
        super.checkError();
    }

    @Override
    public Value genIR() {
        if (children.get(0) instanceof PrimaryExp) {
            return children.get(0).genIR();
        }
        else if (children.get(0) instanceof UnaryOp) {
            TokenNode tokenNode = (TokenNode) children.get(0).getChildren().get(0);
            Value operand1 = children.get(1).genIR();
            Value operand2 = new Constant(0);
            Instr instr = null;
            if (tokenNode.getToken().getType() == TokenType.PLUS) {
                return operand1;
            }
            else if (tokenNode.getToken().getType() == TokenType.MINU) {
                instr = new AluInstr(IRBuilder.getInstance().genLocalVarName(), AluInstr.Op.SUB, operand2, operand1);
                IRBuilder.getInstance().addInstr(instr);
                return instr;
            }
            else {
                instr = new IcmpInstr(IRBuilder.getInstance().genLocalVarName(), IcmpInstr.Op.EQ, operand2, operand1);
                IRBuilder.getInstance().addInstr(instr);
                instr = new ZextInstr(IRBuilder.getInstance().genLocalVarName(), instr, BaseType.INT32);
                IRBuilder.getInstance().addInstr(instr);
                return instr;
            }
        }
        else { // 函数调用
            // 获得函数对应的Value
            Token ident = ((TokenNode)children.get(0)).getToken();
            FuncSymbol funcSymbol = (FuncSymbol)SymbolManager.getInstance().getSymbolByName(ident.getValue());
            Function function = funcSymbol.getLlvmValue();
            // 获得函数的参数
            ArrayList<Value> params = new ArrayList<>();
            if (children.get(2) instanceof FuncRealParams) {
                for (Node child : children.get(2).getChildren()) {
                    if (child instanceof Exp)
                        params.add(child.genIR());
                }
            }
            Instr instr = new CallInstr(IRBuilder.getInstance().genLocalVarName(), function, params);
            IRBuilder.getInstance().addInstr(instr);
            return instr;
        }
    }
}
