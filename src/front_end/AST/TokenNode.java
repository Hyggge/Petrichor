package front_end.AST;

import front_end.lexer.Token;
import front_end.parser.SyntaxVarType;
import java.util.ArrayList;

public class TokenNode extends Node{
    private Token token;

    public TokenNode(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children, Token token) {
        super(startLine, endLine, type, children);
        this.token = token;
    }
}