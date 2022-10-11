package front_end.AST.Func;

import front_end.AST.Node;
import utils.SyntaxVarType;

import java.util.ArrayList;

public class FuncFormalParam extends Node {
    public FuncFormalParam(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }
}
