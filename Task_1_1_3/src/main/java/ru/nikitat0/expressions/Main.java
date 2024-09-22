package ru.nikitat0.expressions;

import java.text.ParseException;
import java.util.Scanner;

/** The Main class contains main function. */
public class Main {
    /**
     * The main functions is a entry point to the program.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) throws ParseException {
        try (Scanner scanner = new Scanner(System.in)) {
            ask("Enter expression: ");
            Expression expr = Expression.parse(scanner.nextLine());
            System.out.printf("Parsed: %s\n", expr);
            System.out.printf("Simplified: %s\n", expr.simplify());

            ask("Variable for derivative (leave blank if the derivative is not needed): ");
            String varName = scanner.nextLine();
            if (!varName.isBlank()) {
                Expression de = expr.derivative(varName).simplify();
                System.out.printf("Derivative by %s: %s\n", varName, de);
            }

            ask("Variable assignments: ");
            String assignments = scanner.nextLine();
            if (!assignments.isBlank()) {
                System.out.printf("Result of evaluation: %s\n", expr.partialEval(assignments));
            }
        }
    }

    private static void ask(String msg) {
        System.out.print(msg);
        System.out.flush();
    }
}
