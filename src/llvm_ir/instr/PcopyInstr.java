package llvm_ir.instr;

import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.BaseType;

import java.util.ArrayList;

public class PcopyInstr extends Instr {
    private ArrayList<Value> dstList;
    private ArrayList<Value> srcList;

    public PcopyInstr(String name) {
        super(BaseType.VOID, name, InstrType.PCOPY);
        this.dstList = new ArrayList<>();
        this.srcList = new ArrayList<>();
    }

    public void addCopy(Value dst, Value src) {
        dstList.add(dst);
        srcList.add(src);
    }

    public ArrayList<Value> getDstList() {
        return dstList;
    }

    public ArrayList<Value> getSrcList() {
        return srcList;
    }
}
