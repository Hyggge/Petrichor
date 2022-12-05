package mid_end;

import llvm_ir.BasicBlock;
import llvm_ir.Function;
import llvm_ir.IRBuilder;
import llvm_ir.Instr;
import llvm_ir.Module;
import llvm_ir.UndefinedValue;
import llvm_ir.Value;
import llvm_ir.instr.BranchInstr;
import llvm_ir.instr.JumpInstr;
import llvm_ir.instr.PcopyInstr;
import llvm_ir.instr.PhiInstr;

import java.util.ArrayList;
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
        for (BasicBlock bb : function.getBBList()) {
            // 遍历bb的前驱基本块集合，有多少前驱就增加多少个pcopy
            ArrayList<BasicBlock> preList = bb.getPreList();
            ArrayList<PcopyInstr> pcopyList = new ArrayList<>();
            preList.forEach((x) -> pcopyList.add(new PcopyInstr(IRBuilder.getInstance().genLocalVarName())));
            // 将每个pcopy插入适当的位置
            for (int i = 0; i < preList.size(); i++) {
                BasicBlock preBB = preList.get(i);
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
    }

    private void insertPcopyToMidBB(PcopyInstr pcopy, BasicBlock preBB, BasicBlock sucBB) {
        BasicBlock midBB = new BasicBlock(IRBuilder.getInstance().genBBName());

        // 修改跳转关系(preBB的最后一句一定是branch)
        BranchInstr instr = (BranchInstr)preBB.getLastInstr();
        BasicBlock thenBlock = instr.getThenBlock();
        BasicBlock elseBlock = instr.getElseBlock();
        if (sucBB.equals(thenBlock)) {
            instr.setThenBlock(midBB);
            JumpInstr jump = new JumpInstr(IRBuilder.getInstance().genLocalVarName(), sucBB);
            midBB.addInstr(jump);
            jump.setParentBB(midBB);
        }
        else {
            instr.setElseBlock(midBB);
            JumpInstr jump = new JumpInstr(IRBuilder.getInstance().genLocalVarName(), sucBB);
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
            // 找到基本块中的pcopy指令
            PcopyInstr pcopy = null;
            for (Instr instr : bb.getInstrList()) {
                if (instr instanceof PcopyInstr) {
                    pcopy = (PcopyInstr) instr;
                }
            }
            // 如果找到了pcopy指令，则将pcopy转化为一系列move
            if (pcopy != null) {

            }
        }
    }
}
