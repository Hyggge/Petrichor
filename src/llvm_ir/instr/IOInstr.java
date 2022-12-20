package llvm_ir.instr;

import back_end.mips.MipsBuilder;
import back_end.mips.Register;
import back_end.mips.assembly.LaAsm;
import back_end.mips.assembly.LiAsm;
import back_end.mips.assembly.MemAsm;
import back_end.mips.assembly.MoveAsm;
import back_end.mips.assembly.SyscallAsm;
import llvm_ir.Constant;
import llvm_ir.Instr;
import llvm_ir.StringLiteral;
import llvm_ir.UndefinedValue;
import llvm_ir.Value;
import llvm_ir.type.BaseType;
import llvm_ir.type.PointerType;
import llvm_ir.type.LLVMType;

public class IOInstr extends Instr {

    public IOInstr(LLVMType type, String name, InstrType instrType) {
        super(type, name, instrType);
    }

    public static class GetInt extends IOInstr{
        public GetInt(String name) {
            super(BaseType.INT32, name, InstrType.IO);
        }
        public static String getDeclare() {
            return "declare i32 @getint(...) ";
        }

        @Override
        public boolean canBeUsed() {
            return true;
        }

        @Override
        public String toString() {
            return name + " = call i32 (...) @getint()";
        }

        @Override
        public void toAssembly() {
            super.toAssembly();
            new LiAsm(Register.V0, 5);
            new SyscallAsm();
            // 如果this有对应的寄存器，直接将V0的值保存在该寄存器中即可
            if (MipsBuilder.getInstance().getRegOf(this) != null) {
                Register reg = MipsBuilder.getInstance().getRegOf(this);
                new MoveAsm(reg, Register.V0);
            }
            // 否则，为当前value开辟栈空间，将v0中读取到的值存入
            else {
                MipsBuilder.getInstance().subCurOffset(4);
                int curOffset = MipsBuilder.getInstance().getCurOffset();
                MipsBuilder.getInstance().addValueOffsetMap(this, curOffset);
                new MemAsm(MemAsm.Op.SW, Register.V0, Register.SP, curOffset);
            }
        }
    }

    public static class PutInt extends IOInstr {

        public PutInt(String name, Value target) {
            super(BaseType.VOID, name, InstrType.IO);
            addOperands(target);
        }

        public Value getTarget() {
            return operands.get(0);
        }

        public static String getDeclare() {
            return "declare void @putint(i32)";
        }

        @Override
        public String toString() {
            Value target = getTarget();
            return "call void @putint(i32 " + target.getName() + ")";
        }

        @Override
        public void toAssembly() {
            super.toAssembly();
            Value target = getTarget();
            // 将target的值保存到a0中
            if (target instanceof Constant || target instanceof UndefinedValue) {
                new LiAsm(Register.A0, Integer.parseInt(target.getName()));
            }
            else if (MipsBuilder.getInstance().getRegOf(target) != null) {
                Register reg = MipsBuilder.getInstance().getRegOf(target);
                new MoveAsm(Register.A0, reg);
            }
            else {
                Integer offset = MipsBuilder.getInstance().getOffsetOf(target);
                if (offset == null) {
                    MipsBuilder.getInstance().subCurOffset(4);
                    offset = MipsBuilder.getInstance().getCurOffset();
                    MipsBuilder.getInstance().addValueOffsetMap(target, offset);
                }
                new MemAsm(MemAsm.Op.LW, Register.A0, Register.SP, offset);
            }
            new LiAsm(Register.V0, 1);
            new SyscallAsm();
        }
    }

    public static class PutStr extends IOInstr {
        private StringLiteral stringLiteral;

        public PutStr(String name, StringLiteral stringLiteral) {
            super(BaseType.VOID, name, InstrType.IO);
            this.stringLiteral = stringLiteral;
        }

        public static String getDeclare() {
            return "declare void @putstr(i8* )";
        }

        @Override
        public String toString() {
            PointerType pointerType = (PointerType) stringLiteral.getType();
            return "call void @putstr(i8* getelementptr inbounds (" +
                    pointerType.getTargetType() + ", " +
                    pointerType + " " +
                    stringLiteral.getName() +", i64 0, i64 0))";
        }

        @Override
        public void toAssembly() {
            super.toAssembly();
            // 将StringLateral的地址保存到a0中
            new LaAsm(Register.A0, stringLiteral.getName().substring(1));
            new LiAsm(Register.V0, 4);
            new SyscallAsm();
        }
    }



}
