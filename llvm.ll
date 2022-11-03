declare i32 @getint(...) 
declare void @putint(i32)
declare void @putstr(i8* )

@s0 = constant [11 x i8] c"Move from \00"
@s1 = constant [5 x i8] c" to \00"
@s2 = constant [2 x i8] c"
\00"
@s3 = constant [11 x i8] c"Move from \00"
@s4 = constant [5 x i8] c" to \00"
@s5 = constant [2 x i8] c"
\00"
@s6 = constant [11 x i8] c"Move from \00"
@s7 = constant [5 x i8] c" to \00"
@s8 = constant [2 x i8] c"
\00"
@s9 = constant [11 x i8] c"Move from \00"
@s10 = constant [5 x i8] c" to \00"
@s11 = constant [2 x i8] c"
\00"
@s12 = constant [11 x i8] c"Move from \00"
@s13 = constant [5 x i8] c" to \00"
@s14 = constant [2 x i8] c"
\00"
@s15 = constant [11 x i8] c"Move from \00"
@s16 = constant [5 x i8] c" to \00"
@s17 = constant [2 x i8] c"
\00"
@s18 = constant [11 x i8] c"Move from \00"
@s19 = constant [5 x i8] c" to \00"
@s20 = constant [2 x i8] c"
\00"
@s21 = constant [11 x i8] c"Move from \00"
@s22 = constant [5 x i8] c" to \00"
@s23 = constant [2 x i8] c"
\00"
@s24 = constant [11 x i8] c"Move from \00"
@s25 = constant [5 x i8] c" to \00"
@s26 = constant [2 x i8] c"
\00"
@s27 = constant [2 x i8] c" \00"
@s28 = constant [2 x i8] c"
\00"
@s29 = constant [2 x i8] c" \00"
@s30 = constant [2 x i8] c"
\00"
@s31 = constant [10 x i8] c"19373354
\00"
@s32 = constant [10 x i8] c"19373354
\00"



define dso_local void @f_move(i32 %a0, i32 %a1, i32 %a2, i32 %a3) {
b0:
	%v0 = alloca i32
	store i32 %a0, i32* %v0
	%v2 = alloca i32
	store i32 %a1, i32* %v2
	%v4 = alloca i32
	store i32 %a2, i32* %v4
	%v6 = alloca i32
	store i32 %a3, i32* %v6
	%v8 = load i32, i32* %v0
	%v9 = icmp eq i32 %v8, 2
	br i1 %v9, label %b1, label %b2
b1:
	%v11 = load i32, i32* %v2
	%v12 = load i32, i32* %v6
	call void @putstr(i8* getelementptr inbounds ([11 x i8], [11 x i8]* @s0, i64 0, i64 0))
	call void @putint(i32 %v11)
	call void @putstr(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @s1, i64 0, i64 0))
	call void @putint(i32 %v12)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s2, i64 0, i64 0))
	%v18 = load i32, i32* %v2
	%v19 = load i32, i32* %v6
	call void @putstr(i8* getelementptr inbounds ([11 x i8], [11 x i8]* @s3, i64 0, i64 0))
	call void @putint(i32 %v18)
	call void @putstr(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @s4, i64 0, i64 0))
	call void @putint(i32 %v19)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s5, i64 0, i64 0))
	ret void
	br label %b2
b2:
	%v27 = load i32, i32* %v0
	%v28 = sub i32 %v27, 2
	%v29 = load i32, i32* %v2
	%v30 = load i32, i32* %v6
	%v31 = load i32, i32* %v4
	call void @f_move(i32 %v28, i32 %v29, i32 %v30, i32 %v31)
	%v33 = load i32, i32* %v2
	%v34 = load i32, i32* %v6
	call void @putstr(i8* getelementptr inbounds ([11 x i8], [11 x i8]* @s6, i64 0, i64 0))
	call void @putint(i32 %v33)
	call void @putstr(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @s7, i64 0, i64 0))
	call void @putint(i32 %v34)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s8, i64 0, i64 0))
	%v40 = load i32, i32* %v2
	%v41 = load i32, i32* %v6
	call void @putstr(i8* getelementptr inbounds ([11 x i8], [11 x i8]* @s9, i64 0, i64 0))
	call void @putint(i32 %v40)
	call void @putstr(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @s10, i64 0, i64 0))
	call void @putint(i32 %v41)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s11, i64 0, i64 0))
	%v47 = load i32, i32* %v0
	%v48 = sub i32 %v47, 2
	%v49 = load i32, i32* %v4
	%v50 = load i32, i32* %v2
	%v51 = load i32, i32* %v6
	call void @f_move(i32 %v48, i32 %v49, i32 %v50, i32 %v51)
	ret void
}

