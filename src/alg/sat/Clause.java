package alg.sat;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a clause in a {@link Cnf}
 */
public class Clause implements Valuable {
    private  List<Literal> literals;

    private Clause() {
        literals = new ArrayList<>();
    }

    public void addLiteral(Literal literal) {
        this.literals.add(literal);
    }

    public Collection<Literal> getLiterals() {
        return literals;
    }

    public boolean value() {
        return literals.stream()
                .anyMatch(Literal::value);
    }

    public boolean hasMoreThanTwoLiterals() {
        return literals.size() > 2;
    }

    public boolean isFalse() {
        return !value();
    }

    public boolean hasAtMostOnePositiveLiteral() {
        int numOfPositiveLiterals = 0;

        for (Literal literal : literals) {
            if (literal.hasPositiveSign()) {
                numOfPositiveLiterals++;
                if (numOfPositiveLiterals > 1)
                    return false;
            }
        }
        return true;
    }


    /**
     * Constructs a new clause from a clause represented by an array.
     * @param literals variables which construct the clause
     * @param clause represented by an int[]
     * @return an {@link Clause} object
     */
    static Clause from(Literal[] literals, int[] clause) {
        Clause clauseObj = new Clause();
        for (int literal : clause) {
            Literal literalObj = literal < 0 ? literals[-literal - 1].getNegation() : literals[literal - 1];
            clauseObj.addLiteral(literalObj);
        }
        return clauseObj;
    }


    /**
     * Constructs a new clause from a {@link List<Literal>}
     * @param literals to be used
     * @return an {@link Clause} object
     */
    static Clause from(List<Literal> literals) {
        Clause clause = new Clause();
        clause.literals = literals;
        return clause;
    }

    /**
     * Returns the representation of the clause as 2-SAT graph edges.
     * The method throws an exception if the instance has more than two literals
     * @return Map containing the edges
     * @throws IllegalStateException if {@link #hasMoreThanTwoLiterals()}
     */
    Map<Literal, Literal> twoSATGraphEdges() throws IllegalStateException {
        if (hasMoreThanTwoLiterals())
            throw new IllegalStateException("Clause has more than 2 literals");

        Map<Literal, Literal> edges = new HashMap<>();

        Literal literal1 = literals.get(0);
        Literal literal2 = literals.get(1);

        edges.put(literal1.getNegation(), literal2);
        edges.put(literal2.getNegation(), literal1);

        return edges;
    }

    /**
     * Returns the representation of the clause as a horn implication.
     * The method throws an exception if the instance has more than one positive literal.
     * @return {@link HornImplication} representing the instance
     * @throws IllegalStateException if {@link #hasAtMostOnePositiveLiteral()} is {@code false}
     */
    HornImplication hornImplication() throws IllegalStateException {
        if (!hasAtMostOnePositiveLiteral())
            throw new IllegalStateException("Clause has more than one positive literal");

        return HornImplication.from(this);
    }

    @Override
    public String toString() {
        String literals = this.literals
                .stream()
                .map(Literal::toString)
                .collect(Collectors.joining(" or "));

        return '(' + literals + ')';
    }
}
