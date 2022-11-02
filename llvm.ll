declare i32 @getint(...) 
declare void @putint(i32)
declare void @putstr(i8* )

@s0 = constant [26 x i8] c"funcTest: move disk from \00"
@s1 = constant [5 x i8] c" to \00"
@s2 = constant [2 x i8] c"
\00"
@s3 = constant [17 x i8] c"blockTest: 7 == \00"
@s4 = constant [8 x i8] c", 8 == \00"
@s5 = constant [2 x i8] c"
\00"
@s6 = constant [17 x i8] c"blockTest: 5 == \00"
@s7 = constant [9 x i8] c", 12 == \00"
@s8 = constant [2 x i8] c"
\00"
@s9 = constant [10 x i8] c"Exptest: \00"
@s10 = constant [2 x i8] c" \00"
@s11 = constant [2 x i8] c" \00"
@s12 = constant [2 x i8] c" \00"
@s13 = constant [2 x i8] c" \00"
@s14 = constant [2 x i8] c" \00"
@s15 = constant [2 x i8] c" \00"
@s16 = constant [2 x i8] c" \00"
@s17 = constant [2 x i8] c"
\00"
@s18 = constant [21 x i8] c"20373569 the mafia~
\00"
@s19 = constant [10 x i8] c"Exptest: \00"
@s20 = constant [2 x i8] c"
\00"

@g0 = dso_local global i32 389
@g1 = dso_local global i32 100005
@g2 = dso_local global i32 0
@g3 = dso_local global i32 0
@g4 = dso_local global i32 0

define dso_local void @move(i32 %a0, i32 %a1) {
b0:
	%v0 = alloca i32
	store i32 %a0, i32* %v0
	%v2 = alloca i32
	store i32 %a1, i32* %v2
	%v4 = load i32, i32* @g3
	%v5 = add i32 %v4, 1
	store i32 %v5, i32* @g3
	%v7 = load i32, i32* @g3
	%v8 = load i32, i32* @g0
	%v9 = srem i32 %v7, %v8
	%v10 = icmp eq i32 %v9, 0
	br i1 %v10, label %b1, label %b2
b1:
	%v12 = load i32, i32* %v0
	%v13 = load i32, i32* %v2
	call void @putstr(i8* getelementptr inbounds ([26 x i8], [26 x i8]* @s0, i64 0, i64 0))
	call void @putint(i32 %v12)
	call void @putstr(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @s1, i64 0, i64 0))
	call void @putint(i32 %v13)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s2, i64 0, i64 0))
	br label %b2
b2:
	ret void
}

define dso_local void @hanoi(i32 %a2, i32 %a3, i32 %a4, i32 %a5) {
b3:
	%v0 = alloca i32
	store i32 %a2, i32* %v0
	%v2 = alloca i32
	store i32 %a3, i32* %v2
	%v4 = alloca i32
	store i32 %a4, i32* %v4
	%v6 = alloca i32
	store i32 %a5, i32* %v6
	%v8 = load i32, i32* %v0
	%v9 = icmp eq i32 %v8, 1
	br i1 %v9, label %b4, label %b5
b4:
	%v11 = load i32, i32* %v2
	%v12 = load i32, i32* %v6
	call void @move(i32 %v11, i32 %v12)
	ret void
	br label %b5
b5:
	%v16 = load i32, i32* %v0
	%v17 = sub i32 %v16, 1
	%v18 = load i32, i32* %v2
	%v19 = load i32, i32* %v6
	%v20 = load i32, i32* %v4
	call void @hanoi(i32 %v17, i32 %v18, i32 %v19, i32 %v20)
	%v22 = load i32, i32* %v2
	%v23 = load i32, i32* %v6
	call void @move(i32 %v22, i32 %v23)
	%v25 = load i32, i32* %v0
	%v26 = sub i32 %v25, 1
	%v27 = load i32, i32* %v4
	%v28 = load i32, i32* %v2
	%v29 = load i32, i32* %v6
	call void @hanoi(i32 %v26, i32 %v27, i32 %v28, i32 %v29)
	ret void
}

