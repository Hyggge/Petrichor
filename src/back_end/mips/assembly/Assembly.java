package back_end.mips.assembly;

import back_end.mips.MipsBuilder;

public class Assembly {

    public Assembly() {
        MipsBuilder.getInstance().addAssembly(this);
    }

}
