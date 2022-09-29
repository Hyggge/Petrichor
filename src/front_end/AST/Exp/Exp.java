package front_end.AST.Exp;

import front_end.AST.Node;
import front_end.parser.SyntaxVarType;

import java.util.ArrayList;

public class Exp extends Node {
    public Exp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}