package front_end.AST.Func;

import front_end.AST.Exp.Exp;
import front_end.AST.Node;
import front_end.symbol.SymbolManager;
import utils.SyntaxVarType;

import java.util.ArrayList;

// FuncRealParams → Exp { ',' Exp }
public class FuncRealParams extends Node {
    public FuncRealParams(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public void checkError() {
        for (Node child : children) {
            child.checkError();
            if (child instanceof Exp) {
                SymbolManager.getInstance().addRecvParamNum();
            }
        }
    }

}
