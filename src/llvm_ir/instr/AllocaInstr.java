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
        // t0保存分配空间的首地址（实际上是最低地址）
        int curOffset = MipsBuilder.getInstance().getCurOffset();
        new AluAsm(AluAsm.Op.ADDI, Register.T0, Register.SP, curOffset);
        // 再从栈上为Value开一个空间，保存刚刚新分配空间的首地址
        MipsBuilder.getInstance().subCurOffset(4);
        curOffset = MipsBuilder.getInstance().getCurOffset();
        MipsBuilder.getInstance().addValueOffsetMap(this, curOffset);
        new MemAsm(MemAsm.Op.SW, Register.T0, Register.SP, curOffset);
    }
}
