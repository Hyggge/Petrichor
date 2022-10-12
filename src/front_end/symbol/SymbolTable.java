package front_end.symbol;

import java.util.HashSet;
import java.util.Iterator;

public class SymbolTable {
    private HashSet<Symbol> symbols;

    public SymbolTable() {
        this.symbols = new HashSet<>();
    }

    public Symbol getSymbolByName(String name) {
        Iterator<Symbol> iterator = symbols.iterator();
        while (iterator.hasNext()) {
            Symbol symbol = iterator.next();
            if (symbol.getSymbolName().equals(name)) {
                return symbol;
            }
        }
        return null;
    }

    public HashSet<String> getNames() {
        HashSet<String> names  = new HashSet<>();
        Iterator<Symbol> iterator = symbols.iterator();
        while (iterator.hasNext()) {
            names.add(iterator.next().getSymbolName());
        }
        return names;
    }

    public void addSymbol(Symbol symbol) {
        symbols.add(symbol);
    }

}
