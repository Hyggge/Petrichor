package llvm_ir;

public class Use {
    private User user;
    private Value userd;

    public Use(User user, Value userd) {
        this.user = user;
        this.userd = userd;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Value getUserd() {
        return userd;
    }

    public void setUserd(Value userd) {
        this.userd = userd;
    }
}