define dso_local i32 @qpow(i32 %a6, i32 %a7) {
b6:
	%v0 = alloca i32
	store i32 %a6, i32* %v0
	%v2 = alloca i32
	store i32 %a7, i32* %v2
	%v4 = alloca i32
	store i32 1, i32* %v4
	br label %b7
b7:
	%v7 = load i32, i32* %v2
	%v8 = icmp ne i32 %v7, 0
	br i1 %v8, label %b8, label %b9
b8:
	%v10 = load i32, i32* %v2
	%v11 = sdiv i32 %v10, 2
	store i32 %v11, i32* %v2
	%v13 = load i32, i32* %v0
	%v14 = load i32, i32* %v0
	%v15 = mul i32 %v13, %v14
	%v16 = load i32, i32* @g0
	%v17 = srem i32 %v15, %v16
	store i32 %v17, i32* %v0
	%v19 = load i32, i32* %v2
	%v20 = srem i32 %v19, 2
	%v21 = icmp ne i32 %v20, 0
	br i1 %v21, label %b10, label %b11
b9:
	%v31 = load i32, i32* %v4
	ret i32 %v31
	ret i32 0
b10:
	%v23 = load i32, i32* %v4
	%v24 = load i32, i32* %v0
	%v25 = mul i32 %v23, %v24
	%v26 = load i32, i32* @g0
	%v27 = srem i32 %v25, %v26
	store i32 %v27, i32* %v4
	br label %b11
b11:
	br label %b7
}

define dso_local i32 @gcd(i32 %a8, i32 %a9) {
b12:
	%v0 = alloca i32
	store i32 %a8, i32* %v0
	%v2 = alloca i32
	store i32 %a9, i32* %v2
	%v4 = load i32, i32* %v2
	%v5 = icmp eq i32 0, %v4
	%v6 = zext i1 %v5 to i32
	%v7 = icmp ne i32 %v6, 0
	br i1 %v7, label %b13, label %b14
b13:
	%v9 = load i32, i32* %v0
	ret i32 %v9
	br label %b14
b14:
	%v12 = load i32, i32* %v2
	%v13 = load i32, i32* %v0
	%v14 = load i32, i32* %v2
	%v15 = srem i32 %v13, %v14
	%v16 = call i32 @gcd(i32 %v12, i32 %v15)
	ret i32 %v16
}

define dso_local i32 @testExp() {
b15:
	%v0 = load i32, i32* @g4
	%v1 = load i32, i32* @g4
	%v2 = mul i32 %v0, %v1
	store i32 %v2, i32* @g2
	%v4 = alloca i32
	%v5 = load i32, i32* @g1
	%v6 = load i32, i32* @g4
	%v7 = sdiv i32 %v5, %v6
	store i32 %v7, i32* %v4
	%v9 = load i32, i32* @g4
	%v10 = load i32, i32* @g4
	%v11 = mul i32 %v9, %v10
	%v12 = load i32, i32* @g4
	%v13 = sdiv i32 %v11, %v12
	%v14 = load i32, i32* @g4
	%v15 = add i32 %v13, %v14
	%v16 = load i32, i32* @g4
	%v17 = sub i32 %v15, %v16
	%v18 = alloca i32
	%v19 = load i32, i32* @g2
	%v20 = add i32 %v19, 1
	store i32 %v20, i32* %v18
	%v22 = alloca i32
	%v23 = sub i32 0, 2147483647
	%v24 = sub i32 0, %v23
	store i32 %v24, i32* %v22
	%v26 = alloca i32
	%v27 = sub i32 0, 1
	%v28 = load i32, i32* %v22
	%v29 = sub i32 %v27, %v28
	store i32 %v29, i32* %v26
	%v31 = alloca i32
	%v32 = load i32, i32* @g2
	%v33 = sub i32 0, %v32
	%v34 = sub i32 1, %v33
	%v35 = load i32, i32* %v18
	%v36 = mul i32 %v34, %v35
	%v37 = sdiv i32 %v36, 3
	%v38 = sub i32 %v37, 2
	%v39 = load i32, i32* @g1
	%v40 = add i32 %v38, %v39
	%v41 = load i32, i32* @g0
	%v42 = srem i32 %v40, %v41
	store i32 %v42, i32* %v31
	%v44 = alloca i32
	%v45 = load i32, i32* @g2
	%v46 = load i32, i32* %v18
	%v47 = call i32 @qpow(i32 %v45, i32 %v46)
	store i32 %v47, i32* %v44
	store i32 10, i32* %v18
	store i32 0, i32* %v22
	store i32 7, i32* %v18
	store i32 8, i32* %v22
	%v53 = load i32, i32* %v18
	%v54 = load i32, i32* %v22
	call void @putstr(i8* getelementptr inbounds ([17 x i8], [17 x i8]* @s3, i64 0, i64 0))
	call void @putint(i32 %v53)
	call void @putstr(i8* getelementptr inbounds ([8 x i8], [8 x i8]* @s4, i64 0, i64 0))
	call void @putint(i32 %v54)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s5, i64 0, i64 0))
	%v60 = alloca i32
	store i32 0, i32* %v60
	br label %b16
b16:
	%v63 = icmp ne i32 1, 0
	br i1 %v63, label %b17, label %b18
