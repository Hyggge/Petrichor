package front_end.AST.Stmt;

import front_end.AST.Node;
import front_end.parser.SyntaxVarType;

import java.util.ArrayList;

public class IfStmt extends Stmt {
    public IfStmt(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}
