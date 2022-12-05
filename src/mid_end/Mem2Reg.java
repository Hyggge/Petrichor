package mid_end;

import llvm_ir.BasicBlock;
import llvm_ir.Function;
import llvm_ir.IRBuilder;
import llvm_ir.Instr;
import llvm_ir.Module;
import llvm_ir.UndefinedValue;
import llvm_ir.Use;
import llvm_ir.Value;
import llvm_ir.instr.AllocaInstr;
import llvm_ir.instr.LoadInstr;
import llvm_ir.instr.PhiInstr;
import llvm_ir.instr.StoreInstr;
import llvm_ir.type.BaseType;
import llvm_ir.type.PointerType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class Mem2Reg {
    private Module module;
    private ArrayList<Instr> useInstrList;
    private ArrayList<Instr> defInstrList;
    private ArrayList<BasicBlock> defBBList;
    private ArrayList<BasicBlock> useBBList;
    private Stack<Value> stack;


    public Mem2Reg(Module module) {
        this.module = module;
        this.useBBList = null;
        this.defBBList = null;
        this.useInstrList = null;
        this.defInstrList = null;
        this.stack = null;
    }

    public void run() {
        for (Function function : module.getFunctionList()) {
            // 遍历所有基本块，插入phi指令
            for (BasicBlock bb : function.getBBList()) {
                for (Instr instr : bb.getInstrList()) {
                    if (instr instanceof AllocaInstr && ((PointerType)instr.getType()).getTargetType() == BaseType.INT32) {
                        // 初始化和该alloca指令相关的数据结构
                        initAttr(instr);
                        // 找出需要添加phi指令的基本块，并添加phi
                        insertPhi(instr);
                        // 通过DFS进行重命名，同时将相关的store和load指令删除
                        rename(function.getBBList().get(0));
                    }
                }
            }
            // 遍历所有基本块，删除所有的alloca, load, store指令
            for (BasicBlock bb : function.getBBList()) {
                bb.getInstrList().removeIf(instr -> instr instanceof AllocaInstr || instr instanceof LoadInstr || instr instanceof StoreInstr);
            }
        }
    }


    private void initAttr(Instr instr) {
        this.useBBList = new ArrayList<>();
        this.defBBList = new ArrayList<>();
        this.useInstrList = new ArrayList<>();
        this.defInstrList = new ArrayList<>();
        this.stack = new Stack<>();
        for (Use use : instr.getUseList()) {
            assert use.getUser() instanceof Instr;
            Instr user = (Instr)use.getUser();
            if (user instanceof StoreInstr) {
                defInstrList.add(user);
                if (! defBBList.contains(user.getParentBB()))
                    defBBList.add(user.getParentBB());
            }
            else if (user instanceof LoadInstr) {
                useInstrList.add(user);
                if (! defBBList.contains(user.getParentBB()))
                    useBBList.add(user.getParentBB());
            }
        }

    }

    private void insertPhi(Instr instr) {
        HashSet<BasicBlock> F = new HashSet<>(); // 需要添加phi的基本块的集合
        HashSet<BasicBlock> W = new HashSet<>(defBBList); // 定义变量的基本块的集合
        while (! W.isEmpty()) {
            BasicBlock X = W.iterator().next();
            W.remove(X);
            for (BasicBlock Y : X.getDF()) {
                if (! F.contains(Y)) {
                    insert(Y);
                    F.add(Y);
                    if (! defBBList.contains(Y)) {
                        W.add(Y);
                    }
                }
            }
        }
    }

    private void insert(BasicBlock bb) {
        LinkedList<Instr> instrList = bb.getInstrList();
        String name = IRBuilder.getInstance().genLocalVarName(bb.getParentFunction());
        Instr phi = new PhiInstr(name, bb.getPreList());
        instrList.addFirst(phi);
        phi.setParentBB(bb);
        // phi既是useInstr,又是defInstr
        useInstrList.add(phi);
        defInstrList.add(phi);
    }


    // 通过DFS对load、store、phi进行重命名
    private void rename(BasicBlock entry) {
        // cnt记录遍历entry的过程中，stack的push次数
        int cnt = 0;
        // 遍历基本块entry的各个指令，修改其reaching-define
        Iterator<Instr> iterator = entry.getInstrList().iterator();
        while (iterator.hasNext()) {
            Instr instr = iterator.next();
            if (instr instanceof LoadInstr && useInstrList.contains(instr)) {
                // 将所有使用该load指令的指令，改为使用stack.peek()
                // 如果当前的stack.peek()为空，则说明该变量没有被def，值是未定义的undefined
                Value newValue = stack.empty() ? new UndefinedValue() : stack.peek();
                instr.modifyAllUseThisToNewValue(newValue);
            }
            else if (instr instanceof StoreInstr && defInstrList.contains(instr)) {
                // 将该指令使用的值推入stack
                Value value = ((StoreInstr) instr).getFrom();
                stack.push(value);
                cnt++;
            }
            else if (instr instanceof PhiInstr && defInstrList.contains(instr)) {
                // 将该指令推入stack
                stack.push(instr);
                cnt++;
            }
        }
        // 遍历entry的后继集合，将最新的define（stack.peek）填充进每个后继块的第一个phi指令中
        // 有可能某个后继块没有和当前alloc指令相关的phi，需要进行特判（符合条件的Phi应该在useInstrList中）
        for (BasicBlock sucBB : entry.getSucList()) {
            Instr firstInstr = sucBB.getFirstInstr();
            if (firstInstr instanceof PhiInstr && useInstrList.contains(firstInstr)) {
                // 将stack.peek() 插入该phi指令的options中
                PhiInstr phi = (PhiInstr) firstInstr;
                Value option = stack.empty() ? new UndefinedValue() : stack.peek();
                phi.addOption(option, entry);
            }
        }
        // 对entry支配的基本块使用rename方法，实现DFS
        for (BasicBlock child : entry.getChildList()) {
            rename(child);
        }
        // 将该次dfs时压入stack的数据全部弹出
        for (int i = 0; i < cnt; i++) {
            stack.pop();
        }
    }
}
