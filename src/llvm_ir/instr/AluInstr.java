package llvm_ir.instr;

import back_end.mips.MipsBuilder;
import back_end.mips.Register;
import back_end.mips.assembly.AluAsm;
import back_end.mips.assembly.HiLoAsm;
import back_end.mips.assembly.LiAsm;
import back_end.mips.assembly.MDAsm;
import back_end.mips.assembly.MemAsm;
import llvm_ir.Constant;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.BaseType;

public class AluInstr extends Instr {
    public enum Op {
        ADD,
        SUB,
        SREM,
        MUL,
        SDIV,
        AND,
        OR
    }

    private Op op;
    private Value operand1;
    private Value operand2;

    public AluInstr(String name, Op op, Value operand1, Value operand2) {
        super(BaseType.INT32, name, InstrType.ALU);
        this.op = op;
        this.operand1 = operand1;
        this.operand2 = operand2;
        addOperands(operand1);
        addOperands(operand2);
    }

    @Override
    public String toString() {
        return name + " = " + op.toString().toLowerCase() + " i32 " + operand1.getName() + ", " + operand2.getName();
    }

    @Override
    public void toAssembly() {
        super.toAssembly();
        // TODO: 优化思路——可以利用addi等进行优化
        // 将第一个操作数的值保存到t0
        if (operand1 instanceof Constant) {
            new LiAsm(Register.T0, ((Constant)operand1).getValue());
        } else {
            int operand1Offset = MipsBuilder.getInstance().getOffsetOf(operand1);
            new MemAsm(MemAsm.Op.LW, Register.T0, Register.SP, operand1Offset);
        }
        // 将第二个操作数的值保存到t0
        if (operand2 instanceof Constant) {
            new LiAsm(Register.T1, ((Constant)operand2).getValue());
        } else {
            int operand2Offset = MipsBuilder.getInstance().getOffsetOf(operand2);
            new MemAsm(MemAsm.Op.LW, Register.T1, Register.SP, operand2Offset);
        }
        // 计算，将结果保存到t2寄存器中
        switch (op) {
            case ADD:
                new AluAsm(AluAsm.Op.ADDU, Register.T2, Register.T0, Register.T1);
                break;
            case SUB:
                new AluAsm(AluAsm.Op.SUBU, Register.T2, Register.T0, Register.T1);
                break;
            case AND:
                new AluAsm(AluAsm.Op.AND, Register.T2, Register.T0, Register.T1);
                break;
            case OR:
                new AluAsm(AluAsm.Op.OR, Register.T2, Register.T0, Register.T1);
                break;
            case MUL:
                new MDAsm(MDAsm.Op.MULT, Register.T0, Register.T1);
                new HiLoAsm(HiLoAsm.Op.MFLO, Register.T2);
                break;
            case SDIV:
                new MDAsm(MDAsm.Op.DIV, Register.T0, Register.T1);
                new HiLoAsm(HiLoAsm.Op.MFLO, Register.T2);
                break;
            case SREM:
                new MDAsm(MDAsm.Op.DIV, Register.T0, Register.T1);
                new HiLoAsm(HiLoAsm.Op.MFHI, Register.T2);
                break;
        }
        // 为Value开一个栈空间，将t2的值store到堆栈上
        MipsBuilder.getInstance().subCurOffset(4);
        int curOffset = MipsBuilder.getInstance().getCurOffset();
        MipsBuilder.getInstance().addValueOffsetMap(this, curOffset);
        new MemAsm(MemAsm.Op.SW, Register.T2, Register.SP, curOffset);
    }
}
