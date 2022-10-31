package llvm_ir.instr;

import llvm_ir.BasicBlock;
import llvm_ir.Instr;
import llvm_ir.type.BaseType;

public class JumpInstr extends Instr {
    private BasicBlock targetBB;

    public JumpInstr(String name) {
        super(BaseType.VOID, name, InstrType.JUMP);
    }

    @Override
    public String toString() {
        return "br label %" + targetBB.getName();
    }
}
