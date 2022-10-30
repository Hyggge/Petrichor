package front_end.AST.Func;

import front_end.AST.Exp.ConstExp;
import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.symbol.SymbolManager;
import front_end.symbol.VarSymbol;
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

}
