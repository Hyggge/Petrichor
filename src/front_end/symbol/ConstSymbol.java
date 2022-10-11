package front_end.symbol;

import utils.SymbolType;
import utils.ValueType;

public class ConstSymbol extends Symbol {
    private int dim;
    private ValueType valueType;

    public ConstSymbol(String name, SymbolType symbolType) {
        super(name, symbolType);
    }
}
