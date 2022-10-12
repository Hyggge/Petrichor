package front_end.symbol;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

public class SymbolManager {
    private static final SymbolManager MANAGER = new SymbolManager();
    private Stack<SymbolTable> symbolTables;
    private HashMap<String, Stack<SymbolTable>> symbolNameMap;
    private FuncSymbol latestFunc; // for check return sentence
    private int loopDepth; // for check continue and break

    private SymbolManager() {
        this.symbolTables = new Stack<>();
        this.symbolNameMap = new HashMap<>();
        this.latestFunc = null;
        this.loopDepth = 0;
    }

    public static SymbolManager getInstance() {
        return MANAGER;
    }

    public boolean addSymbol(Symbol symbol) {
        SymbolTable topTable = this.symbolTables.peek();
        // insert failed
        if (topTable.getSymbolByName(symbol.getSymbolName()) != null) return false;
        // insert success
        topTable.addSymbol(symbol);
        // maintain symbolNameMap
        symbolNameMap.compute(symbol.getSymbolName(), (k, v) -> {
            if (v == null) v = new Stack<>();
            v.add(topTable);
            return v;
        });
        return true;
    }

    public Symbol getSymbolByName (String name) {
        if (symbolNameMap.get(name) == null || symbolNameMap.get(name).isEmpty()) return null;
        SymbolTable targetTable = symbolNameMap.get(name).peek();
        return targetTable.getSymbolByName(name);
    }


    public void enterBlock() {
        SymbolTable symbolTable = new SymbolTable();
        this.symbolTables.push(symbolTable);
    }

    public void leaveBlock() {
        SymbolTable topTable = this.symbolTables.pop();
        Iterator<String> iterator = topTable.getNames().iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            symbolNameMap.get(name).pop();
        }
    }

    public void enterFunc(FuncSymbol symbol) {
        this.latestFunc = symbol;
        enterBlock();
    }

    public void leaveFunc() {
        this.latestFunc = null;
        leaveBlock();
    }

    public void enterLoop() {
        this.loopDepth++;
    }

    public void leaveLoop() {
        this.loopDepth--;
    }

    public int getLoopDepth() {
        return loopDepth;
    }


    public FuncSymbol getLatestFunc() {
        return latestFunc;
    }



}
