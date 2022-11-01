package back_end.mips.assembly;

import back_end.mips.Register;

public class JumpAsm extends Assembly {
    public enum Op {
        J, JAL, JR
    }

    private Op op;
    private String target;
    private Register rd;

    public JumpAsm(Op op, String target) {
        this.op = op;
        this.target = target;
        this.rd = null;
    }


    public JumpAsm(Op op, Register rd) {
        this.op = op;
        this.target = null;
        this.rd = rd;
    }

    @Override
    public String toString() {
        if (op.ordinal() == Op.JR.ordinal()) {
            return op.toString().toLowerCase() + " " + rd;
        }
        return op.toString().toLowerCase() + " " + target;
    }
}
