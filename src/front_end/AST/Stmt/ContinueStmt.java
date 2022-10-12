package front_end.AST.Stmt;

import front_end.AST.Node;
import front_end.symbol.SymbolManager;
import utils.ErrorType;
import utils.Printer;
import utils.SyntaxVarType;

import java.util.ArrayList;

public class ContinueStmt extends Stmt {
    public ContinueStmt(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public void checkError() {
        // check Error m
        if (SymbolManager.getInstance().getLoopDepth() <= 0) {
            Printer.printErrorMsg(startLine, ErrorType.m);
        }
        super.checkError();
    }
}
