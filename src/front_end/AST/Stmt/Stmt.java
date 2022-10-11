package front_end.AST.Stmt;

import front_end.AST.Node;
import utils.SyntaxVarType;

import java.util.ArrayList;

public class Stmt extends Node {
    public Stmt(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}
