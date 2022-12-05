package mid_end;

import llvm_ir.BasicBlock;
import llvm_ir.Constant;
import llvm_ir.Function;
import llvm_ir.IRBuilder;
import llvm_ir.Instr;
import llvm_ir.Module;
import llvm_ir.UndefinedValue;
import llvm_ir.Value;
import llvm_ir.instr.BranchInstr;
import llvm_ir.instr.JumpInstr;
import llvm_ir.instr.MoveInstr;
import llvm_ir.instr.PcopyInstr;
import llvm_ir.instr.PhiInstr;
import llvm_ir.type.BaseType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class RemovePhi {
    private Module module;

    public RemovePhi(Module module) {
        this.module = module;
    }

    public void run() {
        for (Function function : module.getFunctionList()) {
            // 将所有的phi指令删除，增加pcopy指令
            Phi2Pcopy(function);
            // 将所有的pcopy指令删除，增加move指令
            Pcopy2Move(function);
        }
    }

    private void Phi2Pcopy(Function function) {
        ArrayList<BasicBlock> bbList = new ArrayList<>(function.getBBList());
        for (BasicBlock bb : bbList) {
            // 先保证bb中含有phi指令
            if (! (bb.getFirstInstr() instanceof PhiInstr)) continue;
            // 遍历bb的前驱基本块集合，有多少前驱就增加多少个pcopy
            ArrayList<BasicBlock> preList = bb.getPreList();
            ArrayList<PcopyInstr> pcopyList = new ArrayList<>();
            preList.forEach((x) -> pcopyList.add(new PcopyInstr(IRBuilder.getInstance().genLocalVarName(function))));
            // 将每个pcopy插入适当的位置
            ArrayList<BasicBlock> oriPreList = new ArrayList<>(preList);
            for (int i = 0; i < oriPreList.size(); i++) {
                BasicBlock preBB = oriPreList.get(i);
                PcopyInstr pcopy = pcopyList.get(i);
                // 如果preBB只有bb一个后继，那么我们直接将pcopy放在preBB中即可
                if (preBB.getSucList().size() == 1) insertPcopyToPreBB(pcopy, preBB);
                // 如果preBB有多个后继，那么需要在新建一个中间基本块
                else insertPcopyToMidBB(pcopy, preBB, bb);
            }
            // 遍历bb所有Phi，每个phi中option放到对应的各个pcopy中（options本身就是按照前驱的顺序排列的）
            // 如果option是未定义的，那么我们不把他加入对应copy中
            Iterator<Instr> iterator = bb.getInstrList().iterator();
            while (iterator.hasNext()) {
                Instr instr = iterator.next();
                // 找到PhiInstr
                if (instr instanceof PhiInstr) {
                    PhiInstr phi = (PhiInstr) instr;
                    // 获得phi所有的options
                    ArrayList<Value> options = phi.getOptions();
                    // 将每个option都插入到对应的pcopy中
                    for (int i = 0; i < options.size(); i++) {
                        Value option = options.get(i);
                        if (! (option instanceof UndefinedValue)) {
                            pcopyList.get(i).addCopy(phi, option);
                        }
                    }
                    // 删除phi指令
                    iterator.remove();
                }
            }

        }
    }

    private void insertPcopyToPreBB(PcopyInstr pcopy, BasicBlock preBB) {
        LinkedList<Instr> instrList = preBB.getInstrList();
        Instr lastInstr = instrList.getLast();
        assert lastInstr instanceof BranchInstr || lastInstr instanceof JumpInstr;
        // 将pcopy插入到跳转语句之前
        instrList.add(instrList.indexOf(lastInstr), pcopy);
        pcopy.setParentBB(preBB);
    }

    private void insertPcopyToMidBB(PcopyInstr pcopy, BasicBlock preBB, BasicBlock sucBB) {
        Function function = preBB.getParentFunction();
        BasicBlock midBB = new BasicBlock(IRBuilder.getInstance().genBBName());
        midBB.setParentFunction(function);
        function.getBBList().add(function.getBBList().indexOf(sucBB), midBB);
        // 将pcopy插入midBB
        midBB.addInstr(pcopy);
        pcopy.setParentBB(midBB);
        // 修改跳转关系(preBB的最后一句一定是branch)
        BranchInstr instr = (BranchInstr)preBB.getLastInstr();
        BasicBlock thenBlock = instr.getThenBlock();
        BasicBlock elseBlock = instr.getElseBlock();
        if (sucBB.equals(thenBlock)) {
            instr.setThenBlock(midBB);
            String name = IRBuilder.getInstance().genLocalVarName(function);
            JumpInstr jump = new JumpInstr(name, sucBB);
            midBB.addInstr(jump);
            jump.setParentBB(midBB);
        }
        else {
            instr.setElseBlock(midBB);
            String name = IRBuilder.getInstance().genLocalVarName(function);
            JumpInstr jump = new JumpInstr(name, sucBB);
            midBB.addInstr(jump);
            jump.setParentBB(midBB);
        }
        // 修改preBB和sucBB的前驱和后继关系
        preBB.getSucList().remove(sucBB);
        preBB.getSucList().add(midBB);
        sucBB.getPreList().remove(preBB);
        sucBB.getPreList().add(midBB);
        // 为midBB增加前驱后继关系
        midBB.setSucList(new ArrayList<>());
        midBB.getSucList().add(sucBB);
        midBB.setPreList(new ArrayList<>());
        midBB.getPreList().add(preBB);
    }

    private void Pcopy2Move(Function function) {
        for (BasicBlock bb : function.getBBList()) {
            if (bb.getName().equals("b20")) {
                int a = 1;
            }
            // 找到基本块中的pcopy指令, 将其转化为一系列move
            LinkedList<Instr> instrList = bb.getInstrList();
            if (instrList.size() >= 2 && instrList.get(instrList.size() - 2) instanceof PcopyInstr) {
                PcopyInstr pcopy = (PcopyInstr) instrList.get(instrList.size() - 2);
                // 删掉pcopy
                instrList.remove(instrList.size() - 2);
                // 将pcopy转化为一系列move
                LinkedList<MoveInstr> moveList = convert(pcopy);
                // 将move加入到instrList
                for (MoveInstr move : moveList) {
                    instrList.add(instrList.size() - 1, move);
                    move.setParentBB(bb);
                }
                int a = 0;
            }
        }
    }

    private LinkedList<MoveInstr> convert(PcopyInstr pcopy) {
        ArrayList<Value> dstList = pcopy.getDstList();
        ArrayList<Value> srcList = pcopy.getSrcList();
        Function function = pcopy.getParentBB().getParentFunction();
        // 创建初始move序列
        // TODO: 在这个阶段可以先对move的先后顺序进行优化
        LinkedList<MoveInstr> moveList = new LinkedList<>();
        for (int i = 0; i < dstList.size(); i++) {
            MoveInstr move = new MoveInstr(IRBuilder.getInstance().genLocalVarName(function),
                                            dstList.get(i), srcList.get(i));
            moveList.add(move);
        }
        // 解决循环赋值的问题
        HashSet<Value> rec = new HashSet<>();
        for (int i = 0; i < moveList.size(); i++) {
            Value value =  moveList.get(i).getDst();
            if (! (value instanceof Constant) && ! rec.contains(value)) {
                // 检查该指令之后的所有指令，如果value同时是某一个move的src，那么存在循环赋值的问题
                boolean loopAssign = false;
                for (int j = i + 1; j < moveList.size(); j++) {
                    if (moveList.get(j).getSrc().equals(value)) {
                        loopAssign = true;
                        break;
                    }
                }
                // 如果出现了循环赋值的情况，我们需要增加中间变量
                if (loopAssign) {
                    Value midValue = new Value(BaseType.INT32, value.getName() + "_tmp");
                    // 将所有使用value作为src的move指令，将改为使用midValue作为src
                    for (MoveInstr move : moveList) {
                        if (move.getSrc().equals(value)) {
                            move.setSrc(midValue);
                        }
                    }
                    // 在moveList的开头插入新的move
                    MoveInstr move = new MoveInstr(IRBuilder.getInstance().genLocalVarName(function), midValue, value);
                    moveList.addFirst(move);
                }
                rec.add(value);
            }
        }
        return moveList;
    }
}
