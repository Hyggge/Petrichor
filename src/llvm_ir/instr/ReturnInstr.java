package llvm_ir.instr;

import back_end.mips.MipsBuilder;
import back_end.mips.Register;
import back_end.mips.assembly.JumpAsm;
import back_end.mips.assembly.LiAsm;
import back_end.mips.assembly.MemAsm;
import llvm_ir.Constant;
import llvm_ir.Function;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.BaseType;

public class ReturnInstr extends Instr {
    private Value retValue;

    public ReturnInstr(String name, Value retValue) {
        super(BaseType.VOID, name, InstrType.RETURN);
        this.retValue = retValue;
        // 如果retValue != null, 也就是非"ret void"时候，需要记录def-use关系。
        if(retValue != null) addOperands(retValue);

    }

    @Override
    public String toString() {
        if (retValue == null) return "ret void";
        return "ret " + retValue.getType() + " " + retValue.getName();
    }

    @Override
    public void toAssembly() {
        Function function = getParentBB().getParentFunction();
        if (retValue != null) {
            if (retValue instanceof Constant) {
                new LiAsm(Register.V0, ((Constant)retValue).getValue());
            } else {
                int offset = MipsBuilder.getInstance().getOffsetOf(retValue);
                new MemAsm(MemAsm.Op.LW, Register.V0, Register.SP, offset);
            }
            new JumpAsm(JumpAsm.Op.JR, Register.RA);
        }
    }
}
