package front_end.AST.Exp;

import front_end.AST.Node;
import utils.SyntaxVarType;

import java.util.ArrayList;

// ConstExp ==> AddExp
public class ConstExp extends Node {
    public ConstExp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public int execute() {
        return children.get(0).execute();
    }

}
