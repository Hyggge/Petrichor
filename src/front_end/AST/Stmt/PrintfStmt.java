package front_end.AST.Stmt;

import front_end.AST.Exp.Exp;
import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.lexer.Token;
import utils.ErrorType;
import utils.Printer;
import utils.SyntaxVarType;
import utils.TokenType;

import java.util.ArrayList;

// PrintfStmt ==> 'printf''('FormatString{','Exp}')'';'
public class PrintfStmt extends Stmt {
    private Token formatString;
    private ArrayList<Exp> expList;

    public PrintfStmt(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
        // get formatString and expList;
        this.expList = new ArrayList<>();
        for (Node child : children) {
            // formatString
            if (child instanceof TokenNode && ((TokenNode) child).getToken().getType() == TokenType.STRCON) {
                this.formatString = ((TokenNode) child).getToken();
            }
            // expList;
            if (child instanceof Exp) {
                this.expList.add((Exp) child);
            }
        }
    }

    @Override
    public void checkError() {
        int cnt = 0;
        String s = formatString.getValue();
        s = s.substring(1, s.length()-1); // remove " "
        // check Error a
        if (!checkFormatString(s)) {
            Printer.addErrorMsg(formatString.getLineNumber(), ErrorType.a);
            super.checkError(); return;
        }
        // check Error l
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '%' && i+1 < s.length() && s.charAt(i+1) == 'd') cnt++;
        }
        if (cnt != expList.size()) {
            Printer.addErrorMsg(startLine, ErrorType.l);
            super.checkError(); return;
        }
        // check child
        super.checkError();
    }

    private boolean checkFormatString(String s) {
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == ' ' || ch == '!' || ch >= 40 && ch <= 126 && ch != '\\') continue;
            // deal with '\' (92)
            else if (ch == '\\' && i+1 < s.length() && s.charAt(i+1) == 'n') continue;
            else if (ch == '%' && i+1 < s.length() && s.charAt(i+1) == 'd') continue;
            else return false;
        }
        return true;
    }
}
