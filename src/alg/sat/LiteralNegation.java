package alg.sat;

public class LiteralNegation extends Literal {
    public LiteralNegation(Literal literal) {
        super("~" + literal.getName(), !literal.getValue());
    }
}
