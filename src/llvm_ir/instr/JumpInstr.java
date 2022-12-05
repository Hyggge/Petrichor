package llvm_ir.instr;

import back_end.mips.assembly.JumpAsm;
import llvm_ir.BasicBlock;
import llvm_ir.Instr;
import llvm_ir.type.BaseType;

public class JumpInstr extends Instr {

    // private BasicBlock targetBB;

    public JumpInstr(String name, BasicBlock targetBB) {
        super(BaseType.VOID, name, InstrType.JUMP);
        addOperands(targetBB);
    }

    public BasicBlock getTargetBB() {
        return (BasicBlock) operands.get(0);
    }

    @Override
    public String toString() {
        BasicBlock targetBB = getTargetBB();
        return "br label %" + targetBB.getName();
    }
    @Override
    public void toAssembly() {
        super.toAssembly();
        BasicBlock targetBB = getTargetBB();
        new JumpAsm(JumpAsm.Op.J, targetBB.getName());
    }
}
