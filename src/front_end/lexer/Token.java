package front_end.lexer;

public class Token {
    private TokenType type;
    private String value;
    private int lineNumber;

    public Token(TokenType type, String name, int lineNumber) {
        this.type = type;
        this.value = name;
        this.lineNumber = lineNumber;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return type.toString() + " " + value + "\n";
    }
}
