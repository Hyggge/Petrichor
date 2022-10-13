package front_end.AST.Exp;

import front_end.AST.Node;
import utils.SyntaxVarType;

import java.util.ArrayList;

public class PrimaryExp extends Node {
    public PrimaryExp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public Integer getDim() {
        for (Node child : children) {
            if (child.getDim() != null) return child.getDim();
        }
        return null;
    }
}
