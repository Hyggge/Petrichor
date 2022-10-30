package front_end.AST.Var;

import front_end.AST.Exp.ConstExp;
import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.symbol.SymbolManager;
import front_end.symbol.VarSymbol;
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

// VarDef  ==>  Ident {'['  Exp  ']'} ['='  InitVal]
public class VarDef extends Node {
    private VarSymbol symbol;

    public VarDef(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    private VarSymbol createSymbol() {
        // VarDef  ==>  Ident {'[' ConstExp ']'} ['='  InitVal]
        String symbolName = ((TokenNode)children.get(0)).getToken().getValue();
        SymbolType symbolType = SymbolType.SYMBOL_VAR;
        ValueType valueType = ValueType.INT;
        int dim = 0;
        int totLen = 1;
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

        // 如果该变量是全局变量，我们可以直接求出对应的值
        if (SymbolManager.getInstance().isGlobal()) {
            Initial initial = null;
            Type initialType = null;
            // 判断是整数型常量还是数组型常量
            if (dim == 0) initialType = BaseType.INT32;
            else initialType = new ArrayType(totLen, BaseType.INT32);
            // 获得初始值
            if (children.get(children.size()-1).getType() != SyntaxVarType.INIT_VAL) {
                initial = new Initial(initialType, null);
            } else {
                ArrayList<Integer> values = ((InitVal)children.get(children.size()-1)).execute(dim);
                initial = new Initial(initialType, values);
            }
            return new VarSymbol(symbolName, symbolType, valueType, dim, lenList, initial);
        }

        return new VarSymbol(symbolName, symbolType, valueType, dim, lenList);
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
        // 如果生成全局变量
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
