package alg.sat;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int[][] formula = new int[][]{
                {1, -2},
                {-1, -3},
                {1, 2},
                {-3, 4},
                {-1, 4}
        };
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

        Boolean[] assignment = new Boolean[] {Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE};

        System.out.println(Cnf.from(formula, Support.generateDefaultAssignment(4)).to2SATGraph());
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
