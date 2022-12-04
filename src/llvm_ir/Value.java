package llvm_ir;

import llvm_ir.type.LLVMType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

public class Value {

    protected LLVMType type;
    protected String name;
    protected ArrayList<Use> useList;

    public Value(LLVMType type, String name) {
        this.type = type;
        this.name = name;
        this.useList = new ArrayList<>();
    }

    public void addUse(User user) {
        Use use = new Use(user, this);
        useList.add(use);
    }

    public void deleteUser(User user) {
        Iterator<Use> iterator = useList.iterator();
        while (iterator.hasNext()) {
            Use use = iterator.next();
            if (use.getUser() == user) {
                iterator.remove();
                break;
            }
        }
    }

    public void modifyAllUseThisToNewValue(Value newValue) {
        ArrayList<User> users = useList.stream().map(Use::getUser).collect(Collectors.toCollection(ArrayList::new));
        for (User user : users) {
            boolean res = user.modifyOperand(this, newValue);
            assert res;
        }
    }

    public LLVMType getType() {
        return type;
    }

    public ArrayList<Use> getUseList() {
        return useList;
    }

    public String getName() {
        return name;
    }



    public void toAssembly() {
    }
}
