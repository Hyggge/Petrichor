package front_end.symbol;

import utils.SymbolType;
import utils.ValueType;

import java.util.ArrayList;

public class FuncSymbol extends Symbol{
    private ArrayList<ValueType> FParamTypes;
    private ArrayList<Integer> FParamDims;

    public FuncSymbol(String name, SymbolType symbolType) {
        super(name, symbolType);
    }
}
