package front_end.symbol;

import utils.SymbolType;
import utils.ValueType;

import java.util.ArrayList;

public class VarSymbol extends Symbol {
    private ValueType valueType;
    private int dim;
    private ArrayList<Integer> lenList;

    public VarSymbol(String symbolName, SymbolType symbolType, ValueType valueType, int dim, ArrayList<Integer> lenList) {
        super(symbolName, symbolType);
        this.valueType = valueType;
        this.dim = dim;
        this.lenList = lenList;
    }
}
