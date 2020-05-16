package alg.sat;

public class Literal {
    private String name;
    private boolean value;

    public Literal(String name, boolean value) {
        this.name = name;
        this.value = value;
    }

    public Literal(boolean value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public boolean getValue() {
        System.out.println("used");
        return value;
    }

    public Literal or(Literal that) {
        String name = this.name + " or " + that.name;
        boolean value = this.value || that.value;
        return new Literal(name, value);
    }

    @Override
    public String toString() {
        return "Literal{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
