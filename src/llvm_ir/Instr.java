package llvm_ir;

import llvm_ir.type.Type;

public class Instr extends User{
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

    public Instr(Type type, String name, InstrType instrType, BasicBlock parentBB) {
        super(type, name);
        this.instrType = instrType;
        this.parentBB = parentBB;
    }

    public void setParentBB(BasicBlock parentBB) {
        this.parentBB = parentBB;
    }
}
