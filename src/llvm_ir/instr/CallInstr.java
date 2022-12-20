package llvm_ir.instr;

import back_end.mips.MipsBuilder;
import back_end.mips.Register;
import back_end.mips.assembly.AluAsm;
import back_end.mips.assembly.JumpAsm;
import back_end.mips.assembly.LiAsm;
import back_end.mips.assembly.MemAsm;
import back_end.mips.assembly.MoveAsm;
import llvm_ir.Constant;
import llvm_ir.Function;
import llvm_ir.Instr;
import llvm_ir.UndefinedValue;
import llvm_ir.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public String getGVNHash() {
        String hash = getTargetFunc().getName() + "(";
        hash += getParamList().stream().map(Value::getName).collect(Collectors.joining(","));
        hash += ")";
        return hash;
    }

    @Override
    public boolean canBeUsed() {
        return !type.isVoid();
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
        ArrayList<Register> allocatedRegs = MipsBuilder.getInstance().getAllocatedRegs();
        int curOffset = MipsBuilder.getInstance().getCurOffset();
        // 将已经分配的寄存器存储到到sp + curOffset ~ sp + curOffset - 4*regNum这一段区域内
        int regNum = 0;
        for (Register reg : allocatedRegs) {
            ++regNum;
            new MemAsm(MemAsm.Op.SW, reg, Register.SP, curOffset - regNum * 4);
        }
        // 将sp和ra存到sp+curOffset-4*regNum-4 和sp+curOffset-4*regNum-8中
        new MemAsm(MemAsm.Op.SW, Register.SP, Register.SP, curOffset - regNum * 4 - 4);
        new MemAsm(MemAsm.Op.SW, Register.RA, Register.SP, curOffset - regNum * 4 - 8);
        // 将实参的值压入被调用函数的堆栈中
        // 被调用函数的栈底为 sp + curOffset(负数) - regNum * 4 - 8
        int paramNum = 0;
        for (Value param : paramList) {
            ++paramNum;
            Register tempReg = Register.K0;
            // 将实参的值load到K0寄存器中
            if (param instanceof Constant || param instanceof UndefinedValue) {
                new LiAsm(tempReg, Integer.parseInt(param.getName()));
            } else if (MipsBuilder.getInstance().getRegOf(param) != null) {
                tempReg = MipsBuilder.getInstance().getRegOf(param);
            } else {
                new MemAsm(MemAsm.Op.LW, tempReg, Register.SP, MipsBuilder.getInstance().getOffsetOf(param));
            }
            // 将tempReg中的值存入被调用函数的栈区域
            new MemAsm(MemAsm.Op.SW, tempReg, Register.SP,  curOffset - regNum * 4 - 8 -paramNum * 4);
        }
        // 将sp设置为被调用函数的栈底地址，即sp + curOffset(负数) - regNum * 4 - 8
        new AluAsm(AluAsm.Op.ADDI, Register.SP, Register.SP, curOffset- regNum * 4 - 8);
        // 调用函数
        new JumpAsm(JumpAsm.Op.JAL, targetFunc.getName().substring(1));
        // 将sp、ra恢复
        new MemAsm(MemAsm.Op.LW, Register.RA, Register.SP, 0);
        new MemAsm(MemAsm.Op.LW, Register.SP, Register.SP, 4);
        // 恢复完sp和ra之后，sp就指向当前函数的栈底了，而且此时栈的offset仍然是curOffset
        // 下面还需要将寄存器恢复
        regNum = 0;
        for (Register reg : allocatedRegs) {
            ++regNum;
            new MemAsm(MemAsm.Op.LW, reg, Register.SP, curOffset - regNum * 4);
        }
        // 如果当前函数有返回值，那么我们需要从v0中获取返回值，
        // 将这个值放入this对应的寄存器或者压入栈中（给返回值分配4字节空间）
        if (MipsBuilder.getInstance().getRegOf(this) != null) {
            Register reg = MipsBuilder.getInstance().getRegOf(this);
            new MoveAsm(reg, Register.V0);
        }
        else {
            MipsBuilder.getInstance().subCurOffset(4);
            curOffset = MipsBuilder.getInstance().getCurOffset();
            MipsBuilder.getInstance().addValueOffsetMap(this, curOffset);
            new MemAsm(MemAsm.Op.SW, Register.V0, Register.SP, curOffset);
        }
    }
}
