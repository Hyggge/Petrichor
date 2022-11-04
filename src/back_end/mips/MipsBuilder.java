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

    public void addValueOffsetMap(Value value, int offset) {
        stackOffsetMap.put(value, offset);
    }

    public int getOffsetOf(Value value) {
        return stackOffsetMap.get(value);
    }

    public int getCurOffset() {
        return curStackOffset;
    }

    public void subCurOffset(int offset) {
        curStackOffset -= offset;
        assert curStackOffset >= 0;
    }

    public void addAssemblyToData(Assembly assembly) {
        assemblyTable.addAssemblyToData(assembly);
    }

    public void addAssemblyToText(Assembly assembly) {
        assemblyTable.addAssemblyToText(assembly);
    }

    public AssemblyTable getAssemblyTable() {
        return assemblyTable;
    }







}
