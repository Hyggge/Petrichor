package front_end.AST.Func;

import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.symbol.FuncSymbol;
import front_end.symbol.SymbolManager;
import utils.ErrorType;
import utils.Printer;
import utils.SymbolType;
import utils.SyntaxVarType;
import utils.ValueType;

import java.util.ArrayList;

public class FuncDef extends Node {
    private FuncSymbol symbol;

    public FuncDef(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
        this.symbol = createSymbol();
        System.out.println(this.symbol);
    }

    private FuncSymbol createSymbol() {
        // FuncType Ident '(' [FuncFormalParams] ')' Block
        String symbolName = ((TokenNode)children.get(1)).getToken().getValue();
        SymbolType symbolType = SymbolType.SYMBOL_FUNC;
        ValueType returnType = ((FuncType)children.get(0)).getReturnType();
        ArrayList<ValueType> FParamTypes = new ArrayList<>();
        ArrayList<Integer> FParamDims = new ArrayList<>();

        // get FParamTypes and FParamDims
        if (children.get(3) instanceof FuncFormalParams) {
            FuncFormalParams funcFormalParams = (FuncFormalParams)children.get(3);
            FParamTypes = funcFormalParams.getFParamTypes();
            FParamDims = funcFormalParams.getFParamDims();
        }

        return new FuncSymbol(symbolName, symbolType, returnType, FParamTypes, FParamDims);
    }

    @Override
    public void checkError() {
        boolean res = SymbolManager.getInstance().addSymbol(symbol);
        if (! res) Printer.printErrorMsg(children.get(1).getStartLine(), ErrorType.b);
        SymbolManager.getInstance().enterFunc(symbol);
        super.checkError();
        SymbolManager.getInstance().leaveFunc();
    }
}
