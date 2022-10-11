package front_end.symbol;

import utils.SymbolType;
import utils.ValueType;

public class VarSymbol extends Symbol {
    private int dim;
    private ValueType valueType;

    public VarSymbol(String name, SymbolType symbolType) {
        super(name, symbolType);
    }
}
