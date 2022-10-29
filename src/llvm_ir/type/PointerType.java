package llvm_ir.type;

public class PointerType {
    private Type targetType;

    @Override
    public String toString() {
        return targetType.toString() + "*";
    }
}
