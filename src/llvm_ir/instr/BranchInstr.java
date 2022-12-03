package llvm_ir.instr;

import back_end.mips.MipsBuilder;
import back_end.mips.Register;
import back_end.mips.assembly.BranchAsm;
import back_end.mips.assembly.JumpAsm;
import back_end.mips.assembly.MemAsm;
import llvm_ir.BasicBlock;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.BaseType;

public class BranchInstr extends Instr {
    private Value con;
    private BasicBlock thenBlock;
    private BasicBlock elseBlock;

    public BranchInstr(String name, Value con, BasicBlock thenBlock, BasicBlock elseBlock) {
        super(BaseType.VOID, name, InstrType.BRANCH);
        this.con = con;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
        addOperands(thenBlock);
        addOperands(elseBlock);
    }

    public BasicBlock getThenBlock() {
        return thenBlock;
    }

    public BasicBlock getElseBlock() {
        return elseBlock;
    }

    @Override
    public String toString() {
        return "br i1 " + con.getName() + ", label %" + thenBlock.getName() + ", label %" + elseBlock.getName();
    }

    @Override
    public void toAssembly() {
        super.toAssembly();
        // con == 1 相当于 con != 0， 所以我们可以利用BNE指令和$0寄存器实现功能
        // 取出con的值，放到t0寄存器中
        new MemAsm(MemAsm.Op.LW, Register.T0, Register.SP, MipsBuilder.getInstance().getOffsetOf(con));
        // 如果con ！= 0，跳转到thenBlock
        new BranchAsm(BranchAsm.Op.BNE, Register.T0, Register.ZERO, thenBlock.getName());
        // 反之，直接jump到elseBlock
        new JumpAsm(JumpAsm.Op.J, elseBlock.getName());
    }

}
