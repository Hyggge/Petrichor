package llvm_ir;

import back_end.mips.MipsBuilder;
import back_end.mips.assembly.LabelAsm;
import llvm_ir.instr.ReturnInstr;
import llvm_ir.type.LLVMType;
import llvm_ir.type.OtherType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Function extends User{
    // 基本信息
    private ArrayList<Param> paramList;
    private LinkedList<BasicBlock> BBList;
    private LLVMType retType;

    // 和CFG相关内容
    private HashMap<BasicBlock, ArrayList<BasicBlock>> preMap;
    private HashMap<BasicBlock, ArrayList<BasicBlock>> sucMap;

    // 支配树
    private HashMap<BasicBlock, BasicBlock> parentMap;
    private HashMap<BasicBlock, ArrayList<BasicBlock>> childMap;


    public Function(String name, LLVMType retType) {
        super(OtherType.FUNCTION, name);
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

    public LLVMType getRetType() {
        return retType;
    }

    public LinkedList<BasicBlock> getBBList() {
        return BBList;
    }

    public ArrayList<Param> getParamList() {
        return paramList;
    }

    public void setPreMap(HashMap<BasicBlock, ArrayList<BasicBlock>> preMap) {
        this.preMap = preMap;
    }

    public void setSucMap(HashMap<BasicBlock, ArrayList<BasicBlock>> sucMap) {
        this.sucMap = sucMap;
    }

    public void setParentMap(HashMap<BasicBlock, BasicBlock> parentMap) {
        this.parentMap = parentMap;
    }

    public void setChildMap(HashMap<BasicBlock, ArrayList<BasicBlock>> childMap) {
        this.childMap = childMap;
    }

    // 我们需要保证函数一定有一个ret语句
    public void checkExistRet() {
        BasicBlock lastBB = IRBuilder.getInstance().getCurBB();
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
        // 因为调用一个函数前，已经将函数的参数压入新栈的栈底了，所以curOffset不等于0
        for (int i = 0; i < paramList.size(); i++) {
            MipsBuilder.getInstance().subCurOffset(4);
            int curOffset = MipsBuilder.getInstance().getCurOffset();
            MipsBuilder.getInstance().addValueOffsetMap(paramList.get(i), curOffset);
        }
        // 调用各个BB的toAssembly
        // 当没有进行优化时，直接顺序toAssembly即可
        if (sucMap == null) {
            for (BasicBlock block : BBList) {
                block.toAssembly();
            }
        }
        // 当进行优化后，新增了move指令，必须按照前驱后继关系来toAssembly
        else {
            HashSet<BasicBlock> vis = new HashSet<>();
            dfs(BBList.get(0), vis);
        }
    }

    private void dfs(BasicBlock entry, HashSet<BasicBlock> vis) {
        vis.add(entry);
        entry.toAssembly();
        for (BasicBlock sucBB : entry.getSucList()) {
            if (! vis.contains(sucBB)) {
                dfs(sucBB, vis);
            }
        }
    }
}
