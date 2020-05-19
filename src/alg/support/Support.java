package alg.support;

import java.util.Arrays;

public class Support {

    private Support() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static boolean[] generateDefaultAssignment(int numOfVars) {
        boolean[] booleans = new boolean[numOfVars];
        Arrays.fill(booleans, false);
        return booleans;
    }

}
