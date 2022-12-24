package llvm_ir.instr;

import back_end.mips.MipsBuilder;
import back_end.mips.Register;
import back_end.mips.assembly.AluAsm;
import back_end.mips.assembly.MemAsm;
import llvm_ir.Instr;
import llvm_ir.type.PointerType;
import llvm_ir.type.LLVMType;

public class AllocaInstr extends Instr {
    private LLVMType targetType;

    public AllocaInstr(String name, LLVMType targetType) {
        super(new PointerType(targetType), name, InstrType.ALLOCA);
        this.targetType = targetType;
    }

    @Override
    public boolean canBeUsed() {
        return true;
    }

    @Override
    public String toString() {
        return name + " = alloca " + targetType;
    }

    @Override
    public void toAssembly() {
        super.toAssembly();
        // 在栈上分配空间
        if (targetType.isArray()) {
            MipsBuilder.getInstance().subCurOffset(targetType.getLength() * 4);
        } else {
            MipsBuilder.getInstance().subCurOffset(4);
        }
        // 如果alloca出来的指针有对应的寄存器，那么我们直接讲地址赋值给这个寄存器即可
        if (MipsBuilder.getInstance().getRegOf(this) != null) {
            Register pointerReg = MipsBuilder.getInstance().getRegOf(this);
            int curOffset = MipsBuilder.getInstance().getCurOffset();
            new AluAsm(AluAsm.Op.ADDI, pointerReg, Register.SP, curOffset);
        }
        // 反之，我们只能将这个指针值存入桟中（紧跟着前面分配的空间）
        else {
            // k0保存分配空间的首地址（实际上是最低地址）
            int curOffset = MipsBuilder.getInstance().getCurOffset();
            new AluAsm(AluAsm.Op.ADDI, Register.K0, Register.SP, curOffset);
            // 再从栈上为this开一个空间，保存刚刚新分配空间的首地址
            MipsBuilder.getInstance().subCurOffset(4);
            curOffset = MipsBuilder.getInstance().getCurOffset();
            MipsBuilder.getInstance().addValueOffsetMap(this, curOffset);
            new MemAsm(MemAsm.Op.SW, Register.K0, Register.SP, curOffset);
        }

    }
}
