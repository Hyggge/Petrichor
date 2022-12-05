package llvm_ir;

import llvm_ir.type.BaseType;

public class UndefinedValue extends Value {
    public UndefinedValue() {
        super(BaseType.INT32, "0");
    }

    @Override
    public String toString() {
        return "undefined";
    }
}
