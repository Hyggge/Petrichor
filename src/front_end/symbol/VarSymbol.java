package front_end.symbol;

import llvm_ir.Value;
import llvm_ir.initial.Initial;
import utils.SymbolType;
import utils.ValueType;

import java.util.ArrayList;

public class VarSymbol extends Symbol {
    private ValueType valueType;
    private int dim;
    private ArrayList<Integer> lenList;
    private Initial initial;
    private boolean isGlobal;
    private Value llvmValue;

    public VarSymbol(String symbolName, SymbolType symbolType, ValueType valueType, int dim, ArrayList<Integer> lenList) {
        super(symbolName, symbolType);
        this.valueType = valueType;
        this.dim = dim;
        this.lenList = lenList;
        this.initial = null;
        this.llvmValue = null;
        this.isGlobal = SymbolManager.getInstance().isGlobal();
    }

    // 对于全局变量来说初始值是可以确定的
    public VarSymbol(String symbolName, SymbolType symbolType, ValueType valueType, int dim, ArrayList<Integer> lenList, Initial initial) {
        super(symbolName, symbolType);
        this.valueType = valueType;
        this.dim = dim;
        this.lenList = lenList;
        this.initial = initial;
        this.llvmValue = null;
        this.isGlobal = SymbolManager.getInstance().isGlobal();
    }

    public ValueType getValueType() {
        return valueType;
    }

    public int getDim() {
        return dim;
    }

    public ArrayList<Integer> getLenList() {
        return lenList;
    }

    public int getTotLen() {
        int totLen = 1;
        for (int i = 0; i < dim; i++) {
            totLen *= lenList.get(i);
        }
        return totLen;
    }

    public Initial getInitial() {
        return initial;
    }

    public int getConstValue() {
        return initial.getValues().get(0);
    }

    public int getConstValue(int idx) {
        return initial.getValues().get(idx);
    }

    public int getConstValue(int idx1, int idx2) {
        if (idx1 == 0) return initial.getValues().get(idx2);
        return initial.getValues().get(idx1 * lenList.get(1) + idx2);
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public Value getLlvmValue() {
        return llvmValue;
    }

    public void setLlvmValue(Value llvmValue) {
        this.llvmValue = llvmValue;
    }

    @Override
    public String toString() {
        return super.toString() + "  >>>  " +
                "VarSymbol{" +
                "valueType=" + valueType +
                ", dim=" + dim +
                ", lenList=" + lenList +
                ", initial=" + initial +
                ", isGlobal=" + isGlobal +
                ", llvmValue=" + llvmValue +
                '}';
    }
}
