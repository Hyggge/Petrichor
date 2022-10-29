package llvm_ir;

import java.util.LinkedList;

public class Module {
    public static Module instance = new Module();

    private final LinkedList<GlobalVar> globalVarList;
    private final LinkedList<Function> functionList;

    private Module() {
        this.globalVarList = new LinkedList<>();
        this.functionList = new LinkedList<>();
    }

    public static Module getInstance() {
        return instance;
    }


}
