package alg.sat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringJoiner;
import java.util.stream.Collectors;

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

    public boolean hasMoreThanTwoLiterals() {
        return literals.size() > 2;
    }

    public boolean isFalse() {
        return !getValue();
    }

    public boolean hasAtMostOnePositiveLiteral() {
        int numOfPositiveLiterals = 0;

        for (Literal literal : literals) {
            if (literal.isPositiveLiteral()) {
                numOfPositiveLiterals++;
                if (numOfPositiveLiterals > 1)
                    return false;
            }
        }
        return true;
    }

    public static Clause from(Literal[] literals, int[] clause) {
        Clause clauseObj = new Clause();
        for (int literal : clause) {
            Literal literalObj = literal < 0 ? literals[-literal - 1].negation() : literals[literal - 1];
            clauseObj.addLiteral(literalObj);
        }
        return clauseObj;
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
