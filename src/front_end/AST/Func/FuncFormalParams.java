package front_end.AST.Func;

import front_end.AST.Node;
import utils.SyntaxVarType;
import utils.ValueType;

import java.util.ArrayList;

// FuncFormalParams  ==>  FuncFormalParam {',' FuncFormalParam}
public class FuncFormalParams extends Node {
    public FuncFormalParams(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    public ArrayList<ValueType> getFParamTypes() {
        ArrayList<ValueType> list = new ArrayList<>();
        for (Node child : children) {
            if (child instanceof FuncFormalParam) {
                ValueType type = ((FuncFormalParam)child).getFParamType();
                list.add(type);
            }
        }
        return list;
    }

    public ArrayList<Integer> getFParamDims() {
        ArrayList<Integer> list = new ArrayList<>();
        for (Node child : children) {
            if (child instanceof FuncFormalParam) {
                int dim = ((FuncFormalParam)child).getFParamDim();
                list.add(dim);
            }
        }
        return list;
    }
}
