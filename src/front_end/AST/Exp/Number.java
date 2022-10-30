package front_end.AST.Exp;

import front_end.AST.Node;
import front_end.AST.TokenNode;
import utils.SyntaxVarType;

import java.util.ArrayList;

// Number ==> IntConst
public class Number extends Node {
    public Number(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public Integer getDim() {
        return 0;
    }

    @Override
    public int execute() {
        return Integer.parseInt(((TokenNode)children.get(0)).getToken().getValue());
    }

    // checkError d

}
