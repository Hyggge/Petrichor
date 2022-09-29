import front_end.lexer.Lexer;
import front_end.lexer.Token;
import front_end.lexer.TokenStream;

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
        TokenStream tokenStream = lexer.getTokenStream();
        Token token = tokenStream.read();
        while (token != null) {
            System.out.println(token);
            output.write(token.toString().getBytes());
            token = tokenStream.read();
        }

        // close all streams
        input.close();
        output.close();
    }
}
