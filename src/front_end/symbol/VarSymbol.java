package front_end.symbol;

import utils.SymbolType;
import utils.ValueType;

public class VarSymbol extends Symbol {
    private ValueType valueType;
    private int dim;

    public VarSymbol(String symbolName, SymbolType symbolType, ValueType valueType, int dim) {
        super(symbolName, symbolType);
        this.valueType = valueType;
        this.dim = dim;
    }
}
