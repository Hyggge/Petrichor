package front_end.symbol;

import utils.SymbolType;
import utils.ValueType;

import java.util.ArrayList;

public class FuncSymbol extends Symbol {
    private ValueType returnType;
    private ArrayList<ValueType> FParamTypes;
    private ArrayList<Integer> FParamDims;

    public FuncSymbol(String symbolName, SymbolType symbolType, ValueType returnType) {
        super(symbolName, symbolType);
        this.returnType = returnType;
        this.FParamDims = null;
        this.FParamTypes = null;
    }

    public FuncSymbol(String symbolName, SymbolType symbolType, ValueType returnType,
                      ArrayList<ValueType> FParamTypes, ArrayList<Integer> FParamDims) {
        super(symbolName, symbolType);
        this.returnType = returnType;
        this.FParamTypes = FParamTypes;
        this.FParamDims = FParamDims;
    }

    public void setParamInfo(ArrayList<ValueType> FParamTypes, ArrayList<Integer> FParamDims) {
        this.FParamTypes = FParamTypes;
        this.FParamDims = FParamDims;
    }

    public ValueType getReturnType() {
        return returnType;
    }

    public ArrayList<ValueType> getFParamTypes() {
        return FParamTypes;
    }

    public ArrayList<Integer> getFParamDims() {
        return FParamDims;
    }

    @Override
    public String toString() {
        return super.toString() + "  >>>  " +
                "FuncSymbol{" +
                "returnType=" + returnType +
                ", FParamTypes=" + FParamTypes +
                ", FParamDims=" + FParamDims +
                '}';
    }
}
