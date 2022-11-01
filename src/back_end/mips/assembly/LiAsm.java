package back_end.mips.assembly;

import back_end.mips.Register;

public class LiAsm extends Assembly {
    private Register rd;
    private Integer number;

    public LiAsm(Register rd, Integer number) {
        this.rd = rd;
        this.number = number;
    }

    @Override
    public String toString() {
        return "li " + rd + " " + number;
    }
}
