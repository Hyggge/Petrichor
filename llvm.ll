declare i32 @getint(...) 
declare void @putint(i32)
declare void @putstr(i8* )

@s0 = constant [13 x i8] c"hello world
\00"



define dso_local i32 @main() {
b0:
	call void @putstr(i8* getelementptr inbounds ([13 x i8], [13 x i8]* @s0, i64 0, i64 0))
	ret i32 0
}