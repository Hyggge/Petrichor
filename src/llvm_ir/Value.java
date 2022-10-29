package llvm_ir;

import llvm_ir.type.Type;

import java.util.ArrayList;

public class Value {
    protected Type type;
    protected String name;
    protected ArrayList<Use> useList;

//    public Value(Type type, String name, ArrayList<Use> useList) {
//        this.type = type;
//        this.name = name;
//        this.useList = useList;
//    }


}
