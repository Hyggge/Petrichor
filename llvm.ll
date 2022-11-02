declare i32 @getint(...) 
declare void @putint(i32)
declare void @putstr(i8* )

@s0 = constant [2 x i8] c" \00"
@s1 = constant [2 x i8] c"
\00"
@s2 = constant [10 x i8] c"20373354
\00"

@g0 = dso_local global [8 x i32] [i32 2, i32 0, i32 3, i32 7, i32 3, i32 3, i32 5, i32 4]
@g1 = dso_local global [6 x i32] [i32 2, i32 0, i32 0, i32 6, i32 1, i32 3]
@g2 = dso_local global [3 x i32] [i32 0, i32 0, i32 0]
@g3 = dso_local global [4 x i32] [i32 0, i32 0, i32 0, i32 0]

define dso_local void @Permutation(i32 %a0, i32 %a1, i32* %a2, i32* %a3) {
b0:
	%v0 = alloca i32
	store i32 %a0, i32* %v0
	%v2 = alloca i32
	store i32 %a1, i32* %v2
	%v4 = alloca i32
	store i32 0, i32* %v4
	%v6 = load i32, i32* %v0
	%v7 = load i32, i32* %v2
	%v8 = icmp sge i32 %v6, %v7
	br i1 %v8, label %b1, label %b2
b1:
	br label %b4
b2:
	br label %b7
b3:
	ret void
	ret void
b4:
	%v11 = load i32, i32* %v4
	%v12 = load i32, i32* %v2
	%v13 = sub i32 %v12, 1
	%v14 = icmp slt i32 %v11, %v13
	br i1 %v14, label %b5, label %b6
b5:
	%v16 = load i32, i32* %v4
	%v17 = getelementptr inbounds i32, i32* %a2, i32 %v16
	%v18 = load i32, i32* %v17
	call void @putint(i32 %v18)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s0, i64 0, i64 0))
	%v21 = load i32, i32* %v4
	%v22 = add i32 %v21, 1
	store i32 %v22, i32* %v4
	br label %b4
b6:
	%v25 = load i32, i32* %v4
	%v26 = getelementptr inbounds i32, i32* %a2, i32 %v25
	%v27 = load i32, i32* %v26
	call void @putint(i32 %v27)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s1, i64 0, i64 0))
	br label %b3
b7:
	%v32 = load i32, i32* %v4
	%v33 = load i32, i32* %v2
	%v34 = icmp slt i32 %v32, %v33
	br i1 %v34, label %b8, label %b9
b8:
	%v36 = load i32, i32* %v4
	%v37 = getelementptr inbounds i32, i32* %a3, i32 %v36
	%v38 = load i32, i32* %v37
	%v39 = icmp eq i32 %v38, 0
	br i1 %v39, label %b10, label %b11
b9:
	br label %b3
b10:
	%v41 = load i32, i32* %v4
	%v42 = getelementptr inbounds i32, i32* %a3, i32 %v41
	store i32 1, i32* %v42
	%v44 = load i32, i32* %v0
	%v45 = getelementptr inbounds i32, i32* %a2, i32 %v44
	%v46 = load i32, i32* %v4
	%v47 = add i32 %v46, 1
	store i32 %v47, i32* %v45
	%v49 = load i32, i32* %v0
	%v50 = add i32 %v49, 1
	%v51 = load i32, i32* %v2
	%v52 = getelementptr inbounds i32, i32* %a2, i32 0
	%v53 = getelementptr inbounds i32, i32* %a3, i32 0
	call void @Permutation(i32 %v50, i32 %v51, i32* %v52, i32* %v53)
	%v55 = load i32, i32* %v4
	%v56 = getelementptr inbounds i32, i32* %a3, i32 %v55
	store i32 0, i32* %v56
	br label %b11
b11:
	%v59 = load i32, i32* %v4
	%v60 = add i32 %v59, 1
	store i32 %v60, i32* %v4
	br label %b7
}

define dso_local i32 @main() {
b12:
	%v0 = alloca [15 x i32]
	%v1 = alloca [15 x i32]
	%v2 = alloca i32
	%v3 = call i32 (...) @getint()
	store i32 %v3, i32* %v2
	call void @putstr(i8* getelementptr inbounds ([10 x i8], [10 x i8]* @s2, i64 0, i64 0))
	%v6 = alloca i32
	%v7 = getelementptr inbounds [3 x i32], [3 x i32]* @g2, i32 0, i32 0
	%v8 = load i32, i32* %v7
	store i32 %v8, i32* %v6
	%v10 = mul i32 2, 0
	%v11 = add i32 %v10, 0
	%v12 = getelementptr inbounds [4 x i32], [4 x i32]* @g3, i32 0, i32 %v11
	%v13 = load i32, i32* %v12
	store i32 %v13, i32* %v6
	br label %b13
b13:
	%v16 = load i32, i32* %v6
	%v17 = load i32, i32* %v2
	%v18 = icmp slt i32 %v16, %v17
	br i1 %v18, label %b14, label %b15
b14:
	%v20 = load i32, i32* %v6
	%v21 = getelementptr inbounds [15 x i32], [15 x i32]* %v1, i32 0, i32 %v20
	store i32 0, i32* %v21
	%v23 = load i32, i32* %v6
	%v24 = add i32 %v23, 1
	store i32 %v24, i32* %v6
	br label %b13
b15:
	%v27 = load i32, i32* %v2
	%v28 = getelementptr inbounds [15 x i32], [15 x i32]* %v0, i32 0, i32 0
	%v29 = getelementptr inbounds [15 x i32], [15 x i32]* %v1, i32 0, i32 0
	call void @Permutation(i32 0, i32 %v27, i32* %v28, i32* %v29)
	ret i32 0
}