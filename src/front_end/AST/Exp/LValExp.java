package front_end.AST.Exp;

import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.lexer.Token;
import front_end.symbol.ConstSymbol;
import front_end.symbol.Symbol;
import front_end.symbol.SymbolManager;
import front_end.symbol.VarSymbol;
import utils.ErrorType;
import utils.Printer;
import utils.SyntaxVarType;
import utils.TokenType;

import java.util.ArrayList;

// LValExp ==> Ident {'[' Exp ']'}
public class LValExp extends Node {
    public LValExp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public Integer getDim()  {
        Token ident = ((TokenNode)children.get(0)).getToken();
        Symbol symbol = SymbolManager.getInstance().getSymbolByName(ident.getValue());
        if (symbol == null) return null;
        // else
        int dim = 0;
        int cnt = 0;
        // get symbol's dim
        if (symbol instanceof VarSymbol) dim = ((VarSymbol)symbol).getDim();
        else if (symbol instanceof ConstSymbol) dim = ((ConstSymbol)symbol).getDim();
        // get number of '['
        for (Node child : children) {
            if (child instanceof TokenNode && ((TokenNode)child).getToken().getType() == TokenType.LBRACK) {
                cnt++;
            }
        }
        // lval dim = symbol dim - number of '['
        return dim - cnt;
    }

    public boolean isConst() {
        String name = ((TokenNode)children.get(0)).getToken().getValue();
        Symbol symbol = SymbolManager.getInstance().getSymbolByName(name);
        if (symbol instanceof ConstSymbol) return true;
        return false;
    }

    @Override
    public void checkError() {
        // check Error c
        Token ident = ((TokenNode)children.get(0)).getToken();
        if (SymbolManager.getInstance().getSymbolByName(ident.getValue()) == null) {
            Printer.printErrorMsg(ident.getLineNumber(), ErrorType.c);
            super.checkError();
        }
        super.checkError();
    }
}
