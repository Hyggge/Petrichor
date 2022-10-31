package llvm_ir;

import llvm_ir.initial.Initial;
import llvm_ir.type.Type;

public class GlobalVar extends User{
    private Initial initial;

    public GlobalVar(Type type, String name, Initial initial) {
        super(type, name);
        this.initial = initial;
    }

    @Override
    public String toString() {
        return name + " = dso_local global " + initial;
    }
}
