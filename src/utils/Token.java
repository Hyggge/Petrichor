package utils;

public class Token {
    private String name;
    private TokenType type;
    private int lineNumber;

    public Token(String name, TokenType type, int lineNumber) {
        this.name = name;
        this.type = type;
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }
}
