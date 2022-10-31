package llvm_ir.instr;

import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.BaseType;

public class AluInstr extends Instr {
    public enum Op {
        ADD,
        SUB,
        MOD,
        MUL,
        SDIV,
        AND,
        OR
    }

    private Op op;
    private Value operand1;
    private Value operand2;

    public AluInstr(String name, Op op, Value operand1, Value operand2) {
        super(BaseType.INT32, name, InstrType.ALU);
        this.op = op;
        this.operand1 = operand1;
        this.operand2 = operand2;
        addOperands(operand1);
        addOperands(operand2);
    }

    @Override
    public String toString() {
        return name + " = " + op.toString().toLowerCase() + " i32 " + operand1.getName() + ", " + operand2.getName();
    }
}
