package llvm_ir.instr;

import back_end.mips.MipsBuilder;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.LLVMType;

public class ZextInstr extends Instr {
    private Value oriValue;
    private LLVMType targetType;

    public ZextInstr(String name, Value oriValue, LLVMType targetType) {
        super(targetType, name, InstrType.ZEXT);
        this.oriValue = oriValue;
        this.targetType = targetType;
    }

    @Override
    public String toString() {
        return name + " = zext " + oriValue.getType() + " " + oriValue.getName() + " to " + targetType;
    }

    @Override
    public void toAssembly() {
        super.toAssembly();
        // 实际在MIPS中不需要考虑位宽，i32和i1都存储在4字节的堆栈空间中
        // 如果是i1转到i32，我们可以直接将this和“oriValue的offset“绑定
        // 那么每次我们使用this时，实际上是取的oriValue的值
        if (oriValue.getType().isInt1() && targetType.isInt32()) {
            MipsBuilder.getInstance().addValueOffsetMap(this, MipsBuilder.getInstance().getOffsetOf(oriValue));
        }
        // TODO：如果是i32转到i1, 则不等于0时候转化为1，等于0时不用转化
        // TODO: 因为这种情况暂时不会出现，所以不实现也没问题...

    }
}
