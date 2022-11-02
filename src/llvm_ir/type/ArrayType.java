package llvm_ir.type;

public class ArrayType extends Type{
    private int eleNum;
    private Type eleType;

    public ArrayType(int eleNum, Type eleType) {
        this.eleNum = eleNum;
        this.eleType = eleType;
    }

    public Type getEleType() {
        return eleType;
    }

    public int getEleNum() {
        return eleNum;
    }

    @Override
    public String toString() {
        return "[" + eleNum + " x " + eleType + "]";
    }
}
