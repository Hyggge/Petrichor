package front_end.AST.Var;

import front_end.AST.Exp.ConstExp;
import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.symbol.ConstSymbol;
import front_end.symbol.SymbolManager;
import llvm_ir.GlobalVar;
import llvm_ir.IRBuilder;
import llvm_ir.Value;
import llvm_ir.initial.Initial;
import llvm_ir.type.ArrayType;
import llvm_ir.type.BaseType;
import llvm_ir.type.Type;
import utils.ErrorType;
import utils.Printer;
import utils.SymbolType;
import utils.SyntaxVarType;
import utils.ValueType;

import java.util.ArrayList;

// ConstDef ==> Indent {'[' ConstExp ']'} '=' ConstInitVal
public class ConstDef extends Node {
    private ConstSymbol symbol;

    public ConstDef(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    private ConstSymbol createSymbol() {
        // ConstDef ==> Indent {'[' ConstExp ']'} '=' ConstInitVal
        String symbolName = ((TokenNode)children.get(0)).getToken().getValue();
        SymbolType symbolType = SymbolType.SYMBOL_CONST;
        ValueType valueType = ValueType.INT;
        int num = children.size();
        int dim = 0; // 数组维度
        int totLen = 1; // 数组总长度 = 数组各维度长度乘积
        ArrayList<Integer> lenList = new ArrayList<>();

        // get dim
        for (Node child : children) {
            if (child instanceof ConstExp) {
                dim++;
                int tempLen = child.execute();
                totLen *= tempLen;
                lenList.add(tempLen);
            }
        }

        // 如果该常量是全局常量，我们可以直接求出对应的值
        if (SymbolManager.getInstance().isGlobal()) {
            Initial initial = null;
            Type initialType = null;
            // 判断是整数型常量还是数组型常量
            if (dim == 0) initialType = BaseType.INT32;
            else initialType = new ArrayType(totLen, BaseType.INT32);
            // 获得初始值
            if (children.get(num-1).getType() != SyntaxVarType.CONST_INITVAL) {
                initial = new Initial(initialType, null);
            } else {
                ArrayList<Integer> values = ((ConstInitVal)children.get(num-1)).execute(dim);
                initial = new Initial(initialType, values);
            }
            return new ConstSymbol(symbolName, symbolType, valueType, dim, lenList, initial);
        }

        return new ConstSymbol(symbolName, symbolType, valueType, dim, lenList);
    }

    @Override
    public void checkError() {
        this.symbol = createSymbol();
        super.checkError();
        // check Error b
        boolean res = SymbolManager.getInstance().addSymbol(symbol);
        if (! res) Printer.addErrorMsg(children.get(0).getEndLine(), ErrorType.b);
    }

    @Override
    public Value genIR() {
        SymbolManager.getInstance().addSymbol(symbol);
        // 生成global IR
        if (symbol.isGlobal()) {
            String name = IRBuilder.getInstance().genGlobalVarName();
            Initial initial = symbol.getInitial();
            GlobalVar globalVar = new GlobalVar(initial.getType(), name, initial);
            // 将value信息加入符号
            symbol.setLlvmValue(globalVar);
            // 将golbalVar加入module
            IRBuilder.getInstance().addGlobalVar(globalVar);
        }
        super.genIR();
        return null;
    }
}
