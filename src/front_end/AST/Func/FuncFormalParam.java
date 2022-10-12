package front_end.AST.Func;

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

public class FuncFormalParam extends Node {
    private VarSymbol symbol;

    public FuncFormalParam(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
        this.symbol = createSymbol();
        System.out.println(this.symbol);
    }

    public VarSymbol createSymbol() {
        // FuncFormalParam  ==> 'int' Indent ['[' ']'  {'[' ConstExp ']'}]
        String symbolName = ((TokenNode)children.get(1)).getToken().getValue();
        SymbolType symbolType = SymbolType.SYMBOL_VAR;
        ValueType FParamType = null;
        int dim = 0;
        // getFParamType
        TokenType tokenType = ((TokenNode)children.get(0)).getToken().getType();
        if (tokenType == TokenType.INTTK) FParamType = ValueType.INT;
        // getFParamDim
        for (Node child : children) {
            if (child instanceof TokenNode && ((TokenNode)child).getToken().getType() == TokenType.LBRACK) {
                dim++;
            }
        }
        return new VarSymbol(symbolName, symbolType, FParamType, dim);
    }

    public ValueType getFParamType() {
        return symbol.getValueType();
    }

    public int getFParamDim() {
        return symbol.getDim();
    }

    @Override
    public void checkError() {
        boolean res = SymbolManager.getInstance().addSymbol(symbol);
        if (! res) Printer.printErrorMsg(children.get(1).getStartLine(), ErrorType.b);
        super.checkError();
    }

}
