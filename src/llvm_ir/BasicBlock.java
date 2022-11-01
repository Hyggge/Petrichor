package llvm_ir;

import llvm_ir.type.OtherType;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class BasicBlock extends Value {
    private LinkedList<Instr> instrList;
    private Function parentFunction;

    public BasicBlock(String name) {
        super(OtherType.FUNCTION, name);
        this.instrList = new LinkedList<>();
        this.parentFunction = null;
        IRBuilder.getInstance().addBB(this);
    }

    public void addInstr(Instr instr) {
        instrList.add(instr);
    }

    public void setParentFunction(Function parentFunction) {
        this.parentFunction = parentFunction;
    }

    public boolean isEmpty() {
        return instrList.isEmpty();
    }

    public Instr getLastInstr() {
        return instrList.getLast();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name + ":\n\t");
        sb.append(instrList.stream().map(instr -> instr.toString()).collect(Collectors.joining("\n\t")));
        return sb.toString();
    }
}
