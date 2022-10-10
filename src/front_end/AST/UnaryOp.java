package front_end.AST;

import front_end.parser.SyntaxVarType;
import java.util.ArrayList;

public class UnaryOp extends Node{
    public UnaryOp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}
