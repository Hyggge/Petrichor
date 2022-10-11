package front_end.symbol;

import java.util.HashSet;
import java.util.Iterator;

public class SymbolTable {
    private HashSet<Symbol> symbols;

    public SymbolTable() {
        this.symbols = new HashSet<>();
    }

    public boolean has(String name) {
        Iterator<Symbol> iterator = symbols.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getSymbolName().equals(name)) {
                return true;
            }
        }
        return false;
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
