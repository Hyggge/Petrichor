package front_end.AST.Exp;

import front_end.AST.Node;
import llvm_ir.BasicBlock;
import llvm_ir.IRBuilder;
import utils.SyntaxVarType;

import java.util.ArrayList;

// LOrExp ==>  LAndExp {'||' LAndExp}
public class LOrExp extends Node {
    public LOrExp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    public void genIRForOr(BasicBlock thenBlock, BasicBlock elseBlock) {
        for (Node child : children) {
            if (child instanceof LAndExp) {
                if (children.indexOf(child) == children.size() - 1) {
                    ((LAndExp)child).genIRForAnd(thenBlock, elseBlock);
                }
                else {
                    // 创建一个新的block
                    BasicBlock nextBlock = new BasicBlock(IRBuilder.getInstance().genBBName());
                    // 得到判断条件
                    ((LAndExp)child).genIRForAnd(thenBlock, nextBlock);
                    // 将nextBlock作为curBB
                    IRBuilder.getInstance().setCurBB(nextBlock);
                }
            }
        }
    }

}
