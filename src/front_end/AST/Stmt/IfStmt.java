package front_end.AST.Stmt;

import front_end.AST.Node;
import utils.SyntaxVarType;

import java.util.ArrayList;

// IfStmt ==> 'if' '(' Cond ')' Stmt [ 'else' Stmt ]
public class IfStmt extends Stmt {
    public IfStmt(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}
