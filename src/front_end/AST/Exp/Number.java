package front_end.AST.Exp;

import front_end.AST.Node;
import front_end.AST.TokenNode;
import llvm_ir.Constant;
import llvm_ir.Value;
import utils.SyntaxVarType;

import java.util.ArrayList;

// Number ==> IntConst
public class Number extends Node {
    public Number(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public Integer getDim() {
        return 0;
    }

    @Override
    public int execute() {
        return Integer.parseInt(((TokenNode)children.get(0)).getToken().getValue());
    }

    @Override
    public Value genIR() {
        String value = ((TokenNode) children.get(0)).getToken().getValue();
        return new Constant(Integer.parseInt(value));
    }

    // checkError d

}
