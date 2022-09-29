package front_end.AST;

import front_end.parser.SyntaxVarType;

import java.util.ArrayList;

public class Node {
    private int startLine;
    private int endLine;
    private SyntaxVarType type;
    private ArrayList<Node> children;

    public Node(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.type = type;
        this.children = children;
    }
}