define dso_local void @f_solve(i32 %a4, i32 %a5, i32 %a6, i32 %a7) {
b3:
	%v0 = alloca i32
	store i32 %a4, i32* %v0
	%v2 = alloca i32
	store i32 %a5, i32* %v2
	%v4 = alloca i32
	store i32 %a6, i32* %v4
	%v6 = alloca i32
	store i32 %a7, i32* %v6
	%v8 = load i32, i32* %v0
	%v9 = icmp eq i32 %v8, 2
	br i1 %v9, label %b4, label %b5
b4:
	%v11 = load i32, i32* %v2
	%v12 = load i32, i32* %v4
	call void @putstr(i8* getelementptr inbounds ([11 x i8], [11 x i8]* @s12, i64 0, i64 0))
	call void @putint(i32 %v11)
	call void @putstr(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @s13, i64 0, i64 0))
	call void @putint(i32 %v12)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s14, i64 0, i64 0))
	%v18 = load i32, i32* %v2
	%v19 = load i32, i32* %v6
	call void @putstr(i8* getelementptr inbounds ([11 x i8], [11 x i8]* @s15, i64 0, i64 0))
	call void @putint(i32 %v18)
	call void @putstr(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @s16, i64 0, i64 0))
	call void @putint(i32 %v19)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s17, i64 0, i64 0))
	ret void
	br label %b5
b5:
	%v27 = load i32, i32* %v0
	%v28 = sub i32 %v27, 2
	%v29 = load i32, i32* %v2
	%v30 = load i32, i32* %v4
	%v31 = load i32, i32* %v6
	call void @f_move(i32 %v28, i32 %v29, i32 %v30, i32 %v31)
	%v33 = load i32, i32* %v2
	%v34 = load i32, i32* %v4
	call void @putstr(i8* getelementptr inbounds ([11 x i8], [11 x i8]* @s18, i64 0, i64 0))
	call void @putint(i32 %v33)
	call void @putstr(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @s19, i64 0, i64 0))
	call void @putint(i32 %v34)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s20, i64 0, i64 0))
	%v40 = load i32, i32* %v2
	%v41 = load i32, i32* %v4
	call void @putstr(i8* getelementptr inbounds ([11 x i8], [11 x i8]* @s21, i64 0, i64 0))
	call void @putint(i32 %v40)
	call void @putstr(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @s22, i64 0, i64 0))
	call void @putint(i32 %v41)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s23, i64 0, i64 0))
	%v47 = load i32, i32* %v0
	%v48 = sub i32 %v47, 2
	%v49 = load i32, i32* %v6
	%v50 = load i32, i32* %v4
	%v51 = load i32, i32* %v2
	call void @f_move(i32 %v48, i32 %v49, i32 %v50, i32 %v51)
	%v53 = load i32, i32* %v4
	%v54 = load i32, i32* %v6
	call void @putstr(i8* getelementptr inbounds ([11 x i8], [11 x i8]* @s24, i64 0, i64 0))
	call void @putint(i32 %v53)
	call void @putstr(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @s25, i64 0, i64 0))
	call void @putint(i32 %v54)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s26, i64 0, i64 0))
	%v60 = load i32, i32* %v0
	%v61 = sub i32 %v60, 2
	%v62 = load i32, i32* %v2
	%v63 = load i32, i32* %v4
	%v64 = load i32, i32* %v6
	call void @f_solve(i32 %v61, i32 %v62, i32 %v63, i32 %v64)
	ret void
}

define dso_local void @f_dfs(i32* %a8, i32* %a9, i32* %a10, i32 %a11, i32 %a12) {
b6:
	%v0 = alloca i32
	store i32 %a11, i32* %v0
	%v2 = alloca i32
	store i32 %a12, i32* %v2
	%v4 = load i32, i32* %v0
	%v5 = load i32, i32* %v2
	%v6 = icmp eq i32 %v4, %v5
	br i1 %v6, label %b7, label %b8
b7:
	%v8 = alloca i32
	store i32 0, i32* %v8
	br label %b9
b8:
	%v32 = alloca i32
	store i32 0, i32* %v32
	br label %b12
b9:
	%v11 = load i32, i32* %v8
	%v12 = load i32, i32* %v2
	%v13 = icmp slt i32 %v11, %v12
	br i1 %v13, label %b10, label %b11
b10:
	%v15 = load i32, i32* %v8
	%v16 = getelementptr inbounds i32, i32* %a8, i32 %v15
	%v17 = load i32, i32* %v16
	%v18 = load i32, i32* %v8
	%v19 = add i32 %v18, 1
	%v20 = getelementptr inbounds i32, i32* %a10, i32 %v19
	%v21 = load i32, i32* %v20
	%v22 = add i32 %v17, %v21
	call void @putint(i32 %v22)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s27, i64 0, i64 0))
	%v25 = load i32, i32* %v8
	%v26 = add i32 %v25, 1
	store i32 %v26, i32* %v8
	br label %b9
