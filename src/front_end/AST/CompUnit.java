package front_end.AST;

import front_end.symbol.SymbolManager;
import llvm_ir.Value;
import utils.SyntaxVarType;

import java.util.ArrayList;

// CompUnit  ==>  {VarDecl | ConstDecl} {FUncDef} MainFunDef
public class CompUnit extends Node{
    public CompUnit(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public void checkError() {
        SymbolManager.getInstance().setGlobal(true);
        SymbolManager.getInstance().enterBlock();
        super.checkError();
        SymbolManager.getInstance().leaveBlock();
    }

    @Override
    public Value genIR() {
        SymbolManager.getInstance().setGlobal(true);
        SymbolManager.getInstance().enterBlock();
        super.genIR();
        SymbolManager.getInstance().leaveBlock();
        return null;
    }
}
