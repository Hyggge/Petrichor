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
    // private Value con;
    // private BasicBlock thenBlock;
    // private BasicBlock elseBlock;

    public BranchInstr(String name, Value con, BasicBlock thenBlock, BasicBlock elseBlock) {
        super(BaseType.VOID, name, InstrType.BRANCH);
        addOperands(con);
        addOperands(thenBlock);
        addOperands(elseBlock);
    }

    public Value getCon() {
        return operands.get(0);
    }

    public BasicBlock getThenBlock() {
        return (BasicBlock) operands.get(1);
    }

    public BasicBlock getElseBlock() {
        return (BasicBlock) operands.get(2);
    }

    public void setThenBlock(BasicBlock thenBlock) {
        operands.set(1, thenBlock);
    }

    public void setElseBlock(BasicBlock elseBlock) {
        operands.set(2, elseBlock);
    }


    @Override
    public String toString() {
        Value con = getCon();
        BasicBlock thenBlock = getThenBlock();
        BasicBlock elseBlock = getElseBlock();
        return "br i1 " + con.getName() + ", label %" + thenBlock.getName() + ", label %" + elseBlock.getName();
    }

    @Override
    public void toAssembly() {
        super.toAssembly();
        Value con = getCon();
        BasicBlock thenBlock = getThenBlock();
        BasicBlock elseBlock = getElseBlock();

        Register reg = MipsBuilder.getInstance().getRegOf(con);

        // con == 1 相当于 con != 0， 所以我们可以利用BNE指令和$0寄存器实现功能
        // 如果con没有对应的寄存器，则从堆栈中取出con的值，放到reg中
        if (reg == null) {
            reg = Register.K0;
            new MemAsm(MemAsm.Op.LW, reg, Register.SP, MipsBuilder.getInstance().getOffsetOf(con));
        }
        // 如果con ！= 0，跳转到thenBlock
        new BranchAsm(BranchAsm.Op.BNE, reg, Register.ZERO, thenBlock.getName());
        // 反之，直接jump到elseBlock
        new JumpAsm(JumpAsm.Op.J, elseBlock.getName());
    }

}
