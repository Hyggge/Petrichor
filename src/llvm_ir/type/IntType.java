package llvm_ir.type;

public class IntType {
    private int bitWidth;

    @Override
    public String toString() {
        if (bitWidth == 1) {
            return "i1";
        }
        return "i32";
    }
}
