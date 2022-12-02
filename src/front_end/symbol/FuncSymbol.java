package front_end.symbol;

import llvm_ir.Function;
import utils.SymbolType;
import utils.ValueType;

import java.util.ArrayList;

public class FuncSymbol extends Symbol {
    private ValueType returnType;
    private ArrayList<ValueType> FParamTypes;
    private ArrayList<Integer> FParamDims;
    private Function llvmValue;

    public FuncSymbol(String symbolName, ValueType returnType) {
        super(symbolName, SymbolType.SYMBOL_FUNC);
        this.returnType = returnType;
        this.FParamDims = null;
        this.FParamTypes = null;
        this.llvmValue = null;
    }

    public FuncSymbol(String symbolName, ValueType returnType, ArrayList<ValueType> FParamTypes, ArrayList<Integer> FParamDims) {
        super(symbolName, SymbolType.SYMBOL_FUNC);
        this.returnType = returnType;
        this.FParamTypes = FParamTypes;
        this.FParamDims = FParamDims;
        this.llvmValue = null;
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

    public Function getLlvmValue() {
        return llvmValue;
    }

    public void setLlvmValue(Function llvmValue) {
        this.llvmValue = llvmValue;
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
