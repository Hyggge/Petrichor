package front_end.AST.Exp;

import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.lexer.Token;
import front_end.symbol.ConstSymbol;
import front_end.symbol.Symbol;
import front_end.symbol.SymbolManager;
import front_end.symbol.VarSymbol;
import llvm_ir.Constant;
import llvm_ir.IRBuilder;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.instr.AluInstr;
import llvm_ir.instr.GEPInstr;
import llvm_ir.instr.LoadInstr;
import utils.ErrorType;
import utils.Printer;
import utils.SyntaxVarType;
import utils.TokenType;

import java.util.ArrayList;

// LValExp ==> Ident {'[' Exp ']'}
public class LValExp extends Node {
    public LValExp(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    @Override
    public Integer getDim()  {
        Token ident = ((TokenNode)children.get(0)).getToken();
        Symbol symbol = SymbolManager.getInstance().getSymbolByName(ident.getValue());
        if (symbol == null) return null;
        // else
        int dim = 0;
        int cnt = 0;
        // get symbol's dim
        if (symbol instanceof VarSymbol) dim = ((VarSymbol)symbol).getDim();
        else if (symbol instanceof ConstSymbol) dim = ((ConstSymbol)symbol).getDim();
        // get number of '['
        for (Node child : children) {
            if (child instanceof TokenNode && ((TokenNode)child).getToken().getType() == TokenType.LBRACK) {
                cnt++;
            }
        }
        // lval dim = symbol dim - number of '['
        return dim - cnt;
    }

    @Override
    public int execute() {
        String ident = ((TokenNode)children.get(0)).getToken().getValue();
        Symbol symbol = SymbolManager.getInstance().getSymbolByName(ident);
        if (symbol == null) return 0;

        // 计算左值表达式中括号的个数和里面填写的值
        int num = 0;
        int[] lens = new int[2];
        for (Node child : children) {
            if (child instanceof Exp) {
                lens[num++] = child.execute();
            }
        }

        // 如果符号为常量符号
        if (symbol instanceof ConstSymbol) {
            ConstSymbol constSymbol = (ConstSymbol) symbol;
            // 如果是整数类型
            if (constSymbol.getDim() == 0) return constSymbol.getConstValue();
            // 如果是数组类型
            else if (constSymbol.getDim() == 1) return constSymbol.getConstValue(lens[0]);
            return constSymbol.getConstValue(lens[0], lens[1]);
        }
        // 如果符号是变量符号
        else {
            VarSymbol varSymbol = (VarSymbol) symbol;
            // 如果是整数类型
            if (varSymbol.getDim() == 0) return varSymbol.getConstValue();
            // 如果是数组类型
            else if (varSymbol.getDim() == 1) return varSymbol.getConstValue(lens[0]);
            else return varSymbol.getConstValue(lens[0], lens[1]);
        }

    }

    public boolean isConst() {
        String name = ((TokenNode)children.get(0)).getToken().getValue();
        Symbol symbol = SymbolManager.getInstance().getSymbolByName(name);
        if (symbol instanceof ConstSymbol) return true;
        return false;
    }

    @Override
    public void checkError() {
        // check Error c
        Token ident = ((TokenNode)children.get(0)).getToken();
        if (SymbolManager.getInstance().getSymbolByName(ident.getValue()) == null) {
            Printer.addErrorMsg(ident.getLineNumber(), ErrorType.c);
        }
        super.checkError();
    }


    public Value genIRForValue() {
        // get num of '[]'
        int num = 0;
        ArrayList<Value> expIRList = new ArrayList<>();
        for (Node child : children) {
            if (child instanceof Exp) {
                num++;
                expIRList.add(child.genIR());
            }
        }
        // get the Symbol
        String symbolName = ((TokenNode)children.get(0)).getToken().getValue();
        Symbol symbol = SymbolManager.getInstance().getSymbolByName(symbolName);
        Instr instr = null;
        int dim = 0;
        ArrayList<Integer> lenList = null;

        // 如果是常量 TODO: 优化思路——可以直接把常量“取值“后的结果封装成Constant形式
        if (symbol instanceof ConstSymbol) {
            ConstSymbol constSymbol = (ConstSymbol) symbol;
            dim = constSymbol.getDim();
            lenList = constSymbol.getLenList();
            // 如果ident不是数组
            if (dim == 0) {
                instr = new LoadInstr(IRBuilder.getInstance().genLocalVarName(), constSymbol.getLlvmValue());
                return instr;
            }
            // 如果ident是一维数组
            else if (dim == 1) {
                // num < dim 则传地址
                if (num == 0) {
                    instr = new GEPInstr(IRBuilder.getInstance().genLocalVarName(), constSymbol.getLlvmValue(), new Constant(0));
                    return instr;
                }
                // num == dim == 1 则取值
                else {
                    instr = new GEPInstr(IRBuilder.getInstance().genLocalVarName(), constSymbol.getLlvmValue(), expIRList.get(0));
                    instr = new LoadInstr(IRBuilder.getInstance().genLocalVarName(), instr);
                    return instr;
                }
            }
            // 如果ident是二维数组
            else {
                // num < dim 则传地址
                if (num == 0) {
                    instr = new GEPInstr(IRBuilder.getInstance().genLocalVarName(), constSymbol.getLlvmValue(), new Constant(0));
                    return instr;
                }
                else if (num == 1) {
                    instr = new AluInstr(IRBuilder.getInstance().genLocalVarName(), AluInstr.Op.MUL, new Constant(lenList.get(1)), expIRList.get(0));
                    instr = new GEPInstr(IRBuilder.getInstance().genLocalVarName(), constSymbol.getLlvmValue(), instr);
                    return instr;
                }
                // num == dim == 2 则取值
                else {
                    instr = new AluInstr(IRBuilder.getInstance().genLocalVarName(), AluInstr.Op.MUL, new Constant(lenList.get(1)), expIRList.get(0));
                    instr = new AluInstr(IRBuilder.getInstance().genLocalVarName(), AluInstr.Op.ADD, instr, expIRList.get(1));
                    instr = new GEPInstr(IRBuilder.getInstance().genLocalVarName(), constSymbol.getLlvmValue(), instr);
                    instr = new LoadInstr(IRBuilder.getInstance().genLocalVarName(), instr);
                    return instr;
                }
            }
        }

        // 如果是变量
        else {
            VarSymbol varSymbol = (VarSymbol) symbol;
            dim = varSymbol.getDim();
            lenList = varSymbol.getLenList();
            // 如果ident不是数组
            if (dim == 0) {
                instr = new LoadInstr(IRBuilder.getInstance().genLocalVarName(), varSymbol.getLlvmValue());
                return instr;
            }
            // 如果ident是一维数组
            else if (dim == 1) {
                // num < dim 则传地址
                if (num == 0) {
                    instr = new GEPInstr(IRBuilder.getInstance().genLocalVarName(), varSymbol.getLlvmValue(), new Constant(0));
                    return instr;
                }
                // num == dim == 1 则取值
                else {
                    instr = new GEPInstr(IRBuilder.getInstance().genLocalVarName(), varSymbol.getLlvmValue(), expIRList.get(0));
                    instr = new LoadInstr(IRBuilder.getInstance().genLocalVarName(), instr);
                    return instr;
                }
            }
            // 如果ident是二维数组
            else {
                // num < dim 则传地址
                if (num == 0) {
                    instr = new GEPInstr(IRBuilder.getInstance().genLocalVarName(), varSymbol.getLlvmValue(), new Constant(0));
                    return instr;
                }
                else if (num == 1) {
                    instr = new AluInstr(IRBuilder.getInstance().genLocalVarName(), AluInstr.Op.MUL, new Constant(lenList.get(1)), expIRList.get(0));
                    instr = new GEPInstr(IRBuilder.getInstance().genLocalVarName(), varSymbol.getLlvmValue(), instr);
                    return instr;
                }
                // num == dim == 2 则取值
                else {
                    instr = new AluInstr(IRBuilder.getInstance().genLocalVarName(), AluInstr.Op.MUL, new Constant(lenList.get(1)), expIRList.get(0));
                    instr = new AluInstr(IRBuilder.getInstance().genLocalVarName(), AluInstr.Op.ADD, instr, expIRList.get(1));
                    instr = new GEPInstr(IRBuilder.getInstance().genLocalVarName(), varSymbol.getLlvmValue(), instr);
                    instr = new LoadInstr(IRBuilder.getInstance().genLocalVarName(), instr);
                    return instr;
                }
            }
        }
    }

    /**
     * 该函数只能用在Assign语句中
     * 此时LVal只能是变量
     * 而且只能返回指向某一个i32的地址，用于定位那块内存
     * @return
     */
    public Value genIRForAssign() {
        ArrayList<Value> expIRList = new ArrayList<>();
        for (Node child : children) {
            if (child instanceof Exp) {
                expIRList.add(child.genIR());
            }
        }
        // get the Symbol
        String symbolName = ((TokenNode)children.get(0)).getToken().getValue();
        VarSymbol symbol = (VarSymbol) SymbolManager.getInstance().getSymbolByName(symbolName);
        Instr instr = null;
        int dim = symbol.getDim();
        ArrayList<Integer> lenList = symbol.getLenList();

        if (dim == 0) {
            return symbol.getLlvmValue();
        }
        else if (dim == 1) {
            instr = new GEPInstr(IRBuilder.getInstance().genLocalVarName(), symbol.getLlvmValue(), expIRList.get(0));
            return instr;
        }
        else {
            instr = new AluInstr(IRBuilder.getInstance().genLocalVarName(), AluInstr.Op.MUL, new Constant(lenList.get(1)), expIRList.get(0));
            instr = new AluInstr(IRBuilder.getInstance().genLocalVarName(), AluInstr.Op.ADD, instr, expIRList.get(1));
            instr = new GEPInstr(IRBuilder.getInstance().genLocalVarName(), symbol.getLlvmValue(), instr);
            return instr;
        }
    }




}
