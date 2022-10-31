package front_end.AST.Var;

import front_end.AST.Exp.Exp;
import front_end.AST.Node;
import llvm_ir.Value;
import utils.SyntaxVarType;

import java.util.ArrayList;

// InitVal ==>  Exp | '{' [InitVal {',' InitVal}]'}'
public class InitVal extends Node {
    public InitVal(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    public ArrayList<Integer> execute(int dim) {
        ArrayList<Integer> ans = new ArrayList<>();
        if (dim == 0) {
            Exp exp = (Exp) children.get(0);
            ans.add(exp.execute());
        }
        else {
            for (Node child : children) {
                if (child instanceof InitVal) {
                    ArrayList<Integer> temp = ((InitVal) child).execute(dim - 1);
                    ans.addAll(temp);
                }
            }
        }
        return ans;
    }


    public ArrayList<Value> genIRList(int dim) {
        ArrayList<Value> ans = new ArrayList<>();
        if (dim == 0) {
            Value value = children.get(0).genIR();
            ans.add(value);
        }
        else {
            for (Node child : children) {
                if (child instanceof InitVal) {
                    ArrayList<Value> temp = ((InitVal)child).genIRList(dim - 1);
                    ans.addAll(temp);
                }
            }
        }
        return ans;
    }
}
