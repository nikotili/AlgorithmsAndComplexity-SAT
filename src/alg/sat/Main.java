package alg.sat;

public class Main {

    public static void main(String[] args) {
        Literal literal = new Literal("X1", true);
        Literal literal2 = new Literal("X2", false);
        Literal literal3 = new Literal("X3", false);
        Literal literal4 = new Literal("X4", false);
        Clause clause = new Clause();

//        System.out.println(literal.or(literal2).or(literal3).or(literal4));

        System.out.println(clause
                .addLiteral(literal)
                .addLiteral(literal2)
                .addLiteral(literal3)
                .addLiteral(literal4)
                .getValue());

    }
}
