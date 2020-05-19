package alg.support;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterators;

public class Support {

    private Support() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static boolean[] generateDefaultAssignment(int numOfVars) {
        boolean[] booleans = new boolean[numOfVars];
        Arrays.fill(booleans, false);
        return booleans;
    }

    public static FormulaFromFile extractFormulaFromFile(String path) {
        try {
            List<String> strings = Files.readAllLines(Paths.get(path));
            int numOfVariables = Integer.parseInt(strings.get(0));
            int numOfClauses = Integer.parseInt(strings.get(1));

            int[][] formula = new int[numOfClauses][];
            int j = 0;
            for (int i = 2; i < strings.size(); i++) {
                String line = strings.get(i);
                int[] clause = Arrays.stream(line.split(", "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                formula[j++] = clause;
            }

            return new FormulaFromFile(numOfVariables, numOfClauses, formula);

        } catch (IOException e) {
            System.err.println("Error reading file: " + path);
            System.exit(0);
        }
        return null;
    }

    public static class FormulaFromFile {
        private final int numOfVariables;
        private final int numOfClauses;
        private final int[][] formula;


        public FormulaFromFile(int numOfVariables, int numOfClauses, int[][] formula) {
            this.numOfVariables = numOfVariables;
            this.numOfClauses = numOfClauses;
            this.formula = formula;
        }

        public int getNumOfVariables() {
            return numOfVariables;
        }

        public int getNumOfClauses() {
            return numOfClauses;
        }

        public int[][] getFormula() {
            return formula;
        }
    }

}
