declare i32 @getint(...) 
declare void @putint(i32)
declare void @putstr(i8* )



@g0 = dso_local global i32 1
@g1 = dso_local global i32 3

define dso_local void @func(i32* %a0) {
b0:
	ret void
	%v1 = alloca i32
	ret void
}

define dso_local i32 @main() {
b1:
	ret i32 0
}