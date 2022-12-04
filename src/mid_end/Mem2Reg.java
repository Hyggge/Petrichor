package mid_end;

import llvm_ir.BasicBlock;
import llvm_ir.Function;
import llvm_ir.IRBuilder;
import llvm_ir.Instr;
import llvm_ir.Module;
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
            for (BasicBlock bb : function.getBBList()) {
                for (Instr instr : bb.getInstrList()) {
                    if (instr instanceof AllocaInstr &&
                            ((PointerType)instr.getType()).getTargetType() == BaseType.INT32) {
                        initAttr(instr);
                        insertPhi(instr);
                        rename(defBBList.get(0));
                    }
                }
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
        String name = IRBuilder.getInstance().genLocalVarName();
        Instr phi = new PhiInstr(name, new ArrayList<>());
        instrList.addFirst(phi);
        // phi既是useInstr,又是defInstr
        useInstrList.add(phi);
        defInstrList.add(phi);
    }


    // 通过DFS对load、store、phi进行重命名
    private void rename(BasicBlock entry) {
        // 遍历基本块entry的各个指令，修改其reaching-define
        for (Instr instr : entry.getInstrList()) {
            if (instr instanceof LoadInstr) {
                // TODO: 将所有使用该load指令的指令，改为使用stack.peek()
                instr.modifyAllUseThisToNewValue(stack.peek());

            }
            else if (instr instanceof StoreInstr) {
                // TODO: 将该指令使用的值推入stack
                Value value = ((StoreInstr) instr).getFrom();
                stack.push(value);
            }
            else if (instr instanceof PhiInstr) {
                // TODO: 将该指令推入stack
                stack.push(instr);
            }
        }
        // 遍历entry的后继集合，将最新的define（stack.peek）填充进每个后继块的第一个phi指令中
        // 有可能某个后继块没有和当前alloc指令相关的phi，需要进行特判（符合条件的Phi应该在useInstrList中）
        for (BasicBlock sucBB : entry.getSucList()) {
            Instr firstInstr = sucBB.getFirstInstr();
            if (firstInstr instanceof PhiInstr && useInstrList.contains(firstInstr)) {
                // 将stack.peek() 插入该phi指令的options中
                PhiInstr phi = (PhiInstr) firstInstr;
                phi.addOption(stack.peek());
            }
        }
        // 对entry支配的基本块使用rename方法，实现DFS
        for (BasicBlock child : entry.getChildList()) {
            rename(child);
        }
    }
}
