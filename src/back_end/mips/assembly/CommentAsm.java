package back_end.mips.assembly;

public class CommentAsm extends Assembly{
    private String comment;

    public CommentAsm(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return comment;
    }
}
