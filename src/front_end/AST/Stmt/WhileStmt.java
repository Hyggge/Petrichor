package front_end.AST.Stmt;

import front_end.AST.Node;
import front_end.symbol.SymbolManager;
import utils.SyntaxVarType;

import java.util.ArrayList;

// WhileStmt ==> 'while' '(' Cond ')' Stmt
public class WhileStmt extends Stmt {
    public WhileStmt(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public void checkError() {
        SymbolManager.getInstance().enterLoop();
        super.checkError();
        SymbolManager.getInstance().leaveLoop();
    }
}
