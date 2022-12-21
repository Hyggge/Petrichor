package llvm_ir.instr;

import back_end.mips.MipsBuilder;
import back_end.mips.Register;
import back_end.mips.assembly.MemAsm;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.LLVMType;

public class ZextInstr extends Instr {
    private LLVMType targetType;

    public ZextInstr(String name, Value oriValue, LLVMType targetType) {
        super(targetType, name, InstrType.ZEXT);
        this.targetType = targetType;
        this.addOperands(oriValue);
    }

    public Value getOriValue() {
        return operands.get(0);
    }

    @Override
    public boolean canBeUsed() {
        return true;
    }

    @Override
    public String toString() {
        Value oriValue = getOriValue();
        return name + " = zext " + oriValue.getType() + " " + oriValue.getName() + " to " + targetType;
    }

    @Override
    public void toAssembly() {
        super.toAssembly();
        Value oriValue = getOriValue();
        // 实际在MIPS中不需要考虑位宽，i32和i1都存储在4字节的堆栈空间中
        // 如果是i1转到i32，我们可以直接将this和“oriValue的offset“绑定
        // 那么每次我们使用this时，实际上是取的oriValue的值
        // TODO: 可以考虑把Zext变量也放到寄存器中
        if (oriValue.getType().isInt1() && targetType.isInt32()) {
            // 如果oriValue在寄存器中, 那么我们也将this映射到该寄存器中
            if (MipsBuilder.getInstance().getRegOf(oriValue) != null) {
                Register reg = MipsBuilder.getInstance().getRegOf(oriValue);
                //MipsBuilder.getInstance().allocRegForZext(this, reg);
                MipsBuilder.getInstance().subCurOffset(4);
                int curOffset = MipsBuilder.getInstance().getCurOffset();
                MipsBuilder.getInstance().addValueOffsetMap(this, curOffset);
                new MemAsm(MemAsm.Op.SW, reg, Register.SP, curOffset);
            }
            // 如果oriValue在栈中，我们只需要把this映射到对应offset即可
            else {
                MipsBuilder.getInstance().addValueOffsetMap(this, MipsBuilder.getInstance().getOffsetOf(oriValue));
            }
        }
        // TODO：如果是i32转到i1, 则不等于0时候转化为1，等于0时不用转化
        // TODO: 因为这种情况暂时不会出现，所以不实现也没问题...

    }
}
