package front_end.AST.Exp;

import front_end.AST.Node;
import llvm_ir.BasicBlock;
import utils.SyntaxVarType;

import java.util.ArrayList;

// CondExp ==> LorExp
public class CondExp extends Node {
    public CondExp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }


    public void genIRForCond(BasicBlock thenBlock, BasicBlock elseBlock) {
        ((LOrExp)children.get(0)).genIRForAnd(thenBlock, elseBlock);
    }
}
