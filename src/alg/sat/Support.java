package alg.sat;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Support {

    private Support() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static Boolean[] generateDefaultAssignment(int numOfVars) {
        Boolean[] booleans = new Boolean[numOfVars];
        Arrays.fill(booleans, Boolean.FALSE);
        return booleans;
    }

}
