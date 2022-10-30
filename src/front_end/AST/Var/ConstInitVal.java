package front_end.AST.Var;

import front_end.AST.Exp.ConstExp;
import front_end.AST.Node;
import utils.SyntaxVarType;
import java.util.ArrayList;

// ConstInitVal ==> ConstExp | '{' [ConstInitVal {',' ConstInitVal}]'}'
public class ConstInitVal extends Node {
    public ConstInitVal(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    public ArrayList<Integer> execute(int dim) {
        ArrayList<Integer> ans = new ArrayList<>();
        if (dim == 0) {
            ConstExp constExp = (ConstExp) children.get(0);
            ans.add(constExp.execute());
        }
        else if (dim == 1) {
            for (Node child : children) {
                if (child.getType() == SyntaxVarType.CONST_INITVAL) {
                    ArrayList<Integer> temp = ((ConstInitVal) child).execute(0);
                    ans.addAll(temp);
                }
            }
        }
        else {
            for (Node child : children) {
                if (child.getType() == SyntaxVarType.CONST_INITVAL) {
                    ArrayList<Integer> temp = ((ConstInitVal) child).execute(1);
                    ans.addAll(temp);
                }
            }
        }
        return ans;
    }
}
