package front_end.AST.Func;

import front_end.AST.Exp.ConstExp;
import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.symbol.SymbolManager;
import front_end.symbol.VarSymbol;
import llvm_ir.IRBuilder;
import llvm_ir.Instr;
import llvm_ir.Param;
import llvm_ir.Value;
import llvm_ir.instr.AllocaInstr;
import llvm_ir.instr.StoreInstr;
import llvm_ir.type.BaseType;
import llvm_ir.type.PointerType;
import llvm_ir.type.Type;
import utils.ErrorType;
import utils.Printer;
import utils.SymbolType;
import utils.SyntaxVarType;
import utils.TokenType;
import utils.ValueType;

import java.util.ArrayList;

// FuncFormalParam  ==> 'int' Indent ['[' ']'  {'[' ConstExp ']'}]
public class FuncFormalParam extends Node {
    private VarSymbol symbol;

    public FuncFormalParam(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    public VarSymbol createSymbol() {
        // FuncFormalParam  ==> 'int' Indent ['[' ']'  {'[' ConstExp ']'}]
        String symbolName = ((TokenNode)children.get(1)).getToken().getValue();
        SymbolType symbolType = SymbolType.SYMBOL_VAR;
        ValueType FParamType = null;
        int dim = 0;
        ArrayList<Integer> lenList = new ArrayList<>();
        // getFParamType
        TokenType tokenType = ((TokenNode)children.get(0)).getToken().getType();
        if (tokenType == TokenType.INTTK) FParamType = ValueType.INT;
        // getFParamDim
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i) instanceof TokenNode && ((TokenNode)children.get(i)).getToken().getType() == TokenType.LBRACK) {
                dim++;
                if (children.get(i+1) instanceof ConstExp) {
                    lenList.add(children.get(i+1).execute());
                } else {
                    lenList.add(-1);  // 数组形参第一维信息丢失，用-1来站位
                }
            }
        }
        return new VarSymbol(symbolName, symbolType, FParamType, dim, lenList);
    }

    public ValueType getFParamType() {
        return symbol.getValueType();
    }

    public int getFParamDim() {
        return symbol.getDim();
    }

    @Override
    public void checkError() {
        this.symbol = createSymbol();
        // check Error b
        boolean res = SymbolManager.getInstance().addSymbol(symbol);
        if (! res) Printer.addErrorMsg(children.get(1).getEndLine(), ErrorType.b);
        super.checkError();
    }

    @Override
    public Value genIR() {
        SymbolManager.getInstance().addSymbol(symbol);
        Type type = symbol.getDim() == 0 ? BaseType.INT32 : new PointerType(BaseType.INT32); // 只要是数组一律将类型变为i32，然后按照一维数组取值。
        Param param = new Param(type, IRBuilder.getInstance().genParamName());
        // 将Param加入curFunction
        IRBuilder.getInstance().addParam(param);
        // 如果参数是整数类型，为了防止被修改，需要在函数体为其创建一个指针
        if (param.getType().isInt32()) {
            // 我们需要使用alloc为参数创建一个指针，然后通过指针来访问形参，以满足SSA要求
            Instr instr = new AllocaInstr(IRBuilder.getInstance().genLocalVarName(), param.getType());
            symbol.setLlvmValue(instr); // 将value信息加入符号本身
            instr = new StoreInstr(IRBuilder.getInstance().genLocalVarName(), param, instr);
        }
        // 如果参数是数组类型（传入的是i32*）则不需要，因为我们不会修改指针的值
        else {
            symbol.setLlvmValue(param);
        }

        super.genIR();
        return null;
    }
}
