package llvm_ir.instr;

import back_end.mips.MipsBuilder;
import back_end.mips.Register;
import back_end.mips.assembly.LaAsm;
import back_end.mips.assembly.MemAsm;
import llvm_ir.GlobalVar;
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

    @Override
    public void toAssembly() {
        super.toAssembly();
        if (pointer instanceof GlobalVar) {
            new LaAsm(Register.T0, pointer.getName().substring(1));
        } else {
            new MemAsm(MemAsm.Op.LW, Register.T0, Register.SP, MipsBuilder.getInstance().getOffsetOf(pointer));
        }
        // 获得address的值, 保存到t0中
        // 取得“t0中保存的地址”所存储的数值，保存到t1中
        new MemAsm(MemAsm.Op.LW, Register.T1, Register.T0, 0);
        // 为Value新开一个栈空间，把t1的值保存在堆栈上
        int curOffset = MipsBuilder.getInstance().getCurOffset();
        MipsBuilder.getInstance().addValueOffsetMap(this, curOffset);
        new MemAsm(MemAsm.Op.SW, Register.T1, Register.SP, curOffset);
        MipsBuilder.getInstance().addCurOffset(4);
    }
}
