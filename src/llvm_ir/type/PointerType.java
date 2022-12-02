package llvm_ir.type;

public class PointerType extends LLVMType {
    private LLVMType targetType;

    public PointerType(LLVMType targetType) {
        this.targetType = targetType;
    }

    public LLVMType getTargetType() {
        return targetType;
    }

    @Override
    public String toString() {
        return targetType.toString() + "*";
    }
}
