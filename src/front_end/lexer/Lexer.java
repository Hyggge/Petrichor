package front_end.lexer;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.ArrayList;

public class Lexer {
    private int lineNumber;
    private char curChar;
    private PushbackInputStream stream;
    private ArrayList<Token> tokenList;

    // constructor
    public Lexer(PushbackInputStream stream) throws IOException {
        this.stream = stream;
        this.curChar = (char)stream.read();
        this.lineNumber = 1;
        this.tokenList = null;
    }

    // tool function
    private void read() throws IOException {
        curChar = (char)stream.read();
    }

    private void unread() throws IOException {
        stream.unread(curChar);
    }

    private boolean isBlank() throws IOException {
        return curChar == ' ' || curChar == '\t' || isNewLine();
    }

    private boolean isLetter() {
        return Character.isLetter(curChar);
    }

    private boolean isDigit() {
        return Character.isDigit(curChar);
    }

    private boolean isUnderLine() {
        return curChar == '_';
    }

    private boolean isNewLine() throws IOException {
        if (curChar == '\r') read();
        return curChar == '\n';
    }

    private boolean isEOF() {
        return curChar == '\uFFFF';
    }

    // get token type of identifier
    private TokenType getTokenType(String identifier) {
        switch (identifier) {
            case "main" : return TokenType.MAINTK;
            case "const" : return TokenType.CONSTTK;
            case "int" : return TokenType.INTTK;
            case "break" : return TokenType.BREAKTK;
            case "continue" : return TokenType.CONTINUETK;
            case "if" : return TokenType.IFTK;
            case "else" : return TokenType.ELSETK;
            case "while" : return TokenType.WHILETK;
            case "getint" : return TokenType.GETINTTK;
            case "printf" : return TokenType.PRINTFTK;
            case "return" : return TokenType.RETURNTK;
            case "void" : return TokenType.VOIDTK;
            default: return TokenType.IDENFR;
        }
    }

    public TokenStream getTokenStream() throws IOException {
        return new TokenStream(getTokenList());
    }

    public ArrayList<Token> getTokenList() throws IOException {
        if (tokenList != null) return tokenList;
        tokenList = new ArrayList<>();
        Token token = getToken();
        while (token.getType() != TokenType.EOF) {
            tokenList.add(token);
            token = getToken();
        }
        return tokenList;
    }