b11:
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s28, i64 0, i64 0))
	ret void
	br label %b8
b12:
	%v35 = load i32, i32* %v32
	%v36 = load i32, i32* %v2
	%v37 = icmp slt i32 %v35, %v36
	br i1 %v37, label %b13, label %b14
b13:
	%v39 = load i32, i32* %v32
	%v40 = getelementptr inbounds i32, i32* %a9, i32 %v39
	%v41 = load i32, i32* %v40
	%v42 = icmp eq i32 0, %v41
	%v43 = zext i1 %v42 to i32
	%v44 = icmp ne i32 %v43, 0
	br i1 %v44, label %b15, label %b16
b14:
	ret void
	ret void
b15:
	%v46 = load i32, i32* %v32
	%v47 = getelementptr inbounds i32, i32* %a9, i32 %v46
	store i32 1, i32* %v47
	%v49 = load i32, i32* %v0
	%v50 = getelementptr inbounds i32, i32* %a8, i32 %v49
	%v51 = load i32, i32* %v32
	store i32 %v51, i32* %v50
	%v53 = getelementptr inbounds i32, i32* %a8, i32 0
	%v54 = getelementptr inbounds i32, i32* %a9, i32 0
	%v55 = getelementptr inbounds i32, i32* %a10, i32 0
	%v56 = load i32, i32* %v0
	%v57 = add i32 %v56, 1
	%v58 = load i32, i32* %v2
	call void @f_dfs(i32* %v53, i32* %v54, i32* %v55, i32 %v57, i32 %v58)
	%v60 = load i32, i32* %v32
	%v61 = getelementptr inbounds i32, i32* %a9, i32 %v60
	store i32 0, i32* %v61
	br label %b16
b16:
	%v64 = load i32, i32* %v32
	%v65 = add i32 %v64, 1
	store i32 %v65, i32* %v32
	br label %b12
}

define dso_local void @f_matrixPrint(i32* %a13, i32 %a14) {
b17:
	%v0 = alloca i32
	store i32 %a14, i32* %v0
	%v2 = alloca i32
	store i32 0, i32* %v2
	br label %b18
b18:
	%v5 = load i32, i32* %v2
	%v6 = load i32, i32* %v0
	%v7 = icmp slt i32 %v5, %v6
	br i1 %v7, label %b19, label %b20
b19:
	%v9 = alloca i32
	store i32 0, i32* %v9
	br label %b21
b20:
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s30, i64 0, i64 0))
	ret void
	ret void
b21:
	%v12 = load i32, i32* %v9
	%v13 = load i32, i32* %v0
	%v14 = icmp slt i32 %v12, %v13
	br i1 %v14, label %b22, label %b23
b22:
	%v16 = load i32, i32* %v2
	%v17 = load i32, i32* %v9
	%v18 = mul i32 5, %v16
	%v19 = add i32 %v18, %v17
	%v20 = getelementptr inbounds i32, i32* %a13, i32 %v19
	%v21 = load i32, i32* %v20
	call void @putint(i32 %v21)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s29, i64 0, i64 0))
	%v24 = load i32, i32* %v9
	%v25 = add i32 %v24, 1
	store i32 %v25, i32* %v9
	br label %b21
b23:
	%v28 = load i32, i32* %v2
	%v29 = add i32 %v28, 1
	store i32 %v29, i32* %v2
	br label %b18
}

