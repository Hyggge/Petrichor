package llvm_ir;

public class Loop {
    private BasicBlock condBlock;
    private BasicBlock loopBodyBlock;
    private BasicBlock followBlock;

    public Loop(BasicBlock condBlock, BasicBlock loopBodyBlock, BasicBlock followBlock) {
        this.condBlock = condBlock;
        this.loopBodyBlock = loopBodyBlock;
        this.followBlock = followBlock;
    }

    public BasicBlock getCondBlock() {
        return condBlock;
    }

    public BasicBlock getLoopBodyBlock() {
        return loopBodyBlock;
    }

    public BasicBlock getFollowBlock() {
        return followBlock;
    }
}
