package llvm_ir.instr;

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
}
