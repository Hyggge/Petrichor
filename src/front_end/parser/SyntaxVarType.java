package front_end.parser;

public enum SyntaxVarType {
    COMP_UNIT,
    FUC_DEF,
    MAIN_FUNC_DEF,

    // const
    CONST_DECL,
    CONST_DEF,
    CONST_INITVAL,

    VAR_DECL,
    VAR_DEF,
    INIT_VAL,

    FUNC_DEF,
    FUNC_FORMAL_PARAMS,
    FUNC_REAL_PARAMS,
    FUNC_FORMAL_PARAM,
    BLOCK,

    STMT,
    ASSIGN_STMT,
    EXP_STMT,
    IF_STMT,
    WHILE_STMT,
    BREAK_STMT,
    CONTINUE_STMT,
    RETURN_STMT,
    GETINT_STMT,
    PRINTF_STMT,


    LVAL_EXP,
    PRIMARY_EXP,
    UNARY_EXP,
    MUL_EXP,
    ADD_EXP,
    REL_EXP,
    EQ_EXP,
    LAND_EXP,
    LOR_EXP,
    CONST_EXP,
    EXP,
    COND_EXP,

    TOKEN,




}
