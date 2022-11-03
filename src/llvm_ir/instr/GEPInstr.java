package llvm_ir.instr;

import back_end.mips.MipsBuilder;
import back_end.mips.Register;
import back_end.mips.assembly.AluAsm;
import back_end.mips.assembly.LaAsm;
import back_end.mips.assembly.MemAsm;
import llvm_ir.Constant;
import llvm_ir.GlobalVar;
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
        // 先获得pointer中的值（也就是数组的首地址），存入t0中
        if (pointer instanceof GlobalVar) {
            new LaAsm(Register.T0, pointer.getName().substring(1));
        }
        else {
            new MemAsm(MemAsm.Op.LW, Register.T0, Register.SP, MipsBuilder.getInstance().getOffsetOf(pointer));
        }

        // 如果offset是一个常数
        if (offset instanceof Constant) {
            // 将t0和4*offset相加，得到所求地址，存到t2中
            new AluAsm(AluAsm.Op.ADDI, Register.T2, Register.T0, ((Constant)offset).getValue() * 4);
        }
        // 如果offset存在堆栈中
        else {
            // 将offset的值放入t1中
            new MemAsm(MemAsm.Op.LW, Register.T1, Register.SP, MipsBuilder.getInstance().getOffsetOf(offset));
            // 将offset向左移2位(相当与*4),然后再存入t1中
            new AluAsm(AluAsm.Op.SLL, Register.T1, Register.T1, 2);
            // 将t0和t1将加，得到所求地址，存入t2
            new AluAsm(AluAsm.Op.ADDU, Register.T2, Register.T0, Register.T1);
        }
        // 为Value申请一个栈空间，将t2存入堆栈中
        int curOffset = MipsBuilder.getInstance().getCurOffset();
        MipsBuilder.getInstance().addValueOffsetMap(this, curOffset);
        new MemAsm(MemAsm.Op.SW, Register.T2, Register.SP, curOffset);
        MipsBuilder.getInstance().addCurOffset(4);
    }
}
