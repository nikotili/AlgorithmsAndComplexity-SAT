package alg.sat;

import java.util.*;
import java.util.stream.Collectors;

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
        return !getValue();
    }

    public void satisfy() {
        if (getValue())
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
    public boolean getValue() {
        if (rightHandSide == null)
            return leftHandSide.getValue();

        return leftHandSide.getValue() || rightHandSide.getValue();
    }

    @Override
    public String toString() {
        return "HornImplication{" +
                "leftHandSide=" + leftHandSide +
                ", rightHandSide=" + rightHandSide +
                '}';
    }
}
