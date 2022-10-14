package front_end.AST.Var;

import front_end.AST.Node;
import utils.SyntaxVarType;
import java.util.ArrayList;

// VarDecl   ==> 'int' VarDef {',' VarDef} ';'
public class VarDecl extends Node {
    public VarDecl(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}
