package back_end.mips.assembly;

import back_end.mips.Register;

public class MemAsm extends Assembly {
    public enum Op {
        // load
        LW, LH, LHU, LB, LBU,
        // store
        SW, SH, SB
    }

    private Op op;
    private Register rd;
    private Register base;
    private Integer offset;
    private String label;

    public MemAsm(Op op, Register rd, Register base, Integer offset) {
        this.op = op;
        this.rd = rd;
        this.base = base;
        this.offset = offset;
        this.label = null;
    }

    public MemAsm(Op op, Register rd, String label, Integer offset) {
        this.op = op;
        this.rd = rd;
        this.base = null;
        this.offset = offset;
        this.label = label;
    }

    @Override
    public String toString() {
        if (label == null) {
            return op.toString().toLowerCase() + " " + rd + " " + offset + "(" + base + ")";
        }
        return op.toString().toLowerCase() + " " + rd + " " + label + "+" + offset;
    }
}
