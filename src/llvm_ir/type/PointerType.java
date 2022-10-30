package llvm_ir.type;

public class PointerType extends Type{
    private Type targetType;

    public PointerType(Type targetType) {
        this.targetType = targetType;
    }

    @Override
    public String toString() {
        return targetType.toString() + "*";
    }
}
