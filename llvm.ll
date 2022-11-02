declare i32 @getint(...) 
declare void @putint(i32)
declare void @putstr(i8* )

@s0 = constant [14 x i8] c"hello world!
\00"

@g0 = dso_local global i32 1
@g1 = dso_local global [4 x i32] [i32 1, i32 2, i32 3, i32 4]

define dso_local i32 @main() {
b0:
	%v0 = alloca i32
	%v1 = call i32 (...) @getint()
	store i32 %v1, i32* %v0
	%v3 = load i32, i32* %v0
	%v4 = add i32 %v3, 1
	store i32 %v4, i32* %v0
	call void @putstr(i8* getelementptr inbounds ([14 x i8], [14 x i8]* @s0, i64 0, i64 0))
	%v7 = load i32, i32* %v0
	call void @putint(i32 %v7)
	ret i32 0
}