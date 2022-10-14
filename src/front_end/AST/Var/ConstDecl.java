package front_end.AST.Var;

import front_end.AST.Node;
import utils.SyntaxVarType;

import java.util.ArrayList;

// ConstDecl ==> 'const' 'int' ConstDef { ',' ConstDef } ';'
public class ConstDecl extends Node {
    public ConstDecl(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}
