package mid_end;

import llvm_ir.BasicBlock;
import llvm_ir.Constant;
import llvm_ir.Function;
import llvm_ir.Instr;
import llvm_ir.Module;
import llvm_ir.Value;
import llvm_ir.instr.AluInstr;
import llvm_ir.instr.CallInstr;
import llvm_ir.instr.GEPInstr;
import llvm_ir.instr.IcmpInstr;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class GVN {
    private Module module;
    private HashMap<String, Instr> GVNMap;

    public GVN(Module module) {
        this.module = module;
    }

    public void run() {
        for (Function function : module.getFunctionList()) {
            BasicBlock entry = function.getBBList().get(0);
            initAttr();
            GVNVisit(entry);
        }
    }

    private void initAttr() {
        this.GVNMap = new HashMap<>();
    }

    public void GVNVisit(BasicBlock entry) {
        AluOptimize(entry);
        HashSet<Instr> inserted = new HashSet<>();
        LinkedList<Instr> instrList = entry.getInstrList();
        Iterator<Instr> iterator = instrList.iterator();
        // 遍历一遍Instr，找出所有可优化的alu, gep, icmp和func
        while (iterator.hasNext()) {
            Instr instr = iterator.next();
            if (instr instanceof AluInstr || instr instanceof IcmpInstr || instr instanceof GEPInstr ||
                    instr instanceof CallInstr && ((CallInstr) instr).getTargetFunc().canGVN()) {
                String hash = instr.getGVNHash();
                // 如果GVMMap中已存在，直接用该值即可
                if (GVNMap.containsKey(hash)) {
                    instr.modifyAllUseThisToNewValue(GVNMap.get(hash));
                    iterator.remove();
                }
                // 否则插入到GVMMap中
                else {
                    GVNMap.put(hash, instr);
                    inserted.add(instr);
                }
            }
        }
        // 对其直接支配的子节点调用该函数
        for (BasicBlock child : entry.getChildList()) {
            GVNVisit(child);
        }
        // 将该基本块插入GVNMap的instr全部删去, 避免影响兄弟子树的遍历
        for (Instr instr : inserted) {
            GVNMap.remove(instr.getGVNHash());
        }
    }

    public void AluOptimize(BasicBlock bb) {
        Iterator<Instr> iterator = bb.getInstrList().iterator();
        while (iterator.hasNext()) {
            Instr instr = iterator.next();
            if (! (instr instanceof AluInstr)) continue;
            // 如果是alu指令
            AluInstr aluInstr = (AluInstr) instr;
            AluInstr.Op op = ((AluInstr) instr).getOp();
            Value operand1 = aluInstr.getOperand1();
            Value operand2 = aluInstr.getOperand2();
            // 得到constant的个数
            int cnt = 0;
            if (operand1 instanceof Constant) cnt++;
            if (operand2 instanceof Constant) cnt++;
            // 如果constant有两个，直接计算即可
            if (cnt == 2) {
                Value newValue = calcForTwoConstant(operand1, operand2, op);
                instr.modifyAllUseThisToNewValue(newValue);
                iterator.remove();
            }
            // 如果constant有一个，查看是否有a+0, a-0, a*0, a*1, a/1, a%1
            else if (cnt == 1) {
                Value newValue = calcForOneConstant(operand1, operand2, op);
                if (newValue != null) {
                    instr.modifyAllUseThisToNewValue(newValue);
                    iterator.remove();
                }
            }

        }
    }

    public Value calcForTwoConstant(Value operand1, Value operand2, AluInstr.Op op) {
        int ans = 0;
        int op1 = ((Constant)operand1).getValue();
        int op2 = ((Constant)operand2).getValue();
        if ((op == AluInstr.Op.SDIV || op == AluInstr.Op.SREM) &&  op2 == 0) op2 = 1;
        switch (op) {
            case ADD: ans = op1 + op2; break;
            case SUB: ans = op1 - op2; break;
            case AND: ans = op1 & op2; break;
            case OR:  ans = op1 | op2; break;
            case MUL: ans = op1 * op2; break;
            case SDIV: ans = op1 / op2; break;
            case SREM: ans = op1 % op2;
        }
        return new Constant(ans);
    }

    public Value calcForOneConstant(Value operand1, Value operand2, AluInstr.Op op) {
        switch (op) {
            case ADD:
                if (operand1 instanceof Constant && ((Constant)operand1).getValue() == 0) {
                    return operand2;
                }
                else if (operand2 instanceof Constant && ((Constant)operand2).getValue() == 0) {
                    return operand1;
                }
                break;
            case SUB:
                if (operand2 instanceof Constant && ((Constant)operand2).getValue() == 0) {
                    return operand1;
                }
                break;
            case MUL:
                if (operand1 instanceof Constant && ((Constant)operand1).getValue() == 0) {
                    return new Constant(0);
                }
                else if (operand2 instanceof Constant && ((Constant)operand2).getValue() == 0) {
                    return new Constant(0);
                }
                else if (operand1 instanceof Constant && ((Constant)operand1).getValue() == 1) {
                    return operand2;
                }
                else if (operand2 instanceof Constant && ((Constant)operand2).getValue() == 1) {
                    return operand1;
                }
                break;
            case SDIV:
                if (operand2 instanceof Constant && ((Constant)operand2).getValue() == 1) {
                    return operand1;
                }
                break;
            case SREM:
                if (operand2 instanceof Constant && ((Constant)operand2).getValue() == 1) {
                    return new Constant(0);
                }
                break;
        }
        return null;
    }






}
