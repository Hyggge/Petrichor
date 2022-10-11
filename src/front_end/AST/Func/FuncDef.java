package front_end.AST.Func;

import front_end.AST.Node;
import utils.SyntaxVarType;

import java.util.ArrayList;

public class FuncDef extends Node {
    public FuncDef(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}
