package alg.sat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

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
                .noneMatch(Clause::hasMoreThanTwoLiterals);
    }

    public boolean isHornSAT() {
        return clauses
                .stream()
                .allMatch(Clause::hasAtMostOnePositiveLiteral);
    }


    public static Cnf from(int[][] formula, Boolean[] assignment) {
        int length = assignment.length;

        Literal[] vars = new Literal[length];
        for (int i = 0; i < length; i++) {
            vars[i] = new Literal(i + "", assignment[i]);
        }

        Cnf cnf = new Cnf();

        for (int[] clause : formula) {
            Clause clauseObj = Clause.from(vars, clause);
            cnf.addClause(clauseObj);
        }

        return cnf;
    }

    public static Cnf defaultFrom(int[][] formula, int numOfVars) {
        return from(formula, Support.generateDefaultAssignment(numOfVars));
    }

    @Override
    public String toString() {
        return this.clauses
                .stream()
                .map(Clause::toString)
                .collect(Collectors.joining(" and "));
    }
}
