package front_end.AST.Exp;

import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.lexer.Token;
import front_end.symbol.SymbolManager;
import utils.ErrorType;
import utils.Printer;
import utils.SyntaxVarType;

import java.util.ArrayList;

// LValExp ==> Ident {'[' Exp ']'}
public class LValExp extends Node {
    public LValExp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public void checkError() {
        Token ident = ((TokenNode)children.get(0)).getToken();
        if (SymbolManager.getInstance().getSymbolByName(ident.getValue()) == null) {
            Printer.printErrorMsg(ident.getLineNumber(), ErrorType.c);
        }
        super.checkError();
    }
}
