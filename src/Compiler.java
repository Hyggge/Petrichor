import front_end.AST.Node;
import front_end.lexer.Lexer;
import front_end.lexer.TokenStream;
import front_end.parser.Parser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class Compiler {
    public static void main(String[] args) throws IOException {
        PushbackInputStream input = new PushbackInputStream(new FileInputStream("testfile.txt"), 16);
        FileOutputStream output = new FileOutputStream("output.txt");
        Lexer lexer = new Lexer(input);
        TokenStream tokenStream = lexer.getTokenStream();
        Parser parser = new Parser(tokenStream);
        Node AST = parser.parseCompUnit();

        // close all streams
        input.close();
        output.close();
    }
}
