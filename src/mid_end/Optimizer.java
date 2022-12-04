package mid_end;

import llvm_ir.Module;

public class Optimizer {
    private static final Optimizer optimizer = new Optimizer();

    public static Optimizer getInstance() {
        return optimizer;
    }

    public void run(Module module) {
        new CFGBuilder(module).run();
        new Mem2Reg(module).run();
    }



}
