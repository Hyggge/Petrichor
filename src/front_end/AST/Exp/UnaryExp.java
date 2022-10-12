package front_end.AST.Exp;

import front_end.AST.Func.FuncRealParams;
import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.lexer.Token;
import front_end.symbol.FuncSymbol;
import front_end.symbol.SymbolManager;
import utils.ErrorType;
import utils.Printer;
import utils.SyntaxVarType;
import utils.ValueType;

import java.util.ArrayList;

// UnaryExp ==> PrimaryExp | Ident '(' [FuncRealParams] ')' | UnaryOp UnaryExp
public class UnaryExp extends Node {
    public UnaryExp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public void checkError() {
        if (children.get(0) instanceof TokenNode) {
            // check Error c
            Token ident = ((TokenNode)children.get(0)).getToken();
            FuncSymbol funcSymbol = (FuncSymbol)SymbolManager.getInstance().getSymbolByName(ident.getValue());
            if (funcSymbol == null) {
                Printer.printErrorMsg(ident.getLineNumber(), ErrorType.c);
                super.checkError(); return;
            }

            if (children.get(2) instanceof FuncRealParams) {
                // check Error d
                FuncRealParams funcRealParams = (FuncRealParams)children.get(2);
                ArrayList<ValueType> RParamTypes = funcRealParams.getRParamTypes();
                ArrayList<Integer> RParamDims = funcRealParams.getRParamDims();
                ArrayList<ValueType> FParamTypes = funcSymbol.getFParamTypes();
                ArrayList<Integer> FParamDims = funcSymbol.getFParamDims();
                if (RParamTypes.size() != FParamTypes.size()) {
                    Printer.printErrorMsg(ident.getLineNumber(), ErrorType.d);
                    super.checkError(); return;
                }

                // check Error e
                for (int i = 0; i < RParamTypes.size(); i++) {
                    if (RParamTypes.get(i) != FParamTypes.get(i) || ! RParamDims.get(i).equals(FParamDims.get(i))) {
                        Printer.printErrorMsg(ident.getLineNumber(), ErrorType.e);
                        break; // only print once
                    }
                }
            }
        }
        super.checkError();
    }
}
