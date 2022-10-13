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
    public Integer getDim() {
        // function call;
        if (children.get(0) instanceof TokenNode) {
            String funcName = ((TokenNode) children.get(0)).getToken().getValue();
            FuncSymbol funcSymbol = (FuncSymbol) SymbolManager.getInstance().getSymbolByName(funcName);
            if (funcSymbol.getReturnType() == ValueType.INT) return 0;
            else return null;
        }
        for (Node child : children) {
            if (child.getDim() != null) return child.getDim();
        }
        return null;
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
                super.checkError(); return;
            }
            // call the function
            ArrayList<Integer> FParamDims = funcSymbol.getFParamDims();
            ArrayList<Integer> RParamDims = new ArrayList<>();
            if (children.size() > 2 &&  children.get(2) instanceof FuncRealParams) {
                RParamDims = ((FuncRealParams) children.get(2)).getRParamDims();
            }

            System.out.println(FParamDims);
            System.out.println(RParamDims);
            // check Error d
            if (RParamDims.size() != FParamDims.size()) {
                Printer.printErrorMsg(ident.getLineNumber(), ErrorType.d);
                super.checkError(); return;
            }
            // check Error e
            for (int i = 0; i < RParamDims.size(); i++) {
                if (! FParamDims.get(i).equals(RParamDims.get(i))) {
                    Printer.printErrorMsg(ident.getLineNumber(), ErrorType.e);
                    super.checkError(); return; // only print once
                }
            }

        }
        super.checkError();
    }
}
