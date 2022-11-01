declare i32 @getint(...) 
declare void @putint(i32)
declare void @putstr(i8* )



@g0 = dso_local global i32 4
@g1 = dso_local global [6 x i32] [i32 1, i32 2, i32 3, i32 4, i32 5, i32 6]

define dso_local i32 @main() {
b0:
	%v0 = mul i32 3, 1
	%v1 = add i32 %v0, 1
	%v2 = getelementptr inbounds [6 x i32], [6 x i32]* @g1, i32 0, i32 %v1
	%v3 = load i32, i32* %v2
	call void @putint(i32 %v3)
	ret i32 0
}