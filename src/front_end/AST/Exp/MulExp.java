package front_end.AST.Exp;

import front_end.AST.Node;
import front_end.AST.TokenNode;
import utils.SyntaxVarType;
import utils.TokenType;

import java.util.ArrayList;

// MulExp ==> UnaryExp {('*' | '/' | '%') UnaryExp}
public class MulExp extends Node {
    public MulExp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
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
        int ans = children.get(0).execute();
        for (int i = 1; i < children.size(); i++) {
            // '*'
            if (children.get(i) instanceof TokenNode && ((TokenNode)children.get(i)).getToken().getType() == TokenType.MULT) {
                ans *= children.get(++i).execute();
            }
            // '/'
            else if (children.get(i) instanceof TokenNode && ((TokenNode)children.get(i)).getToken().getType() == TokenType.DIV) {
                ans /= children.get(++i).execute();
            }
            // '%'
            else {
                ans %= children.get(++i).execute();
            }
        }
        return ans;
    }
}
