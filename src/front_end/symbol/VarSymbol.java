package front_end.symbol;

import utils.SymbolType;
import utils.ValueType;

import java.util.ArrayList;

public class VarSymbol extends Symbol {
    private ValueType valueType;
    private int dim;
    private ArrayList<Integer> lenList;

    public VarSymbol(String symbolName, SymbolType symbolType, ValueType valueType, int dim) {
        super(symbolName, symbolType);
        this.valueType = valueType;
        this.dim = dim;
        this.lenList = null; // TODO
    }

    public ValueType getValueType() {
        return valueType;
    }

    public int getDim() {
        return dim;
    }

    @Override
    public String toString() {
        return  super.toString() + "  >>>  " +
                "VarSymbol{" +
                "valueType=" + valueType +
                ", dim=" + dim +
                ", lenList=" + lenList +
                '}';
    }
}
