package front_end.AST.Func;

import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.symbol.FuncSymbol;
import utils.SymbolType;
import utils.SyntaxVarType;
import utils.ValueType;

import java.util.ArrayList;

public class FuncDef extends Node {
    private FuncSymbol symbol;

    public FuncDef(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
        this.symbol = createSymbol();
    }

    private FuncSymbol createSymbol() {
        // FuncType Ident '(' [FuncFormalParams] ')' Block
        String symbolName = ((TokenNode)children.get(1)).getToken().getValue();
        SymbolType symbolType = SymbolType.SYMBOL_FUNC;
        ValueType returnType = ((FuncType)children.get(0)).getReturnType();
        ArrayList<ValueType> FParamTypes = new ArrayList<>();
        ArrayList<Integer> FParamDims = new ArrayList<>();

        if (children.get(3) instanceof FuncFormalParams) {
            FuncFormalParams funcFormalParams = (FuncFormalParams)children.get(3);
            FParamTypes = funcFormalParams.getFParamTypes();
            FParamDims = funcFormalParams.getFParamDims();
        }

        return new FuncSymbol(symbolName, symbolType, returnType, FParamTypes, FParamDims);
    }
}
