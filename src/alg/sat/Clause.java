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

}
