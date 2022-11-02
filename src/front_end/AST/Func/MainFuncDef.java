package front_end.AST.Func;

import front_end.AST.Block;
import front_end.AST.Node;
import front_end.AST.Stmt.ReturnStmt;
import front_end.AST.TokenNode;
import front_end.symbol.FuncSymbol;
import front_end.symbol.SymbolManager;
import llvm_ir.BasicBlock;
import llvm_ir.Function;
import llvm_ir.IRBuilder;
import llvm_ir.Value;
import llvm_ir.type.BaseType;
import llvm_ir.type.Type;
import utils.ErrorType;
import utils.Printer;
import utils.SymbolType;
import utils.SyntaxVarType;
import utils.ValueType;

import java.util.ArrayList;

// MainFuncDef  ==>  'int' 'main' '('  ')' Block
public class MainFuncDef extends Node {
    private FuncSymbol symbol;

    public MainFuncDef(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    private FuncSymbol createSymbol() {
        // 'int' 'main' '('  ')' Block
        String symbolName = ((TokenNode)children.get(1)).getToken().getValue();
        SymbolType symbolType = SymbolType.SYMBOL_FUNC;
        ValueType returnType = ValueType.INT;
        return new FuncSymbol(symbolName, symbolType, returnType);
    }

    private void setParamInfo() {
        ArrayList<ValueType> FParamTypes = new ArrayList<>();
        ArrayList<Integer> FParamDims = new ArrayList<>();
        // get FParamTypes and FParamDims
        if (children.get(3) instanceof FuncFormalParams) {
            FuncFormalParams funcFormalParams = (FuncFormalParams)children.get(3);
            FParamTypes = funcFormalParams.getFParamTypes();
            FParamDims = funcFormalParams.getFParamDims();
        }
        symbol.setParamInfo(FParamTypes, FParamDims);
    }


    @Override
    public void checkError() {
        SymbolManager.getInstance().setGlobal(false); // 接下来不可能出现全局变量的定义
        this.symbol = createSymbol();
        // check Error b
        boolean res = SymbolManager.getInstance().addSymbol(symbol);
        if (! res) Printer.addErrorMsg(children.get(1).getStartLine(), ErrorType.b);

        // set SymbolManager
        SymbolManager.getInstance().enterFuncDef(symbol);

        // check children's error
        // 因为只有形参checkError之后，形参的symbol才能生成，然后函数symbol的参数类型、维度信息才能获得
        // 必须要保证在检查block之前将参数类型、维度信息传给函数的symbol，否则会影响函数体内对函数自调用的检查
        for (Node child : children) {
            if (child instanceof Block) { // 在检查Block之前加入参数类型、维度信息
                setParamInfo();
            }
            child.checkError();
        }

        // set SymbolManager
        SymbolManager.getInstance().leaveFuncDef();

        // check Error g
        Node block = children.get(children.size() - 1); // Block ==> '{' {VarDecl | ConstDecl | Stmt} '}'
        int senNum = block.getChildren().size();
        Node lastSentence = block.getChildren().get(senNum - 2);
            // the last sentence is not return sentence
        if (! (lastSentence instanceof ReturnStmt) && symbol.getReturnType() != ValueType.VOID) {
            Printer.addErrorMsg(endLine, ErrorType.g);
        }
    }

    @Override
    public Value genIR() {
        SymbolManager.getInstance().setGlobal(false); // 接下来不可能出现全局变量的定义，因此设为false
        SymbolManager.getInstance().addSymbol(symbol);
        SymbolManager.getInstance().enterFuncDef(symbol);
        // 创建函数IR
        String name = IRBuilder.getInstance().genFuncName(symbol.getSymbolName());
        Type retType = symbol.getReturnType() == ValueType.INT ? BaseType.INT32 : BaseType.VOID;
        Function function = new Function(name, retType);
        // 将函数IR加入符号本身
        symbol.setLlvmValue(function);
        // 将函数IR设置为curFunction
        IRBuilder.getInstance().setCurFunction(function);
        // 创建一个新的基本快，并加入curFunction
        String bbName = IRBuilder.getInstance().genBBName();
        BasicBlock bb = new BasicBlock(bbName);
        IRBuilder.getInstance().setCurBB(bb);
        // 解析函数参数和函数体
        super.genIR();
        // 保证函数最后一个BB一定有一个ret语句
        function.checkExistRet();
        SymbolManager.getInstance().leaveFuncDef();
        return null;
    }
}
