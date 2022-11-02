package llvm_ir.instr;

import back_end.mips.MipsBuilder;
import back_end.mips.Register;
import back_end.mips.assembly.AluAsm;
import back_end.mips.assembly.MemAsm;
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

    @Override
    public void toAssembly() {
        // 在栈上分配空间
        int oriOffset = MipsBuilder.getInstance().getCurOffset();
        if (targetType.isArray()) {
            MipsBuilder.getInstance().addCurOffset(targetType.getLength() * 4);
        } else {
            MipsBuilder.getInstance().addCurOffset(4);
        }
        int curOffset = MipsBuilder.getInstance().getCurOffset();
        // t0保存分配空间的首地址
        new AluAsm(AluAsm.Op.ADDI, Register.T0, Register.SP, oriOffset);
        // 再从栈上为Value开一个空间，保存刚刚新分配空间的首地址
        MipsBuilder.getInstance().addValueOffsetMap(this, curOffset);
        new MemAsm(MemAsm.Op.SW, Register.T0, Register.SP, curOffset);
        MipsBuilder.getInstance().addCurOffset(4);
    }
}
