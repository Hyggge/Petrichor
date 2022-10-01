package front_end.parser;
import front_end.AST.Block;
import front_end.AST.CompUnit;
import front_end.AST.ConstDecl;
import front_end.AST.ConstDef;
import front_end.AST.Exp.AddExp;
import front_end.AST.Exp.CondExp;
import front_end.AST.Exp.ConstExp;
import front_end.AST.Exp.EqExp;
import front_end.AST.Exp.Exp;
import front_end.AST.Exp.LAndExp;
import front_end.AST.Exp.LOrExp;
import front_end.AST.Exp.LValExp;
import front_end.AST.Exp.MulExp;
import front_end.AST.Exp.PrimaryExp;
import front_end.AST.Exp.RelExp;
import front_end.AST.Exp.UnaryExp;
import front_end.AST.FuncDef;
import front_end.AST.FuncFormalParam;
import front_end.AST.FuncFormalParams;
import front_end.AST.FuncRealParams;
import front_end.AST.InitVal;
import front_end.AST.MainFuncDef;
import front_end.AST.Node;
import front_end.AST.Stmt.AssignStmt;
import front_end.AST.Stmt.BreakStmt;
import front_end.AST.Stmt.ContinueStmt;
import front_end.AST.Stmt.ExpStmt;
import front_end.AST.Stmt.GetIntStmt;
import front_end.AST.Stmt.IfStmt;
import front_end.AST.Stmt.PrintfStmt;
import front_end.AST.Stmt.ReturnStmt;
import front_end.AST.Stmt.Stmt;
import front_end.AST.Stmt.WhileStmt;
import front_end.AST.TokenNode;
import front_end.AST.VarDecl;
import front_end.AST.VarDef;
import front_end.lexer.Token;
import utils.Printer;

import java.util.ArrayList;

public class NodeFactory {
    public static Node createNode(int startLine, int endLine, SyntaxVarType type, ArrayList<Node> children) {
        Printer.printSyntaxVarType(type);
        switch (type) {
            case COMP_UNIT:             return new CompUnit(startLine, endLine, type, children);

            case CONST_DECL:            return new ConstDecl(startLine, endLine, type, children);
            case CONST_DEF:             return new ConstDef(startLine, endLine, type, children);
            case CONST_INITVAL:         return new InitVal(startLine, endLine, type, children);

            case VAR_DECL:              return new VarDecl(startLine, endLine, type, children);
            case VAR_DEF:               return new VarDef(startLine, endLine, type, children);
            case INIT_VAL:              return new InitVal(startLine, endLine, type, children);

            case FUNC_DEF:              return new FuncDef(startLine, endLine, type, children);
            case MAIN_FUNC_DEF:         return new MainFuncDef(startLine, endLine, type, children);
            case FUNC_FORMAL_PARAMS:    return new FuncFormalParams(startLine, endLine, type, children);
            case FUNC_FORMAL_PARAM:     return new FuncFormalParam(startLine, endLine, type, children);
            case FUNC_REAL_PARAMS:      return new FuncRealParams(startLine, endLine, type, children);
            case BLOCK:                 return new Block(startLine, endLine, type, children);

            case STMT:                  return new Stmt(startLine, endLine, type, children);
            case ASSIGN_STMT:           return new AssignStmt(startLine, endLine, type, children);
            case EXP_STMT:              return new ExpStmt(startLine, endLine, type, children);
            case IF_STMT:               return new IfStmt(startLine, endLine, type, children);
            case WHILE_STMT:            return new WhileStmt(startLine, endLine, type, children);
            case BREAK_STMT:            return new BreakStmt(startLine, endLine, type, children);
            case CONTINUE_STMT:         return new ContinueStmt(startLine, endLine, type, children);
            case RETURN_STMT:           return new ReturnStmt(startLine, endLine, type, children);
            case GETINT_STMT:           return new GetIntStmt(startLine, endLine, type, children);
            case PRINTF_STMT:           return new PrintfStmt(startLine, endLine, type, children);

            case LVAL_EXP:              return new LValExp(startLine, endLine, type, children);
            case PRIMARY_EXP:           return new PrimaryExp(startLine, endLine, type, children);
            case UNARY_EXP:             return new UnaryExp(startLine, endLine, type, children);
            case MUL_EXP:               return new MulExp(startLine, endLine, type, children);
            case ADD_EXP:               return new AddExp(startLine, endLine, type, children);
            case REL_EXP:               return new RelExp(startLine, endLine, type, children);
            case EQ_EXP:                return new EqExp(startLine, endLine, type, children);
            case LAND_EXP:              return new LAndExp(startLine, endLine, type, children);
            case LOR_EXP:               return new LOrExp(startLine, endLine, type, children);
            case CONST_EXP:             return new ConstExp(startLine, endLine, type, children);
            case EXP:                   return new Exp(startLine, endLine, type, children);
            case COND_EXP:              return new CondExp(startLine, endLine, type, children);
            default: return null;// TODO: error
        }
    }

    public static Node createNode(Token token) {
        Printer.printToken(token);
        return new TokenNode(token.getLineNumber(), token.getLineNumber(), SyntaxVarType.TOKEN, null, token);
    }


}
