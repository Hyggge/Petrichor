package llvm_ir.instr;

public class IcmpInstr {
    public enum Op {
        EQ,
        NEQ,
        UGT,
        UGE,
        ULT,
        ULE,
        SGT,
        SGE,
        SLT,
        SLE
    }
    private Op op;
}
