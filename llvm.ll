declare i32 @getint(...) 
declare void @putint(i32)
declare void @putstr(i8* )



@g0 = dso_local global i32 1
@g1 = dso_local global [4 x i32] [i32 1, i32 2, i32 3, i32 4]

define dso_local i32 @main() {
b0:
	%v0 = alloca i32
	store i32 1, i32* %v0
	%v2 = alloca i32
	store i32 2, i32* %v2
	%v4 = alloca i32
	%v5 = load i32, i32* %v0
	%v6 = load i32, i32* %v2
	%v7 = add i32 %v5, %v6
	store i32 %v7, i32* %v4
	ret i32 0
}