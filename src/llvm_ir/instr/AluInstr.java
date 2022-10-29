package llvm_ir.instr;

public class AluInstr {
    public enum Op {
        ADD,
        SUB,
        MOD,
        MUL,
        SDIV,
        AND,
        OR
    }

    private Op op;
}
