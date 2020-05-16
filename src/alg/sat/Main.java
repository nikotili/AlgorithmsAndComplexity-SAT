package alg.sat;

import java.nio.LongBuffer;
import java.util.Arrays;
import java.util.BitSet;

public class Main {

    public static void main(String[] args) {
        int[][] formula = new int[][]{
                {0, -1},
                {0, -2},
                {0, -3, -4},
                {-3, -4}
        };
        solveGeneralSAT(formula, 5);
    }

    private static void testGeneral() {
        int[][] formula = new int[][]{
                {0, -1},
                {0, -2},
                {0, -3, -4},
                {-3, -4}
        };

        Boolean[] assignment = Support.generateDefaultAssignment(5);

        System.out.println(Cnf.from(formula, assignment).isHornSAT());
    }

    //todo needs optimization
    private static void solveGeneralSAT(int[][] formula, int numOfVars) {
        double numOfCases = Math.pow(2, numOfVars);

        for (int i = 0; i < numOfCases; i++) {
            Boolean[] varsToBe = Arrays.stream(Integer.toBinaryString(i)
                    .split(""))
                    .map(s -> s.equals("1"))
                    .toArray(Boolean[]::new);

            Boolean[] vars = new Boolean[numOfVars];

            System.arraycopy(varsToBe, 0, vars, vars.length - varsToBe.length, varsToBe.length);
            Arrays.fill(vars, 0, vars.length - varsToBe.length, false);

            Cnf cnf = Cnf.from(formula, vars);
            System.out.println(cnf + ": " + cnf.getValue());
        }
    }


}
