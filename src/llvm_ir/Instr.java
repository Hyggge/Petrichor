package llvm_ir;

import llvm_ir.type.Type;

public class Instr extends User{
    public static enum InstrType {
        ALU,
        ALLOCA,
        BRANCH,
        CALL,
        GEP,
        ICMP,
        JUMP,
        LOAD,
        PHI,
        RETURN,
        STORE,
        ZEXT,
        IO
    }

    private InstrType instrType;
    private BasicBlock parentBB;

    public Instr(Type type, String name, InstrType instrType) {
        super(type, name);
        this.instrType = instrType;
        this.parentBB = null;
        if (IRBuilder.mode == IRBuilder.AUTO_INSERT_MODE) {
            IRBuilder.getInstance().addInstr(this);
        }
    }

    public BasicBlock getParentBB() {
        return parentBB;
    }

    public void setParentBB(BasicBlock parentBB) {
        this.parentBB = parentBB;
    }
}
