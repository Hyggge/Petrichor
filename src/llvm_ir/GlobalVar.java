package llvm_ir;

import back_end.mips.Register;
import back_end.mips.assembly.GlobalAsm;
import back_end.mips.assembly.LiAsm;
import back_end.mips.assembly.MemAsm;
import llvm_ir.initial.Initial;
import llvm_ir.type.PointerType;
import llvm_ir.type.LLVMType;

// 全局变量和全局常量都会生成GlobalVar
public class GlobalVar extends User{
    private Initial initial;

    public GlobalVar(LLVMType type, String name, Initial initial) {
        super(type, name);
        this.initial = initial;
        if (IRBuilder.mode == IRBuilder.AUTO_INSERT_MODE) {
            IRBuilder.getInstance().addGlobalVar(this);
        }
    }

    @Override
    public String toString() {
        return name + " = dso_local global " + initial;
    }

    @Override
    public void toAssembly() {
        LLVMType targetType = ((PointerType) type).getTargetType();
        // 如果全局变量为非数组变量
        if (targetType.isInt32()) {
            // 无初始值的情况
            if (initial.getValues() == null) new GlobalAsm.Word(name.substring(1), 0);
            // 有初始值的情况
            else new GlobalAsm.Word(name.substring(1), initial.getValues().get(0));
        }
        // 如果全局变量是数组变量
        else {
            new GlobalAsm.Space(name.substring(1), targetType.getLength() * 4);
            // 有初始值的情况
            if (initial.getValues() != null) {
                int offset = 0;
                for (Integer value : initial.getValues()) {
                    new LiAsm(Register.T0, value);
                    new MemAsm(MemAsm.Op.SW, Register.T0, name.substring(1), offset);
                    offset += 4;
                }
            }
        }
    }
}
