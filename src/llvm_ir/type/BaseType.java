package llvm_ir.type;

public class BaseType extends Type{
    public static BaseType VOID = new BaseType(0);
    public static BaseType INT1 = new BaseType(1);
    public static BaseType INT8 = new BaseType(8);
    public static BaseType INT32 = new BaseType(32);

    private int bitWidth;

    private BaseType(int bitWidth) {
        this.bitWidth = bitWidth;
    }


    @Override
    public String toString() {
        if (bitWidth == 0) return "void";
        else if (bitWidth == 1) return "i1";
        else if (bitWidth == 8) return "i8";
        return "i32";
    }
}