define dso_local void @f_matrixMul(i32* %a15, i32* %a16, i32* %a17, i32 %a18) {
b24:
	%v0 = alloca i32
	store i32 %a18, i32* %v0
	%v2 = alloca i32
	store i32 0, i32* %v2
	%v4 = getelementptr inbounds i32, i32* %a16, i32 0
	%v5 = load i32, i32* %v0
	call void @f_matrixPrint(i32* %v4, i32 %v5)
	%v7 = getelementptr inbounds i32, i32* %a17, i32 0
	%v8 = load i32, i32* %v0
	call void @f_matrixPrint(i32* %v7, i32 %v8)
	br label %b25
b25:
	%v11 = load i32, i32* %v2
	%v12 = load i32, i32* %v0
	%v13 = icmp slt i32 %v11, %v12
	br i1 %v13, label %b26, label %b27
b26:
	%v15 = alloca i32
	store i32 0, i32* %v15
	br label %b28
b27:
	%v73 = getelementptr inbounds i32, i32* %a16, i32 0
	%v74 = load i32, i32* %v0
	call void @f_matrixPrint(i32* %v73, i32 %v74)
	%v76 = getelementptr inbounds i32, i32* %a17, i32 0
	%v77 = load i32, i32* %v0
	call void @f_matrixPrint(i32* %v76, i32 %v77)
	%v79 = getelementptr inbounds i32, i32* %a15, i32 0
	%v80 = load i32, i32* %v0
	call void @f_matrixPrint(i32* %v79, i32 %v80)
	ret void
	ret void
b28:
	%v18 = load i32, i32* %v15
	%v19 = load i32, i32* %v0
	%v20 = icmp slt i32 %v18, %v19
	br i1 %v20, label %b29, label %b30
b29:
	%v22 = alloca i32
	store i32 0, i32* %v22
	%v24 = load i32, i32* %v2
	%v25 = load i32, i32* %v15
	%v26 = mul i32 5, %v24
	%v27 = add i32 %v26, %v25
	%v28 = getelementptr inbounds i32, i32* %a15, i32 %v27
	store i32 0, i32* %v28
	br label %b31
b30:
	%v69 = load i32, i32* %v2
	%v70 = add i32 %v69, 1
	store i32 %v70, i32* %v2
	br label %b25
b31:
	%v31 = load i32, i32* %v22
	%v32 = load i32, i32* %v0
	%v33 = icmp slt i32 %v31, %v32
	br i1 %v33, label %b32, label %b33
b32:
	%v35 = load i32, i32* %v2
	%v36 = load i32, i32* %v15
	%v37 = mul i32 5, %v35
	%v38 = add i32 %v37, %v36
	%v39 = getelementptr inbounds i32, i32* %a15, i32 %v38
	%v40 = load i32, i32* %v2
	%v41 = load i32, i32* %v15
	%v42 = mul i32 5, %v40
	%v43 = add i32 %v42, %v41
	%v44 = getelementptr inbounds i32, i32* %a15, i32 %v43
	%v45 = load i32, i32* %v44
	%v46 = load i32, i32* %v2
	%v47 = load i32, i32* %v22
	%v48 = mul i32 5, %v46
	%v49 = add i32 %v48, %v47
	%v50 = getelementptr inbounds i32, i32* %a16, i32 %v49
	%v51 = load i32, i32* %v50
	%v52 = load i32, i32* %v22
	%v53 = load i32, i32* %v15
	%v54 = mul i32 5, %v52
	%v55 = add i32 %v54, %v53
	%v56 = getelementptr inbounds i32, i32* %a17, i32 %v55
	%v57 = load i32, i32* %v56
	%v58 = mul i32 %v51, %v57
	%v59 = add i32 %v45, %v58
	store i32 %v59, i32* %v39
	%v61 = load i32, i32* %v22
	%v62 = add i32 %v61, 1
	store i32 %v62, i32* %v22
	br label %b31
b33:
	%v65 = load i32, i32* %v15
	%v66 = add i32 %v65, 1
	store i32 %v66, i32* %v15
	br label %b28
}