    // get a token from input stream
    private Token getToken() throws IOException {
        StringBuilder sb = new StringBuilder();

        // delete the blank character
        while(isBlank()) {
            if (isNewLine()) lineNumber++;
            read();
        }

        // check if reached EOF
        if (isEOF()) {
            return new Token(TokenType.EOF, "EOF", lineNumber);
        }

        // deal with +  -  *  ;  ,  (  )  {  }  [  ]
        else if (curChar == '+') {
            sb.append(curChar); read();
            return new Token(TokenType.PLUS, sb.toString(), lineNumber);
        }
        else if (curChar == '-') {
            sb.append(curChar); read();
            return new Token(TokenType.MINU, sb.toString(), lineNumber);
        }
        else if (curChar == '*') {
            sb.append(curChar); read();
            return new Token(TokenType.MULT, sb.toString(), lineNumber);
        }
        else if (curChar == ';') {
            sb.append(curChar); read();
            return new Token(TokenType.SEMICN, sb.toString(), lineNumber);
        }
        else if (curChar == ',') {
            sb.append(curChar); read();
            return new Token(TokenType.COMMA, sb.toString(), lineNumber);
        }
        else if (curChar == '(') {
            sb.append(curChar); read();
            return new Token(TokenType.LPARENT, sb.toString(), lineNumber);
        }
        else if (curChar == ')') {
            sb.append(curChar); read();
            return new Token(TokenType.RPARENT, sb.toString(), lineNumber);
        }
        else if (curChar == '[') {
            sb.append(curChar); read();
            return new Token(TokenType.LBRACK, sb.toString(), lineNumber);
        }
        else if (curChar == ']') {
            sb.append(curChar); read();
            return new Token(TokenType.RBRACK, sb.toString(), lineNumber);
        }
        else if (curChar == '{') {
            sb.append(curChar); read();
            return new Token(TokenType.LBRACE, sb.toString(), lineNumber);
        }
        else if (curChar == '}') {
            sb.append(curChar); read();
            return new Token(TokenType.RBRACE, sb.toString(), lineNumber);
        }
        else if (curChar == '%') {
            sb.append(curChar); read();
            return new Token(TokenType.MOD, sb.toString(), lineNumber);
        }

        // deal with ||  &&
        else if (curChar == '&') {
            sb.append(curChar); read();
            sb.append(curChar); read();
            return new Token(TokenType.AND, sb.toString(), lineNumber);
        }
        else if (curChar == '|') {
            sb.append(curChar); read();
            sb.append(curChar); read();
            return new Token(TokenType.OR, sb.toString(), lineNumber);
        }

        // deal with <  <=  >  >=  !  !=  = ==
        else if (curChar == '<') {
            sb.append(curChar); read();
            if (curChar == '=') {
                sb.append(curChar); read();
                return new Token(TokenType.LEQ, sb.toString(), lineNumber);
            }
            return new Token(TokenType.LSS, sb.toString(), lineNumber);
        }
        else if (curChar == '>') {
            sb.append(curChar); read();
            if (curChar == '=') {
                sb.append(curChar); read();
                return new Token(TokenType.GEQ, sb.toString(), lineNumber);
            }
            return new Token(TokenType.GRE, sb.toString(), lineNumber);
        }
        else if (curChar == '!') {
            sb.append(curChar); read();
            if (curChar == '=') {
                sb.append(curChar); read();
                return new Token(TokenType.NEQ, sb.toString(), lineNumber);
            }
            return new Token(TokenType.NOT, sb.toString(), lineNumber);
        }
        else if (curChar == '=') {
            sb.append(curChar); read();
            if (curChar == '=') {
                sb.append(curChar); read();
                return new Token(TokenType.EQL, sb.toString(), lineNumber);
            }
            return new Token(TokenType.ASSIGN, sb.toString(), lineNumber);
        }

        // deal with  /  // /*
        else if (curChar == '/') {
            sb.append(curChar); read();
            if (curChar == '/') { // single-line comment
                read();
                while (! isNewLine()) read();
                lineNumber++;
                read(); // skip the newline character
                return getToken();
            }
            else if (curChar == '*') {// multi-line comment
                read();
                while (true) {
                    while (curChar != '*') {
                        if (isEOF()) return new Token(TokenType.EOF, "EOF", lineNumber);
                        if (isNewLine()) lineNumber++;
                        read();
                    }
                    while (curChar == '*') read();
                    if (curChar == '/') break;
                }
                read(); // skip the last '/'
                return getToken();
            }
            return new Token(TokenType.DIV, sb.toString(), lineNumber);
        }

        // integer
        else if (isDigit()) {
            while (isDigit()) {
                sb.append(curChar); read();
            }
            return new Token(TokenType.INTCON, sb.toString(), lineNumber);
        }

        // format string
        else if (curChar == '"') {
            sb.append(curChar); read();
            while (curChar != '"') {
                sb.append(curChar); read();
            }
            sb.append(curChar); read(); // skip the last '"'
            return new Token(TokenType.STRCON, sb.toString(), lineNumber);
        }

        // reserved words or identifier
        else if (isLetter() || isUnderLine()) {
            sb.append(curChar); read();
            while (isLetter() || isDigit() || isUnderLine()) {
                sb.append(curChar); read();
            }
            return new Token(getTokenType(sb.toString()), sb.toString(), lineNumber);
        }

        return new Token(TokenType.ERROR, "ERROR", lineNumber);
    }


}
