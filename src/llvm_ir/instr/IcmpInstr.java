package llvm_ir.instr;

import back_end.mips.MipsBuilder;
import back_end.mips.Register;
import back_end.mips.assembly.CmpAsm;
import back_end.mips.assembly.LiAsm;
import back_end.mips.assembly.MemAsm;
import llvm_ir.Constant;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.BaseType;

public class IcmpInstr extends Instr {
    public enum Op {
        EQ,
        NE,
        SGT,
        SGE,
        SLT,
        SLE
    }
    private Op op;
    private Value operand1;
    private Value operand2;


    public IcmpInstr(String name, Op op, Value operand1, Value operand2) {
        super(BaseType.INT1, name, InstrType.ICMP);
        this.op = op;
        this.operand1 = operand1;
        this.operand2 = operand2;
        addOperands(operand1);
        addOperands(operand2);
    }

    @Override
    public String toString() {
        return name + " = icmp " + op.toString().toLowerCase() + " i32 " + operand1.getName() + ", " + operand2.getName();
    }

    @Override
    public void toAssembly() {
        super.toAssembly();
        // 对于>和<，尽量使用slt和sgt，因为这两个是基础指令
        // 对于>=和<=, 暂时采用sle和sge, 这两个指令都会翻译成三个基础指令
        // 对于==和!=，暂时采用seq和sne，seq会翻译成三条基础指令，slt会翻译成两条基础指令
        // 即使是和数字比较，我们也将数字存到寄存器中再比较，类似于ALUAsm，TODO：后期可以考虑常数优化

        // 获得operand1的值，存到t0中
        if (operand1 instanceof Constant) {
            new LiAsm(Register.T0, ((Constant)operand1).getValue());
        } else {
            new MemAsm(MemAsm.Op.LW, Register.T0, Register.SP, MipsBuilder.getInstance().getOffsetOf(operand1));
        }
        // 获得operand2的值，存到t1中
        if (operand2 instanceof Constant) {
            new LiAsm(Register.T1, ((Constant)operand2).getValue());
        } else {
            new MemAsm(MemAsm.Op.LW, Register.T1, Register.SP, MipsBuilder.getInstance().getOffsetOf(operand2));
        }
        // 根据op进行比较，结果存储在t2中
        switch (op) {
            case EQ: new CmpAsm(CmpAsm.Op.SEQ, Register.T2, Register.T0, Register.T1); break;
            case NE: new CmpAsm(CmpAsm.Op.SNE, Register.T2, Register.T0, Register.T1); break;
            case SGT: new CmpAsm(CmpAsm.Op.SGT, Register.T2, Register.T0, Register.T1); break;
            case SGE: new CmpAsm(CmpAsm.Op.SGE, Register.T2, Register.T0, Register.T1); break;
            case SLT: new CmpAsm(CmpAsm.Op.SLT, Register.T2, Register.T0, Register.T1); break;
            case SLE: new CmpAsm(CmpAsm.Op.SLE, Register.T2, Register.T0, Register.T1); break;
        }
        // 为Value开一个栈空间，将t2的值store到堆栈上
        int curOffset = MipsBuilder.getInstance().getCurOffset();
        MipsBuilder.getInstance().addValueOffsetMap(this, curOffset);
        new MemAsm(MemAsm.Op.SW, Register.T2, Register.SP, curOffset);
        MipsBuilder.getInstance().addCurOffset(4);
    }
}
