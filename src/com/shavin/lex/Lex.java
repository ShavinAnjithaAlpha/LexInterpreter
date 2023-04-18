package com.shavin.lex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Lex {

    private static boolean hadError;

    public static void main(String[] args) throws IOException {
        // call to the methods based on supplied arguments
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }

    }

    private static void runPrompt() throws IOException {
        // prompt the user to type their code in the console line by line
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        for (;;) {
            System.out.println("> ");
            String line = bufferedReader.readLine();
            if (line == null) break; // exit the loop if user enter Ctrl+Z (end of file maker)
            run(line);
            // reset the error indicator to default
            hadError = false;
        }
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset())); // pass to the run method for run the entire source code

        // indicate an error in the exit code
        if (hadError) System.exit(65);
    }

    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<String> tokens = scanner.tokens().toList();

        // for now just print the tokens
        for (String token : tokens) {
            System.out.println(token);
        }

    }

    public static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        // report an error to user with line number where error occurred and error message
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message
        );
        hadError = true; // set the error indicator as true
    }
}
