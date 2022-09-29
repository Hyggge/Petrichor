import front_end.lexer.Lexer;
import front_end.lexer.TokenType;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class testLexer {
    @Test
    public void testTokenType() {
        System.out.println(TokenType.IDENFR);
    }

    @Test
    public void testEOF() throws IOException {
        String input = "int a = 10; //yououu/88/*\n";
        ByteArrayInputStream bytes = new ByteArrayInputStream(input.getBytes());
        PushbackInputStream stream = new PushbackInputStream(bytes);
        while (true) {
            char ch = (char)stream.read();
            if (((int)ch) == 0xffff) {System.out.println("EOF"); break;}
            else System.out.println(ch);
        }
    }

    @Test
    public void testLexer() throws IOException {
        String input = "const int array[2] = {1,2};\n" +
                "\n" +
                "int main(){\n" +
                "    int c;\n" +
                "    c = getint();\n" +
                "    printf(\"output is %d\",c);\n" +
                "    return c;\n" +
                "}";
        ByteArrayInputStream bytes = new ByteArrayInputStream(input.getBytes());
        PushbackInputStream stream = new PushbackInputStream(bytes);
        Lexer lexer = new Lexer(stream);

    }
}