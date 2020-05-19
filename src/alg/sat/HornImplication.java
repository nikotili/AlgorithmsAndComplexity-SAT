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

    public static HornImplication from(Clause clause) {
        if (!clause.hasAtMostOnePositiveLiteral())
            throw new IllegalArgumentException();

        List<Literal> literals = clause.getLiterals()
                .stream()
                .filter(Literal::isLiteralNegation)
                .collect(Collectors.toList());
        Clause leftHandSide = Clause.from(literals);


        Literal rightHandSide = clause.getLiterals()
                .stream()
                .filter(Literal::isPositiveLiteral)
                .findAny()
                .orElse(null);

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
