package llvm_ir;

import back_end.mips.assembly.LabelAsm;
import llvm_ir.type.OtherType;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class BasicBlock extends Value {
    private LinkedList<Instr> instrList;
    private Function parentFunction;

    // 和CFG相关属性
    private HashSet<BasicBlock> preSet; // 流图
    private HashSet<BasicBlock> sucSet; // 流图
    private BasicBlock parent; // 支配树
    private HashSet<BasicBlock> childrenSet; // 支配树
    private HashSet<BasicBlock> domSet;
    private HashSet<BasicBlock> DF;


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

    public LinkedList<Instr> getInstrList() {
        return instrList;
    }

    public HashSet<BasicBlock> getPreSet() {
        return preSet;
    }

    public void setPreSet(HashSet<BasicBlock> preSet) {
        this.preSet = preSet;
    }

    public HashSet<BasicBlock> getSucSet() {
        return sucSet;
    }

    public void setSucSet(HashSet<BasicBlock> sucSet) {
        this.sucSet = sucSet;
    }

    public BasicBlock getParent() {
        return parent;
    }

    public void setParent(BasicBlock parent) {
        this.parent = parent;
    }

    public HashSet<BasicBlock> getChildrenSet() {
        return childrenSet;
    }

    public void setChildrenSet(HashSet<BasicBlock> childrenSet) {
        this.childrenSet = childrenSet;
    }

    public HashSet<BasicBlock> getDomSet() {
        return domSet;
    }

    public void setDomSet(HashSet<BasicBlock> domSet) {
        this.domSet = domSet;
    }

    public HashSet<BasicBlock> getDF() {
        return DF;
    }

    public void setDF(HashSet<BasicBlock> DF) {
        this.DF = DF;
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
