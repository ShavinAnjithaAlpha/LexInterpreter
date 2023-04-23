package com.shavin.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class GenerateAST {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }

        String outputDir = args[0];
        definesExpr(outputDir, "Expr", Arrays.asList(
                "Binary : Expr left,Token operator,Expr right",
                "Grouping : Expr expression",
                "Literal : Object value",
                "Unary : Token operator,Expr right"
        ));
    }

    private static void definesExpr(String outputDir, String baseName, List<String> types) throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter printWriter = new PrintWriter(path, StandardCharsets.UTF_8);

        printWriter.println("package com.shavin.lox;");
        printWriter.println();
        printWriter.println("import java.util.List;");
        printWriter.println();
        printWriter.println("abstract class " + baseName + " {");

        for (String type : types) {
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(printWriter, baseName, className, fields);
        }

        printWriter.println("}");
        printWriter.close();


    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {

        writer.println("    static class " + className + " extends " + baseName + " {");

        // write the constructor
        writer.println("    " + className + "(" + fieldList + ") {");

        // store parameters in fields
        String[] fields = fieldList.split(",");
        for (String field : fields) {
            String name = field.split(" ")[1];
            writer.println("    this." + name + " = " + name + ";");
        }
        writer.println("    }");

        writer.println();

        // fields
        for (String field : fields) {
            writer.println("    final " + field + ";");
        }
        writer.println("    }");

    }
}
