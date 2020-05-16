package alg.sat;

import java.util.ArrayList;
import java.util.Collection;

public class Cnf implements Valuable {
    private final Collection<Clause> clauses;

    public Cnf() {
        clauses = new ArrayList<>();
    }

    public Cnf addClause(Clause clause) {
        clauses.add(clause);
        return this;
    }

    public boolean getValue() {
        return clauses
                .stream()
                .noneMatch(Clause::isFalse);
    }

    public boolean is2SAT() {
        return clauses
                .stream()
                .noneMatch(Clause::hasSizeNotTwo);
    }

    public boolean isHornSAT() {
        return clauses
                .stream()
                .allMatch(Clause::hasAtMostOnePositiveLiteral);
    }


    public static Cnf newCnfFrom(int[][] formula, boolean[] assignment) {
        int length = assignment.length;

        Literal[] vars = new Literal[length];
        for (int i = 0; i < length; i++) {
            vars[i] = new Literal(i + "", assignment[i]);
        }

        Cnf cnf = new Cnf();

        for (int[] clause : formula) {
            Clause clauseObj = Clause.newClauseFrom(vars, clause);
            cnf.addClause(clauseObj);
        }

        return cnf;
    }

}
