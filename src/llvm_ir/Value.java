package llvm_ir;

import llvm_ir.type.LLVMType;

import java.util.ArrayList;

public class Value {

    protected LLVMType type;
    protected String name;
    protected ArrayList<Use> useList;

    public Value(LLVMType type, String name) {
        this.type = type;
        this.name = name;
        this.useList = new ArrayList<>();
    }

    public void addUse(User user) {
        Use use = new Use(user, this);
        useList.add(use);
    }

    public LLVMType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void toAssembly() {
    }
}
