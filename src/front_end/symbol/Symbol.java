package front_end.symbol;


import utils.SymbolType;

public class Symbol {
    protected String symbolName;
    protected SymbolType symbolType;

    public Symbol(String symbolName, SymbolType symbolType) {
        this.symbolName = symbolName;
        this.symbolType = symbolType;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public SymbolType getSymbolType() {
        return symbolType;
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "symbolName='" + symbolName + '\'' +
                ", symbolType=" + symbolType +
                '}';
    }
}
