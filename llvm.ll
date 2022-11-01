declare i32 @getint(...) 
declare void @putint(i32)
declare void @putstr(i8* )



@g0 = dso_local global i32 4
@g1 = dso_local global [6 x i32] [i32 1, i32 2, i32 3, i32 4, i32 5, i32 6]
@g2 = dso_local global i32 0
@g3 = dso_local global [2 x i32] zeroinitializer

define dso_local i32 @main() {
b0:
	ret i32 0
}