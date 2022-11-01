package back_end.mips;

import back_end.mips.assembly.Assembly;
import back_end.mips.assembly.GlobalAsm;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class AssemblyTable {
    private LinkedList<Assembly> dataSegent;
    private LinkedList<Assembly> textSegment;

    public AssemblyTable() {
        this.dataSegent = new LinkedList<>();
        this.textSegment = new LinkedList<>();
    }

    public void addAssembly(Assembly assembly) {
        if (assembly instanceof GlobalAsm) {
            dataSegent.add(assembly);
        } else {
            textSegment.add(assembly);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(".data\n");
        sb.append(dataSegent.stream().map(assembly -> assembly.toString()).collect(Collectors.joining("\n"))) ;
        sb.append("\n\n\n.text\n");
        sb.append(textSegment.stream().map(assembly -> assembly.toString()).collect(Collectors.joining("\n")));
        return sb.toString();
    }
}
