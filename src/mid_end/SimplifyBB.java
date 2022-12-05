package mid_end;

import llvm_ir.BasicBlock;
import llvm_ir.Function;
import llvm_ir.Instr;
import llvm_ir.Module;
import llvm_ir.instr.BranchInstr;
import llvm_ir.instr.JumpInstr;
import llvm_ir.instr.ReturnInstr;

import java.util.Iterator;

public class SimplifyBB {
    private Module module;

    public SimplifyBB(Module module) {
        this.module = module;
    }

    public void run() {
        for (Function function : module.getFunctionList()) {
            for (BasicBlock basicBlock : function.getBBList()) {
                simplify(basicBlock);
            }
        }
    }

    private void simplify(BasicBlock basicBlock) {
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

}
