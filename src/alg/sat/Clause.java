package alg.sat;

import java.util.*;
import java.util.stream.Collectors;

public class Clause implements Valuable {
    private  List<Literal> literals;

    private Clause() {
        literals = new ArrayList<>();
    }

    public void addLiteral(Literal literal) {
        this.literals.add(literal);
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

    static Clause from(List<Literal> literals) {
        Clause clause = new Clause();
        clause.literals = literals;
        return clause;
    }


    public Map<Literal, Literal> twoSATGraphEdges() {
        Map<Literal, Literal> edges = new HashMap<>();

        Literal literal1 = literals.get(0);
        Literal literal2 = literals.get(1);

        edges.put(literal1.getNegation(), literal2);
        edges.put(literal2.getNegation(), literal1);

        return edges;
    }

    public HornImplication hornImplication() {
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
