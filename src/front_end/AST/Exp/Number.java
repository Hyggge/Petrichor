package front_end.AST.Exp;

import front_end.AST.Node;
import utils.SyntaxVarType;
import java.util.ArrayList;

public class Number extends Node {
    public Number(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}
