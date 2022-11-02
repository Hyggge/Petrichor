package llvm_ir;

import back_end.mips.MipsBuilder;
import back_end.mips.assembly.LabelAsm;
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
        if (IRBuilder.mode == IRBuilder.AUTO_INSERT_MODE) {
            IRBuilder.getInstance().addFunction(this);
        }
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

    // 我们需要保证函数最后一个BB一定有一个ret语句
    public void checkEmptyBB() {
        BasicBlock lastBB = BBList.getLast();
        if (lastBB.isEmpty() || ! (lastBB.getLastInstr() instanceof ReturnInstr)) {
            // int类型函数, 默认最后一句为'ret i32 0'
            if (retType.isInt32()) new ReturnInstr(IRBuilder.getInstance().genLocalVarName(), new Constant(0));
            // void 类型函数，默认最后一句为'ret void'
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


    @Override
    public void toAssembly() {
        new LabelAsm(name.substring(1));
        // 为形参预留空间
        MipsBuilder.getInstance().addCurStackOffset(paramList.size() * 4);
        // 调用各个BB的toAssembly
        for (BasicBlock block : BBList) {
            block.toAssembly();
        }
    }
}
