package alg.sat;

import java.util.*;

/**
 * Represents an Horn Implication like: (x and y and z) -> w
 * in the form: (-x or -y or -z) or w.
 */
public class HornImplication implements Valuable {
    private final Clause leftHandSide;
    private final Literal rightHandSide;

    private HornImplication(Clause leftHandSide, Literal rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }


    //todo test
    /**
     * Creates a horn-implication representation of a clause.
     * The method throws an exception if the clause has more than one positive literal.
     * @return {@link HornImplication} representing the instance
     * @throws IllegalArgumentException if the passed {@link Clause} has more than one
     * positive literal.
     */
    public static HornImplication from(Clause clause) throws IllegalArgumentException {
        if (!clause.hasAtMostOnePositiveLiteral())
            throw new IllegalArgumentException("Not a Horn Clause");

        Collection<Literal> clauseLiterals = clause.getLiterals();

        List<Literal> literalNegations = new ArrayList<>();
        Literal rightHandSide = null;

        for (Literal clauseLiteral : clauseLiterals) {
            if (clauseLiteral.hasNegativeSign()) {
                literalNegations.add(clauseLiteral);
            } else {
                rightHandSide = clauseLiteral;
            }
        }

        Clause leftHandSide = Clause.from(literalNegations);

        return new HornImplication(leftHandSide, rightHandSide);
    }

    public boolean toBeSatisfied() {
        return !value();
    }

    /**
     * Satisfies the implication by setting the RHS to true.
     */
    public void satisfy() {
        if (value())
            return;

        rightHandSide.setValue(true);
        rightHandSide.getNegation().setValue(false);
    }

    /**
     * Indicates if the implication has literals in both sides
     * @return {@code true} if the implication is regular,
     * {@code false} otherwise.
     */
    public boolean isRegularImplication() {
        return !isPureNegativeClause();
    }


    /**
     * Indicates if the implication has no literals in its LHS
     * @return {@code true} if the implication is a singleton,
     * {@code false} otherwise.
     */
    public boolean isSingleton() {
        return leftHandSide.getLiterals().size() == 0;
    }


    /**
     * Indicates if the implication has no literal in its RHS
     * @return {@code true} if the implication is a pure negative clause,
     * {@code false} otherwise.
     */
    public boolean isPureNegativeClause() {
        return rightHandSide == null;
    }

    /**
     * Returns the result of the implication.
     * @return
     */
    @Override
    public boolean value() {
        if (rightHandSide == null)
            return leftHandSide.value();

        return leftHandSide.value() || rightHandSide.value();
    }

    @Override
    public String toString() {
        return "HornImplication{" +
                "leftHandSide=" + leftHandSide +
                ", rightHandSide=" + rightHandSide +
                '}';
    }
}
