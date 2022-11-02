package llvm_ir;

import llvm_ir.type.BaseType;

public class Constant extends Value{
    private int value;

    public Constant(int value) {
        super(BaseType.INT32, String.valueOf(value));
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
