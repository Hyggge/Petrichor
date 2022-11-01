package llvm_ir;

import llvm_ir.instr.ReturnInstr;
import llvm_ir.type.OtherType;
import llvm_ir.type.Type;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Function extends User{
    private ArrayList<Param> paramList;
    private LinkedList<BasicBlock> BBList;
    private Type retType;

    public Function(String name, Type retType) {
        super(OtherType.FUNCTION, "@" + name);
        this.retType = retType;
        this.paramList = new ArrayList<>();
        this.BBList = new LinkedList<>();
        IRBuilder.getInstance().addFunction(this);
    }

    public void addParam(Param param) {
        paramList.add(param);
    }

    public void addBB(BasicBlock bb) {
        BBList.add(bb);
    }

    public Type getRetType() {
        return retType;
    }

    public void checkEmptyBB() {
        BasicBlock lastBB = BBList.getLast();
        if (lastBB.isEmpty()) {
            if (retType.isInt32()) new ReturnInstr(IRBuilder.getInstance().genLocalVarName(), new Constant(0));
            else new ReturnInstr(IRBuilder.getInstance().genLocalVarName(), null);
        }
    }

    @Override
    public String toString() {
        String paramInfo = paramList.stream().map(param -> param.toString()).collect(Collectors.joining(", "));
        StringBuilder sb = new StringBuilder();
        sb.append("define dso_local " + retType + " " + name + "(" + paramInfo + ") {\n");
        sb.append(BBList.stream().map(block -> block.toString()).collect(Collectors.joining("\n")));
        sb.append("\n}");
        return sb.toString();
    }
}
