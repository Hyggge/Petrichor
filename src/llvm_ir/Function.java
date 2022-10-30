package llvm_ir;

import llvm_ir.type.OtherType;
import llvm_ir.type.Type;

import java.util.ArrayList;
import java.util.LinkedList;

public class Function extends User{
    private ArrayList<Param> paramList;
    private LinkedList<BasicBlock> BBList;
    private Type retType;

    public Function(String name, Type retType) {
        super(OtherType.FUNCTION, name);
        this.retType = retType;
        this.paramList = new ArrayList<>();
        this.BBList = new LinkedList<>();
    }

    public void addParam(Param param) {
        paramList.add(param);
    }

    public void addBB(BasicBlock bb) {
        BBList.add(bb);
    }

}
