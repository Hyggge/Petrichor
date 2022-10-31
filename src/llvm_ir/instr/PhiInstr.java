package llvm_ir.instr;

import llvm_ir.Instr;
import llvm_ir.type.BaseType;

public class PhiInstr extends Instr {
    public PhiInstr(String name) {
        super(BaseType.VOID, name, InstrType.PHI);
    }
}
