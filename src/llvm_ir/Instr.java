package llvm_ir;

import back_end.mips.assembly.CommentAsm;
import llvm_ir.type.LLVMType;

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
        RETURN,
        STORE,
        ZEXT,
        IO,
        PHI,
        PCOPY,
        MOVE
    }

    private InstrType instrType;
    private BasicBlock parentBB;

    public Instr(LLVMType type, String name, InstrType instrType) {
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

    @Override
    public void toAssembly() {
        new CommentAsm("\n# " + this.toString());
    }
}
