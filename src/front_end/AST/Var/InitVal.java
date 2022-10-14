package front_end.AST.Var;

import front_end.AST.Node;
import utils.SyntaxVarType;

import java.util.ArrayList;

// InitVal ==>  Exp | '{' [InitVal {',' InitVal}]'}'
public class InitVal extends Node {
    public InitVal(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}
