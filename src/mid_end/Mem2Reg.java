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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

public class Mem2Reg {
    private Module module;
    private HashSet<Instr> useInstrList;
    private HashSet<Instr> defInstrList;
    private HashSet<BasicBlock> defBBList;
    private HashSet<BasicBlock> useBBList;
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
                    if (instr instanceof AllocaInstr) {
                        initAttr(instr);
                        insertPhi(instr);
                        rename(instr);
                    }
                }
            }
        }
    }


    private void initAttr(Instr instr) {
        this.useBBList = new HashSet<>();
        this.defBBList = new HashSet<>();
        this.useInstrList = new HashSet<>();
        this.defInstrList = new HashSet<>();
        this.stack = new Stack<>();
        for (Use use : instr.getUseList()) {
            assert use.getUser() instanceof Instr;
            Instr user = (Instr)use.getUser();
            if (user instanceof StoreInstr) {
                defInstrList.add(user);
                defBBList.add(user.getParentBB());
            }
            else if (user instanceof LoadInstr) {
                useInstrList.add(user);
                defBBList.add(user.getParentBB());
            }
        }

    }

    private void insertPhi(Instr instr) {
        HashSet<BasicBlock> F = new HashSet<>(); // 需要添加phi的基本块的集合
        HashSet<BasicBlock> W = new HashSet<>(defBBList); // 定义变量的基本块的集合
        while (! W.isEmpty()) {
            BasicBlock X = W.iterator().next();
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
    }


    private void rename(Instr instr) {

    }
}
