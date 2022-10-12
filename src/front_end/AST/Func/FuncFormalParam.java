package front_end.AST.Func;

import front_end.AST.Node;
import front_end.AST.TokenNode;
import utils.SyntaxVarType;
import utils.TokenType;
import utils.ValueType;

import java.util.ArrayList;

public class FuncFormalParam extends Node {
    public FuncFormalParam(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    public ValueType getFParamType() {
        TokenType tokenType = ((TokenNode)children.get(0)).getToken().getType();
        if (tokenType == TokenType.INTTK) return ValueType.INT;
        return null;
    }

    public int getFParamDim() {
        int dim = 0;
        for (Node child : children) {
            if (child instanceof TokenNode && ((TokenNode)child).getToken().getType() == TokenType.LBRACK) {
                dim++;
            }
        }
        return dim;
    }

}
