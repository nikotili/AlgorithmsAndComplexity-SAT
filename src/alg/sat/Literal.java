package alg.sat;

import java.util.Objects;

public class Literal implements Valuable {
    private final String name;
    private final boolean value;
    protected Literal negation;

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

    public Literal or(Literal that) {
        String name = this.name + " or " + that.name;
        boolean value = this.value || that.value;
        return new Literal(name, value);
    }

    public void initNegation() {
        if (this.negation == null) {
            this.negation = new LiteralNegation(this);
            this.negation.negation = this;
        }
    }

    public Literal getNegation() {
        if (negation == null)
            initNegation();
        return negation;
    }

    public boolean isPositiveLiteral() {
        return !(this instanceof LiteralNegation);
    }

    @Override
    public String toString() {
        return name;
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
}
