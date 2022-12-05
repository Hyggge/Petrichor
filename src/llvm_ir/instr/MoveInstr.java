package llvm_ir.instr;

import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.BaseType;

public class MoveInstr extends Instr {

    public MoveInstr(String name, Value dst, Value src) {
        super(BaseType.VOID, name, InstrType.MOVE);
        addOperands(dst);
        addOperands(src);
    }

    public Value getDst() {
        return operands.get(0);
    }

    public Value getSrc() {
        return operands.get(1);
    }

    public void setDst(Value dst) {
        operands.set(0,dst);
    }

    public void setSrc(Value src) {
        operands.set(1, src);
    }



    @Override
    public String toString() {
        Value dst = getDst();
        Value src = getSrc();
        return "move " + dst.getType() + " " + dst.getName() + ", " + src.getName();
    }
}
