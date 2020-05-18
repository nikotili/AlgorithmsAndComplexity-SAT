package alg.sat;

import java.util.*;
import java.util.stream.Collectors;

public class Clause implements Valuable {
    private final List<Literal> literals;

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

    public Boolean isFalse() {
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

    static Clause from(Literal[] literals, int[] clause) {
        Clause clauseObj = new Clause();
        for (int literal : clause) {
            Literal literalObj = literal < 0 ? literals[-literal - 1].getNegation() : literals[literal - 1];
            clauseObj.addLiteral(literalObj);
        }
        return clauseObj;
    }

    public Collection<Literal> getLiterals() {
        return literals;
    }


    public Map<Literal, Literal> get2SATGraphEdges() {
        Map<Literal, Literal> edges = new HashMap<>();

        Literal literal1 = literals.get(0);
        Literal literal2 = literals.get(1);

        edges.put(literal1.getNegation(), literal2);
        edges.put(literal2.getNegation(), literal1);

        return edges;
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
