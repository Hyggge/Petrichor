package llvm_ir.instr;

import back_end.mips.MipsBuilder;
import back_end.mips.Register;
import back_end.mips.assembly.LaAsm;
import back_end.mips.assembly.LiAsm;
import back_end.mips.assembly.MemAsm;
import llvm_ir.Constant;
import llvm_ir.GlobalVar;
import llvm_ir.Instr;
import llvm_ir.UndefinedValue;
import llvm_ir.Value;
import llvm_ir.type.BaseType;

public class StoreInstr extends Instr {
    // private Value from;
    // private Value to;

    public StoreInstr(String name, Value from, Value to) {
        super(BaseType.VOID, name, InstrType.STORE);
        addOperands(from);
        addOperands(to);
    }

    public Value getFrom() {
        return operands.get(0);
    }

    public Value getTo() {
        return operands.get(1);
    }

    @Override
    public String toString() {
        Value from = getFrom();
        Value to = getTo();
        return "store " +
                from.getType() + " " +
                from.getName() + ", " +
                to.getType() + " " +
                to.getName();
    }

    @Override
    public void toAssembly() {
        super.toAssembly();
        Value from = getFrom();
        Value to = getTo();
        Register fromReg = Register.K0;
        Register toReg = Register.K1;
        // 我们先获得address的值, 保存到toReg中
        if (to instanceof GlobalVar) {
            new LaAsm(toReg, to.getName().substring(1));
        }
        else if (MipsBuilder.getInstance().getRegOf(to) != null) {
            toReg = MipsBuilder.getInstance().getRegOf(to);
        }
        else {
            new MemAsm(MemAsm.Op.LW, toReg, Register.SP, MipsBuilder.getInstance().getOffsetOf(to));
        }
        // 下面获取需要存入的值
        if (from instanceof Constant || from instanceof UndefinedValue) {
            // 将常数保存到fromReg中
            new LiAsm(fromReg, Integer.parseInt(from.getName()));
        }
        else if (MipsBuilder.getInstance().getRegOf(from) != null) {
            fromReg = MipsBuilder.getInstance().getRegOf(from);
        }
        else {
            Integer valueOffset = MipsBuilder.getInstance().getOffsetOf(from);
            if (valueOffset == null) {
                MipsBuilder.getInstance().subCurOffset(4);
                valueOffset = MipsBuilder.getInstance().getCurOffset();
                MipsBuilder.getInstance().addValueOffsetMap(from, valueOffset);
            }
            // 然后获得value的值，保存到fromReg中
            new MemAsm(MemAsm.Op.LW, fromReg, Register.SP, valueOffset);
        }
        // 将fromReg的值存到address的位置
        new MemAsm(MemAsm.Op.SW, fromReg, toReg, 0);
    }
}
