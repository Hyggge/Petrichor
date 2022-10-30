package front_end.AST.Exp;

import front_end.AST.Node;
import utils.SyntaxVarType;

import java.util.ArrayList;

// PrimaryExp ==> '(' Exp ')' | LValExp | Number
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

    @Override
    public int execute() {
        int ans = 0;
        if (children.get(0) instanceof Number) {
            ans = children.get(0).execute();
        }
        else if (children.get(0) instanceof LValExp) {
            ans = children.get(0).execute();
        }
        else {
            ans = children.get(1).execute(); // '(' Exp ')'
        }
        return ans;
    }
}
