package front_end.parser;

import front_end.AST.Node;
import front_end.AST.Stmt.BreakStmt;
import front_end.AST.Stmt.GetIntStmt;
import front_end.AST.Stmt.ReturnStmt;
import front_end.lexer.Token;
import front_end.lexer.TokenStream;
import front_end.lexer.TokenType;
import utils.Printer;

import java.util.ArrayList;

public class parser {
    private TokenStream tokenStream;
    private Token curToken;

    public parser(TokenStream tokenStream) {
        this.tokenStream = tokenStream;
        read();
    }

    private void read() {
        curToken = tokenStream.read();
    }

    private void unread() {
        tokenStream.unread();
    }


    /**
     * the root node
     */
    public Node parseCompUnit() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        Node node = null;
        while (true) {
            if (tokenStream.look(1).getType() == TokenType.MAINTK) {
                node = parseMainFuncDef();
            } else if (tokenStream.look(2).getType() == TokenType.LPARENT) {
                node = parseFuncDef();
            } else if (curToken.getType() == TokenType.CONSTTK) {
                node = parseConstDecl();
            } else if (curToken.getType() == TokenType.INTTK) {
                node = parseVarDecl();
            } else break;
            children.add(node);
        }
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        Printer.printSyntaxVarType(SyntaxVarType.COMP_UNIT);
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.COMP_UNIT, children);
    }

    /**
     * variable node
     */
    public Node parseVarDecl() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // parse 'int'
        Node node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse varDef
        node = parseVarDef();
        children.add(node);
        // parse {',' VarDef}
        while (curToken.getType() == TokenType.COMMA) {
            // parse ','
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
            // parse VarDef
            node = parseVarDef();
            children.add(node);
        }
        // parse ';'
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return  NodeFactory.createNode(startLine, endLine, SyntaxVarType.VAR_DECL, children);
    }

    public Node parseVarDef() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // parse Ident
        Node node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse {'['  Exp  ']'}
        while (curToken.getType() == TokenType.LBRACK) {
            // parse '['
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
            // parse Exp
            node = parseExp();
            children.add(node);
            // parse ']'
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
        }
        // parse ['='  InitVal]
        if (curToken.getType() == TokenType.ASSIGN) {
            // parse '='
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
            // parse InitVal
            node = parseInitVal();
            children.add(node);
        }
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.VAR_DEF, children);
    }

    public Node parseInitVal() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        Node node = null;
        // parse '{' [InitVal {',' InitVal}]'}'
        if (curToken.getType() == TokenType.LBRACE) {
            // parse '{'
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
            while (curToken.getType() != TokenType.RBRACE) {
                // parse  ','
                if (curToken.getType() == TokenType.COMMA) {
                    node = NodeFactory.createNode(curToken);
                    children.add(node); read();
                }
                // parse InitVal
                else {
                    node = parseInitVal();
                    children.add(node);
                }
            }
            // parse '}'
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
        }
        // parse Exp
        else {
            node = parseExp();
            children.add(node);
        }
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.INIT_VAL, children);
    }

    /**
     * const node
     */
    public Node parseConstDecl() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // parse 'const'
        Node node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse 'int'
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse ConstDef
        node = parseConstDef();
        children.add(node);
        // parse {',' ConstDef}
        while (curToken.getType() == TokenType.COMMA) {
            // parse ','
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
            // parse ConstDef
            node = parseConstDef();
            children.add(node);
        }
        // parse ';'
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.CONST_DECL, children);
    }

    public Node parseConstDef() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // parse Ident
        Node node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse {'['  ConstExp  ']'}
        while (curToken.getType() == TokenType.LBRACK) {
            // parse '['
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
            // parse ConstExp
            node = parseConstExp();
            children.add(node);
            // parse ']'
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
        }
        // parse ['='  ConstInitVal]
        if (curToken.getType() == TokenType.ASSIGN) {
            // parse '='
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
            // parse ConstInitVal
            node = parseConstInitVal();
            children.add(node);
        }
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.CONST_DEF, children);
    }

    public Node parseConstInitVal() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        Node node = null;
        // parse '{' [ConstInitVal {',' ConstInitVal}]'}'
        if (curToken.getType() == TokenType.LBRACE) {
            // parse '{'
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
            while (curToken.getType() != TokenType.RBRACE) {
                // parse ','
                if (curToken.getType() == TokenType.COMMA) {
                    node = NodeFactory.createNode(curToken);
                    children.add(node); read();
                }
                // parse ConstInitVal
                else {
                    node = parseConstInitVal();
                    children.add(node);
                }
            }
            // parser '}'
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
        }
        // parse ConstExp
        else {
            node = parseConstExp();
            children.add(node);
        }
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.CONST_INITVAL, children);
    }

    /**
     * function node
     */
    public Node parseFuncDef() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // parse 'void' or 'int'
        Node node = NodeFactory.createNode(curToken);
        Printer.printSyntaxVarType(SyntaxVarType.FUNC_TYPE);
        children.add(node); read();
        // parse Ident
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse '('
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse 'FuncFormalParams'
        if (curToken.getType() != TokenType.RPARENT) {
            node = parseFuncFormalParams();
            children.add(node);
        }
        // parse ')'
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse Block
        node = parseBlock();
        children.add(node);
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.FUNC_DEF, children);
    }

    public Node parseMainFuncDef() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        Node node = null;
        // parse 'int', 'main', '(', ')'
        for (int i = 0; i < 4; i++) {
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
        }
        // parse Block
        node = parseBlock();
        children.add(node);
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.MAIN_FUNC_DEF, children);
    }

    public Node parseFuncFormalParams() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        Node node = null;
        // parse FuncFormalParam
        node = parseFuncFormalParam();
        children.add(node);
        // parse {',' FuncFormalParam}
        while (curToken.getType() == TokenType.COMMA) {
            // parse ','
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
            // parse FuncFormalParam
            node = parseFuncFormalParam();
            children.add(node);
        }
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.FUNC_FORMAL_PARAMS, children);
    }

    public Node parseFuncFormalParam() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // parse 'int'
        Node node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse Ident
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse ['[' ']'  {'[' ConstExp ']'}]
        if (curToken.getType() == TokenType.LBRACK) {
            // parse '['
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
            // parse ']'
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
            // parse {'[' ConstExp ']'}
            while (curToken.getType() == TokenType.LBRACK) {
                // parse '['
                node = NodeFactory.createNode(curToken);
                children.add(node); read();
                // parse ConstExp
                node = parseConstExp();
                children.add(node);
                // parse ']'
                node = NodeFactory.createNode(curToken);
                children.add(node); read();
            }
        }
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.FUNC_FORMAL_PARAM, children);
    }

    public Node parseFuncRealParams() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // parse Exp
        Node node = parseExp();
        children.add(node);
        // parse {',' Exp}
        while (curToken.getType() == TokenType.COMMA) {
            // parse ','
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
            // parse Exp
            node = parseExp();
            children.add(node);
        }
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.FUNC_REAL_PARAMS, children);
    }

    public Node parseBlock() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // parse '{'
        Node node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse {VarDecl | ConstDecl | Stmt}
        while (curToken.getType() != TokenType.RBRACE) {
            // parse ConstDecl
            if (curToken.getType() == TokenType.CONSTTK) {
                node = parseConstDecl();
                children.add(node);
            }
            // parse VarDecl
            else if (curToken.getType() == TokenType.INTTK) {
                node = parseVarDecl();
                children.add(node);
            }
            // parse Stmt
            else {
                node = parseStmt();
                children.add(node);
            }
            // parse '}'
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
        }
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.BLOCK, children);
    }

    /**
     * statement node
     */
    public Node parseStmt() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        Node node = null;
        if (curToken.getType() == TokenType.LBRACE) {
            return parseBlock();
        }
        else if (curToken.getType() == TokenType.IFTK) {
            return parseIfStmt();
        }
        else if (curToken.getType() == TokenType.WHILETK) {
            return parseWhileStmt();
        }
        else if (curToken.getType() == TokenType.BREAKTK) {
            return parseBreakStmt();
        }
        else if (curToken.getType() == TokenType.CONTINUETK) {
            return parseContinueStmt();
        }
        else if (curToken.getType() == TokenType.RETURNTK) {
            return parseReturnStmt();
        }
        else if (curToken.getType() == TokenType.PRINTFTK) {
            return parsePrintfStmt();
        }
        // [Exp];
        else if (curToken.getType() == TokenType.SEMICN) {
            return parseExpStmt();
        }

        tokenStream.setWatchPoint();
        parseExp();
        if (curToken.getType() == TokenType.ASSIGN) {
            if (tokenStream.look(1).getType() == TokenType.GETINTTK) {
                curToken = tokenStream.backToWatchPoint();
                return parseGetIntStmt();
            } else {
                curToken = tokenStream.backToWatchPoint();
                return parseAssignStmt();
            }
        }
        else {
            curToken = tokenStream.backToWatchPoint();
            return parseExpStmt();
        }
    }

    public Node parseAssignStmt() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // parse LValExp;
        Node node = parseLValExp();
        children.add(node);
        // parse '='
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse Exp
        node = parseExp();
        children.add(node);
        // parse ';'
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.ASSIGN_STMT, children);
    }

    public Node parseExpStmt() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        Node node = null;
        // parse Exp
        if (curToken.getType() != TokenType.SEMICN) {
            node = parseExpStmt();
            children.add(node);
        }
        // parse ';'
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.EXP_STMT, children);
    }

    public Node parseIfStmt() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // parse 'If'
        Node node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse '('
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse CondExp
        node = parseCondExp();
        children.add(node);
        // parse ')'
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse Stmt
        node = parseStmt();
        children.add(node);
        // parse [ 'else' Stmt ]
        if (curToken.getType() == TokenType.ELSETK) {
            // parse 'else'
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
            // parse Stmt
            node = parseStmt();
            children.add(node);
        }
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.IF_STMT, children);
    }

    public Node parseWhileStmt() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // parse 'While'
        Node node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse '('
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse CondExp
        node = parseCondExp();
        children.add(node);
        // parse ')'
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse Stmt
        node = parseStmt();
        children.add(node);
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.WHILE_STMT, children);
    }

    public Node parseContinueStmt() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // parse 'continue'
        Node node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse ';'
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.CONTINUE_STMT, children);
    }

    public Node parseBreakStmt() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // parse 'break'
        Node node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse ';'
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.BREAK_STMT, children);
    }

    public Node parseReturnStmt() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // parse 'return'
        Node node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse [Exp]
        if (curToken.getType() != TokenType.SEMICN) {
            node = parseExp();
            children.add(node);
        }
        // parse ';'
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.RETURN_STMT, children);
    }

    public Node parseGetIntStmt() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // parse LValExp;
        Node node = parseLValExp();
        children.add(node);
        // parse '=', 'getint', '(', ')', ';'
        for (int i = 0; i < 5; i++) {
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
        }
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.GETINT_STMT, children);
    }

    public Node parsePrintfStmt() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        Node node = null;
        // parse 'printf', '(', FormatString
        for (int i = 0; i < 3; i++) {
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
        }
        // parse {','Exp}
        while (curToken.getType() == TokenType.COMMA) {
            // parse ','
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
            // parse Exp
            node = parseExp();
            children.add(node);
        }
        // parse ')'
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse ';'
        node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.PRINTF_STMT, children);
    }


    /**
     * expression node
     */
    public Node parseLValExp() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // parse Ident
        Node node = NodeFactory.createNode(curToken);
        children.add(node); read();
        // parse {'[' Exp ']'}
        while (curToken.getType() == TokenType.LBRACK) {
            // parse '['
            node = NodeFactory.createNode(curToken);
            children.add(node); read();// parse Ident
            // parse Exp
            node = parseExp();
            children.add(node);
            // parse ']'
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
        }
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.LVAL_EXP, children);
    }

    public Node parsePrimaryExp() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        Node node = null;
        // parse Number
        if (curToken.getType() == TokenType.INTCON) {
            node = NodeFactory.createNode(curToken);
            Printer.printSyntaxVarType(SyntaxVarType.NUMBER);
            children.add(node); read();
        }
        // parse '(' Exp ')'
        else if (curToken.getType() == TokenType.LPARENT) {
            // parse '('
            node = NodeFactory.createNode(curToken);
            children.add(node); read();// parse Ident
            // parse Exp
            node = parseExp();
            children.add(node);
            // parse ')'
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
        }
        // parse LValExp
        else {
            node = parseLValExp();
            children.add(node);
        }
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.PRIMARY_EXP, children);
    }

    public Node parseUnaryExp() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        Node node = null;
        // parse Ident '(' [FuncRealParams] ')'
        if (curToken.getType() == TokenType.IDENFR) {
            // parse Ident
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
            // parse '('
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
            // parse [FuncRealParams]
            if (curToken.getType() != TokenType.RPARENT) {
                node = parseFuncRealParams();
                children.add(node);
            }
            // parse ')'
            node = NodeFactory.createNode(curToken);
            children.add(node); read();
        }
        // parse ('+' | '-' | '!') UnaryExp
        else if (curToken.getType() == TokenType.PLUS || curToken.getType() == TokenType.MINU || curToken.getType() == TokenType.NOT) {
            // parse ('+' | '-' | '!')
            node = NodeFactory.createNode(curToken);
            Printer.printSyntaxVarType(SyntaxVarType.UNARY_OP);
            children.add(node); read();
            // parse UnaryExp
            node = parseUnaryExp();
            children.add(node);
        }
        // parse PrimaryExp
        else {
            node = parsePrimaryExp();
            children.add(node);
        }
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
        return NodeFactory.createNode(startLine, endLine, SyntaxVarType.UNARY_EXP, children);
    }

    public Node parseMulExp() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // parse UnaryExp
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
    }

    public Node parseAddExp() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();

        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
    }

    public Node parseRelExp() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();

        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
    }

    public Node parseEqExp() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();


        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
    }

    public Node parseLAndExp() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();


        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
    }

    public Node parseLOrExp() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();

        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
    }

    public Node parseCondExp() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();
        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
    }

    public Node parseConstExp() {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();

        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
    }

    public Node parseExp () {
        ArrayList<Node> children = new ArrayList<>();
        int startLine = curToken.getLineNumber();

        // create a node
        int endLine = tokenStream.look(-1).getLineNumber();
    }

}
