package front_end.AST.Stmt;

import front_end.AST.Node;
import front_end.symbol.SymbolManager;
import llvm_ir.Value;
import utils.SyntaxVarType;

import java.util.ArrayList;

// BlockStmt ==> block
public class BlockStmt extends Stmt {
    public BlockStmt(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public void checkError() {
        SymbolManager.getInstance().enterBlock();
        super.checkError();
        SymbolManager.getInstance().leaveBlock();
    }

    @Override
    public Value genIR() {
        SymbolManager.getInstance().enterBlock();
        super.genIR();
        SymbolManager.getInstance().leaveBlock();
        return null;
    }
}
