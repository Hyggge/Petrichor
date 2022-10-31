package front_end.AST.Exp;

import front_end.AST.Node;
import front_end.AST.TokenNode;
import llvm_ir.IRBuilder;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.instr.AluInstr;
import utils.SyntaxVarType;
import utils.TokenType;

import java.util.ArrayList;

// AddExp ==> MulExp {('+' | '-') MulExp}
public class AddExp extends Node {
    public AddExp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public Integer getDim() {
        for (Node child : children) {
            if (child.getDim() != null) return child.getDim();
        }
        return null;
    }

    @Override
    public int execute() {
        int ans = children.get(0).execute();
        for (int i = 1; i < children.size(); i++) {
            // '+'
            if (children.get(i) instanceof TokenNode && ((TokenNode)children.get(i)).getToken().getType() == TokenType.PLUS) {
                ans += children.get(++i).execute();
            }
            // '-'
            else {
                ans -= children.get(++i).execute();
            }
        }
        return ans;
    }

    @Override
    public Value genIR() {
        Value operand1 = children.get(0).genIR();
        Value operand2 = null;
        Instr instr = null;

        for (int i = 1; i < children.size(); i++) {
            if (children.get(i) instanceof TokenNode && ((TokenNode)children.get(i)).getToken().getType() == TokenType.PLUS) {
                operand2 = children.get(++i).genIR();
                instr = new AluInstr(IRBuilder.getInstance().genLocalVarName(), AluInstr.Op.ADD, operand1, operand2);
                IRBuilder.getInstance().addInstr(instr);
                operand1 = instr;
            }
            else {
                operand2 = children.get(++i).genIR();
                instr = new AluInstr(IRBuilder.getInstance().genLocalVarName(), AluInstr.Op.SUB, operand1, operand2);
                IRBuilder.getInstance().addInstr(instr);
                operand1 = instr;
            }
        }
        return operand1;
    }
}
