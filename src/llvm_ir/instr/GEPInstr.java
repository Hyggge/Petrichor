package llvm_ir.instr;

import back_end.mips.MipsBuilder;
import back_end.mips.Register;
import back_end.mips.assembly.AluAsm;
import back_end.mips.assembly.LaAsm;
import back_end.mips.assembly.MemAsm;
import llvm_ir.Constant;
import llvm_ir.GlobalVar;
import llvm_ir.Instr;
import llvm_ir.UndefinedValue;
import llvm_ir.Value;
import llvm_ir.type.BaseType;
import llvm_ir.type.PointerType;
import llvm_ir.type.LLVMType;

public class GEPInstr extends Instr {
//    private Value pointer;
//    private Value offset;

    public GEPInstr(String name, Value pointer, Value offset) {
        super(new PointerType(BaseType.INT32), name, InstrType.GEP);
        addOperands(pointer);
        addOperands(offset);
    }

    public Value getPointer() {
        return operands.get(0);
    }

    public Value getOffset() {
        return operands.get(1);
    }

    public String getGVNHash() {
        return getPointer().getName() + " " + getOffset().getName();
    }

    @Override
    public boolean canBeUsed() {
        return true;
    }

    @Override
    public String toString() {
        Value pointer = getPointer();
        Value offset = getOffset();
        PointerType pointerType = (PointerType) pointer.getType();
        LLVMType targetType = pointerType.getTargetType();
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
        super.toAssembly();
        Value pointer = getPointer();
        Value offset = getOffset();
        Register pointerReg = Register.K0;
        Register offsetReg = Register.K1;
        Register tarReg = MipsBuilder.getInstance().getRegOf(this);
        if (tarReg == null) tarReg = Register.K0;

        // 先获得pointer中的值（也就是数组的首地址），存入pointerReg中
        if (pointer instanceof GlobalVar) {
            new LaAsm(pointerReg, pointer.getName().substring(1));
        }
        else if (MipsBuilder.getInstance().getRegOf(pointer) != null) {
            pointerReg = MipsBuilder.getInstance().getRegOf(pointer);
        }
        else {
            new MemAsm(MemAsm.Op.LW, pointerReg, Register.SP, MipsBuilder.getInstance().getOffsetOf(pointer));
        }

        // 如果offset是一个常数
        if (offset instanceof Constant || offset instanceof UndefinedValue) {
            // 将pointerReg和4*offset相加，得到所求地址，存到tarReg中
            new AluAsm(AluAsm.Op.ADDI, tarReg, pointerReg, Integer.parseInt(offset.getName()) * 4);
        }
        else {
            // 如果offset在寄存器中
            if (MipsBuilder.getInstance().getRegOf(offset) != null) {
                offsetReg = MipsBuilder.getInstance().getRegOf(offset);
            }
            // 如果offset在堆栈中
            else {
                Integer offsetOffset = MipsBuilder.getInstance().getOffsetOf(offset);
                if (offsetOffset == null) {
                    MipsBuilder.getInstance().subCurOffset(4);
                    offsetOffset = MipsBuilder.getInstance().getCurOffset();
                    MipsBuilder.getInstance().addValueOffsetMap(offset, offsetOffset);
                }
                // 将offset的值放入t1中
                new MemAsm(MemAsm.Op.LW, offsetReg, Register.SP, offsetOffset);
            }
            // 将offset向左移2位(相当与*4),然后再存入K0中(这里我们不能写成offsetReg或者tarReg，因为offsetReg和tarReg都有可能指向t*或者s*寄存器)
            new AluAsm(AluAsm.Op.SLL, Register.K1, offsetReg, 2);
            // 将tarReg和pointerReg相加，得到所求地址，存入tarReg
            new AluAsm(AluAsm.Op.ADDU, tarReg, Register.K1, pointerReg);
        }

        // 如果this不在寄存器中，那么我们需要为Value申请一个栈空间，将tarReg存入堆栈中
        if (MipsBuilder.getInstance().getRegOf(this) == null) {
            MipsBuilder.getInstance().subCurOffset(4);
            int curOffset = MipsBuilder.getInstance().getCurOffset();
            MipsBuilder.getInstance().addValueOffsetMap(this, curOffset);
            new MemAsm(MemAsm.Op.SW, tarReg, Register.SP, curOffset);
        }
    }
}
