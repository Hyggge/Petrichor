package llvm_ir.type;

public class PointerType extends Type{
    private Type targetType;

    @Override
    public String toString() {
        return targetType.toString() + "*";
    }
}
