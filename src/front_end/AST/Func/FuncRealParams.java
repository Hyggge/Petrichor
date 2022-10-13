package front_end.AST.Func;

import front_end.AST.Exp.Exp;
import front_end.AST.Node;
import utils.SyntaxVarType;

import java.util.ArrayList;

// FuncRealParams → Exp { ',' Exp }
public class FuncRealParams extends Node {
    public FuncRealParams(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    public ArrayList<Integer> getRParamDims() {
        ArrayList<Integer> RParamDims = new ArrayList<>();
        for (Node child : children) {
            if (child instanceof Exp) {
                RParamDims.add(child.getDim());
            }
        }
        return RParamDims;
    }

}
