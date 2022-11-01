package llvm_ir.instr;

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

    @Override
    public String toString() {
        return "br i1 " + con.getName() + ", label %" + thenBlock.getName() + ", label %" + elseBlock.getName();
    }
}
