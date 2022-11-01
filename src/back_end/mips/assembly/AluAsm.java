package back_end.mips.assembly;

import back_end.mips.Register;

public class AluAsm extends Assembly {

    public enum Op {
        // calc_R
        ADD, SUB, ADDU, SUBU, AND, OR, NOR, XOR, SLT, SLTU,
        // calc_I
        ADDI, ADDIU, ANDI, ORI, XORI, SLTI, SLTIU,
        // shift
        SLL, SRA, SRL,
        // shiftv
        SLLV, SRAV, SRLV
    }

    private Op op;
    private Register rd;
    private Register rs;
    private Register rt;
    private Integer number;

    // calc_R、shift
    public AluAsm(Op op, Register rd, Register rs, Register rt) {
        this.op = op;
        this.rd = rd;
        this.rs = rs;
        this.rt = rt;
        this.number = null;
    }

    // calc_I、shiftv
    public AluAsm(Op op, Register rd, Register rs, int number) {
        this.op = op;
        this.rd = rd;
        this.rs = rs;
        this.rt = null;
        this.number = number;
    }

    @Override
    public String toString() {
        if (op.ordinal() >= Op.ADD.ordinal() && op.ordinal() <= Op.SLTU.ordinal() ||
            op.ordinal() >= Op.SLL.ordinal() && op.ordinal() <= Op.SRL.ordinal()) {
            return op.toString().toLowerCase() + " " + rd + " " + rs + " " + rt;
        }
        return op.toString().toLowerCase() + " " + rd + " " + rs + " " + number;
    }
}
