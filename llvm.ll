declare i32 @getint(...) 
declare void @putint(i32)
declare void @putstr(i8* )

@s0 = constant [14 x i8] c"This is f().
\00"
@s1 = constant [3 x i8] c", \00"
@s2 = constant [2 x i8] c"
\00"
@s3 = constant [10 x i8] c"19373035
\00"
@s4 = constant [3 x i8] c"a:\00"
@s5 = constant [2 x i8] c"
\00"

@g0 = dso_local global i32 1
@g1 = dso_local global i32 2
@g2 = dso_local global i32 3
@g3 = dso_local global i32 4
@g4 = dso_local global i32 5
@g5 = dso_local global i32 6
@g6 = dso_local global [2 x i32] [i32 1, i32 2]
@g7 = dso_local global [6 x i32] [i32 1, i32 2, i32 3, i32 4, i32 5, i32 6]
@g8 = dso_local global i32 1
@g9 = dso_local global i32 2
@g10 = dso_local global i32 3
@g11 = dso_local global i32 0
@g12 = dso_local global i32 0
@g13 = dso_local global [2 x i32] [i32 1, i32 2]
@g14 = dso_local global [6 x i32] [i32 1, i32 2, i32 3, i32 4, i32 5, i32 6]
@g15 = dso_local global [3 x i32] zeroinitializer
@g16 = dso_local global [6 x i32] zeroinitializer

define dso_local void @f_f() {
b0:
	call void @putstr(i8* getelementptr inbounds ([14 x i8], [14 x i8]* @s0, i64 0, i64 0))
	%v1 = alloca i32
	store i32 4, i32* %v1
	store i32 5, i32* %v1
	%v4 = load i32, i32* %v1
	%v5 = add i32 %v4, 1
	store i32 %v5, i32* %v1
	%v7 = load i32, i32* %v1
	%v8 = add i32 %v7, 2
	store i32 %v8, i32* %v1
	%v10 = icmp ne i32 1, 0
	br i1 %v10, label %b1, label %b2
b1:
	br label %b2
b2:
	%v13 = load i32, i32* %v1
	%v14 = icmp ne i32 %v13, 0
	br i1 %v14, label %b3, label %b4
b3:
	br label %b5
b4:
	br label %b5
b5:
	br label %b6
b6:
	%v19 = load i32, i32* %v1
	%v20 = icmp sgt i32 %v19, 0
	br i1 %v20, label %b7, label %b8
b7:
	%v22 = load i32, i32* %v1
	%v23 = sub i32 %v22, 1
	store i32 %v23, i32* %v1
	%v25 = load i32, i32* %v1
	%v26 = icmp eq i32 %v25, 1
	br i1 %v26, label %b9, label %b10
b8:
	ret void
	ret void
b9:
	br label %b8
	br label %b11
b10:
	br label %b6
	br label %b11
b11:
	br label %b6
}

define dso_local i32 @f_g0() {
b12:
	ret i32 0
}

define dso_local i32 @f_g1(i32 %a0) {
b13:
	%v0 = alloca i32
	store i32 %a0, i32* %v0
	%v2 = load i32, i32* %v0
	%v3 = add i32 %v2, 1
	ret i32 %v3
}

define dso_local i32 @f_g2(i32 %a1, i32 %a2) {
b14:
	%v0 = alloca i32
	store i32 %a1, i32* %v0
	%v2 = alloca i32
	store i32 %a2, i32* %v2
	%v4 = load i32, i32* %v0
	%v5 = load i32, i32* %v2
	%v6 = add i32 %v4, %v5
	ret i32 %v6
}

define dso_local i32 @f_g3(i32 %a3, i32 %a4, i32 %a5) {
b15:
	%v0 = alloca i32
	store i32 %a3, i32* %v0
	%v2 = alloca i32
	store i32 %a4, i32* %v2
	%v4 = alloca i32
	store i32 %a5, i32* %v4
	%v6 = load i32, i32* %v0
	%v7 = load i32, i32* %v2
	%v8 = add i32 %v6, %v7
	%v9 = load i32, i32* %v4
	%v10 = add i32 %v8, %v9
	ret i32 %v10
}

define dso_local void @f_h(i32* %a6, i32* %a7) {
b16:
	%v0 = getelementptr inbounds i32, i32* %a6, i32 0
	%v1 = load i32, i32* %v0
	%v2 = mul i32 4, 0
	%v3 = add i32 %v2, 0
	%v4 = getelementptr inbounds i32, i32* %a7, i32 %v3
	%v5 = load i32, i32* %v4
	call void @putint(i32 %v1)
	call void @putstr(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @s1, i64 0, i64 0))
	call void @putint(i32 %v5)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s2, i64 0, i64 0))
	ret void
}

define dso_local void @f_n() {
b17:
	ret void
}

