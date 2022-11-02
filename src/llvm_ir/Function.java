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

    public ArrayList<Param> getParamList() {
        return paramList;
    }

    // 我们需要保证函数最后一个BB一定有一个ret语句
    public void checkExistRet() {
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
        // 进入一个新的函数定义，调用enterFunc方法，重置栈的offset，清空ValueOffsetMap
        MipsBuilder.getInstance().enterFunc(this);
        // 建立形参和offset的映射关系
        for (int i = 0; i < paramList.size(); i++) {
            MipsBuilder.getInstance().addValueOffsetMap(paramList.get(i), 4 * i);
        }
        // 因为调用一个函数前，已经将函数的参数压入新栈的栈底了，所以curOffset不等于0
        MipsBuilder.getInstance().addCurOffset(paramList.size() * 4);
        // 调用各个BB的toAssembly
        for (BasicBlock block : BBList) {
            block.toAssembly();
        }
    }
}
