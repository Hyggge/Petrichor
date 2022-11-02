package llvm_ir;

import back_end.mips.assembly.LabelAsm;
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
        if (IRBuilder.mode == IRBuilder.AUTO_INSERT_MODE) {
            IRBuilder.getInstance().addBB(this);
        }
    }

    public void addInstr(Instr instr) {
        instrList.add(instr);
    }

    public void setParentFunction(Function parentFunction) {
        this.parentFunction = parentFunction;
    }

    public Function getParentFunction() {
        return parentFunction;
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

    @Override
    public void toAssembly() {
        new LabelAsm(name);
        for (Instr instr : instrList) {
            instr.toAssembly();
        }
    }
}
