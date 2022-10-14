```C	
CompUnit  ==>  {VarDecl | ConstDecl} {FUncDef} MainFunDef


ConstDecl ==> 'const' 'int' ConstDef { ',' ConstDef } ';'  
    //error i: 可能缺少分号
ConstDef ==> Indent {'[' ConstExp ']'} '=' ConstInitVal
    //error b: 重复定义const变量； 
    //error k: cosnt数组定义中缺少右中括号
ConstInitVal ==> ConstExp | '{' [ConstInitVal {',' ConstInitVal}]'}'


VarDecl   ==> 'int' VarDef {',' VarDef} ';'
    //error i: 可能缺少分号
VarDef  ==>  Ident {'['  Exp  ']'} ['='  InitVal]
	//error b: 重复定义变量； 
    //error k: 数组定义中缺少右中括号
InitVal ==>  Exp | '{' [InitVal {',' InitVal}]'}'
 
    
    
FuncDef  ==>  FuncType Ident '(' [FuncFormalParams] ')' Block
    //error b: 重复定义函数
    //error g: 有返回值的函数最后一句缺少return
    //error j: 缺少右小括号
FuncType ==> 'void' | 'int'
MainFuncDef  ==>  'int' 'main' '('  ')' Block
    //error b: 重复定义函数
    //error g: 有返回值的函数最后一句缺少return
    //error j: 缺少右小括号
FuncFormalParams  ==>  FuncFormalParam {',' FuncFormalParam}
FuncFormalParam  ==> 'int' Indent ['[' ']'  {'[' ConstExp ']'}]
    //gerror b: 形参名重复定义
    //error k: 对于数组形参缺少右中括号
FuncRealParams → Exp { ',' Exp } 
Block ==> '{' {VarDecl | ConstDecl | Stmt} '}'   
    
    
    
Stmt ==> AssignStmt | ExpStmt | BlockStmt | IfStmt | WhileStmt | BreakStmt | CReturnStmt | GetIntStmt | PrintfStmt
AssignStmt ==> LVal '=' Exp ';'
    //error h: LVal不可以是常量
    //error i: 可能缺少分号
ExpStmt ==> [Exp] ';'
    //error i: 可能缺少分号
BlockStmt ==> block
IfStmt ==> 'if' '(' Cond ')' Stmt [ 'else' Stmt ]
    //error j: 缺少右小括号
WhileStmt ==> 'while' '(' Cond ')' Stmt
    //error j: 缺少右小括号
BreakStmt ==> 'break' ';'
    //error i: 可能缺少分号
    //error m: 循环中可能缺少break语句
ContinueStmt ==> 'continue' ';'
    //error i: 可能缺少分号
    //error m: 循环中可能缺少continue语句
ReturnStmt ==> 'return' [Exp] ';'
    //error f: 无返回值的函数有return exp
    //error i: 可能缺少分号
GetIntStmt ==> LVal '=' 'getint''('')'';'
    //error h: LVal不可以是常量
    //error i: 可能缺少分号
    //error j: 可能缺少右小括号
PrintfStmt ==> 'printf''('FormatString{','Exp}')'';'
    //error i: 可能缺少分号
    //error j: 可能缺少右小括号
    //error l: 格式字符与表达式个数不匹配

    
    
    
LValExp ==> Ident {'[' Exp ']'}
	//error c: 使用未定义的名字
	//error k: 缺少右中括号
PrimaryExp ==> '(' Exp ')' | LValExp | Number
UnaryExp ==> PrimaryExp | Ident '(' [FuncRealParams] ')' | UnaryOp UnaryExp
    //error c: 可能使用未定义的名字（函数调用中） 
    //error d: 函数调用时形参和实参的个数不匹配
    //error e: 函数调用时参数类型不匹配
    //error j: 可能缺少右小括号
MulExp ==> UnaryExp {('*' | '/' | '%') UnaryExp}  //注意归约
AddExp ==> MulExp {('+' | '-') MulExp} //注意归约
RelExp ==> AddExp {('<' | '>' | '<=' | '>=') AddExp} //注意归约
EqExp ==> RelExp {('==' | '!=') RelExp} //注意归约
LAndExp ==> EqExp {'&&' EqExp} //注意归约
LOrExp ==>  LAndExp {'||' LAndExp} //注意归约
CondExp ==> LorExp
ConstExp ==> AddExp
Exp ==> AddExp
    
UnaryOp ==> '+' | '-' | '!'
Number ==> IntConst

    

//下面的语法单元不单独建类
FormatString ==> '"'{Char}'"' 
	//error a: 格式字符串中有非法字符
Char ==> FormatChar | NormalChar
FormatChar ==> %d
NormalChar ==> 十进制编码为32,33,40-126的ASCII字符，'\'（编码92）出现当且仅当为'\n'
```





