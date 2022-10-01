import front_end.AST.Node;
import front_end.lexer.Lexer;
import front_end.lexer.TokenStream;
import front_end.parser.Parser;
import utils.Printer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PushbackInputStream;

public class Compiler {
    public static void main(String[] args) throws Exception {
        PushbackInputStream input = new PushbackInputStream(new FileInputStream("testfile.txt"), 16);
        FileOutputStream output = new FileOutputStream("output.txt");
        // set Printer
        Printer.setFileOutputStream(output);
        Printer.fileOutMode();
        Printer.open();
        // token analyse
        Lexer lexer = new Lexer(input);
        TokenStream tokenStream = lexer.getTokenStream();
        // syntax analyse
        Parser parser = new Parser(tokenStream);
        Node AST = parser.parseCompUnit();

        // close all streams
        input.close();
        output.close();
    }
}
