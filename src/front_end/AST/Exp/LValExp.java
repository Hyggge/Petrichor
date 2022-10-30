package front_end.AST.Exp;

import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.lexer.Token;
import front_end.symbol.ConstSymbol;
import front_end.symbol.Symbol;
import front_end.symbol.SymbolManager;
import front_end.symbol.VarSymbol;
import utils.ErrorType;
import utils.Printer;
import utils.SyntaxVarType;
import utils.TokenType;

import java.util.ArrayList;

// LValExp ==> Ident {'[' Exp ']'}
public class LValExp extends Node {
    public LValExp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public Integer getDim()  {
        Token ident = ((TokenNode)children.get(0)).getToken();
        Symbol symbol = SymbolManager.getInstance().getSymbolByName(ident.getValue());
        if (symbol == null) return null;
        // else
        int dim = 0;
        int cnt = 0;
        // get symbol's dim
        if (symbol instanceof VarSymbol) dim = ((VarSymbol)symbol).getDim();
        else if (symbol instanceof ConstSymbol) dim = ((ConstSymbol)symbol).getDim();
        // get number of '['
        for (Node child : children) {
            if (child instanceof TokenNode && ((TokenNode)child).getToken().getType() == TokenType.LBRACK) {
                cnt++;
            }
        }
        // lval dim = symbol dim - number of '['
        return dim - cnt;
    }

    @Override
    public int execute() {
        String ident = ((TokenNode)children.get(0)).getToken().getValue();
        Symbol symbol = SymbolManager.getInstance().getSymbolByName(ident);
        if (symbol == null) return 0;

        // 计算左值表达式中括号的个数和里面填写的值
        int num = 0;
        int[] lens = new int[2];
        for (Node child : children) {
            if (child instanceof Exp) {
                lens[num++] = child.execute();
            }
        }

        // 如果符号为常量符号
        if (symbol instanceof ConstSymbol) {
            ConstSymbol constSymbol = (ConstSymbol) symbol;
            // 如果是整数类型
            if (constSymbol.getDim() == 0) return constSymbol.getConstValue();
            // 如果是数组类型
            else if (constSymbol.getDim() == 1) return constSymbol.getConstValue(lens[0]);
            return constSymbol.getConstValue(lens[0], lens[1]);
        }
        // 如果符号是变量符号
        else {
            VarSymbol varSymbol = (VarSymbol) symbol;
            // 如果是整数类型
            if (varSymbol.getDim() == 0) return varSymbol.getConstValue();
            // 如果是数组类型
            else if (varSymbol.getDim() == 1) return varSymbol.getConstValue(lens[0]);
            else return varSymbol.getConstValue(lens[0], lens[1]);
        }

    }

    public boolean isConst() {
        String name = ((TokenNode)children.get(0)).getToken().getValue();
        Symbol symbol = SymbolManager.getInstance().getSymbolByName(name);
        if (symbol instanceof ConstSymbol) return true;
        return false;
    }

    @Override
    public void checkError() {
        // check Error c
        Token ident = ((TokenNode)children.get(0)).getToken();
        if (SymbolManager.getInstance().getSymbolByName(ident.getValue()) == null) {
            Printer.addErrorMsg(ident.getLineNumber(), ErrorType.c);
        }
        super.checkError();
    }
}
