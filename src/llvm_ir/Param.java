package llvm_ir;

import llvm_ir.type.LLVMType;

public class Param extends Value{
    private Function parentFunction;

    public Param(LLVMType type, String name) {
        super(type, name);
        if (IRBuilder.mode == IRBuilder.AUTO_INSERT_MODE) {
            IRBuilder.getInstance().addParam(this);
        }
    }

    public void setParentFunction(Function parentFunction) {
        this.parentFunction = parentFunction;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
}
