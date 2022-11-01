package llvm_ir.instr;

import llvm_ir.Instr;
import llvm_ir.StringLiteral;
import llvm_ir.Value;
import llvm_ir.type.BaseType;
import llvm_ir.type.PointerType;
import llvm_ir.type.Type;

public class IOInstr extends Instr {

    public IOInstr(Type type, String name, InstrType instrType) {
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
        public String toString() {
            return name + " = call i32 (...) @getint()";
        }
    }

    public static class PutInt extends IOInstr {
        private Value target;

        public PutInt(String name, Value target) {
            super(BaseType.VOID, name, InstrType.IO);
            this.target = target;
        }

        public static String getDeclare() {
            return "declare void @putint(i32)";
        }

        @Override
        public String toString() {
            return "call void @putint(i32 " + target.getName() + ")";
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
    }



}
