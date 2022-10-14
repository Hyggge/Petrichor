package front_end.AST.Exp;

import front_end.AST.Node;
import utils.SyntaxVarType;

import java.util.ArrayList;

// RelExp ==> AddExp {('<' | '>' | '<=' | '>=') AddExp}
public class RelExp extends Node {
    public RelExp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}
