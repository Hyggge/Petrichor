package front_end.AST;

import front_end.parser.SyntaxVarType;

import java.util.ArrayList;

public class ConstDecl extends Node {
    public ConstDecl(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}
