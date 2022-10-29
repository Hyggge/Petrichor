package llvm_ir;

import java.util.LinkedList;

public class BasicBlock {
    private LinkedList<Instruction> instrList;
    private Function parentFunction;
}
