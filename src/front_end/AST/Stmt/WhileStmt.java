package front_end.AST.Stmt;

import front_end.AST.Exp.CondExp;
import front_end.AST.Node;
import front_end.symbol.SymbolManager;
import llvm_ir.BasicBlock;
import llvm_ir.IRBuilder;
import llvm_ir.Instr;
import llvm_ir.Loop;
import llvm_ir.Value;
import llvm_ir.instr.JumpInstr;
import utils.SyntaxVarType;

import java.util.ArrayList;

// WhileStmt ==> 'while' '(' Cond ')' Stmt
public class WhileStmt extends Stmt {
    public WhileStmt(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public void checkError() {
        SymbolManager.getInstance().enterLoop();
        super.checkError();
        SymbolManager.getInstance().leaveLoop();
    }

    @Override
    public Value genIR() {
        SymbolManager.getInstance().enterLoop();

        Instr instr = null;
        // 为condition单独建立一个BB
        BasicBlock condBlock = new BasicBlock(IRBuilder.getInstance().genBBName());
        // 生成loopBodyBlock
        BasicBlock loopBodyBlock = new BasicBlock(IRBuilder.getInstance().genBBName());
        // 生成followBlock
        BasicBlock followBlock = new BasicBlock(IRBuilder.getInstance().genBBName());
        // 将Loop压入栈
        IRBuilder.getInstance().pushLoop(new Loop(condBlock, loopBodyBlock, followBlock));
        // 生成跳转到condBlock的指令
        instr = new JumpInstr(IRBuilder.getInstance().genLocalVarName(), condBlock);
        // 生成和cond相关的IR
        IRBuilder.getInstance().setCurBB(condBlock);
        ((CondExp)children.get(2)).genIRForCond(loopBodyBlock, followBlock);
        // 解析loop body中的内容
        IRBuilder.getInstance().setCurBB(loopBodyBlock);
        children.get(4).genIR();
        instr = new JumpInstr(IRBuilder.getInstance().genLocalVarName(), condBlock);
        // Loop退出栈
        IRBuilder.getInstance().popLoop();
        // 将followBlock设置为curBlock
        IRBuilder.getInstance().setCurBB(followBlock);

        SymbolManager.getInstance().leaveLoop();
        return null;
    }
}
