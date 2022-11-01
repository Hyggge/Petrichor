package front_end.AST.Exp;

import front_end.AST.Node;
import llvm_ir.BasicBlock;
import llvm_ir.IRBuilder;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.instr.BranchInstr;
import utils.SyntaxVarType;

import java.util.ArrayList;

// LAndExp ==> EqExp {'&&' EqExp}
public class LAndExp extends Node {
    public LAndExp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    public void genIRForAnd(BasicBlock thenBlock, BasicBlock elseBlock) {
        for (Node child : children) {
            if (child instanceof EqExp) {
                if (children.indexOf(child) == children.size() - 1) {
                    Value cond = child.genIR();
                    Instr instr = new BranchInstr(IRBuilder.getInstance().genLocalVarName(), cond, thenBlock, elseBlock);
                }
                else {
                    // 创建一个新的block
                    BasicBlock nextBlock = new BasicBlock(IRBuilder.getInstance().genBBName());
                    // 得到判断条件
                    Value cond = child.genIR();
                    Instr instr = new BranchInstr(IRBuilder.getInstance().genLocalVarName(), cond, nextBlock, elseBlock);
                    // 将nextBlock作为curBB
                    IRBuilder.getInstance().setCurBB(nextBlock);
                }
            }
        }
    }
}
