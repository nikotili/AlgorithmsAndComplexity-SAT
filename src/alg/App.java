package alg;

import alg.sat.Cnf;
import alg.sat.Literal;
import alg.support.Generator;
import alg.support.Support;

import java.util.*;

import static alg.support.Support.*;

public class App {
    private Scanner scanner;

    private App() {
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        misc();
//        new App().start(args);
    }

    //todo test menu
    private void start(String[] args) {

        System.out.print("Enter the path of the file containing the formula: ");
        String path = scanner.nextLine();
        FormulaFromFile formulaFromFile = Support.extractFormulaFromFile(path);
        while (true) {
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
            System.out.println();
            System.out.println();
            System.out.println();
        }
    }

    private void solveHornSAT(FormulaFromFile formulaFromFile) {
        System.out.println("Solving Horn-SAT");
        Collection<Literal> literals = Cnf.from(
                formulaFromFile.getFormula(),
                formulaFromFile.getNumOfVariables()
        ).solveHornSAT();

        System.out.println(literals);
    }

    private void solve2SAT(FormulaFromFile formulaFromFile) {
        System.out.println("Solving 2-SAT");
        Collection<Literal> literals = Cnf.from(
                formulaFromFile.getFormula(),
                formulaFromFile.getNumOfVariables()
        ).solve2SAT();
        System.out.println(literals);
    }

    private void isHornSAT(FormulaFromFile formulaFromFile) {
        boolean isHornSAT = Cnf.from(
                formulaFromFile.getFormula(),
                formulaFromFile.getNumOfVariables()
        ).isHornSAT();

        System.out.printf("Formula is %s Horn-SAT!\n\n", isHornSAT ? "" : "not");
    }

    private void is2SAT(FormulaFromFile formulaFromFile) {
        boolean is2SAT = Cnf.from(
                formulaFromFile.getFormula(),
                formulaFromFile.getNumOfVariables()
        ).is2SAT();

        System.out.printf("Formula is %s 2-SAT!\n\n", is2SAT ? "" : "not");
    }

    private static void misc() {
        int[][] formula = new int[][]{
                {1, -2},
                {-1, -3},
                {1, 2},
                {-3, 4},
                {-1, 4}
        };

        int[][] formula1 = new int[][]{
                {1, -2, -4},
                {-1, -3},
                {-2, -3}
        };

        int[][] hornFormula = new int[][]{
                {-1, -2, -3, -4, -5},
                {-1, -2, -3, -4},
                {-3, 4},
                {3},
                {-3, -5},
                {-1, -3, -4},
                {-2, -3, -4, 5},
                {-1, -2, -3, -5},
        };

        int[][] f = new int[][] {
                {-1, -2},
                {2}
        };


        Cnf from = Cnf.from(f, 2);
        System.out.println(from.solveHornSAT());
        System.out.println(from.getValue());
//        solveGeneralSAT(hornFormula, 5);
//        testGeneral();
//        solveHornSAT(hornFormula, 5);
//        solveGeneralSAT(formula, 4);
    }

    private void checkAssignment(FormulaFromFile formulaFromFile) {
        System.out.print("Enter assignment (0s and 1s only separated by space): ");
        String input = scanner.nextLine();
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

        boolean isSatisfied = Cnf.from(formulaFromFile.getFormula(), assignment).getValue();
        System.out.printf("Formula is %s satisfied!\n\n", isSatisfied ? "" : "not");
    }

    private static void testGeneral() {
        int[][] formula = new int[][]{
                {1, -2},
                {-1, -3},
                {1, 2},
                {-3, 4},
                {-1, 4}
        };

//        Boolean[] assignment = new Boolean[] {Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE};


        System.out.println(Cnf.from(formula, 4).solve2SAT());

    }

    private static void solveHornSAT(int[][] formula, int numOfVars) {
        Cnf.from(formula, numOfVars).solveHornSAT();
    }

    private void solveGeneralSAT(FormulaFromFile formulaFromFile) {
        System.out.println("Solving general-SAT");
        int[][] formula = formulaFromFile.getFormula();
        int numOfVars = formulaFromFile.getNumOfVariables();
        boolean[] defaultVars = generateDefaultAssignment(numOfVars);

        boolean isSatisfied = Cnf.from(formula, defaultVars).getValue();
        if (isSatisfied) {
            System.out.println(Arrays.toString(defaultVars));
            return;
        }

        Generator generator = new Generator(numOfVars);

        while (generator.hasNext()) {
            boolean[] vars = generator.next();
            Cnf cnf = Cnf.from(formula, vars);
            boolean value = cnf.getValue();
            if (value) {
                System.out.println(Arrays.toString(vars));
                return;
            }
        }
        System.err.println("No Solution");
    }
}
