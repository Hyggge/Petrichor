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
    // private Value pointer;

    public LoadInstr(String name, Value pointer) {
        super(((PointerType)pointer.getType()).getTargetType(), name, InstrType.LOAD);
        addOperands(pointer);
    }

    public Value getPointer() {
        return operands.get(0);
    }

    @Override
    public boolean canBeUsed() {
        return true;
    }

    @Override
    public String toString() {
        Value pointer = getPointer();
        return name + " = load " + type + ", " + pointer.getType() + " " + pointer.getName();
    }

    @Override
    public void toAssembly() {
        super.toAssembly();
        Value pointer = getPointer();
        if (pointer instanceof GlobalVar) {
            new LaAsm(Register.T0, pointer.getName().substring(1));
        } else {
            Integer offset = MipsBuilder.getInstance().getOffsetOf(pointer);
            if (offset == null) {
                MipsBuilder.getInstance().subCurOffset(4);
                offset = MipsBuilder.getInstance().getCurOffset();
                MipsBuilder.getInstance().addValueOffsetMap(pointer, offset);
            }
            new MemAsm(MemAsm.Op.LW, Register.T0, Register.SP, offset);
        }
        // 获得address的值, 保存到t0中
        // 取得“t0中保存的地址”所存储的数值，保存到t1中
        new MemAsm(MemAsm.Op.LW, Register.T1, Register.T0, 0);
        // 为Value新开一个栈空间，把t1的值保存在堆栈上
        MipsBuilder.getInstance().subCurOffset(4);
        int curOffset = MipsBuilder.getInstance().getCurOffset();
        MipsBuilder.getInstance().addValueOffsetMap(this, curOffset);
        new MemAsm(MemAsm.Op.SW, Register.T1, Register.SP, curOffset);
    }
}
