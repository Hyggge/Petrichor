package back_end.mips.assembly;

import back_end.mips.Register;

public class BranchAsm extends Assembly {
    public enum Op {
        BEQ,BNE,BGTZ,BLEZ,BGEZ,BLTZ
    }

    private Op op;
    private Register rs;
    private Register rt;
    private String label;

    // beq, bne
    public BranchAsm(Op op, Register rs, Register rt, String label) {
        this.op = op;
        this.rs = rs;
        this.rt = rt;
        this.label = label;
    }

    // bgtz, bgez, bltz, blez
    public BranchAsm(Op op, Register rs, String label) {
        this.op = op;
        this.rs = rs;
        this.rt = null;
        this.label = label;
    }



    @Override
    public String toString() {
        if (op.ordinal() == Op.BEQ.ordinal() || op.ordinal() == Op.BNE.ordinal()) {
            return op.toString().toLowerCase() + " " + rs + " " + rt + " " + label;
        }
        return op.toString().toLowerCase() + " " + rs + " " + label;
    }
}
