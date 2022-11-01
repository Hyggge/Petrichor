package llvm_ir;

import llvm_ir.type.Type;

import java.util.ArrayList;

public class Value {

    protected Type type;
    protected String name;
    protected ArrayList<Use> useList;

    public Value(Type type, String name) {
        this.type = type;
        this.name = name;
        this.useList = new ArrayList<>();
    }

    public void addUse(User user) {
        Use use = new Use(user, this);
        useList.add(use);
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void toAssembly() {
    }
}
