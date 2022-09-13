import front_end.Lexer;
import utils.Token;
import utils.TokenType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class Compiler {
    public static void main(String[] args) throws IOException {
        PushbackInputStream input = new PushbackInputStream(new FileInputStream("testfile.txt"), 16);
        FileOutputStream output = new FileOutputStream("output.txt");
        Lexer lexer = new Lexer(input);

        // get tokens
        Token token = lexer.getToken();
        while (token.getType() != TokenType.EOF) {
            if (token.getType() == TokenType.RETURNTK) {
                int a;
            }
            output.write(token.toString().getBytes());
            token = lexer.getToken();
        }

        // close all streams
        input.close();
        output.close();
    }
}
