package llvm_ir.type;

public class Type {

    public boolean isArray() {
        return this instanceof ArrayType;
    }

    public boolean isInt1() {
        return this == BaseType.INT1;
    }

    public boolean isInt32() {
        return this == BaseType.INT32;
    }

    public boolean isVoid() {
        return this == BaseType.VOID;
    }

    public boolean isPointer() {
        return this instanceof PointerType;
    }

    public boolean isBB() {
        return this == OtherType.BB;
    }

    public boolean isFUNCTION() {
        return this == OtherType.FUNCTION;
    }


}
