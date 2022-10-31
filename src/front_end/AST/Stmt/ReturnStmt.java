package front_end.AST.Stmt;

import front_end.AST.Exp.Exp;
import front_end.AST.Node;
import front_end.symbol.FuncSymbol;
import front_end.symbol.SymbolManager;
import llvm_ir.Constant;
import llvm_ir.IRBuilder;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.instr.ReturnInstr;
import llvm_ir.type.BaseType;
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
                Printer.addErrorMsg(children.get(0).getStartLine(), ErrorType.f);
            }
        }

        super.checkError();
    }

    @Override
    public Value genIR() {
        // 只有一条return时，如果函数返回类型为int，那么我们就翻译成“ret i32 0”, 反之就翻译成“ret void”;
        Instr instr = null;
        Value retValue = null;
        if (children.get(1) instanceof Exp) {
            retValue = children.get(1).genIR();
        }
        else if (IRBuilder.getInstance().getCurFunction().getRetType() == BaseType.INT32) {
            retValue = new Constant(0);
        }
        instr = new ReturnInstr(IRBuilder.getInstance().genLocalVarName(), retValue);
        IRBuilder.getInstance().addInstr(instr);
        return instr;
    }
}
