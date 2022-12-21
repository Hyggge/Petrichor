package mid_end;

import llvm_ir.BasicBlock;
import llvm_ir.Function;
import llvm_ir.Module;
import llvm_ir.Value;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class ActivenessAnalysis {
    private Module module;
    private HashMap<BasicBlock, HashSet<Value>> inMap;
    private HashMap<BasicBlock, HashSet<Value>> outMap;


    public ActivenessAnalysis(Module module) {
        this.module = module;
    }

    public void run() {
        for (Function function : module.getFunctionList()) {
            initAttr(function);
            for (BasicBlock bb : function.getBBList()) {
                bb.buildDefUse();
            }
            getInOut(function);
            // 打印相关信息
            for (BasicBlock bb : function.getBBList()) {
                System.out.println("\n" + bb.getName() + ": ");
                System.out.print("use: ");
                for (Value value : bb.getUse()) {
                    System.out.print(value.getName() + " ");
                }
                System.out.print("\ndef:");
                for (Value value : bb.getDef()) {
                    System.out.print(value.getName() + " ");
                }
                System.out.print("\nin:");
                for (Value value : bb.getIn()) {
                    System.out.print(value.getName() + " ");
                }
                System.out.print("\nout:");
                for (Value value : bb.getOut()) {
                    System.out.print(value.getName() + " ");
                }
                System.out.println("");
            }
        }
    }

    private void initAttr(Function function) {
        inMap = new HashMap<>();
        outMap = new HashMap<>();
        for (BasicBlock bb : function.getBBList()) {
            outMap.put(bb, new HashSet<>());
            inMap.put(bb, new HashSet<>());
        }
    }


    private void getInOut(Function function) {
        LinkedList<BasicBlock> bbList = function.getBBList();
        boolean change = true;
        // 根据算法找到所有基本块的in和out
        while (change) {
            change = false;
            for (int i = bbList.size() - 1; i >= 0; i--) {
                BasicBlock bb = bbList.get(i);
                // 根据后继的in，求出当前bb的out
                HashSet<Value> out = new HashSet<>();
                for (BasicBlock sucBB : bb.getSucList()) {
                    out.addAll(inMap.get(sucBB));
                    // 因为后继的in中没有计算phi指令使用的operand
                    // 如果sucBB中有PHI指令
                    // 我们还需要将PHI指令中和bb相对应的operand加入到out中
                    //for (Instr instr : sucBB.getInstrList()) {
                    //    if (instr instanceof PhiInstr) {
                    //        Value targetOperand = ((PhiInstr) instr).getOperandFrom(bb);
                    //        if (targetOperand != null) {
                    //        //    if (targetOperand instanceof Instr || targetOperand instanceof Param || targetOperand instanceof GlobalVar) {
                    //        //        out.add(targetOperand);
                    //            out.add(instr);
                    //            //}
                    //        }
                    //    }
                    //}
                    outMap.put(bb, out);
                }
                // 根据公式in = (out - def) + use，求出当前基本块的in
                HashSet<Value> oriIn = inMap.get(bb);
                HashSet<Value> newIn = new HashSet<>();
                newIn.addAll(out);
                newIn.removeAll(bb.getDef());
                newIn.addAll(bb.getUse());
                //for (Instr instr : bb.getInstrList()) {
                //    if (instr instanceof PhiInstr) {
                //        newIn.add(instr);
                //    }
                //}
                if (! newIn.equals(oriIn)) {
                    inMap.put(bb, newIn);
                    change = true;
                }
            }
        }
        // 将in out 写入每个基本块
        for (BasicBlock bb : bbList) {
            bb.setIn(inMap.get(bb));
            bb.setOut(outMap.get(bb));
        }
    }

}
