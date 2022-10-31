package llvm_ir;

public class IRBuilder {
    // static attributes
    private static IRBuilder irBuilder = new IRBuilder();
    private static final String GLOBAL_VAR_NAME_PREFIX = "@g";
    private static final String LOCAL_VAR_NAME_PREFIX = "%v";
    private static final String PARAM_NAME_PREFIX = "a";
    private static final String BB_NAME_PREFIX = "b";

    public static IRBuilder getInstance() {
        return irBuilder;
    }

    // class attributes
    private int bbCnt;
    private int paramCnt;
    private int localVarCnt;
    private int globalVarCnt;

    private Module curModule;
    private Function curFunction;
    private BasicBlock curBB;

    public IRBuilder() {
        this.bbCnt = 0;
        this.paramCnt = 0;
        this.localVarCnt = 0;
        this.globalVarCnt = 0;
        this.curModule = new Module();
        this.curFunction = null;
        this.curBB = null;
    }

    public void addGlobalVar(GlobalVar globalVar) {
        curModule.addGlobalVal(globalVar);
    }

    public void addFunction(Function function) {
        curModule.addFunction(function);
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
        return LOCAL_VAR_NAME_PREFIX + localVarCnt++;
    }

    public String genGlobalVarName() {
        return GLOBAL_VAR_NAME_PREFIX + globalVarCnt++;
    }

    public String genParamName() {
        return PARAM_NAME_PREFIX + paramCnt++;
    }

    public String genBBName() {
        return BB_NAME_PREFIX + bbCnt++;
    }


    // getter and setter
    public Function getCurFunction() {
        return curFunction;
    }

    public void setCurFunction(Function curFunction) {
        this.localVarCnt = 0;
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
