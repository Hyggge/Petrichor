package llvm_ir.initial;

import llvm_ir.type.Type;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
        if (type.isInt32()) {
            return type + " " + values.get(0);
        }
        else {
            String valueInfo = values.stream().map(number -> "i32 " + number).collect(Collectors.joining(", "));
            return type + " [" + valueInfo + "]";
        }
    }
}
