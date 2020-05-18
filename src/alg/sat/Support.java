package alg.sat;

import java.util.Arrays;

public class Support {

    private Support() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static Boolean[] generateDefaultAssignment(int numOfVars) {
        return new Boolean[numOfVars];
    }

}
