package back_end.mips.assembly;

import back_end.mips.Register;

public class HiLoAsm extends Assembly {
    public enum Op {
        MFHI, MFLO, MTHI, MTLO
    }

    private Op op;
    private Register rd;

    public HiLoAsm(Op op, Register rd) {
        this.op = op;
        this.rd = rd;
    }

    @Override
    public String toString() {
        return op.toString().toLowerCase() + " " + rd;
    }
}
