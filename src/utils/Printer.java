package utils;

import front_end.lexer.Token;
import front_end.parser.SyntaxVarType;

public class Printer {
    public static void printToken(Token token) {
        System.out.println(token.getType() + " " + token.getName());
    }

    public static void printSyntaxVarType(SyntaxVarType type) {
        System.out.println("<" + type.toString() + ">");
    }
}
