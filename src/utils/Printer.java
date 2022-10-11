package utils;

import front_end.lexer.Token;
import front_end.parser.SyntaxVarType;

import java.io.FileOutputStream;
import java.io.IOException;

public class Printer {
    public static final boolean FILE_OUT = true;
    public static final boolean FILE_ERR = true;

    public static boolean onOff = false;
    public static FileOutputStream fileOut = null;
    public static FileOutputStream fileErr = null;

    public static void init(FileOutputStream out, FileOutputStream err) {
        Printer.fileOut = out;
        Printer.fileErr = err;
    }

    public static void open() {
        onOff = true;
    }

    public static void close() {
        onOff = false;
    }

    public static void printToken(Token token){
        String content = token.toString();
        if (onOff & FILE_OUT) {
            try {fileOut.write(content.getBytes());} catch (IOException e) {throw new RuntimeException(e);}
        }
    }

    public static void printSyntaxVarType(SyntaxVarType type) {
        String content = "<" + type.toString() + ">\n";
        if (onOff & FILE_ERR) {
            try {fileOut.write((content).getBytes());} catch (IOException e) {throw new RuntimeException(e);}
        }
    }

    public static void printErrorMsg() {


    }
}
