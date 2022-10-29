package llvm_ir;

public class Instruction extends User{
    public static enum InstrType {
        Alu,
        Alloca,
        Branch,
        Call,
        GEP,
        Icmp,
        Jump,
        Load,
        Phi,
        Return,
        Store,
        Zext
    }

    private InstrType instrType;
    private BasicBlock parentBB;

}
