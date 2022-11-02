package llvm_ir.instr;

import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.BaseType;
import llvm_ir.type.PointerType;
import llvm_ir.type.Type;

public class GEPInstr extends Instr {
    private Value pointer;
    private Value offset;

    public GEPInstr(String name, Value pointer, Value offset) {
        super(new PointerType(BaseType.INT32), name, InstrType.GEP);
        this.pointer = pointer;
        this.offset = offset;
        addOperands(pointer);
        addOperands(offset);
    }


    @Override
    public String toString() {
        PointerType pointerType = (PointerType) pointer.getType();
        Type targetType = pointerType.getTargetType();
        // 如果传入的是[n * i32]*
        if (targetType.isArray()) {
            return name + " = getelementptr inbounds " +
                    targetType + ", " +
                    pointerType + " " +
                    pointer.getName() + ", i32 0, i32 " +
                    offset.getName();
        }
        // 如果传入的是i32*
        else {
            return name + " = getelementptr inbounds " +
                    targetType + ", " +
                    pointerType + " " +
                    pointer.getName() + ", i32 " +
                    offset.getName();
        }
    }

    @Override
    public void toAssembly() {
    }
}
