package llvm_ir.initial;

import llvm_ir.type.Type;

import java.util.ArrayList;

public class Initial {
    private Type type;
    private ArrayList<Integer> values;

    public Initial(Type type, ArrayList<Integer> values) {
        this.type = type;
        this.values = values;
    }

    public Type getType() {
        return type;
    }


    public ArrayList<Integer> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "Initial{" +
                "type=" + type +
                ", values=" + values +
                '}';
    }
}
