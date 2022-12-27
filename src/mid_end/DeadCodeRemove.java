package mid_end;

import llvm_ir.BasicBlock;
import llvm_ir.Function;
import llvm_ir.Instr;
import llvm_ir.Module;

import java.util.Iterator;

public class DeadCodeRemove {
    private Module module;

    public DeadCodeRemove(Module module) {
        this.module = module;
    }

    public void run() {
        for (Function function : module.getFunctionList()) {
            for (BasicBlock bb : function.getBBList()) {
                Iterator<Instr> iterator = bb.getInstrList().iterator();
                while (iterator.hasNext()) {
                    Instr instr = iterator.next();
                    if (instr.canBeUsed()) {
                        if (instr.getUseList().isEmpty()) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
    }

}
