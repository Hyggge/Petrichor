package llvm_ir.instr;

import llvm_ir.Function;
import llvm_ir.Instr;
import llvm_ir.Value;

import java.util.ArrayList;

public class CallInstr extends Instr {
    private Function targetFunc;
    private ArrayList<Value> paramList;

    public CallInstr(String name, Function targetFunc, ArrayList<Value> paramList) {
        super(targetFunc.getRetType(), name, InstrType.CALL);
        this.targetFunc = targetFunc;
        this.paramList = paramList;
        addOperands(targetFunc);
        for (Value param : paramList) {
            addOperands(param);
        }
    }

    @Override
    public String toString() {
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
}
