declare i32 @getint(...) 
declare void @putint(i32)
declare void @putstr(i8* )

@s0 = constant [2 x i8] c"
\00"
@s1 = constant [2 x i8] c"
\00"
@s2 = constant [2 x i8] c"
\00"

@g0 = dso_local global i32 1
@g1 = dso_local global [4 x i32] [i32 1, i32 2, i32 3, i32 4]

define dso_local i32 @func(i32 %a0, i32 %a1, i32 %a2) {
b0:
	%v0 = alloca i32
	store i32 %a0, i32* %v0
	%v2 = alloca i32
	store i32 %a1, i32* %v2
	%v4 = alloca i32
	store i32 %a2, i32* %v4
	%v6 = load i32, i32* %v0
	call void @putint(i32 %v6)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s0, i64 0, i64 0))
	%v9 = load i32, i32* %v2
	call void @putint(i32 %v9)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s1, i64 0, i64 0))
	%v12 = load i32, i32* %v4
	call void @putint(i32 %v12)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s2, i64 0, i64 0))
	%v15 = load i32, i32* %v0
	%v16 = load i32, i32* %v2
	%v17 = add i32 %v15, %v16
	%v18 = load i32, i32* %v4
	%v19 = add i32 %v17, %v18
	ret i32 %v19
}

define dso_local i32 @main() {
b1:
	%v0 = alloca i32
	store i32 10, i32* %v0
	%v2 = alloca i32
	store i32 20, i32* %v2
	%v4 = alloca i32
	%v5 = load i32, i32* %v0
	%v6 = load i32, i32* %v2
	%v7 = load i32, i32* %v0
	%v8 = load i32, i32* %v2
	%v9 = add i32 %v7, %v8
	%v10 = call i32 @func(i32 %v5, i32 %v6, i32 %v9)
	store i32 %v10, i32* %v4
	%v12 = load i32, i32* %v4
	call void @putint(i32 %v12)
	ret i32 0
}