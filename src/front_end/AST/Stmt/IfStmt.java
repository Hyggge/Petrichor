package front_end.AST.Stmt;

import front_end.AST.Exp.CondExp;
import front_end.AST.Node;
import llvm_ir.BasicBlock;
import llvm_ir.IRBuilder;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.instr.JumpInstr;
import utils.SyntaxVarType;

import java.util.ArrayList;

// IfStmt ==> 'if' '(' Cond ')' Stmt [ 'else' Stmt ]
public class IfStmt extends Stmt {
    public IfStmt(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public Value genIR() {
        boolean hashElse = children.size() > 5;
        Instr instr = null;
        // 生成thenBlock
        BasicBlock thenBlock = new BasicBlock(IRBuilder.getInstance().genBBName());
        // 如果有elseBlock
        if (hashElse) {
            BasicBlock elseBlock = new BasicBlock(IRBuilder.getInstance().genBBName());
            // 生成followBlock
            BasicBlock followBlock = new BasicBlock(IRBuilder.getInstance().genBBName());
            // 生成和cond相关的LR
            ((CondExp)children.get(2)).genIRForCond(thenBlock, elseBlock);
            // 解析then里的内容，最后跳转到followBlock
            IRBuilder.getInstance().setCurBB(thenBlock);
            children.get(4).genIR();
            instr = new JumpInstr(IRBuilder.getInstance().genLocalVarName(), followBlock);
            // 解析else里的内容，最后跳转到followBlock
            IRBuilder.getInstance().setCurBB(elseBlock);
            children.get(6).genIR();
            instr = new JumpInstr(IRBuilder.getInstance().genLocalVarName(), followBlock);
            // 将followBlock设置为curBlock
            IRBuilder.getInstance().setCurBB(followBlock);
        }
        // 如果没有elseBlock
        else {
            // 生成followBlock
            BasicBlock followBlock = new BasicBlock(IRBuilder.getInstance().genBBName());
            // 生成和cond相关的LR
            ((CondExp)children.get(2)).genIRForCond(thenBlock, followBlock);
            // 解析then里的内容，最后跳转到followBlock
            IRBuilder.getInstance().setCurBB(thenBlock);
            children.get(4).genIR();
            instr = new JumpInstr(IRBuilder.getInstance().genLocalVarName(), followBlock);
            // 将followBlock设置为curBlock
            IRBuilder.getInstance().setCurBB(followBlock);
        }
        return null;
    }
}
