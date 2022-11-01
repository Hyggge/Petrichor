package front_end.AST.Stmt;

import front_end.AST.Exp.LValExp;
import front_end.AST.Node;
import llvm_ir.IRBuilder;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.instr.StoreInstr;
import utils.ErrorType;
import utils.Printer;
import utils.SyntaxVarType;

import java.util.ArrayList;

// AssignStmt ==> LVal '=' Exp ';'
public class AssignStmt extends Stmt {
    public AssignStmt(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
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

    @Override
    public Value genIR() {
        Value lVal = ((LValExp) children.get(0)).genIRForAssign();
        Value exp = children.get(2).genIR();
        Instr instr = new StoreInstr(IRBuilder.getInstance().genLocalVarName(), exp, lVal);
        return instr;
    }
}
