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
            if (mode == 0) System.out.println(token.getType() + " " + token.getName());
            else {
                try {
                    fileOutputStream.write((token.getType() + " " + token.getName() + "\n").getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void printSyntaxVarType(SyntaxVarType type) {
        if (onOff) {
            if (mode == 0) System.out.println("<" + type.toString() + ">");
            else {
                try {
                    fileOutputStream.write(("<" + type.toString() + ">\n").getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
