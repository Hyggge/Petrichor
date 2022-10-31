package llvm_ir.instr;

import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.PointerType;

public class LoadInstr extends Instr {
    private Value pointer;

    public LoadInstr(String name, Value pointer) {
        super(((PointerType)pointer.getType()).getTargetType(), name, InstrType.LOAD);
        this.pointer = pointer;
        addOperands(pointer);
    }

    @Override
    public String toString() {
        return name + " = load " + type + ", " + pointer.getType() + " " + pointer.getName();
    }
}
