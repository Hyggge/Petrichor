package front_end.parser;

public enum SyntaxVarType {
    CompUnit,
    FucDef,
    MainFuncDef,

    // const
    ConstDecl,
    ConstDef,
    ConstInitVal,

    VarDecl,
    VarDef,
    InitVal,

    FuncDef,
    FuncFParams,
    FuncRParams,
    FuncFParam,
    Block,

    Stmt,
    AssignStmt,
    ExpStmt,
    IfStmt,
    WhileStmt,
    BreakStmt,
    ContinueStmt,
    ReturnStmt,
    GetIntStmt,
    PrintfStmt,


    LVal,
    PrimaryExp,
    UnaryOp,
    MulExp,
    AddExp,
    RelExp,
    EqExp,
    LAndExp,
    LOrExp,
    ConstExp,
    Exp,
    Cond,

    Token,



}
