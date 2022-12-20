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
import llvm_ir.UndefinedValue;
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
    // private Value operand1;
    // private Value operand2;

    public AluInstr(String name, Op op, Value operand1, Value operand2) {
        super(BaseType.INT32, name, InstrType.ALU);
        this.op = op;
        addOperands(operand1);
        addOperands(operand2);
    }

    public Op getOp() {
        return op;
    }

    public Value getOperand1() {
        return operands.get(0);
    }

    public Value getOperand2() {
        return operands.get(1);
    }

    public String getGVNHash() {
        String operand1 = getOperand1().getName();
        String operand2 = getOperand2().getName();
        if (op == Op.ADD || op == Op.MUL) {
            if (operand1.compareTo(operand2) < 0) {
                return operand1 + " " + op + " " + operand2;
            } else {
                return operand2 + " " + op+ " " + operand1;
            }
        } else {
            return operand1 + " " + op + " " + operand2;
        }
    }

    @Override
    public boolean canBeUsed() {
        return true;
    }

    @Override
    public String toString() {
        Value operand1 = getOperand1();
        Value operand2 = getOperand2();
        return name + " = " + op.toString().toLowerCase() + " i32 " + operand1.getName() + ", " + operand2.getName();
    }

    @Override
    public void toAssembly() {
        super.toAssembly();
        Value operand1 = getOperand1();
        Value operand2 = getOperand2();
        Register reg1 = Register.K0; // reg1表示operand1的值存放的寄存器
        Register reg2 = Register.K1; // reg2表示operand2的值存放的寄存器
        Register tarReg = MipsBuilder.getInstance().getRegOf(this); // tarReg是alu的结果保存的寄存器
        if (tarReg == null) tarReg = Register.K0;

        // TODO: 优化思路——可以利用addi等进行优化
        // 将第一个操作数的值保存到reg1
        if (operand1 instanceof Constant || operand1 instanceof UndefinedValue) {
            new LiAsm(reg1, Integer.parseInt(operand1.getName()));
        } 
        else if (MipsBuilder.getInstance().getRegOf(operand1) != null) {
            reg1 = MipsBuilder.getInstance().getRegOf(operand1);
        }
        else {
            Integer operand1Offset = MipsBuilder.getInstance().getOffsetOf(operand1);
            if (operand1Offset == null) {
                MipsBuilder.getInstance().subCurOffset(4);
                operand1Offset = MipsBuilder.getInstance().getCurOffset();
                MipsBuilder.getInstance().addValueOffsetMap(operand1, operand1Offset);
            }
            new MemAsm(MemAsm.Op.LW, reg1, Register.SP, operand1Offset);
        }
        // 将第二个操作数的值保存到reg2
        if (operand2 instanceof Constant || operand2 instanceof UndefinedValue) {
            new LiAsm(reg2, Integer.parseInt(operand2.getName()));
        }
        else if (MipsBuilder.getInstance().getRegOf(operand2) != null) {
            reg2 = MipsBuilder.getInstance().getRegOf(operand2);
        }
        else {
            Integer operand2Offset = MipsBuilder.getInstance().getOffsetOf(operand2);
            if (operand2Offset == null) {
                MipsBuilder.getInstance().subCurOffset(4);
                operand2Offset = MipsBuilder.getInstance().getCurOffset();
                MipsBuilder.getInstance().addValueOffsetMap(operand2, operand2Offset);
            }
            new MemAsm(MemAsm.Op.LW, reg2, Register.SP, operand2Offset);
        }
        // 计算，将结果保存到t2寄存器中
        switch (op) {
            case ADD:
                new AluAsm(AluAsm.Op.ADDU, tarReg, reg1, reg2);
                break;
            case SUB:
                new AluAsm(AluAsm.Op.SUBU, tarReg, reg1, reg2);
                break;
            case AND:
                new AluAsm(AluAsm.Op.AND, tarReg, reg1, reg2);
                break;
            case OR:
                new AluAsm(AluAsm.Op.OR, tarReg, reg1, reg2);
                break;
            case MUL:
                new MDAsm(MDAsm.Op.MULT, reg1, reg2);
                new HiLoAsm(HiLoAsm.Op.MFLO, tarReg);
                break;
            case SDIV:
                new MDAsm(MDAsm.Op.DIV, reg1, reg2);
                new HiLoAsm(HiLoAsm.Op.MFLO, tarReg);
                break;
            case SREM:
                new MDAsm(MDAsm.Op.DIV, reg1, reg2);
                new HiLoAsm(HiLoAsm.Op.MFHI, tarReg);
                break;
        }
        // 如果没有为this分配寄存器，应该开一个栈空间，将tarReg的值store到堆栈上
        if (MipsBuilder.getInstance().getRegOf(this) == null) {
            MipsBuilder.getInstance().subCurOffset(4);
            int curOffset = MipsBuilder.getInstance().getCurOffset();
            MipsBuilder.getInstance().addValueOffsetMap(this, curOffset);
            new MemAsm(MemAsm.Op.SW, tarReg, Register.SP, curOffset);
        }
    }
}
