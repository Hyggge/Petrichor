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
        // 我们先获得address的值, 保存到t0中
        if (to instanceof GlobalVar) {
            new LaAsm(Register.T0, to.getName().substring(1));
        } else {
            new MemAsm(MemAsm.Op.LW, Register.T0, Register.SP, MipsBuilder.getInstance().getOffsetOf(to));
        }
        // 下面获取value，并存到address代表的位置
        if (from instanceof Constant || from instanceof UndefinedValue) {
            // 将常数保存到t1中
            new LiAsm(Register.T1, Integer.parseInt(from.getName()));
            // 将value存到address的位置
            new MemAsm(MemAsm.Op.SW, Register.T1, Register.T0, 0);
        }
        else {
            int valueOffset = MipsBuilder.getInstance().getOffsetOf(from);
            // 然后获得value的值，保存到t1中
            new MemAsm(MemAsm.Op.LW, Register.T1, Register.SP, valueOffset);
            // 将value存到address的位置
            new MemAsm(MemAsm.Op.SW, Register.T1, Register.T0, 0);
        }
    }
}
