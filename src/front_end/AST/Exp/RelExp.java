package front_end.AST.Exp;

import front_end.AST.Node;
import front_end.AST.TokenNode;
import llvm_ir.IRBuilder;
import llvm_ir.Value;
import llvm_ir.instr.IcmpInstr;
import llvm_ir.instr.ZextInstr;
import llvm_ir.type.BaseType;
import utils.SyntaxVarType;
import utils.TokenType;

import java.util.ArrayList;

// RelExp ==> AddExp {('<' | '>' | '<=' | '>=') AddExp}
public class RelExp extends Node {
    public RelExp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public Value genIR() {
        Value operand1 = children.get(0).genIR();
        Value operand2 = null, ans = null;
        if (children.size() == 1) {
            return operand1;
        }
        else {
            for (int i = 1; i < children.size(); i++) {
                if (children.get(i) instanceof TokenNode) {
                    // 把前面得到的结果转化为i32
                    if (! operand1.getType().isInt32()) {
                        operand1 = new ZextInstr(IRBuilder.getInstance().genLocalVarName(), operand1, BaseType.INT32);
                    }
                    // 获得新的AddExp的结果，类型一定为i32
                    operand2 = children.get(i+1).genIR();
                    // 将上面两者进行比较，将结果赋值给operand1用于后面的计算
                    // 判断 >
                    if (((TokenNode)children.get(i)).getToken().getType() == TokenType.GRE) {
                        ans = new IcmpInstr(IRBuilder.getInstance().genLocalVarName(), IcmpInstr.Op.SGT, operand1, operand2);
                    }
                    // 判断 >=
                    else if (((TokenNode)children.get(i)).getToken().getType() == TokenType.GEQ) {
                        ans = new IcmpInstr(IRBuilder.getInstance().genLocalVarName(), IcmpInstr.Op.SGE, operand1, operand2);
                    }
                    // 判断 <
                    else if (((TokenNode)children.get(i)).getToken().getType() == TokenType.LSS) {
                        ans = new IcmpInstr(IRBuilder.getInstance().genLocalVarName(), IcmpInstr.Op.SLT, operand1, operand2);
                    }
                    // 判断 <=
                    else {
                        ans = new IcmpInstr(IRBuilder.getInstance().genLocalVarName(), IcmpInstr.Op.SLE, operand1, operand2);
                    }
                    operand1 = ans;
                }
            }
            return operand1;

        }
    }
}
