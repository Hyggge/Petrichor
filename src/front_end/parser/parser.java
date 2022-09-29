package front_end.parser;

import front_end.AST.Node;
import front_end.lexer.Token;
import front_end.lexer.TokenStream;
import front_end.lexer.TokenType;

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
    }

    /**
     * variable node
     */
    public Node parseVarDecl() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseVarDef() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseInitVal() {
        ArrayList<Node> children = new ArrayList<>();
    }

    /**
     * const node
     */
    public Node parseConstDecl() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseConstDef() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseConstInitVal() {
        ArrayList<Node> children = new ArrayList<>();
    }

    /**
     * function node
     */
    public Node parseFuncDef() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseMainFuncDef() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseFuncFormalParams() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseFuncFormalParam() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseFuncRealParams() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseBlock() {
        ArrayList<Node> children = new ArrayList<>();
    }

    /**
     * statement node
     */
    public Node parseStmt() {
        ArrayList<Node> children = new ArrayList<>();
    }


    /**
     * expression node
     */
    public Node parseLValExp() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parsePrimaryExp() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseUnaryExp() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseMulExp() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseAddExp() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseRelExp() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseEqExp() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseLAndExp() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseLOrExp() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseCondExp() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseConstExp() {
        ArrayList<Node> children = new ArrayList<>();
    }

    public Node parseExp () {
        ArrayList<Node> children = new ArrayList<>();
    }

}
