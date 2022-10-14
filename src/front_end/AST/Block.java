package front_end.AST;

import utils.SyntaxVarType;

import java.util.ArrayList;

// Block ==> '{' {VarDecl | ConstDecl | Stmt} '}'
public class Block extends Node{
    public Block(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

}
