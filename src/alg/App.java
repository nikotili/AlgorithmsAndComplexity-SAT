package alg;

import alg.sat.Cnf;
import alg.sat.Literal;
import alg.support.Util;

import java.util.*;

import static alg.support.Util.*;

public class App {
    private final Scanner scanner;

    private App() {
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new App().start();
    }

    private void start() {

        System.out.print("Enter the path of the file containing the formula: ");
        String path = scanner.nextLine();
        FormulaFromFile formulaFromFile = Util.extractFormulaFromFile(path);
        System.out.printf("1. %s\n2. %s\n3. %s\n4. %s\n5. %s\n6. %s\n0. %s\n> ",
                "check-assignment(F, I)",
                "is-2-SAT(F)",
                "is-Horn-SAT(F)",
                "solve-general-SAT(F)",
                "solve-2-SAT(F)",
                "solve-Horn-SAT(F)",
                "exit");

        int selection = scanner.nextInt();

        switch (selection) {
            case 0:
                System.exit(0);
            case 1:
                checkAssignment(formulaFromFile);
                break;
            case 2:
                is2SAT(formulaFromFile);
                break;
            case 3:
                isHornSAT(formulaFromFile);
                break;
            case 4:
                solveGeneralSAT(formulaFromFile);
                break;
            case 5:
                solve2SAT(formulaFromFile);
                break;
            case 6:
                solveHornSAT(formulaFromFile);
                break;
            default:
                System.err.println("Wrong Operation");
        }
    }

    private void solveHornSAT(FormulaFromFile formulaFromFile) {
        System.out.println("Solving Horn-SAT");
        Collection<Literal> literals = Cnf.from(
                formulaFromFile.getFormula(),
                formulaFromFile.getNumOfVariables()
        ).solveAsHornSAT();

        System.out.println(literals);
    }

    private void solve2SAT(FormulaFromFile formulaFromFile) {
        System.out.println("Solving 2-SAT");
        Collection<Literal> literals = Cnf.from(
                formulaFromFile.getFormula(),
                formulaFromFile.getNumOfVariables()
        ).solveAs2SAT();
        System.out.println(literals);
    }

    private void isHornSAT(FormulaFromFile formulaFromFile) {
        boolean isHornSAT = Cnf.from(
                formulaFromFile.getFormula(),
                formulaFromFile.getNumOfVariables()
        ).isHornSAT();

        System.out.printf("Formula is %sHorn-SAT!\n\n", isHornSAT ? "" : "not ");
    }

    private void is2SAT(FormulaFromFile formulaFromFile) {
        boolean is2SAT = Cnf.from(
                formulaFromFile.getFormula(),
                formulaFromFile.getNumOfVariables()
        ).is2SAT();

        System.out.printf("Formula is %s2-SAT!\n\n", is2SAT ? "" : "not ");
    }

    private void checkAssignment(FormulaFromFile formulaFromFile) {
        System.out.print("Enter assignment (0s and 1s only separated by space): ");
        scanner.nextLine();
        String input;
        input = scanner.nextLine();
        String[] assignmentStr = input.split(" ");
        int numOfVariables = formulaFromFile.getNumOfVariables();
        boolean[] assignment = new boolean[numOfVariables];
        for (int i = 0; i < numOfVariables; i++) {
            if (assignmentStr[i].equals("1")) {
                assignment[i] = true;
            } else if (assignmentStr[i].equals("0")) {
                assignment[i] = false;
            }
        }

        boolean isSatisfied = Cnf.from(formulaFromFile.getFormula(), assignment).value();
        System.out.printf("Formula is %s satisfied!\n\n", isSatisfied ? "" : "not");
    }

    private void solveGeneralSAT(FormulaFromFile formulaFromFile) {
        Collection<Literal> solution = Cnf.from(
                formulaFromFile.getFormula(),
                formulaFromFile.getNumOfVariables()
        ).solveAsGeneralSAT();

        System.out.println(solution);
    }
}
