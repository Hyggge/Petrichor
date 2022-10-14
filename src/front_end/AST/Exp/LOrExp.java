package front_end.AST.Exp;

import front_end.AST.Node;
import utils.SyntaxVarType;

import java.util.ArrayList;

// LOrExp ==>  LAndExp {'||' LAndExp}
public class LOrExp extends Node {
    public LOrExp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}
