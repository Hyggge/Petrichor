package back_end.mips.assembly;

import back_end.mips.Register;

public class LaAsm extends Assembly {
    private Register target;
    private String lable;

    public LaAsm(Register target, String lable) {
        this.target = target;
        this.lable = lable;
    }

    @Override
    public String toString() {
        return "la " + target + ", " + lable;
    }
}
