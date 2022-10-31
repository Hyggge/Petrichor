package llvm_ir.type;

public class ArrayType extends Type{
    private int length;
    private Type eleType;

    public ArrayType(int length, Type eleType) {
        this.length = length;
        this.eleType = eleType;
    }

    @Override
    public String toString() {
        return "[" + length + " x " + eleType + "]";
    }
}
