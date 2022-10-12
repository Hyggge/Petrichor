package front_end.AST.Func;

import front_end.AST.Exp.Exp;
import front_end.AST.Exp.LValExp;
import front_end.AST.Node;
import utils.SyntaxVarType;
import utils.ValueType;

import java.util.ArrayList;

public class FuncRealParams extends Node {
    public FuncRealParams(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    public ArrayList<ValueType> getRParamTypes() {
        ArrayList<ValueType> RParamTypes = new ArrayList<>();
        for (Node node : children) {
            if (node instanceof Exp) {
                // Exp => AddExp
                Node temp = node.getChildren().get(0);
                // AddExp => MulExp => UnaryExp => PrimaryExp => '(' Exp ')' | LValExp | Number
                for (int i = 0; i < 4; i++) temp = temp.getChildren().get(0);
                // LValExp
                if (temp instanceof LValExp) {
                    LValExp lValExp = (LValExp) temp;
                    RParamTypes.add(lValExp.getValueType());
                }
                // TODO: '(' Exp ')' | Number
                else RParamTypes.add(ValueType.INT);
            }
        }
        return RParamTypes;
    }

    public ArrayList<Integer> getRParamDims() {
        ArrayList<Integer> RParamDims = new ArrayList<>();
        for (Node node : children) {
            if (node instanceof Exp) {
                // Exp => AddExp
                Node temp = node.getChildren().get(0);
                // AddExp => MulExp => UnaryExp => PrimaryExp => '(' Exp ')' | LValExp | Number
                for (int i = 0; i < 4; i++) temp = temp.getChildren().get(0);
                // LValExp
                if (temp instanceof LValExp) {
                    LValExp lValExp = (LValExp) temp;
                    RParamDims.add(lValExp.getDim());
                }
                // TODO: '(' Exp ')' | Number
                else RParamDims.add(0);
            }
        }
        return RParamDims;
    }

}
