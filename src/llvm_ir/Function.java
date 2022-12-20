package llvm_ir;

import back_end.mips.MipsBuilder;
import back_end.mips.Register;
import back_end.mips.assembly.LabelAsm;
import llvm_ir.instr.CallInstr;
import llvm_ir.instr.IOInstr;
import llvm_ir.instr.ReturnInstr;
import llvm_ir.type.LLVMType;
import llvm_ir.type.OtherType;

import java.util.ArrayList;
import java.util.HashMap;
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

    // 是否可以进行GVN优化
    private Boolean canGVN = null;

    // 寄存器分配
    private HashMap<Value, Register> var2reg;


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

    public HashMap<BasicBlock, ArrayList<BasicBlock>> getPreMap() {
        return preMap;
    }

    public HashMap<BasicBlock, ArrayList<BasicBlock>> getSucMap() {
        return sucMap;
    }

    public HashMap<BasicBlock, BasicBlock> getParentMap() {
        return parentMap;
    }

    public HashMap<BasicBlock, ArrayList<BasicBlock>> getChildMap() {
        return childMap;
    }

    public HashMap<Value, Register> getVar2reg() {
        return var2reg;
    }

    public void setVar2reg(HashMap<Value, Register> var2reg) {
        this.var2reg = var2reg;
    }

    // 检查该函数的调用是否进行GVN优化
    // 必须满足：参数没有指针类型, 每个指令没有读写全局变量，不能调用其他函数
    public boolean canGVN() {
        if (canGVN != null) return canGVN;

        for (Param param : paramList) {
            if (param.getType().isPointer()) {
                canGVN = false;
                return false;
            }
        }
        for (BasicBlock bb : BBList) {
            for (Instr instr : bb.getInstrList()) {
                // 不能调用其他函数
                if (instr instanceof CallInstr || instr instanceof IOInstr) {
                    canGVN = false;
                    return false;
                }
                // 每个指令不能读写全局变量
                for (Value operand : instr.getOperands()) {
                    if (operand instanceof GlobalVar) {
                        canGVN = false;
                        return false;
                    }
                }
            }
        }
        canGVN = true;
        return true;
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
        for (BasicBlock block : BBList) {
            block.toAssembly();
        }
    }

}
