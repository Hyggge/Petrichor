package llvm_ir;

import back_end.mips.assembly.LabelAsm;
import llvm_ir.type.OtherType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class BasicBlock extends Value {
    private LinkedList<Instr> instrList;
    private Function parentFunction;

    // 和CFG相关属性
    private ArrayList<BasicBlock> preList; // 流图
    private ArrayList<BasicBlock> sucList; // 流图
    private BasicBlock parent; // 支配树
    private ArrayList<BasicBlock> childList; // 支配树
    private ArrayList<BasicBlock> domList;
    private ArrayList<BasicBlock> DF;

    // 是否被删除
    private boolean isDeleted = false;


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

    public Instr getFirstInstr() {
        return instrList.getFirst();
    }

    public LinkedList<Instr> getInstrList() {
        return instrList;
    }

    public ArrayList<BasicBlock> getPreList() {
        return preList;
    }

    public void setPreList(ArrayList<BasicBlock> preList) {
        this.preList = preList;
    }

    public ArrayList<BasicBlock> getSucList() {
        return sucList;
    }

    public void setSucList(ArrayList<BasicBlock> sucList) {
        this.sucList = sucList;
    }

    public BasicBlock getParent() {
        return parent;
    }

    public void setParent(BasicBlock parent) {
        this.parent = parent;
    }

    public ArrayList<BasicBlock> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<BasicBlock> childList) {
        this.childList = childList;
    }

    public ArrayList<BasicBlock> getDomList() {
        return domList;
    }

    public void setDomList(ArrayList<BasicBlock> domList) {
        this.domList = domList;
    }

    public ArrayList<BasicBlock> getDF() {
        return DF;
    }

    public void setDF(ArrayList<BasicBlock> DF) {
        this.DF = DF;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public boolean isDeleted() {
        return this.isDeleted;
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
