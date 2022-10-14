package front_end.AST.Func;

import front_end.AST.Node;
import front_end.AST.TokenNode;
import utils.SyntaxVarType;
import utils.TokenType;
import utils.ValueType;

import java.util.ArrayList;

// FuncType ==> 'void' | 'int'
public class FuncType extends Node {

    public FuncType(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    public ValueType getReturnType() {
        TokenType tokenType = ((TokenNode)children.get(0)).getToken().getType();
        if (tokenType == TokenType.INTTK) return ValueType.INT;
        else if (tokenType == TokenType.VOIDTK) return ValueType.VOID;
        return null;
    }

}
