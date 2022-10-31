package llvm_ir;

import java.util.LinkedList;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(globalVarList.stream().map(globalVar -> globalVar.toString()).collect(Collectors.joining("\n")));
        sb.append("\n\n");
        sb.append(functionList.stream().map(function -> function.toString()).collect(Collectors.joining("\n\n")));
        return sb.toString();
    }
}