define dso_local i32 @main() {
b18:
	%v0 = alloca i32
	store i32 1, i32* %v0
	%v2 = call i32 (...) @getint()
	store i32 %v2, i32* %v0
	call void @putstr(i8* getelementptr inbounds ([10 x i8], [10 x i8]* @s3, i64 0, i64 0))
	%v5 = load i32, i32* %v0
	%v6 = add i32 %v5, 1
	store i32 %v6, i32* %v0
	%v8 = alloca [3 x i32]
	%v9 = getelementptr inbounds [3 x i32], [3 x i32]* %v8, i32 0, i32 0
	store i32 1, i32* %v9
	%v11 = alloca [12 x i32]
	%v12 = mul i32 4, 0
	%v13 = add i32 %v12, 0
	%v14 = getelementptr inbounds [12 x i32], [12 x i32]* %v11, i32 0, i32 %v13
	store i32 1, i32* %v14
	%v16 = add i32 1, 1
	%v17 = load i32, i32* %v0
	%v18 = add i32 2, 5
	%v19 = load i32, i32* %v0
	%v20 = sub i32 %v18, %v19
	%v21 = getelementptr inbounds [3 x i32], [3 x i32]* %v8, i32 0, i32 1
	%v22 = load i32, i32* %v21
	%v23 = call i32 @f_g0()
	%v24 = call i32 @f_g1(i32 3)
	%v25 = call i32 @f_g2(i32 3, i32 5)
	%v26 = load i32, i32* %v0
	%v27 = load i32, i32* %v0
	%v28 = load i32, i32* %v0
	%v29 = call i32 @f_g3(i32 %v26, i32 %v27, i32 %v28)
	%v30 = load i32, i32* %v0
	%v31 = icmp eq i32 0, %v30
	%v32 = zext i1 %v31 to i32
	%v33 = icmp ne i32 %v32, 0
	br i1 %v33, label %b19, label %b20
b19:
	br label %b20
b20:
	%v36 = getelementptr inbounds [3 x i32], [3 x i32]* %v8, i32 0, i32 0
	%v37 = getelementptr inbounds [12 x i32], [12 x i32]* %v11, i32 0, i32 0
	call void @f_h(i32* %v36, i32* %v37)
	%v39 = mul i32 4, 0
	%v40 = getelementptr inbounds [12 x i32], [12 x i32]* %v11, i32 0, i32 %v39
	%v41 = getelementptr inbounds [12 x i32], [12 x i32]* %v11, i32 0, i32 0
	call void @f_h(i32* %v40, i32* %v41)
	%v43 = load i32, i32* @g8
	%v44 = load i32, i32* @g9
	%v45 = mul i32 %v43, %v44
	%v46 = add i32 %v45, 3
	%v47 = sub i32 %v46, 4
	%v48 = load i32, i32* @g8
	%v49 = load i32, i32* @g9
	%v50 = sdiv i32 %v48, %v49
	%v51 = load i32, i32* @g8
	%v52 = icmp slt i32 %v50, %v51
	br i1 %v52, label %b21, label %b22
b21:
	br label %b22
b22:
	%v55 = load i32, i32* @g8
	%v56 = load i32, i32* @g9
	%v57 = srem i32 %v55, %v56
	%v58 = load i32, i32* @g8
	%v59 = icmp sgt i32 %v57, %v58
	br i1 %v59, label %b23, label %b24
b23:
	br label %b24
b24:
	%v62 = load i32, i32* @g8
	%v63 = load i32, i32* @g9
	%v64 = srem i32 %v62, %v63
	%v65 = load i32, i32* @g8
	%v66 = icmp sge i32 %v64, %v65
	br i1 %v66, label %b25, label %b26
b25:
	br label %b26
b26:
	%v69 = load i32, i32* @g8
	%v70 = load i32, i32* @g9
	%v71 = srem i32 %v69, %v70
	%v72 = load i32, i32* @g8
	%v73 = icmp sle i32 %v71, %v72
	br i1 %v73, label %b27, label %b28
b27:
	br label %b28
b28:
	%v76 = icmp eq i32 3, 2
	br i1 %v76, label %b29, label %b30
b29:
	br label %b30
b30:
	%v79 = icmp ne i32 3, 2
	br i1 %v79, label %b31, label %b32
b31:
	br label %b32
b32:
	%v82 = icmp ne i32 3, 0
	br i1 %v82, label %b35, label %b34
b33:
	br label %b34
b34:
	%v87 = icmp ne i32 3, 0
	br i1 %v87, label %b36, label %b38
b35:
	%v84 = icmp ne i32 2, 0
	br i1 %v84, label %b33, label %b34
b36:
	br label %b37
b37:
	%v92 = sub i32 0, 1
	%v93 = load i32, i32* %v0
	%v94 = add i32 %v93, 1
	call void @putstr(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @s4, i64 0, i64 0))
	call void @putint(i32 %v94)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s5, i64 0, i64 0))
	ret i32 0
	ret i32 0
b38:
	%v89 = icmp ne i32 2, 0
	br i1 %v89, label %b36, label %b37
}