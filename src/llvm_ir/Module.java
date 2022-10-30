package llvm_ir;

import java.util.LinkedList;

public class Module {

    private final LinkedList<GlobalVar> globalVarList;
    private final LinkedList<Function> functionList;

    public Module() {
        this.globalVarList = new LinkedList<>();
        this.functionList = new LinkedList<>();
    }

    public void addGlobalVal(GlobalVar globalVar) {
        globalVarList.add(globalVar);
    }

    public void addFunction(Function function) {
        functionList.add(function);
    }

}
