package front_end.AST.Func;

import front_end.AST.Block;
import front_end.AST.Node;
import front_end.AST.Stmt.ReturnStmt;
import front_end.AST.TokenNode;
import front_end.symbol.FuncSymbol;
import front_end.symbol.SymbolManager;
import utils.ErrorType;
import utils.Printer;
import utils.SymbolType;
import utils.SyntaxVarType;
import utils.ValueType;

import java.util.ArrayList;

// FuncType Ident '(' [FuncFormalParams] ')' Block
public class FuncDef extends Node {
    private FuncSymbol symbol;

    public FuncDef(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        super(startLine, endLine, type, children);
    }

    private FuncSymbol createSymbol() {
        // FuncType Ident '(' [FuncFormalParams] ')' Block
        String symbolName = ((TokenNode)children.get(1)).getToken().getValue();
        SymbolType symbolType = SymbolType.SYMBOL_FUNC;
        ValueType returnType = ((FuncType)children.get(0)).getReturnType();
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

    //

    @Override
    public void checkError() {
        this.symbol = createSymbol();
        // check Error b
        boolean res = SymbolManager.getInstance().addSymbol(symbol);
        if (! res) Printer.addErrorMsg(children.get(1).getEndLine(), ErrorType.b);

        // set SymbolManager
        SymbolManager.getInstance().enterFuncDef(symbol);
        SymbolManager.getInstance().setGlobal(false);

        // check children's error
        // 因为只有形参checkError之后，形参的symbol才能生成，然后函数symbol的参数类型、维度信息才能获得
        // 必须要保证在检查block之前将参数类型、维度信息传给函数的symbol，否则会影响函数体内对函数自调用的检查
        for (Node child : children) {
            if (child instanceof Block) { // 在检查Block之前加入参数类型、维度信息
                setParamInfo();
            }
            child.checkError();
        }

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
}
