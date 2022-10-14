package front_end.AST.Stmt;

import front_end.AST.Exp.LValExp;
import front_end.AST.Node;
import utils.ErrorType;
import utils.Printer;
import utils.SyntaxVarType;

import java.util.ArrayList;

// GetIntStmt ==> LVal '=' 'getint''('')'';'
public class GetIntStmt extends Stmt {
    public GetIntStmt(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public void checkError() {
        // check Error h
        LValExp lValExp = (LValExp) children.get(0);
        if (lValExp.isConst()) {
            Printer.addErrorMsg(lValExp.getEndLine(), ErrorType.h);
        }
        super.checkError();
    }
}
