package front_end.AST.Exp;

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
        // function call
        if (children.get(0) instanceof TokenNode) {
            Token ident = ((TokenNode)children.get(0)).getToken();
            FuncSymbol funcSymbol = (FuncSymbol)SymbolManager.getInstance().getSymbolByName(ident.getValue());
            // check Error c
            if (funcSymbol == null) {
                Printer.printErrorMsg(ident.getLineNumber(), ErrorType.c);
                super.checkError();
            }
            // call the function
            else {
                SymbolManager.getInstance().enterFuncCall(funcSymbol);
                super.checkError();

                ArrayList<ValueType> RParamTypes = SymbolManager.getInstance().getRParamTypes();
                ArrayList<Integer> RParamDims = SymbolManager.getInstance().getRParamDims();
                ArrayList<ValueType> FParamTypes = funcSymbol.getFParamTypes();
                ArrayList<Integer> FParamDims = funcSymbol.getFParamDims();
                System.out.println(FParamTypes);
                System.out.println(FParamDims);

                System.out.println(RParamTypes);
                System.out.println(RParamDims);
                // check Error d
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
        else super.checkError();
    }
}
