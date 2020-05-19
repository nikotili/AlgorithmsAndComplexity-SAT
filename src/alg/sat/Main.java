package alg.sat;

import alg.support.Generator;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[][] formula = new int[][]{
                {1, -2},
                {-1, -3},
                {1, 2},
                {-3, 4},
                {-1, 4}
        };
        int[][] formula1 = new int[][] {
                {1, -2, -4},
                {-1, -3},
                {-2, -3}
        };

//        solveGeneralSAT(formula1, 4);
        testGeneral();
//        solveGeneralSAT(formula, 4);
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

    private static void solveGeneralSAT(int[][] formula, int numOfVars) {
        double numOfCases = Math.pow(2, numOfVars);
        Generator generator = new Generator(numOfVars);

        while (generator.hasNext()) {
            boolean[] vars = generator.next();
            Cnf cnf = Cnf.from(formula, vars);
            System.out.println(Arrays.toString(vars) + ": " + cnf.getValue());
        }
    }
}
