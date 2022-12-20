package llvm_ir.instr;

import back_end.mips.MipsBuilder;
import back_end.mips.Register;
import back_end.mips.assembly.LaAsm;
import back_end.mips.assembly.MemAsm;
import llvm_ir.GlobalVar;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.PointerType;

public class  LoadInstr extends Instr {
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
        Register pointerReg = Register.K0;
        Register tarReg = MipsBuilder.getInstance().getRegOf(this); // tarReg是load的结果保存的寄存器
        if (tarReg == null) tarReg = Register.K0;

        if (pointer instanceof GlobalVar) {
            new LaAsm(pointerReg, pointer.getName().substring(1));
        }
        else if (MipsBuilder.getInstance().getRegOf(pointer) != null) {
            pointerReg = MipsBuilder.getInstance().getRegOf(pointer);
        }
        else {
            Integer offset = MipsBuilder.getInstance().getOffsetOf(pointer);
            if (offset == null) {
                MipsBuilder.getInstance().subCurOffset(4);
                offset = MipsBuilder.getInstance().getCurOffset();
                MipsBuilder.getInstance().addValueOffsetMap(pointer, offset);
            }
            new MemAsm(MemAsm.Op.LW, pointerReg, Register.SP, offset);
        }
        // 取得“pointerReg中保存的地址”所存储的数值，保存到tarReg中
        new MemAsm(MemAsm.Op.LW, tarReg, pointerReg, 0);
        // 如果没有为this分配寄存器，应该开一个栈空间，将tarReg的值store到堆栈上
        if (MipsBuilder.getInstance().getRegOf(this) == null) {
            MipsBuilder.getInstance().subCurOffset(4);
            int curOffset = MipsBuilder.getInstance().getCurOffset();
            MipsBuilder.getInstance().addValueOffsetMap(this, curOffset);
            new MemAsm(MemAsm.Op.SW, tarReg, Register.SP, curOffset);
        }
    }
}
