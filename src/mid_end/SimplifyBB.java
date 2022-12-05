package mid_end;

import llvm_ir.BasicBlock;
import llvm_ir.Function;
import llvm_ir.Instr;
import llvm_ir.Module;
import llvm_ir.instr.BranchInstr;
import llvm_ir.instr.JumpInstr;
import llvm_ir.instr.ReturnInstr;

import java.util.HashSet;
import java.util.Iterator;

public class SimplifyBB {
    private Module module;

    public SimplifyBB(Module module) {
        this.module = module;
    }

    public void run() {
        for (Function function : module.getFunctionList()) {
            for (BasicBlock basicBlock : function.getBBList()) {
                deleteDupBr(basicBlock);
            }
        }

        for (Function function : module.getFunctionList()) {
            deleteDeadBB(function);
        }
    }

    private void deleteDupBr(BasicBlock basicBlock) {
        boolean canRemove = false;
        Iterator<Instr> iterator = basicBlock.getInstrList().iterator();
        while (iterator.hasNext()) {
            Instr instr = iterator.next();
            if (canRemove) {
                iterator.remove();
            }
            else if (instr instanceof BranchInstr || instr instanceof JumpInstr || instr instanceof ReturnInstr){
                canRemove = true;
            }

        }
    }
    
    // 删除永远不可能进入的BB
    private void deleteDeadBB(Function function) {
        BasicBlock entry = function.getBBList().get(0);
        HashSet<BasicBlock> vis = new HashSet<>();
        dfs(entry, vis);
        function.getBBList().removeIf(bb -> !vis.contains(bb));
    }

    private void dfs(BasicBlock entry, HashSet<BasicBlock>vis) {
        vis.add(entry);
        Instr instr = entry.getLastInstr();
        if (instr instanceof BranchInstr) {
            BasicBlock thenBlock = ((BranchInstr) instr).getThenBlock();
            BasicBlock elseBlock = ((BranchInstr) instr).getElseBlock();
            if (! vis.contains(thenBlock)) dfs(thenBlock, vis);
            if (! vis.contains(elseBlock)) dfs(elseBlock, vis);
        }
        else if (instr instanceof JumpInstr) {
            BasicBlock targetBB = ((JumpInstr) instr).getTargetBB();
            dfs(targetBB, vis);
        }
    
    }

}
