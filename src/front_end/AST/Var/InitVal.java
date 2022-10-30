package front_end.AST.Var;

import front_end.AST.Exp.Exp;
import front_end.AST.Node;
import utils.SyntaxVarType;

import java.util.ArrayList;

// InitVal ==>  Exp | '{' [InitVal {',' InitVal}]'}'
public class InitVal extends Node {
    public InitVal(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    public ArrayList<Integer> execute(int dim) {
        ArrayList<Integer> ans = new ArrayList<>();
        if (dim == 0) {
            Exp exp = (Exp) children.get(0);
            ans.add(exp.execute());
        }
        else if (dim == 1) {
            for (Node child : children) {
                if (child.getType() == SyntaxVarType.INIT_VAL) {
                    ArrayList<Integer> temp = ((InitVal) child).execute(0);
                    ans.addAll(temp);
                }
            }
        }
        else {
            for (Node child : children) {
                if (child.getType() == SyntaxVarType.INIT_VAL) {
                    ArrayList<Integer> temp = ((InitVal) child).execute(1);
                    ans.addAll(temp);
                }
            }
        }
        return ans;
    }
}
