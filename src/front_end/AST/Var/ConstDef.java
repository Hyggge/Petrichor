package front_end.AST.Var;

import front_end.AST.Exp.ConstExp;
import front_end.AST.Node;
import front_end.AST.TokenNode;
import front_end.symbol.ConstSymbol;
import front_end.symbol.SymbolManager;
import llvm_ir.Constant;
import llvm_ir.GlobalVar;
import llvm_ir.IRBuilder;
import llvm_ir.Instr;
import llvm_ir.Value;
import llvm_ir.initial.Initial;
import llvm_ir.instr.AllocaInstr;
import llvm_ir.instr.GEPInstr;
import llvm_ir.instr.StoreInstr;
import llvm_ir.type.ArrayType;
import llvm_ir.type.BaseType;
import llvm_ir.type.PointerType;
import llvm_ir.type.LLVMType;
import utils.ErrorType;
import utils.Printer;
import utils.SymbolType;
import utils.SyntaxVarType;
import utils.ValueType;

import java.util.ArrayList;

// ConstDef ==> Indent {'[' ConstExp ']'} '=' ConstInitVal
public class ConstDef extends Node {
    private ConstSymbol symbol;

    public ConstDef(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    private ConstSymbol createSymbol() {
        // ConstDef ==> Indent {'[' ConstExp ']'} '=' ConstInitVal
        String symbolName = ((TokenNode)children.get(0)).getToken().getValue();
        SymbolType symbolType = SymbolType.SYMBOL_CONST;
        ValueType valueType = ValueType.INT;
        int num = children.size();
        int dim = 0; // 数组维度
        int totLen = 1; // 数组总长度 = 数组各维度长度乘积
        ArrayList<Integer> lenList = new ArrayList<>();

        // get dim
        for (Node child : children) {
            if (child instanceof ConstExp) {
                dim++;
                int tempLen = child.execute();
                totLen *= tempLen;
                lenList.add(tempLen);
            }
        }

        Initial initial = null;
        LLVMType initialType = null;
        // 判断是整数型常量还是数组型常量
        if (dim == 0) initialType = BaseType.INT32;
        else initialType = new ArrayType(totLen, BaseType.INT32);
        // 获得初始值
        if (children.get(num-1).getType() != SyntaxVarType.CONST_INITVAL) {
            initial = new Initial(initialType, null);
        } else {
            ArrayList<Integer> values = ((ConstInitVal)children.get(num-1)).execute(dim);
            initial = new Initial(initialType, values);
        }
        return new ConstSymbol(symbolName, valueType, dim, lenList, initial);
    }

    @Override
    public void checkError() {
        this.symbol = createSymbol();
        super.checkError();
        // check Error b
        boolean res = SymbolManager.getInstance().addSymbol(symbol);
        if (! res) Printer.addErrorMsg(children.get(0).getEndLine(), ErrorType.b);
    }

    @Override
    public Value genIR() {
        SymbolManager.getInstance().addSymbol(symbol);
        Initial initial = symbol.getInitial();
        // 如果生成全局常量
        if (symbol.isGlobal()) {
            String name = IRBuilder.getInstance().genGlobalVarName();
            GlobalVar globalVar = new GlobalVar(new PointerType(initial.getType()), name, initial);
            // 将value信息加入符号
            symbol.setLlvmValue(globalVar);
        }
        // 如果生成局部常量
        else {
            Instr instr = null;
            // 如果是非数组类型
            if (symbol.getDim() == 0) {
                // 生成alloc指令
                LLVMType allocType = BaseType.INT32;
                instr = new AllocaInstr(IRBuilder.getInstance().genLocalVarName(), allocType);
                symbol.setLlvmValue(instr);
                // 生成store指令，将初始值存入常量
                int value = initial.getValues().get(0);
                instr = new StoreInstr(IRBuilder.getInstance().genLocalVarName(), new Constant(value), instr);
            }
            // 如果是数组类型
            else {
                // 生成alloc指令
                LLVMType allocType = new ArrayType(symbol.getTotLen(), BaseType.INT32);
                instr = new AllocaInstr(IRBuilder.getInstance().genLocalVarName(), allocType);
                symbol.setLlvmValue(instr);
                // 生成一系列GEP+store指令，将初始值存入常量
                Value pointer = instr;
                int offset = 0;
                for (Integer value : initial.getValues()) {
                    instr = new GEPInstr(IRBuilder.getInstance().genLocalVarName(), pointer, new Constant(offset));
                    instr = new StoreInstr(IRBuilder.getInstance().genLocalVarName(), new Constant(value), instr);
                    offset++;
                }
            }
        }
        return null;
    }
}
