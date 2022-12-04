package llvm_ir.instr;

import back_end.mips.MipsBuilder;
import back_end.mips.Register;
import back_end.mips.assembly.AluAsm;
import back_end.mips.assembly.JumpAsm;
import back_end.mips.assembly.LiAsm;
import back_end.mips.assembly.MemAsm;
import llvm_ir.Constant;
import llvm_ir.Function;
import llvm_ir.Instr;
import llvm_ir.Value;

import java.util.ArrayList;
import java.util.List;

public class CallInstr extends Instr {
    // private Function targetFunc;
    // private ArrayList<Value> paramList;

    public CallInstr(String name, Function targetFunc, ArrayList<Value> paramList) {
        super(targetFunc.getRetType(), name, InstrType.CALL);
        addOperands(targetFunc);
        for (Value param : paramList) {
            addOperands(param);
        }
    }

    public Function getTargetFunc() {
        return (Function) operands.get(0);
    }

    public List<Value> getParamList() {
        return operands.subList(1, operands.size());
    }

    @Override
    public String toString() {
        Function targetFunc = getTargetFunc();
        List<Value> paramList = getParamList();
        ArrayList<String> paramInfo = new ArrayList<>();
        for (Value param : paramList) {
            paramInfo.add(param.getType() + " " + param.getName());
        }
        if (type.isVoid()) {
            return "call void " + targetFunc.getName() + "(" + String.join(", ", paramInfo) +")";
        } else {
            return name + " = call i32 " + targetFunc.getName() + "(" + String.join(", ", paramInfo) +")";
        }
    }

    @Override
    public void toAssembly() {
        super.toAssembly();
        Function targetFunc = getTargetFunc();
        List<Value> paramList = getParamList();
        int curOffset = MipsBuilder.getInstance().getCurOffset();
        new MemAsm(MemAsm.Op.SW, Register.SP, Register.SP, curOffset - 4);
        new MemAsm(MemAsm.Op.SW, Register.RA, Register.SP, curOffset - 8);
        // 将实参的值压入被调用函数的堆栈中
        // 被调用函数的栈底为 sp + curOffset(负数) - 8
        int paramNum = 0;
        for (Value param : paramList) {
            ++paramNum;
            // 将实参的值load到t0寄存器中
            if (param instanceof Constant) {
                new LiAsm(Register.T0, ((Constant)param).getValue());
            } else {
                new MemAsm(MemAsm.Op.LW, Register.T0, Register.SP, MipsBuilder.getInstance().getOffsetOf(param));
            }
            // 将t0中的值存入被调用函数的栈区域
            new MemAsm(MemAsm.Op.SW, Register.T0, Register.SP, curOffset - 8 - paramNum * 4);

        }
        // 将sp设置为被调用函数的栈底地址，即sp + curOffset - 8
        new AluAsm(AluAsm.Op.ADDI, Register.SP, Register.SP, curOffset - 8);
        // 调用函数
        new JumpAsm(JumpAsm.Op.JAL, targetFunc.getName().substring(1));
        // 将sp、ra恢复
        new MemAsm(MemAsm.Op.LW, Register.RA, Register.SP, 0);
        new MemAsm(MemAsm.Op.LW, Register.SP, Register.SP, 4);
        // 恢复完sp和ra之后，sp就指向当前函数的栈底了，而且此时栈的offset仍然是curOffset
        // 如果当前函数有返回值，那么我们需要从v0中获取返回值，并将值压入栈中（给返回值分配4字节空间）
        // 此外，需要设置当前Value和curOffset的对应关系，
        MipsBuilder.getInstance().subCurOffset(4);
        curOffset = MipsBuilder.getInstance().getCurOffset();
        MipsBuilder.getInstance().addValueOffsetMap(this, curOffset);
        new MemAsm(MemAsm.Op.SW, Register.V0, Register.SP, curOffset);
    }
}
