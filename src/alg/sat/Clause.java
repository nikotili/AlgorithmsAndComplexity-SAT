package alg.sat;

import java.util.ArrayList;
import java.util.Collection;

public class Clause implements Valuable {
    private final Collection<Literal> literals;

    public Clause() {
        literals = new ArrayList<>();
    }

    public Clause addLiteral(Literal literal) {
        this.literals.add(literal);
        return this;
    }

    public boolean getValue() {
        return literals.stream()
                .anyMatch(Literal::getValue);
    }

    public boolean hasSizeNotTwo() {
        return literals.size() != 2;
    }

    public boolean isFalse() {
        return !getValue();
    }

    public boolean hasAtMostOnePositiveLiteral() {
        return literals.stream()
                .filter(Literal::isPositiveLiteral)
                .count() <= 1;
    }

    public static Clause newClauseFrom(Literal[] vars, int[] clause) {
        Clause clauseObj = new Clause();
        for (int var : clause) {
            Literal literal = var < 0 ? vars[-var].negation() : vars[var];
            clauseObj.addLiteral(literal);
        }
        return clauseObj;
    }

}
