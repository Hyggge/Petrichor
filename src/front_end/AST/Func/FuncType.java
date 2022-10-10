package front_end.AST.Func;

import front_end.AST.Node;
import front_end.parser.SyntaxVarType;
import java.util.ArrayList;

public class FuncType extends Node {
    public FuncType(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}
