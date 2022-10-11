package front_end.symbol;

import utils.SymbolType;
import utils.ValueType;

import java.util.ArrayList;

public class FuncSymbol extends Symbol {
    private ValueType returnType;
    private ArrayList<ValueType> FParamTypes;
    private ArrayList<Integer> FParamDims;

    public FuncSymbol(String symbolName, SymbolType symbolType, ValueType returnType,
                      ArrayList<ValueType> FParamTypes, ArrayList<Integer> FParamDims) {
        super(symbolName, symbolType);
        this.returnType = returnType;
        this.FParamTypes = FParamTypes;
        this.FParamDims = FParamDims;
    }
}
