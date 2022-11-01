package back_end.mips;

import back_end.mips.assembly.Assembly;
import llvm_ir.Function;
import llvm_ir.Value;

import java.util.HashMap;

public class MipsBuilder {
    private static MipsBuilder mipsBuilder = new MipsBuilder();
    public static MipsBuilder getInstance() {return  mipsBuilder;}

    private Function curFunction;
    private int curStackOffset;
    private HashMap<Value, Integer> stackOffsetMap;
    private AssemblyTable assemblyTable;

    private MipsBuilder() {
        this.curFunction = null;
        this.assemblyTable = new AssemblyTable();
    }


    public void enterFunc(Function newFunction) {
        this.curFunction = newFunction;
        this.curStackOffset = 0;
        this.stackOffsetMap = new HashMap<>();
    }

    public void addValueToStack(Value value) {
        stackOffsetMap.put(value, curStackOffset);
        curStackOffset += 4;
    }

    public int getStackOffset(Value value) {
        return stackOffsetMap.get(value);
    }

    public void addAssembly(Assembly assembly) {
        assemblyTable.addAssembly(assembly);
    }

    public AssemblyTable getAssemblyTable() {
        return assemblyTable;
    }






}