b17:
	%v65 = load i32, i32* %v60
	%v66 = add i32 %v65, 1
	store i32 %v66, i32* %v60
	%v68 = load i32, i32* %v60
	%v69 = srem i32 %v68, 2
	%v70 = icmp ne i32 %v69, 0
	br i1 %v70, label %b19, label %b20
b18:
	%v95 = load i32, i32* %v18
	%v96 = load i32, i32* %v22
	call void @putstr(i8* getelementptr inbounds ([17 x i8], [17 x i8]* @s6, i64 0, i64 0))
	call void @putint(i32 %v95)
	call void @putstr(i8* getelementptr inbounds ([9 x i8], [9 x i8]* @s7, i64 0, i64 0))
	call void @putint(i32 %v96)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s8, i64 0, i64 0))
	%v102 = alloca i32
	%v103 = load i32, i32* @g4
	store i32 %v103, i32* %v102
	%v105 = alloca i32
	store i32 0, i32* %v105
	%v107 = load i32, i32* %v102
	%v108 = icmp slt i32 %v107, 0
	br i1 %v108, label %b27, label %b28
b19:
	br label %b16
	br label %b20
b20:
	%v74 = load i32, i32* %v60
	%v75 = load i32, i32* %v18
	%v76 = icmp sge i32 %v74, %v75
	br i1 %v76, label %b21, label %b22
b21:
	br label %b18
	br label %b23
b22:
	%v80 = load i32, i32* %v22
	%v81 = icmp slt i32 %v80, 10
	br i1 %v81, label %b24, label %b25
b23:
	br label %b16
b24:
	%v83 = load i32, i32* %v22
	%v84 = load i32, i32* %v60
	%v85 = add i32 %v83, %v84
	store i32 %v85, i32* %v22
	br label %b26
b25:
	%v88 = load i32, i32* %v22
	%v89 = load i32, i32* %v60
	%v90 = sub i32 %v88, %v89
	store i32 %v90, i32* %v22
	br label %b26
b26:
	br label %b23
b27:
	store i32 10, i32* %v105
	br label %b29
b28:
	%v112 = load i32, i32* %v102
	%v113 = icmp sgt i32 %v112, 10
	br i1 %v113, label %b30, label %b31
b29:
	%v125 = load i32, i32* %v102
	%v126 = icmp sle i32 %v125, 10
	br i1 %v126, label %b35, label %b36
b30:
	store i32 20, i32* %v105
	br label %b32
b31:
	%v117 = load i32, i32* %v102
	%v118 = load i32, i32* @g4
	%v119 = icmp eq i32 %v117, %v118
	br i1 %v119, label %b33, label %b34
b32:
	br label %b29
b33:
	store i32 30, i32* %v105
	br label %b34
b34:
	br label %b32
b35:
	%v128 = load i32, i32* %v105
	%v129 = load i32, i32* %v102
	%v130 = add i32 %v128, %v129
	store i32 %v130, i32* %v105
	br label %b36
b36:
	%v133 = load i32, i32* @g2
	%v134 = load i32, i32* %v18
	%v135 = load i32, i32* %v22
	%v136 = load i32, i32* %v26
	%v137 = load i32, i32* %v44
	call void @putstr(i8* getelementptr inbounds ([10 x i8], [10 x i8]* @s9, i64 0, i64 0))
	call void @putint(i32 %v133)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s10, i64 0, i64 0))
	call void @putint(i32 %v134)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s11, i64 0, i64 0))
	call void @putint(i32 %v135)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s12, i64 0, i64 0))
	call void @putint(i32 %v136)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s13, i64 0, i64 0))
	call void @putint(i32 %v137)
	%v148 = load i32, i32* %v102
	%v149 = load i32, i32* %v105
	%v150 = load i32, i32* %v31
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s14, i64 0, i64 0))
	call void @putint(i32 %v148)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s15, i64 0, i64 0))
	call void @putint(i32 %v149)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s16, i64 0, i64 0))
	call void @putint(i32 %v150)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s17, i64 0, i64 0))
	%v158 = load i32, i32* %v102
	%v159 = load i32, i32* %v105
	%v160 = call i32 @gcd(i32 %v158, i32 %v159)
	ret i32 %v160
}

define dso_local i32 @main() {
b37:
	call void @putstr(i8* getelementptr inbounds ([21 x i8], [21 x i8]* @s18, i64 0, i64 0))
	%v1 = call i32 (...) @getint()
	store i32 %v1, i32* @g4
	%v3 = load i32, i32* @g4
	call void @hanoi(i32 %v3, i32 1, i32 2, i32 3)
	%v5 = call i32 @testExp()
	call void @putstr(i8* getelementptr inbounds ([10 x i8], [10 x i8]* @s19, i64 0, i64 0))
	call void @putint(i32 %v5)
	call void @putstr(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @s20, i64 0, i64 0))
	ret i32 0
}