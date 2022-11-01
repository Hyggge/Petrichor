package llvm_ir;

import back_end.mips.assembly.GlobalAsm;
import llvm_ir.type.ArrayType;
import llvm_ir.type.BaseType;
import llvm_ir.type.PointerType;

public class StringLiteral extends Value{
    private String content;

    public StringLiteral(String name, String content) {
        super(new PointerType(new ArrayType(content.length() + 1, BaseType.INT8)), name);
        this.content = content;
        if (IRBuilder.mode == IRBuilder.AUTO_INSERT_MODE) {
            IRBuilder.getInstance().addStringLiteral(this);
        }
    }

    @Override
    public String toString() {
        return name + " = constant " + ((PointerType)type).getTargetType() + " c\"" + content + "\\00\"";
    }

    @Override
    public void toAssembly() {
        new GlobalAsm.Asciiz(name.substring(1), content);
    }
}
