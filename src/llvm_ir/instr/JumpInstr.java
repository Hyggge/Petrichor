package llvm_ir.instr;

import back_end.mips.assembly.JumpAsm;
import llvm_ir.BasicBlock;
import llvm_ir.Instr;
import llvm_ir.type.BaseType;

public class JumpInstr extends Instr {
    private BasicBlock targetBB;

    public JumpInstr(String name, BasicBlock targetBB) {
        super(BaseType.VOID, name, InstrType.JUMP);
        this.targetBB = targetBB;
    }

    @Override
    public String toString() {
        return "br label %" + targetBB.getName();
    }
    @Override
    public void toAssembly() {
        new JumpAsm(JumpAsm.Op.J, targetBB.getName());
    }
}
