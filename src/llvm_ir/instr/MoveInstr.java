package llvm_ir.instr;

import back_end.mips.MipsBuilder;
import back_end.mips.Register;
import back_end.mips.assembly.LiAsm;
import back_end.mips.assembly.MemAsm;
import llvm_ir.Constant;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.type.BaseType;

public class MoveInstr extends Instr {

    public MoveInstr(String name, Value dst, Value src) {
        super(BaseType.VOID, name, InstrType.MOVE);
        addOperands(dst);
        addOperands(src);
    }

    public Value getDst() {
        return operands.get(0);
    }

    public Value getSrc() {
        return operands.get(1);
    }

    public void setDst(Value dst) {
        operands.set(0,dst);
    }

    public void setSrc(Value src) {
        operands.set(1, src);
    }



    @Override
    public String toString() {
        Value dst = getDst();
        Value src = getSrc();
        return "move " + dst.getType() + " " + dst.getName() + ", " + src.getName();
    }

    @Override
    public void toAssembly() {
        super.toAssembly();
        Value dst = getDst();
        Value src = getSrc();

        // 先将src的值取出来, 放到t0中
        if (src instanceof Constant) {
            new LiAsm(Register.T0, ((Constant)src).getValue());
        }
        else {
            Integer srcOffset = MipsBuilder.getInstance().getOffsetOf(src);
            // move指令的src可能在后面某个bb中被定义，这里我们先为其分配栈空间
            if (srcOffset == null) {
                MipsBuilder.getInstance().subCurOffset(4);
                srcOffset = MipsBuilder.getInstance().getCurOffset();
                MipsBuilder.getInstance().addValueOffsetMap(src, srcOffset);
            }
            new MemAsm(MemAsm.Op.LW, Register.T0, Register.SP, srcOffset);
        }

        Integer offset = MipsBuilder.getInstance().getOffsetOf(dst);
        // 如果dst没有被定义，需要为dst开一个栈空间，并将t0的值store到堆栈上
        if (offset == null) {
            MipsBuilder.getInstance().subCurOffset(4);
            int curOffset = MipsBuilder.getInstance().getCurOffset();
            MipsBuilder.getInstance().addValueOffsetMap(dst, curOffset);
            new MemAsm(MemAsm.Op.SW, Register.T0, Register.SP, curOffset);
        }
        // 如果dst被定义了，直接将t0写入堆栈
        else {
            new MemAsm(MemAsm.Op.SW, Register.T0, Register.SP, offset);
        }

    }
}
