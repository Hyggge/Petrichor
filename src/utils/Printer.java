package utils;

import front_end.lexer.Token;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Printer {
    public static boolean FILE_OUT = false;
    public static boolean FILE_ERR = true;
    public static boolean onOff = true;

    private static FileOutputStream fileOut = null;
    private static FileOutputStream fileErr = null;
    private static HashMap<Integer, ErrorType> errorMap;

    public static void init(FileOutputStream out, FileOutputStream err) {
        Printer.fileOut = out;
        Printer.fileErr = err;
        Printer.errorMap = new HashMap<>();
    }

    public static void printToken(Token token){
        String content = token.toString();
        if (onOff & FILE_OUT) {
            try {fileOut.write(content.getBytes());} catch (IOException e) {throw new RuntimeException(e);}
        }
    }

    public static void printSyntaxVarType(SyntaxVarType type) {
        String content = "<" + type.toString() + ">\n";
        if (onOff & FILE_OUT) {
            try {fileOut.write((content).getBytes());} catch (IOException e) {throw new RuntimeException(e);}
        }
    }


    public static void addErrorMsg(Integer lineNumber, ErrorType errorType) {
        errorMap.put(lineNumber, errorType);
    }
    public static void printAllErrorMsg() {
        Object[] lineNumbers = errorMap.keySet().toArray();
        Arrays.sort(lineNumbers);
        for (Object lineNumber : lineNumbers) {
            String content = lineNumber + " " + errorMap.get((Integer)lineNumber) + "\n";
            if (onOff & FILE_ERR) {
                try {fileErr.write((content).getBytes());} catch (IOException e) {throw new RuntimeException(e);}
            }
        }
    }
}
