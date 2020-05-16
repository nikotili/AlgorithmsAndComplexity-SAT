package alg.sat;

public class Support {

    private Support() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static Boolean[] generateDefaultAssignment(int numOfVars) {
        return new Boolean[numOfVars];
    }

}
