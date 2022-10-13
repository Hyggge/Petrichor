package front_end.AST.Exp;

import front_end.AST.Node;
import front_end.symbol.SymbolManager;
import utils.SyntaxVarType;
import utils.ValueType;

import java.util.ArrayList;

public class Number extends Node {
    public Number(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public void checkError() {
        // get RParam's type and dim
        if (SymbolManager.getInstance().getCalledFunc() != null) {
            SymbolManager.getInstance().addRParamInfo(ValueType.INT, 0);
        }
        super.checkError();
    }


    // checkError d

}
