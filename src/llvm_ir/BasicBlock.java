package llvm_ir;

import llvm_ir.type.OtherType;

import java.util.LinkedList;

public class BasicBlock extends Value {
    private LinkedList<Instr> instrList;
    private Function parentFunction;

    public BasicBlock(String name) {
        super(OtherType.FUNCTION, name);
        this.instrList = new LinkedList<>();
        this.parentFunction = null;
    }

    public void addInstr(Instr instr) {
        instrList.add(instr);
    }

    public void setParentFunction(Function parentFunction) {
        this.parentFunction = parentFunction;
    }
}
