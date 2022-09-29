package front_end.lexer;

public class Token {
    private TokenType type;
    private String name;
    private int lineNumber;

    public Token(TokenType type, String name, int lineNumber) {
        this.type = type;
        this.name = name;
        this.lineNumber = lineNumber;
    }

    public TokenType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name + "\n";
    }
}
