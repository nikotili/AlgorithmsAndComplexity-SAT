package alg.sat;

import java.util.Objects;

public class Literal implements Valuable {
    private final String name;
    private boolean value;
    private Literal negation;

    public Literal(String name, boolean value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    private void initNegation() {
        this.negation = new LiteralNegation(this);
        this.negation.negation = this;
    }

    public Literal getNegation() {
        if (negation == null)
            initNegation();
        return negation;
    }

    public boolean isPositiveLiteral() {
        return !isLiteralNegation();
    }

    public boolean isLiteralNegation() {
        return this instanceof LiteralNegation;
    }

    @Override
    public String toString() {
        return name + ": " + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Literal literal = (Literal) o;
        return Objects.equals(name, literal.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    private static class LiteralNegation extends Literal {
        public LiteralNegation(Literal literal) {
            super("~" + literal.getName(), !literal.value);
        }
    }
}
