```C	
CompUnit  ==>  {VarDecl | ConstDecl} {FUncDef} MainFunDef

VarDecl   ==> 'int' VarDef {',' VarDef} ';'
VarDef  ==>  Ident {'['  Exp  ']'} ['='  InitVal]
InitVal ==>  Exp | '{' [InitVal {',' InitVal}]'}'

ConstDecl ==> 'const' 'int' ConstDef { ',' ConstDef } ';' 
ConstDef ==> Indent {'[' ConstExp ']'} '=' ConstInitVal
ConstInitVal ==> ConstExp | '{' [ConstInitVal {',' ConstInitVal}]'}'


FuncDef  ==>  FuncType Ident '(' [FuncFormalParams] ')' Block
FuncType ==> 'void' | 'int'
MainFuncDef  ==>  'int' 'main' '('  ')' Block
FuncFormalParams  ==>  FuncFormalParam {',' FuncFormalParam}
FuncFormalParam  ==> 'int' Indent ['[' ']'  {'[' ConstExp ']'}]
FuncRealParams → Exp { ',' Exp } 
Block ==> '{' {VarDecl | ConstDecl | Stmt} '}'   
    
    
    
Stmt ==> AssignStmt | ExpStmt | BlockStmt | IfStmt | WhileStmt | BreakStmt | CReturnStmt | GetIntStmt | PrintfStmt
AssignStmt ==> LVal '=' Exp ';'
ExpStmt ==> [Exp] ';'
BlockStmt ==> block
IfStmt ==> 'if' '(' Cond ')' Stmt [ 'else' Stmt ]
WhileStmt ==> 'while' '(' Cond ')' Stmt
BreakStmt ==> 'break' ';' 
ContinueStmt ==> 'continue' ';'
ReturnStmt ==> 'return' [Exp] ';'
GetIntStmt ==> LVal '=' 'getint''('')'';'
PrintfStmt ==> 'printf''('FormatString{','Exp}')'';'

    
    
    
LValExp ==> Ident {'[' Exp ']'}
PrimaryExp ==> '(' Exp ')' | LValExp | Number
	Number ==> IntConst
UnaryExp ==> PrimaryExp | Ident '(' [FuncRealParams] ')' | UnaryOp UnaryExp
	UnaryOp ==> '+' | '-' | '!'
MulExp ==> UnaryExp {('*' | '/' | '%') UnaryExp}  //注意归约
AddExp ==> MulExp {('+' | '-') MulExp} //注意归约
RelExp ==> AddExp {('<' | '>' | '<=' | '>=') AddExp} //注意归约
EqExp ==> RelExp {('==' | '!=') RelExp} //注意归约
LAndExp ==> EqExp {'&&' EqExp} //注意归约
LOrExp ==>  LAndExp {'||' LAndExp} //注意归约
CondExp ==> LorExp
ConstExp ==> AddExp
Exp ==> AddExp
```





