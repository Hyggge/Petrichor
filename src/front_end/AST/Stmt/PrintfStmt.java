package front_end.AST.Stmt;

import front_end.AST.Exp.Exp;
import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.lexer.Token;
import llvm_ir.IRBuilder;
import llvm_ir.Instr;
import llvm_ir.StringLiteral;
import llvm_ir.Value;
import llvm_ir.instr.IOInstr;
import utils.ErrorType;
import utils.Printer;
import utils.SyntaxVarType;
import utils.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Value genIR() {
        List<Value> expValueList = children.stream().filter(child -> child instanceof Exp).map(Node::genIR).collect(Collectors.toList());
        String s = formatString.getValue().substring(1, formatString.getValue().length() - 1);
        StringBuilder sb = new StringBuilder();
        int expCnt = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '%') {
                // 如果sb中有字符串，那么将该字符串输出，然后清空sb
                if (sb.length() != 0) {
                    StringLiteral stringLiteral = new StringLiteral(IRBuilder.getInstance().genStringLiteralName(), sb.toString());
                    Instr instr = new IOInstr.PutStr(IRBuilder.getInstance().genLocalVarName(), stringLiteral);
                    sb.setLength(0);
                }
                // 输出%d所代表的exp值
                Value value = expValueList.get(expCnt++);
                Instr instr = new IOInstr.PutInt(IRBuilder.getInstance().genLocalVarName(), value);
                // 跳过'd'
                i = i + 1;
            }
            else if (s.charAt(i) == '\\'){
                sb.append('\n');
                // 跳过'n'
                i = i + 1;
            }
            else sb.append(s.charAt(i));
        }
        // 如果sb中有字符串，那么将该字符串清空
        if (sb.length() != 0) {
            StringLiteral stringLiteral = new StringLiteral(IRBuilder.getInstance().genStringLiteralName(), sb.toString());
            new IOInstr.PutStr(IRBuilder.getInstance().genLocalVarName(), stringLiteral);
        }
        return null;
    }
}
