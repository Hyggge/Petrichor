package front_end.AST.Exp;

import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.lexer.Token;
import front_end.symbol.SymbolManager;
import utils.ErrorType;
import utils.Printer;
import utils.SyntaxVarType;

import java.util.ArrayList;

// UnaryExp ==> PrimaryExp | Ident '(' [FuncRealParams] ')' | UnaryOp UnaryExp
public class UnaryExp extends Node {
    public UnaryExp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public void checkError() {
        if (children.get(0) instanceof TokenNode) {
            Token ident = ((TokenNode)children.get(0)).getToken();
            if (SymbolManager.getInstance().getSymbolByName(ident.getValue()) == null) {
                Printer.printErrorMsg(ident.getLineNumber(), ErrorType.c);
            }
        }
        super.checkError();
    }
}
