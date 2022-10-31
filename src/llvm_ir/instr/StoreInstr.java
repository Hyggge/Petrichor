package llvm_ir.instr;

import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.BaseType;

public class StoreInstr extends Instr {
    private Value from;
    private Value to;
    public StoreInstr(String name, Value from, Value to) {
        super(BaseType.VOID, name, InstrType.STORE);
        this.from = from;
        this.to = to;
        addOperands(from);
        addOperands(to);
    }

    @Override
    public String toString() {
        return "store " +
                from.getType() + " " +
                from.getName() + ", " +
                to.getType() + " " +
                to.getName();
    }
}
