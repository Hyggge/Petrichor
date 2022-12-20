package llvm_ir;

import back_end.mips.assembly.LabelAsm;
import llvm_ir.instr.PhiInstr;
import llvm_ir.type.OtherType;

import java.util.ArrayList;
import java.util.HashSet;
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

    // 和活跃变量分析相关属性
    private HashSet<Value> in;
    private HashSet<Value> out;
    private HashSet<Value> def;
    private HashSet<Value> use;

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

    public HashSet<Value> getIn() {
        return in;
    }

    public void setIn(HashSet<Value> in) {
        this.in = in;
    }

    public HashSet<Value> getOut() {
        return out;
    }

    public void setOut(HashSet<Value> out) {
        this.out = out;
    }

    public HashSet<Value> getDef() {
        return def;
    }

    public HashSet<Value> getUse() {
        return use;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public void buildDefUse() {
        def = new HashSet<>();
        use = new HashSet<>();
        // 因为所有的phi指令是并行赋值的，所以其所有的右值都是先使用的
        for (Instr instr : instrList) {
            if (instr instanceof PhiInstr) {
                for (Value operand : instr.getOperands()) {
                    if (operand instanceof Instr || operand instanceof Param || operand instanceof GlobalVar) {
                        use.add(operand);
                    }
                }
            }
        }

        for (Instr instr : instrList) {
            // 先使用后定义的变量放在use中
            for (Value operand : instr.operands) {
                if (! def.contains(operand) && (operand instanceof Instr || operand instanceof Param || operand instanceof GlobalVar)) {
                    use.add(operand);
                }
            }
            // 先定义后使用的变量放在def中
            if (! use.contains(instr) && instr.canBeUsed() ) {
                def.add(instr);
            }
        }
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
