package mid_end;

import llvm_ir.BasicBlock;
import llvm_ir.Function;
import llvm_ir.Instr;
import llvm_ir.Module;
import llvm_ir.instr.BranchInstr;
import llvm_ir.instr.JumpInstr;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public class CFGBuilder {
    private Module module;

    // 流图
    private HashMap<BasicBlock, HashSet<BasicBlock>> preMap;
    private HashMap<BasicBlock, HashSet<BasicBlock>> sucMap;
    // 支配树
    private HashMap<BasicBlock, BasicBlock> parentMap;
    private HashMap<BasicBlock, HashSet<BasicBlock>> childMap;
    // DF
    private HashMap<BasicBlock, HashSet<BasicBlock>> DFMap;


    public CFGBuilder(Module module) {
        this.module = module;
        this.preMap = null;
        this.sucMap = null;
        this.parentMap = null;
        this.childMap = null;
        this.DFMap = null;
    }

    public void run() {
        for (Function function : module.getFunctionList()) {
            // 初始化辅助变量
            initAttr(function);
            // 求函数的控制流图
            getCFG(function);
            // 求某个基本块的支配集合
            getDom(function);
            // 求支配树（即直接支配关系）
            getIDom(function);
            // 求支配边界集合
            getDF(function);
        }
    }

    private void initAttr(Function function) {
        preMap = new HashMap<>();
        sucMap = new HashMap<>();
        parentMap = new HashMap<>();
        childMap = new HashMap<>();
        DFMap = new HashMap<>();
        for (BasicBlock bb : function.getBBList()) {
            preMap.put(bb, new HashSet<>());
            sucMap.put(bb, new HashSet<>());
            parentMap.put(bb, null);
            childMap.put(bb, new HashSet<>());
            DFMap.put(bb, new HashSet<>());
        }
    }

    private void getCFG(Function function) {
        LinkedList<BasicBlock> bbList = function.getBBList();
        // 求出preMap和sucMap
        for (BasicBlock bb : bbList) {
            Instr lastInstr = bb.getLastInstr();
            if (lastInstr instanceof BranchInstr) {
                BasicBlock thenBlock = ((BranchInstr) lastInstr).getThenBlock();
                BasicBlock elseBlock = ((BranchInstr) lastInstr).getElseBlock();
                sucMap.get(bb).add(thenBlock);
                sucMap.get(bb).add(elseBlock);
                preMap.get(thenBlock).add(bb);
                preMap.get(elseBlock).add(bb);
            }
            else if (lastInstr instanceof JumpInstr) {
                BasicBlock targetBB = ((JumpInstr) lastInstr).getTargetBB();
                sucMap.get(bb).add(targetBB);
                preMap.get(targetBB).add(bb);
            }
        }
        // 将前驱和后继信息写入BB
        for (BasicBlock bb : bbList) {
            bb.setPreSet(preMap.get(bb));
            bb.setSucSet(sucMap.get(bb));
        }
        // 将sucMap和preMap写入function
        function.setPreMap(preMap);
        function.setSucMap(sucMap);
    }


    private void getDom(Function function) {
        LinkedList<BasicBlock> bbList = function.getBBList();
        BasicBlock entry = bbList.getFirst();
        for (BasicBlock target : bbList) {
            // 找到所有不被target支配的BB，放入reachedSet中
            HashSet<BasicBlock> reachedSet = new HashSet<>();
            DFSForJudgeDom(entry, target, reachedSet);
            // 接下来，所有不在reached中的BB，都是被target支配的BB(包括target本身)
            HashSet<BasicBlock> domSet = new HashSet<>();
            for (BasicBlock temp : bbList) {
                if (! reachedSet.contains(temp)) {
                    domSet.add(temp);
                }
            }
            target.setDomSet(domSet);
        }
    }

    // 找到所有不被target支配的BB
    private void DFSForJudgeDom(BasicBlock entry, BasicBlock target, HashSet<BasicBlock> reachedSet) {
        if (entry.equals(target)) {
            return;
        }
        reachedSet.add(entry);
        for (BasicBlock sucBB : entry.getSucSet()) {
            if (! reachedSet.contains(sucBB)) {
                DFSForJudgeDom(sucBB, target, reachedSet);
            }
        }
    }

    // 直接根据定义：A直接支配B——A严格支配B，且不严格支配任何严格支配B的节点
    private void getIDom(Function function) {
        LinkedList<BasicBlock> bbList = function.getBBList();
        for (BasicBlock domer : bbList) {
            for (BasicBlock domed : bbList) {
                if (judgeIDOM(domer, domed, bbList)) {
                    assert parentMap.get(domed) == null;
                    parentMap.put(domed, domer);
                    childMap.get(domer).add(domed);
                }
            }
        }
        // 将支配和被支配信息写入BB
        for (BasicBlock bb : bbList) {
            bb.setParent(parentMap.get(bb));
            bb.setChildrenSet(childMap.get(bb));
        }
        // 将支配树写入function
        function.setParentMap(parentMap);
        function.setChildMap(childMap);
    }

    private boolean judgeIDOM(BasicBlock domer, BasicBlock domed, LinkedList<BasicBlock> bbList) {
        // 如果domer和domed没有支配关系，那么显然也没有直接支配关系
        if (! domer.getDomSet().contains(domed)) {
            return false;
        }
        for (BasicBlock mid : domer.getDomSet()) {
            if (! mid.equals(domer) && ! mid.equals(domed) && mid.getDomSet().contains(domed)) {
                return false;
            }
        }
        return true;
    }

    private void getDF(Function function) {
        LinkedList<BasicBlock> bbList = function.getBBList();
        // 对所有的边进行遍历, 求出DFMap
        for (Map.Entry<BasicBlock, HashSet<BasicBlock>> entry : sucMap.entrySet()) {
            BasicBlock a = entry.getKey();
            for (BasicBlock b : entry.getValue()) {
                // a, b是某条边的两个节点（基本块）
                BasicBlock x = a;
                while (! x.getDomSet().contains(b)) {
                    DFMap.get(x).add(b);
                    x = x.getParent();
                }
            }
        }
        // 将DF信息写入每个BB
        for (BasicBlock bb : bbList) {
            bb.setDF(DFMap.get(bb));
        }
    }

}
