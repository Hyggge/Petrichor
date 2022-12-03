import back_end.mips.AssemblyTable;
import back_end.mips.MipsBuilder;
import front_end.AST.Node;
import front_end.lexer.Lexer;
import front_end.lexer.Token;
import front_end.lexer.TokenStream;
import front_end.parser.Parser;
import llvm_ir.IRBuilder;
import llvm_ir.Module;
import utils.Printer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PushbackInputStream;

public class Compiler {
    public static void main(String[] args) throws Exception {
         String arg = "-ge";
//        String arg = args[0];

        PushbackInputStream input = new PushbackInputStream(new FileInputStream("testfile.txt"), 16);
        FileOutputStream output = new FileOutputStream("output.txt");
        FileOutputStream error = new FileOutputStream("error.txt");
        FileOutputStream llvm = new FileOutputStream("llvm_ir.txt");
        FileOutputStream mips = new FileOutputStream("mips.txt");
        // set Printer
        Printer.init(output, error, llvm, mips);

        if (arg.equals("-la")) {
            // token analyse
            Lexer lexer = new Lexer(input);
            TokenStream tokenStream = lexer.getTokenStream();
            Token token = tokenStream.read();
            while (token != null) {
                Printer.printToken(token);
                token = tokenStream.read();
            }

        }
        else if (arg.equals("-sa")) {
            // token analyse
            Lexer lexer = new Lexer(input);
            TokenStream tokenStream = lexer.getTokenStream();
            // syntax analyse
            Parser parser = new Parser(tokenStream);
            Node AST = parser.parseCompUnit();

        }

        else if (arg.equals("-ce")) {
            // token analyse
            Lexer lexer = new Lexer(input);
            TokenStream tokenStream = lexer.getTokenStream();
            // syntax analyse
            Parser parser = new Parser(tokenStream);
            Node compUnit = parser.parseCompUnit();
            // check error
            compUnit.checkError();
            Printer.printAllErrorMsg();
        }

        else if (arg.equals("-ge")) {
            // token analyse
            Lexer lexer = new Lexer(input);
            TokenStream tokenStream = lexer.getTokenStream();
            // syntax analyse
            Parser parser = new Parser(tokenStream);
            Node compUnit = parser.parseCompUnit();
            // check error
            compUnit.checkError();
            Printer.printAllErrorMsg();
            // generate IR
            IRBuilder.mode = IRBuilder.AUTO_INSERT_MODE;
            Module module = IRBuilder.getInstance().getModule();
            compUnit.genIR();
            Printer.printLLVM(module);
            // generate MIPS
            AssemblyTable assemblyTable = MipsBuilder.getInstance().getAssemblyTable();
            module.toAssembly();
            Printer.printMIPS(assemblyTable);
        }

        else if (arg.equals("-geo")) {
            // token analyse
            Lexer lexer = new Lexer(input);
            TokenStream tokenStream = lexer.getTokenStream();
            // syntax analyse
            Parser parser = new Parser(tokenStream);
            Node compUnit = parser.parseCompUnit();
            // check error
            compUnit.checkError();
            Printer.printAllErrorMsg();
            // generate IR
            IRBuilder.mode = IRBuilder.AUTO_INSERT_MODE;
            compUnit.genIR();
            Module module = IRBuilder.getInstance().getModule();
            Printer.printLLVM(module);
            // optimize
            IRBuilder.mode = IRBuilder.DEFAULT_MODE;
            // generate MIPS
            module.toAssembly();
            AssemblyTable assemblyTable = MipsBuilder.getInstance().getAssemblyTable();
            Printer.printMIPS(assemblyTable);
        }


        // close all streams
        input.close();
        output.close();
    }
}
