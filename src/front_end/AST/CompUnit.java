package front_end.AST;

import utils.SyntaxVarType;

import java.util.ArrayList;

public class CompUnit extends Node{
    public CompUnit(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}
