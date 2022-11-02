package back_end.mips.assembly;

import back_end.mips.MipsBuilder;

public class Assembly {

    public Assembly() {
        if (this instanceof GlobalAsm) {
            MipsBuilder.getInstance().addAssemblyToData(this);
        } else {
            MipsBuilder.getInstance().addAssemblyToText(this);
        }
    }

}
