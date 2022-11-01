declare i32 @getint(...) 
declare void @putint(i32)
declare void @putstr(i8* )



@g0 = dso_local global i32 1
@g1 = dso_local global [4 x i32] [i32 1, i32 2, i32 3, i32 4]

define dso_local i32 @main() {
b0:
	ret i32 0
}