package llvm_ir.instr;

import llvm_ir.BasicBlock;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.BaseType;
import llvm_ir.type.Type;

public class BranchInstr extends Instr {
    private Value con;
    private BasicBlock trueTarget;
    private BasicBlock falseTarget;

    public BranchInstr(Type type, String name, Value con, BasicBlock tureTarget, BasicBlock falseTarget) {
        super(BaseType.VOID, name, InstrType.BRANCH);
        this.con = con;
        this.trueTarget = tureTarget;
        this.falseTarget = falseTarget;
        addOperands(tureTarget);
        addOperands(falseTarget);
    }

    @Override
    public String toString() {
        return "br i1 " + con.getName() + ", label %" + trueTarget.getName() + ", label %" + falseTarget.getName();
    }
}
