package llvm_ir;

import llvm_ir.type.LLVMType;

import java.util.ArrayList;

public class User extends Value{
    protected ArrayList<Value> operands;

    public User(LLVMType type, String name) {
        super(type, name);
        this.operands = new ArrayList<>();
    }

    public void addOperands(Value value) {
        operands.add(value);
        if (value != null) {
            value.addUse(this);
        }
    }

    public boolean modifyOperand(Value oldValue, Value newValue) {
        if (! operands.contains(oldValue)) return false;
        int index = operands.indexOf(oldValue);
        // 将this从oldValue的useList中删除
        oldValue.deleteUser(this);
        // 将newValue加入到oldValue中，位置不变
        operands.set(index, newValue);
        // 将this加入到newValue的useList中
        newValue.addUse(this);
        return true;
    }
}
