package front_end.AST;

import utils.SyntaxVarType;

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

    public int getStartLine() {
        return startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public SyntaxVarType getType() {
        return type;
    }
}
