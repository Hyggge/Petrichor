package back_end.mips;

import back_end.mips.assembly.Assembly;

import java.util.LinkedList;

public class AssemblyTable {
    private LinkedList<Assembly> assemblyList;

    public AssemblyTable() {
        this.assemblyList = new LinkedList<>();
    }

    public void addAssembly(Assembly assembly) {
        this.assemblyList.add(assembly);
    }


}
