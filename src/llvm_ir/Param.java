package llvm_ir;

import llvm_ir.type.Type;

public class Param extends Value{
    private Function parentFunction;

    public Param(Type type, String name) {
        super(type, name);
    }

    public void setParentFunction(Function parentFunction) {
        this.parentFunction = parentFunction;
    }
}
