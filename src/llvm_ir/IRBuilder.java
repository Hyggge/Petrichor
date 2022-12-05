package llvm_ir;

import java.util.HashMap;
import java.util.Stack;

public class IRBuilder {
    // mode为AUTO_INSERT_MODE的时候，表示新建fucntion、globalVal、String、Param、BB、Instr时候会自动插入对应位置
    public static final int AUTO_INSERT_MODE = 1;
    public static final int DEFAULT_MODE = 0;
    public static int mode = DEFAULT_MODE;

    // static attributes
    private static IRBuilder irBuilder = new IRBuilder();
    private static final String GLOBAL_VAR_NAME_PREFIX = "@g";
    private static final String STRING_LITERAL_NAME_PREFIX = "@s";
    private static final String LOCAL_VAR_NAME_PREFIX = "%v";
    private static final String PARAM_NAME_PREFIX = "%a";
    private static final String BB_NAME_PREFIX = "b";
    private static final String FUNC_NAME_PREFIX = "@f_";

    public static IRBuilder getInstance() {
        return irBuilder;
    }

    // class attributes
    private int bbCnt;
    private int paramCnt;
    private HashMap<Function, Integer> localVarCntMap;
    private int globalVarCnt;
    private int stringLiteralCnt;

    private Module curModule;
    private Function curFunction;
    private BasicBlock curBB;

    private Stack<Loop> loopStack;

    private IRBuilder() {
        this.bbCnt = 0;
        this.paramCnt = 0;
        this.localVarCntMap = new HashMap<>();
        this.globalVarCnt = 0;
        this.stringLiteralCnt = 0;
        this.curModule = new Module();
        this.curFunction = null;
        this.curBB = null;
        this.loopStack = new Stack<>();
    }

    public void addGlobalVar(GlobalVar globalVar) {
        curModule.addGlobalVal(globalVar);
    }

    public void addFunction(Function function) {
        curModule.addFunction(function);
    }

    public void addStringLiteral(StringLiteral stringLiteral) {
        curModule.addStringLiteral(stringLiteral);
    }

    public void addBB(BasicBlock bb) {
        curFunction.addBB(bb);
        bb.setParentFunction(curFunction);
    }

    public void addParam(Param param) {
        curFunction.addParam(param);
        param.setParentFunction(curFunction);
    }

    public void addInstr(Instr instr) {
        curBB.addInstr(instr);
        instr.setParentBB(curBB);
    }


    // 名字生成
    public String genLocalVarName() {
        int cutIndex = localVarCntMap.get(curFunction);
        String name = LOCAL_VAR_NAME_PREFIX + cutIndex;
        localVarCntMap.put(curFunction, cutIndex + 1);
        return name;
    }

    public String genLocalVarName(Function bb) {
        int cutIndex = localVarCntMap.get(bb);
        String name = LOCAL_VAR_NAME_PREFIX + cutIndex;
        localVarCntMap.put(bb, cutIndex + 1);
        return name;

    }

    public String genGlobalVarName() {
        return GLOBAL_VAR_NAME_PREFIX + globalVarCnt++;
    }

    public String genStringLiteralName() {
        return STRING_LITERAL_NAME_PREFIX + stringLiteralCnt++;
    }
    public String genParamName() {
        return PARAM_NAME_PREFIX + paramCnt++;
    }

    public String genBBName() {
        return BB_NAME_PREFIX + bbCnt++;
    }

    public String genFuncName(String name) {
        if (name.equals("main")) return "@" + name;
        return FUNC_NAME_PREFIX + name;
    }


    // loop stack relative
    public void pushLoop(Loop loop) {
        loopStack.push(loop);
    }

    public void popLoop() {
        loopStack.pop();
    }

    public Loop getCurLoop() {
        return loopStack.peek();
    }


    // getter and setter
    public Function getCurFunction() {
        return curFunction;
    }

    public void setCurFunction(Function curFunction) {
        this.localVarCntMap.put(curFunction, 0);
        this.curFunction = curFunction;
    }

    public BasicBlock getCurBB() {
        return curBB;
    }

    public void setCurBB(BasicBlock curBB) {
        this.curBB = curBB;
    }

    public Module getModule() {
        return curModule;
    }
}
