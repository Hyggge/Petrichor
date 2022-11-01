package back_end.mips.assembly;

import back_end.mips.Register;

public class MoveAsm extends Assembly {
    private Register dst;
    private Register src;

    public MoveAsm(Register dst, Register src) {
        this.dst = dst;
        this.src = src;
    }

    @Override
    public String toString() {
        return "move " + dst + " " + src;
    }
}
