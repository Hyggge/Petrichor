package front_end.AST.Var;

import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.symbol.ConstSymbol;
import front_end.symbol.SymbolManager;
import utils.ErrorType;
import utils.Printer;
import utils.SymbolType;
import utils.SyntaxVarType;
import utils.TokenType;
import utils.ValueType;

import java.util.ArrayList;

public class ConstDef extends Node {
    private ConstSymbol symbol;

    public ConstDef(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
        this.symbol = createSymbol();
        System.out.println(this.symbol);
    }

    private ConstSymbol createSymbol() {
        // ConstDef ==> Indent {'[' ConstExp ']'} '=' ConstInitVal
        String symbolName = ((TokenNode)children.get(0)).getToken().getValue();
        SymbolType symbolType = SymbolType.SYMBOL_CONST;
        ValueType valueType = ValueType.INT;
        int dim = 0;

        // get dim
        for (Node child : children) {
            if (child instanceof TokenNode && ((TokenNode)child).getToken().getType() == TokenType.LBRACK) {
                dim++;
            }
        }
        return new ConstSymbol(symbolName, symbolType, valueType, dim);
    }

    @Override
    public void checkError() {
        boolean res = SymbolManager.getInstance().addSymbol(symbol);
        if (! res) Printer.printErrorMsg(children.get(0).getStartLine(), ErrorType.b);
        super.checkError();
    }
}
