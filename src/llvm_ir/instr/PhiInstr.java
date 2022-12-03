package llvm_ir.instr;

import llvm_ir.Instr;
import llvm_ir.type.BaseType;

import java.util.ArrayList;

public class PhiInstr extends Instr {
    private ArrayList<Instr> options;

    public PhiInstr(String name, ArrayList<Instr> options) {
        super(BaseType.INT32, name, InstrType.PHI);
        this.options = options;
    }





}
