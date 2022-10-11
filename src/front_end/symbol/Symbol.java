package front_end.symbol;


import utils.SymbolType;

public class Symbol {
    private String name;
    private SymbolType symbolType;

    public Symbol(String name, SymbolType symbolType) {
        this.name = name;
        this.symbolType = symbolType;
    }
}
