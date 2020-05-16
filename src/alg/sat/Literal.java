package alg.sat;

public class Literal implements Valuable {
    private final String name;
    private final boolean value;

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

    public LiteralNegation negation() {
        return new LiteralNegation("~" + name, !value);
    }

    public boolean isPositiveLiteral() {
        return !(this instanceof LiteralNegation);
    }

    @Override
    public String toString() {
        return "Literal{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
