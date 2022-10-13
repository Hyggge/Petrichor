package front_end.AST.Stmt;

import front_end.AST.Exp.Exp;
import front_end.AST.Node;
import front_end.symbol.FuncSymbol;
import front_end.symbol.SymbolManager;
import utils.ErrorType;
import utils.Printer;
import utils.SyntaxVarType;
import utils.ValueType;

import java.util.ArrayList;

// ReturnStmt ==> 'return' [Exp] ';'
public class ReturnStmt extends Stmt {
    public ReturnStmt(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public void checkError() {
        // check Error f
        if (children.size() >= 2 && children.get(1) instanceof Exp) {
            FuncSymbol func = SymbolManager.getInstance().getLatestFunc();
            if (func.getReturnType() == ValueType.VOID) {
                Printer.printErrorMsg(children.get(0).getStartLine(), ErrorType.f);
            }
        }

        super.checkError();
    }
}
