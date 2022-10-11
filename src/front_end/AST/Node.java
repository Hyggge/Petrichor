package front_end.AST;

import utils.SyntaxVarType;

import java.util.ArrayList;

public class Node {
    protected int startLine;
    protected int endLine;
    protected SyntaxVarType type;
    protected ArrayList<Node> children;

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

    public void checkError() {
        // for token nodes
        if (children == null) return;
        // for other nodes
        for (Node child : children) {
            child.checkError();
        }
    }


}
