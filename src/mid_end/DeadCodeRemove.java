package mid_end;

import llvm_ir.BasicBlock;
import llvm_ir.Function;
import llvm_ir.Instr;
import llvm_ir.Module;
import llvm_ir.instr.CallInstr;
import llvm_ir.instr.IOInstr;

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
                    // canBeUsed的指令有alloca，alu，call，gep，io——getint，load，phi，zext
                    // 其中call指令调用的函数将指针作为形参、修改全局变量、调用了其他函数，因此不能直接删除
                    // io中的getint指令获得的数字即使没有用到也应该完成io操作，也不能删除
                    if (instr.canBeUsed() && (! (instr instanceof CallInstr) && ! (instr instanceof IOInstr))) {
                        if (instr.getUseList().isEmpty()) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
    }

}
