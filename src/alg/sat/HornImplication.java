package alg.sat;

import java.util.*;

public class HornImplication implements Valuable {
    private final Clause leftHandSide;
    private final Literal rightHandSide;

    private HornImplication(Clause leftHandSide, Literal rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }


    //todo test
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

    public void satisfy() {
        if (value())
            return;

        rightHandSide.setValue(true);
        rightHandSide.getNegation().setValue(false);
    }

    public boolean isImplication() {
        return !isPureNegativeClause();
    }

    public boolean isSingleton() {
        return leftHandSide.getLiterals().size() == 0;
    }

    public boolean isPureNegativeClause() {
        return rightHandSide == null;
    }

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
