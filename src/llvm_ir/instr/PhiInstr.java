package llvm_ir.instr;

import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.BaseType;

import java.util.ArrayList;

public class PhiInstr extends Instr {
    private ArrayList<Value> options;

    public PhiInstr(String name, ArrayList<Value> options) {
        super(BaseType.INT32, name, InstrType.PHI);
        this.options = options;
    }

    public void addOption(Value value) {
        options.add(value);
    }





}
