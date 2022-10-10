package utils;

import front_end.lexer.Token;
import front_end.parser.SyntaxVarType;

import java.io.FileOutputStream;
import java.io.IOException;

public class Printer {
    public static boolean onOff = false;
    public static int mode = 0;
    public static FileOutputStream fileOutputStream = null;

    public static void setFileOutputStream(FileOutputStream fileOutputStream) {
        Printer.fileOutputStream = fileOutputStream;
    }

    public static void open() {
        onOff = true;
    }

    public static void close() {
        onOff = false;
    }

    public static void stdOutMode() {
        mode = 0;
    }

    public static void fileOutMode() throws Exception {
        if (fileOutputStream == null) {
            throw new Exception("No FileOutputStream");
        }
        mode = 1;
    }

    public static void printToken(Token token){
        if (onOff) {
            String content = token.toString();
            if (mode == 0) System.out.println(content);
            else try {fileOutputStream.write(content.getBytes());} catch (IOException e) {throw new RuntimeException(e);}
        }
    }

    public static void printSyntaxVarType(SyntaxVarType type) {
        if (onOff) {
            String content = "<" + type.toString() + ">\n";
            if (mode == 0) System.out.println(content);
            else try {fileOutputStream.write((content).getBytes());} catch (IOException e) {throw new RuntimeException(e);}
        }

    }
}
