package back_end.mips.assembly;

public class LabelAsm extends Assembly{
    private String label;

    public LabelAsm(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label + ":";
    }
}
