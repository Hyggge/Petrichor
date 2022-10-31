package llvm_ir.instr;

import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.Type;

public class ZextInstr extends Instr {
    private Value oriValue;
    private Type targetType;

    public ZextInstr(String name, Value oriValue, Type targetType) {
        super(targetType, name, InstrType.ZEXT);
        this.oriValue = oriValue;
        this.targetType = targetType;
    }

    @Override
    public String toString() {
        return name + " = zext " + oriValue.getType() + " " + oriValue.getName() + " to " + targetType;
    }
}
