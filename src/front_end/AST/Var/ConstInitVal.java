package front_end.AST.Var;

import front_end.AST.Node;
import front_end.parser.SyntaxVarType;
import java.util.ArrayList;

public class ConstInitVal extends Node {
    public ConstInitVal(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}
