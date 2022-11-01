package llvm_ir;

import llvm_ir.type.ArrayType;
import llvm_ir.type.BaseType;
import llvm_ir.type.PointerType;

public class StringLiteral extends Value{
    private String content;

    public StringLiteral(String name, String content) {
        super(new PointerType(new ArrayType(content.length() + 1, BaseType.INT8)), name);
        this.content = content;
        IRBuilder.getInstance().addStringLiteral(this);
    }

    @Override
    public String toString() {
        return name + " = constant " + ((PointerType)type).getTargetType() + " c\"" + content + "\\00\"";
    }
}
