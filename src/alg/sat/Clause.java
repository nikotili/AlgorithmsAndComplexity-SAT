package alg.sat;

import java.util.ArrayList;
import java.util.Collection;

public class Clause {
    private Collection<Literal> literals;

    public Clause() {
        literals = new ArrayList<>();
    }

    public Clause addLiteral(Literal literal) {
        this.literals.add(literal);
        return this;
    }

    public boolean getValue() {
        return literals.stream().anyMatch(Literal::getValue);
    }

//    public boolean getValueDevMode() {
//        return literals.stream()
//                .map(Literal::getValue);
//    }
}
