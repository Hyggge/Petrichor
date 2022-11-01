package llvm_ir;

import llvm_ir.instr.IOInstr;
import llvm_ir.type.OtherType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Module extends Value{
    private final ArrayList<String> declareList;
    private final LinkedList<StringLiteral> stringLiterals;
    private final LinkedList<GlobalVar> globalVarList;
    private final LinkedList<Function> functionList;

    public Module() {
        super(OtherType.MODULE, "module");
        this.globalVarList = new LinkedList<>();
        this.functionList = new LinkedList<>();
        this.stringLiterals = new LinkedList<>();
        this.declareList = new ArrayList<>();
        // 加入IO相关函数的声明
        this.declareList.add(IOInstr.GetInt.getDeclare());
        this.declareList.add(IOInstr.PutInt.getDeclare());
        this.declareList.add(IOInstr.PutStr.getDeclare());
    }

    public void addGlobalVal(GlobalVar globalVar) {
        globalVarList.add(globalVar);
    }

    public void addFunction(Function function) {
        functionList.add(function);
    }

    public void addStringLiteral(StringLiteral stringLiteral) {
        this.stringLiterals.add(stringLiteral);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(declareList.stream().collect(Collectors.joining("\n")));
        sb.append("\n\n");
        sb.append(stringLiterals.stream().map(stringLiteral -> stringLiteral.toString()).collect(Collectors.joining("\n")));
        sb.append("\n\n");
        sb.append(globalVarList.stream().map(globalVar -> globalVar.toString()).collect(Collectors.joining("\n")));
        sb.append("\n\n");
        sb.append(functionList.stream().map(function -> function.toString()).collect(Collectors.joining("\n\n")));
        return sb.toString();
    }

    @Override
    public void toAssembly() {
        for (StringLiteral stringLiteral : stringLiterals) {
            stringLiteral.toAssembly();
        }
        for (GlobalVar globalVar : globalVarList) {
            globalVar.toAssembly();
        }
        for (Function function : functionList) {
            function.toAssembly();
        }
    }
}
