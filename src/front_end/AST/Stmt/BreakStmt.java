package front_end.AST.Stmt;

import front_end.AST.Node;
import front_end.symbol.SymbolManager;
import llvm_ir.IRBuilder;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.instr.JumpInstr;
import utils.ErrorType;
import utils.Printer;
import utils.SyntaxVarType;
import java.util.ArrayList;

// BreakStmt ==> 'break' ';'
public class BreakStmt extends Stmt {
    public BreakStmt(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public void checkError() {
        // check Error m
        if (SymbolManager.getInstance().getLoopDepth() <= 0) {
            Printer.addErrorMsg(startLine, ErrorType.m);
        }
        super.checkError();
    }

    @Override
    public Value genIR() {
        Instr instr = new JumpInstr(IRBuilder.getInstance().genLocalVarName(),
                                    IRBuilder.getInstance().getCurLoop().getFollowBlock());
        return null;
    }
}
