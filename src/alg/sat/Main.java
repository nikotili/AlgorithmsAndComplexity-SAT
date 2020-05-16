package alg.sat;

public class Main {

    public static void main(String[] args) {
        int[][] formula = new int[][] {
                {0, 1},
                {0, -2},
                {0, 3, 4},
                {-3, -4}
        };

        boolean[] assignment = {false, true, false, true, false};

        System.out.println(CnfFactory.newCnfFrom(formula, assignment).getValue());
    }
}
