package llvm_ir.type;

public class ArrayType extends LLVMType {
    private int eleNum;
    private LLVMType eleType;

    public ArrayType(int eleNum, LLVMType eleType) {
        this.eleNum = eleNum;
        this.eleType = eleType;
    }

    public LLVMType getEleType() {
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