define dso_local i32 @main() {
b34:
	%v0 = alloca i32
	%v1 = call i32 (...) @getint()
	store i32 %v1, i32* %v0
	call void @putstr(i8* getelementptr inbounds ([10 x i8], [10 x i8]* @s31, i64 0, i64 0))
	%v4 = load i32, i32* %v0
	call void @f_solve(i32 %v4, i32 1, i32 2, i32 3)
	%v6 = call i32 (...) @getint()
	store i32 %v6, i32* %v0
	%v8 = alloca [10 x i32]
	%v9 = alloca [10 x i32]
	%v10 = getelementptr inbounds [10 x i32], [10 x i32]* %v9, i32 0, i32 0
	store i32 9, i32* %v10
	%v12 = getelementptr inbounds [10 x i32], [10 x i32]* %v9, i32 0, i32 1
	store i32 8, i32* %v12
	%v14 = getelementptr inbounds [10 x i32], [10 x i32]* %v9, i32 0, i32 2
	store i32 7, i32* %v14
	%v16 = getelementptr inbounds [10 x i32], [10 x i32]* %v9, i32 0, i32 3
	store i32 6, i32* %v16
	%v18 = getelementptr inbounds [10 x i32], [10 x i32]* %v9, i32 0, i32 4
	store i32 5, i32* %v18
	%v20 = getelementptr inbounds [10 x i32], [10 x i32]* %v9, i32 0, i32 5
	store i32 4, i32* %v20
	%v22 = getelementptr inbounds [10 x i32], [10 x i32]* %v9, i32 0, i32 6
	store i32 3, i32* %v22
	%v24 = getelementptr inbounds [10 x i32], [10 x i32]* %v9, i32 0, i32 7
	store i32 2, i32* %v24
	%v26 = getelementptr inbounds [10 x i32], [10 x i32]* %v9, i32 0, i32 8
	store i32 1, i32* %v26
	%v28 = getelementptr inbounds [10 x i32], [10 x i32]* %v9, i32 0, i32 9
	store i32 0, i32* %v28
	%v30 = alloca [10 x i32]
	%v31 = alloca [10 x i32]
	%v32 = getelementptr inbounds [10 x i32], [10 x i32]* %v31, i32 0, i32 0
	store i32 8, i32* %v32
	%v34 = getelementptr inbounds [10 x i32], [10 x i32]* %v31, i32 0, i32 1
	store i32 7, i32* %v34
	%v36 = getelementptr inbounds [10 x i32], [10 x i32]* %v31, i32 0, i32 2
	store i32 6, i32* %v36
	%v38 = getelementptr inbounds [10 x i32], [10 x i32]* %v31, i32 0, i32 3
	store i32 5, i32* %v38
	%v40 = getelementptr inbounds [10 x i32], [10 x i32]* %v31, i32 0, i32 4
	store i32 4, i32* %v40
	%v42 = getelementptr inbounds [10 x i32], [10 x i32]* %v31, i32 0, i32 5
	store i32 3, i32* %v42
	%v44 = getelementptr inbounds [10 x i32], [10 x i32]* %v31, i32 0, i32 6
	store i32 2, i32* %v44
	%v46 = getelementptr inbounds [10 x i32], [10 x i32]* %v31, i32 0, i32 7
	store i32 1, i32* %v46
	%v48 = getelementptr inbounds [10 x i32], [10 x i32]* %v31, i32 0, i32 8
	store i32 0, i32* %v48
	%v50 = getelementptr inbounds [10 x i32], [10 x i32]* %v31, i32 0, i32 9
	store i32 9, i32* %v50
	%v52 = alloca [30 x i32]
	%v53 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 0
	store i32 7, i32* %v53
	%v55 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 1
	store i32 6, i32* %v55
	%v57 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 2
	store i32 5, i32* %v57
	%v59 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 3
	store i32 4, i32* %v59
	%v61 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 4
	store i32 3, i32* %v61
	%v63 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 5
	store i32 2, i32* %v63
	%v65 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 6
	store i32 1, i32* %v65
	%v67 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 7
	store i32 0, i32* %v67
	%v69 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 8
	store i32 9, i32* %v69
	%v71 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 9
	store i32 8, i32* %v71
	%v73 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 10
	store i32 6, i32* %v73
	%v75 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 11
	store i32 5, i32* %v75
	%v77 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 12
	store i32 4, i32* %v77
	%v79 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 13
	store i32 3, i32* %v79
	%v81 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 14
	store i32 2, i32* %v81
	%v83 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 15
	store i32 1, i32* %v83
	%v85 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 16
	store i32 0, i32* %v85
	%v87 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 17
	store i32 9, i32* %v87
	%v89 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 18
	store i32 8, i32* %v89
	%v91 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 19
	store i32 7, i32* %v91
	%v93 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 20
	store i32 5, i32* %v93
	%v95 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 21
	store i32 4, i32* %v95
	%v97 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 22
	store i32 3, i32* %v97
	%v99 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 23
	store i32 2, i32* %v99
	%v101 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 24
	store i32 1, i32* %v101
	%v103 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 25
	store i32 0, i32* %v103
	%v105 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 26
	store i32 9, i32* %v105
	%v107 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 27
	store i32 8, i32* %v107
	%v109 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 28
	store i32 7, i32* %v109
	%v111 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 29
	store i32 6, i32* %v111
	%v113 = alloca i32
	store i32 0, i32* %v113
	br label %b35
b35:
	%v116 = load i32, i32* %v113
	%v117 = load i32, i32* %v0
	%v118 = icmp slt i32 %v116, %v117
	br i1 %v118, label %b36, label %b37
b36:
	%v120 = load i32, i32* %v113
	%v121 = getelementptr inbounds [10 x i32], [10 x i32]* %v9, i32 0, i32 %v120
	%v122 = load i32, i32* %v113
	%v123 = getelementptr inbounds [10 x i32], [10 x i32]* %v9, i32 0, i32 %v122
	%v124 = load i32, i32* %v123
	%v125 = load i32, i32* %v113
	%v126 = mul i32 10, 2
	%v127 = add i32 %v126, %v125
	%v128 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 %v127
	%v129 = load i32, i32* %v128
	%v130 = add i32 %v124, %v129
	%v131 = add i32 %v130, 1
	store i32 %v131, i32* %v121
	%v133 = load i32, i32* %v113
	%v134 = getelementptr inbounds [10 x i32], [10 x i32]* %v31, i32 0, i32 %v133
	%v135 = load i32, i32* %v113
	%v136 = getelementptr inbounds [10 x i32], [10 x i32]* %v31, i32 0, i32 %v135
	%v137 = load i32, i32* %v136
	%v138 = load i32, i32* %v113
	%v139 = mul i32 10, 2
	%v140 = add i32 %v139, %v138
	%v141 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 %v140
	%v142 = load i32, i32* %v141
	%v143 = add i32 %v137, %v142
	%v144 = add i32 %v143, 2
	store i32 %v144, i32* %v134
	%v146 = load i32, i32* %v113
	%v147 = mul i32 10, 0
	%v148 = add i32 %v147, %v146
	%v149 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 %v148
	%v150 = load i32, i32* %v113
	%v151 = mul i32 10, 0
	%v152 = add i32 %v151, %v150
	%v153 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 %v152
	%v154 = load i32, i32* %v153
	%v155 = load i32, i32* %v113
	%v156 = mul i32 10, 2
	%v157 = add i32 %v156, %v155
	%v158 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 %v157
	%v159 = load i32, i32* %v158
	%v160 = add i32 %v154, %v159
	%v161 = add i32 %v160, 3
	store i32 %v161, i32* %v149
	%v163 = load i32, i32* %v113
	%v164 = mul i32 10, 1
	%v165 = add i32 %v164, %v163
	%v166 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 %v165
	%v167 = load i32, i32* %v113
	%v168 = mul i32 10, 1
	%v169 = add i32 %v168, %v167
	%v170 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 %v169
	%v171 = load i32, i32* %v170
	%v172 = load i32, i32* %v113
	%v173 = mul i32 10, 2
	%v174 = add i32 %v173, %v172
	%v175 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 %v174
	%v176 = load i32, i32* %v175
	%v177 = add i32 %v171, %v176
	%v178 = add i32 %v177, 4
	store i32 %v178, i32* %v166
	%v180 = load i32, i32* %v113
	%v181 = add i32 %v180, 1
	store i32 %v181, i32* %v113
	br label %b35
b37:
	store i32 0, i32* %v113
	br label %b38
b38:
	%v186 = icmp ne i32 114514, 0
	br i1 %v186, label %b39, label %b40
b39:
	%v188 = load i32, i32* %v113
	%v189 = getelementptr inbounds [10 x i32], [10 x i32]* %v30, i32 0, i32 %v188
	store i32 0, i32* %v189
	%v191 = load i32, i32* %v113
	%v192 = add i32 %v191, 1
	store i32 %v192, i32* %v113
	%v194 = load i32, i32* %v113
	%v195 = load i32, i32* %v0
	%v196 = icmp sge i32 %v194, %v195
	br i1 %v196, label %b41, label %b42
b40:
	%v201 = getelementptr inbounds [10 x i32], [10 x i32]* %v8, i32 0, i32 0
	%v202 = getelementptr inbounds [10 x i32], [10 x i32]* %v30, i32 0, i32 0
	%v203 = getelementptr inbounds [10 x i32], [10 x i32]* %v9, i32 0, i32 0
	%v204 = load i32, i32* %v0
	call void @f_dfs(i32* %v201, i32* %v202, i32* %v203, i32 0, i32 %v204)
	store i32 0, i32* %v113
	br label %b43
b41:
	br label %b40
	br label %b42
b42:
	br label %b38
b43:
	%v208 = icmp ne i32 114514, 0
	br i1 %v208, label %b44, label %b45
b44:
	%v210 = load i32, i32* %v113
	%v211 = getelementptr inbounds [10 x i32], [10 x i32]* %v30, i32 0, i32 %v210
	store i32 0, i32* %v211
	%v213 = load i32, i32* %v113
	%v214 = add i32 %v213, 1
	store i32 %v214, i32* %v113
	%v216 = load i32, i32* %v113
	%v217 = load i32, i32* %v0
	%v218 = icmp sge i32 %v216, %v217
	br i1 %v218, label %b46, label %b47
b45:
	%v224 = getelementptr inbounds [10 x i32], [10 x i32]* %v8, i32 0, i32 0
	%v225 = getelementptr inbounds [10 x i32], [10 x i32]* %v30, i32 0, i32 0
	%v226 = getelementptr inbounds [10 x i32], [10 x i32]* %v31, i32 0, i32 0
	%v227 = load i32, i32* %v0
	call void @f_dfs(i32* %v224, i32* %v225, i32* %v226, i32 0, i32 %v227)
	store i32 0, i32* %v113
	br label %b49
b46:
	br label %b45
	br label %b48
b47:
	br label %b48
b48:
	br label %b43
b49:
	%v231 = icmp ne i32 114514, 0
	br i1 %v231, label %b50, label %b51
b50:
	%v233 = load i32, i32* %v113
	%v234 = getelementptr inbounds [10 x i32], [10 x i32]* %v30, i32 0, i32 %v233
	store i32 0, i32* %v234
	%v236 = load i32, i32* %v113
	%v237 = add i32 %v236, 1
	store i32 %v237, i32* %v113
	%v239 = load i32, i32* %v113
	%v240 = load i32, i32* %v0
	%v241 = icmp slt i32 %v239, %v240
	br i1 %v241, label %b52, label %b53
b51:
	%v247 = getelementptr inbounds [10 x i32], [10 x i32]* %v8, i32 0, i32 0
	%v248 = getelementptr inbounds [10 x i32], [10 x i32]* %v30, i32 0, i32 0
	%v249 = mul i32 10, 0
	%v250 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 %v249
	%v251 = load i32, i32* %v0
	call void @f_dfs(i32* %v247, i32* %v248, i32* %v250, i32 0, i32 %v251)
	store i32 0, i32* %v113
	br label %b54
b52:
	br label %b49
	br label %b53
b53:
	br label %b51
	br label %b49
b54:
	%v255 = icmp ne i32 114514, 0
	br i1 %v255, label %b55, label %b56
b55:
	%v257 = load i32, i32* %v113
	%v258 = getelementptr inbounds [10 x i32], [10 x i32]* %v30, i32 0, i32 %v257
	store i32 0, i32* %v258
	%v260 = load i32, i32* %v113
	%v261 = add i32 %v260, 1
	store i32 %v261, i32* %v113
	%v263 = load i32, i32* %v113
	%v264 = load i32, i32* %v0
	%v265 = icmp slt i32 %v263, %v264
	br i1 %v265, label %b57, label %b58
b56:
	%v272 = getelementptr inbounds [10 x i32], [10 x i32]* %v8, i32 0, i32 0
	%v273 = getelementptr inbounds [10 x i32], [10 x i32]* %v30, i32 0, i32 0
	%v274 = mul i32 10, 1
	%v275 = getelementptr inbounds [30 x i32], [30 x i32]* %v52, i32 0, i32 %v274
	%v276 = load i32, i32* %v0
	call void @f_dfs(i32* %v272, i32* %v273, i32* %v275, i32 0, i32 %v276)
	%v278 = alloca [25 x i32]
	%v279 = sub i32 0, 1625382311
	%v280 = sub i32 0, 492323543
	%v281 = sub i32 0, 252604752
	%v282 = sub i32 0, 51943305
	%v283 = sub i32 0, 1854253096
	%v284 = sub i32 0, 1024571774
	%v285 = sub i32 0, 1803769826
	%v286 = sub i32 0, 1485716863
	%v287 = sub i32 0, 1192520056
	%v288 = sub i32 0, 1503793409
	%v289 = sub i32 0, 1911831470
	%v290 = sub i32 0, 1387735054
	%v291 = sub i32 0, 274170342
	%v292 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 0
	store i32 307728049, i32* %v292
	%v294 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 1
	store i32 %v279, i32* %v294
	%v296 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 2
	store i32 1271106322, i32* %v296
	%v298 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 3
	store i32 309930583, i32* %v298
	%v300 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 4
	store i32 2007032850, i32* %v300
	%v302 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 5
	store i32 1074055550, i32* %v302
	%v304 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 6
	store i32 2036831205, i32* %v304
	%v306 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 7
	store i32 %v280, i32* %v306
	%v308 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 8
	store i32 %v281, i32* %v308
	%v310 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 9
	store i32 1295820596, i32* %v310
	%v312 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 10
	store i32 728458994, i32* %v312
	%v314 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 11
	store i32 1673642498, i32* %v314
	%v316 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 12
	store i32 %v282, i32* %v316
	%v318 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 13
	store i32 %v283, i32* %v318
	%v320 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 14
	store i32 %v284, i32* %v320
	%v322 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 15
	store i32 1430035460, i32* %v322
	%v324 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 16
	store i32 1705232663, i32* %v324
	%v326 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 17
	store i32 %v285, i32* %v326
	%v328 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 18
	store i32 %v286, i32* %v328
	%v330 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 19
	store i32 %v287, i32* %v330
	%v332 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 20
	store i32 %v288, i32* %v332
	%v334 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 21
	store i32 2027983592, i32* %v334
	%v336 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 22
	store i32 %v289, i32* %v336
	%v338 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 23
	store i32 %v290, i32* %v338
	%v340 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 24
	store i32 %v291, i32* %v340
	%v342 = alloca [25 x i32]
	%v343 = sub i32 0, 39878981
	%v344 = sub i32 0, 1839064934
	%v345 = sub i32 0, 68127266
	%v346 = sub i32 0, 425501077
	%v347 = sub i32 0, 1281736928
	%v348 = sub i32 0, 1304579021
	%v349 = sub i32 0, 1777897472
	%v350 = sub i32 0, 1921297034
	%v351 = sub i32 0, 226241316
	%v352 = sub i32 0, 1840038765
	%v353 = sub i32 0, 1238621050
	%v354 = sub i32 0, 1338682930
	%v355 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 0
	store i32 %v343, i32* %v355
	%v357 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 1
	store i32 1475936537, i32* %v357
	%v359 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 2
	store i32 1944942266, i32* %v359
	%v361 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 3
	store i32 1579815806, i32* %v361
	%v363 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 4
	store i32 1734290467, i32* %v363
	%v365 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 5
	store i32 1606724733, i32* %v365
	%v367 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 6
	store i32 %v344, i32* %v367
	%v369 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 7
	store i32 1749681186, i32* %v369
	%v371 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 8
	store i32 1409364573, i32* %v371
	%v373 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 9
	store i32 %v345, i32* %v373
	%v375 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 10
	store i32 1869282840, i32* %v375
	%v377 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 11
	store i32 1574753938, i32* %v377
	%v379 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 12
	store i32 1959861217, i32* %v379
	%v381 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 13
	store i32 %v346, i32* %v381
	%v383 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 14
	store i32 %v347, i32* %v383
	%v385 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 15
	store i32 %v348, i32* %v385
	%v387 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 16
	store i32 %v349, i32* %v387
	%v389 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 17
	store i32 %v350, i32* %v389
	%v391 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 18
	store i32 1623258471, i32* %v391
	%v393 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 19
	store i32 %v351, i32* %v393
	%v395 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 20
	store i32 824482268, i32* %v395
	%v397 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 21
	store i32 %v352, i32* %v397
	%v399 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 22
	store i32 %v353, i32* %v399
	%v401 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 23
	store i32 1453319654, i32* %v401
	%v403 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 24
	store i32 %v354, i32* %v403
	%v405 = alloca [25 x i32]
	%v406 = getelementptr inbounds [25 x i32], [25 x i32]* %v405, i32 0, i32 0
	%v407 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 0
	%v408 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 0
	call void @f_matrixMul(i32* %v406, i32* %v407, i32* %v408, i32 4)
	%v410 = getelementptr inbounds [25 x i32], [25 x i32]* %v405, i32 0, i32 0
	%v411 = getelementptr inbounds [25 x i32], [25 x i32]* %v342, i32 0, i32 0
	%v412 = getelementptr inbounds [25 x i32], [25 x i32]* %v278, i32 0, i32 0
	call void @f_matrixMul(i32* %v410, i32* %v411, i32* %v412, i32 5)
	br label %b60
b57:
	br label %b54
	br label %b59
b58:
	br label %b56
	br label %b59
b59:
	br label %b54
b60:
	%v415 = icmp ne i32 114514, 0
	br i1 %v415, label %b61, label %b62
b61:
	ret i32 0
	call void @putstr(i8* getelementptr inbounds ([10 x i8], [10 x i8]* @s32, i64 0, i64 0))
	br label %b60
b62:
	ret i32 0
}