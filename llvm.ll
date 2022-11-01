declare i32 @getint(...) 
declare void @putint(i32)
declare void @putstr(i8* )

@s0 = constant [2 x i8] c"
\00"



define dso_local i32 @func(i32* %a0, i32 %a1) {
b0:
	%v0 = alloca i32
	store i32 %a1, i32* %v0
	%v2 = alloca i32
	store i32 1, i32* %v2
	br label %b1
b1:
	%v5 = load i32, i32* %v2
	%v6 = load i32, i32* %v0
	%v7 = icmp sle i32 %v5, %v6
	br i1 %v7, label %b2, label %b3
b2:
	%v9 = load i32, i32* %v2
	%v10 = icmp eq i32 %v9, 1
	br i1 %v10, label %b4, label %b7
b3:
	ret i32 0
b4:
	%v15 = load i32, i32* %v2
	%v16 = getelementptr inbounds i32, i32* %a0, i32 %v15
	store i32 1, i32* %v16
	br label %b6
b5:
	%v19 = load i32, i32* %v2
	%v20 = getelementptr inbounds i32, i32* %a0, i32 %v19
	%v21 = load i32, i32* %v2
	%v22 = sub i32 %v21, 1
	%v23 = getelementptr inbounds i32, i32* %a0, i32 %v22
	%v24 = load i32, i32* %v23
	%v25 = load i32, i32* %v2
	%v26 = sub i32 %v25, 2
	%v27 = getelementptr inbounds i32, i32* %a0, i32 %v26
	%v28 = load i32, i32* %v27
	%v29 = add i32 %v24, %v28
	store i32 %v29, i32* %v20
	br label %b6
b6:
	%v32 = load i32, i32* %v2
	%v33 = add i32 %v32, 1
	store i32 %v33, i32* %v2
	br label %b1
b7:
	%v12 = load i32, i32* %v2
	%v13 = icmp eq i32 %v12, 2
	br i1 %v13, label %b4, label %b5
}

define dso_local i32 @foo(i32* %a2) {
b8:
	%v0 = getelementptr inbounds i32, i32* %a2, i32 0
	%v1 = load i32, i32* %v0
	ret i32 %v1
}

define dso_local i32 @main() {
b9:
	%v0 = alloca i32
	%v1 = call i32 (...) @getint()
	store i32 %v1, i32* %v0
	%v3 = alloca [100 x i32]
	%v4 = getelementptr inbounds [100 x i32], [100 x i32]* %v3, i32 0, i32 0
	%v5 = load i32, i32* %v0
	%v6 = call i32 @func(i32* %v4, i32 %v5)
	%v7 = load i32, i32* %v0
	%v8 = getelementptr inbounds [100 x i32], [100 x i32]* %v3, i32 0, i32 %v7
	%v9 = load i32, i32* %v8
	call void @putint(i32 %v9)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s0, i64 0, i64 0))
	%v12 = alloca [4 x i32]
	%v13 = getelementptr inbounds [4 x i32], [4 x i32]* %v12, i32 0, i32 0
	store i32 1, i32* %v13
	%v15 = getelementptr inbounds [4 x i32], [4 x i32]* %v12, i32 0, i32 1
	store i32 2, i32* %v15
	%v17 = getelementptr inbounds [4 x i32], [4 x i32]* %v12, i32 0, i32 2
	store i32 3, i32* %v17
	%v19 = getelementptr inbounds [4 x i32], [4 x i32]* %v12, i32 0, i32 3
	store i32 4, i32* %v19
	%v21 = alloca i32
	%v22 = mul i32 2, 1
	%v23 = getelementptr inbounds [4 x i32], [4 x i32]* %v12, i32 0, i32 %v22
	%v24 = call i32 @foo(i32* %v23)
	store i32 %v24, i32* %v21
	%v26 = load i32, i32* %v21
	call void @putint(i32 %v26)
	ret i32 0
}