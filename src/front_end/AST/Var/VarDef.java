package front_end.AST.Var;

import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.symbol.SymbolManager;
import front_end.symbol.VarSymbol;
import utils.SymbolType;
import utils.SyntaxVarType;
import utils.TokenType;
import utils.ValueType;

import java.util.ArrayList;

public class VarDef extends Node {
    private VarSymbol symbol;

    public VarDef(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
        this.symbol = createSymbol();
        System.out.println(this.symbol);
    }

    private VarSymbol createSymbol() {
        // VarDef  ==>  Ident {'[' Exp ']'} ['='  InitVal]
        String symbolName = ((TokenNode)children.get(0)).getToken().getValue();
        SymbolType symbolType = SymbolType.SYMBOL_VAR;
        ValueType valueType = ValueType.INT;
        int dim = 0;

        // get dim
        for (Node child : children) {
            if (child instanceof TokenNode && ((TokenNode)child).getToken().getType() == TokenType.LBRACK) {
                dim++;
            }
        }
        return new VarSymbol(symbolName, symbolType, valueType, dim);
    }

    @Override
    public void checkError() {
        SymbolManager.getInstance().addSymbol(symbol);
        super.checkError();
    }
}
