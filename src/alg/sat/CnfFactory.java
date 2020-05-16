package alg.sat;

public class CnfFactory {

    public static Cnf newCnfFrom(int[][] formula, boolean[] assignment) {
        int length = assignment.length;

        Literal[] vars = new Literal[length];
        for (int i = 0; i < length; i++) {
            vars[i] = new Literal(i + "", assignment[i]);
        }

        Cnf cnf = new Cnf();

        for (int[] clause : formula) {
            Clause clauseObj = new Clause();
            for (int var : clause) {
                Literal literal = var < 0 ? vars[-var].negation() : vars[var];
                clauseObj.addLiteral(literal);
            }
            cnf.addClause(clauseObj);
        }

        return cnf;
    }
}
