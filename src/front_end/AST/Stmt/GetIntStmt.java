package front_end.AST.Stmt;

import front_end.AST.Node;
import utils.SyntaxVarType;

import java.util.ArrayList;

public class GetIntStmt extends Stmt {
    public GetIntStmt(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}
