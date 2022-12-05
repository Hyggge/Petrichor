package llvm_ir.instr;

import llvm_ir.BasicBlock;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.BaseType;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PhiInstr extends Instr {
    private ArrayList<BasicBlock> preBBList;

    public PhiInstr(String name, ArrayList<BasicBlock> preBBList) {
        super(BaseType.INT32, name, InstrType.PHI);
        this.preBBList = preBBList;
        for (int i = 0; i < preBBList.size(); i++) {
            addOperands(null);
        }
    }

    public void addOption(Value value, BasicBlock preBB) {
        int index = preBBList.indexOf(preBB);
        operands.set(index, value);
        value.addUse(this);
    }

    public ArrayList<Value> getOptions() {
        return operands;
    }

    @Override
    public String toString() {
        ArrayList<Value> options = getOptions();
        //  %4 = phi i32 [ 1, %2 ], [ %6, %5 ]
        return name + " = phi " + type + " "
                + preBBList.stream()
                            .map(bb -> "[ " + options.get(preBBList.indexOf(bb)).getName() + ", %" + bb.getName() + " ]")
                            .collect(Collectors.joining(", "));
    }
}
