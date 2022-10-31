package llvm_ir.instr;

import llvm_ir.Instr;
import llvm_ir.type.PointerType;
import llvm_ir.type.Type;

public class AllocaInstr extends Instr {
    private Type targetType;

    public AllocaInstr(String name, Type targetType) {
        super(new PointerType(targetType), name, InstrType.ALLOCA);
        this.targetType = targetType;
    }

    @Override
    public String toString() {
        return name + " = alloca " + targetType;
    }
}
