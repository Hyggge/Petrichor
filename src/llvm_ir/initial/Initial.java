package llvm_ir.initial;

import llvm_ir.type.Type;

import java.util.ArrayList;
import java.util.stream.Collectors;

// 只有全局变量，全局常量，局部常量的Symbol中才有Initial（因为这些的初始值都可以计算出）
// 对于全局变量和常量，Initial会直接加入对应的GlobalVal
// 对于局部常量，Initial只是起到了优化作用，仍然需要通过store指令将初始值存入对应的位置
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

    public boolean noInitialValue() {
        return values == null;
    }

    @Override
    public String toString() {
        // 如果有初始值 （全局常量或者全局变量）
        if (values != null) {
            if (type.isInt32()) return type + " " + values.get(0);
            else {
                String valueInfo = values.stream().map(number -> "i32 " + number).collect(Collectors.joining(", "));
                return type + " [" + valueInfo + "]";
            }
        }
        // 如果没有初始化值（全局变量）
        else {
            if (type.isInt32()) return type + " 0";
            else return type + " zeroinitializer";
        }
    }
}
