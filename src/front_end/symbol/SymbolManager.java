package front_end.symbol;

import utils.ValueType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

public class SymbolManager {
    private static final SymbolManager MANAGER = new SymbolManager();
    private Stack<SymbolTable> symbolTables;
    private HashMap<String, Stack<SymbolTable>> symbolNameMap;
    private FuncSymbol latestFunc; // for check return sentence
    private int loopDepth; // for check continue and break

    // for check match of FParam and RParam
    private FuncSymbol calledFunc;
    private int recvParamNum;
    private ArrayList<ValueType> RParamTypes;
    private ArrayList<Integer> RParamDims;

    private SymbolManager() {
        this.symbolTables = new Stack<>();
        this.symbolNameMap = new HashMap<>();
        this.latestFunc = null;
        this.loopDepth = 0;

        this.calledFunc = null;
        this.recvParamNum = 0;
        this.RParamTypes = new ArrayList<>();
        this.RParamDims = new ArrayList<>();
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

    public void enterFuncDef(FuncSymbol symbol) {
        this.latestFunc = symbol;
        enterBlock();
    }

    public void leaveFuncDef() {
        this.latestFunc = null;
        leaveBlock();
    }

    public void enterFuncCall(FuncSymbol funcSymbol) {
        this.calledFunc = funcSymbol;
        this.recvParamNum = 0;
        this.RParamTypes = new ArrayList<>();
        this.RParamDims = new ArrayList<>();
    }

    public void leaveFuncCall() {
        this.calledFunc = null;
    }

    public void enterLoop() {
        this.loopDepth++;
    }

    public void leaveLoop() {
        this.loopDepth--;
    }

    // getter and setter
    public int getLoopDepth() {
        return loopDepth;
    }

    public FuncSymbol getLatestFunc() {
        return latestFunc;
    }

    public FuncSymbol getCalledFunc() {
        return calledFunc;
    }

    public int getRecvParamNum() {
        return this.recvParamNum;
    }

    public ArrayList<ValueType> getRParamTypes() {
        return RParamTypes;
    }

    public ArrayList<Integer> getRParamDims() {
        return RParamDims;
    }

    public void addRParamInfo(ValueType valueType, Integer dim) {
        if (recvParamNum >= RParamTypes.size()) {
            this.RParamTypes.add(valueType);
            this.RParamDims.add(dim);
        }
    }

    public void addRecvParamNum () {
        this.recvParamNum++;
    }
}
