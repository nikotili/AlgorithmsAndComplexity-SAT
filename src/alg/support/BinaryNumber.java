package alg.support;

import java.util.Arrays;

/**
 * Represents a non-negative integer by an array of boolean values
 */
public class BinaryNumber {

    private final boolean[] arr;
    private final boolean ONE = true;
    private final boolean ZERO = false;
    private final boolean[] bound;

    /**
     * Initializes the number as 0 ([false, false, false, ..., false])
     * @param size of the array representing the number
     */
    public BinaryNumber(int size) {

        arr = new boolean[size];
        bound = new boolean[size];
        Arrays.fill(bound, true);
    }

    public boolean[] get() {
        return arr;
    }

    public boolean[] incrementAndGet() {
        increment(arr.length -1, ONE, ZERO);
        return arr;
    }

    /**
     * Performs the increment operation of the array.
     * Example: array = [false, false, false, false,... ,false]
     * after calling increment: array = [false, false, false, false,..., true]
     */
    private void increment(int currPos, boolean operand, boolean reminder) {
        if(arr[currPos] == ONE && operand == ONE) {
            arr[currPos] = ZERO;
            operand = ZERO;
            reminder = ONE;
            increment(--currPos, operand, reminder);
        } else if (arr[currPos] == ONE && reminder == ONE) {
            arr[currPos] = ZERO;
            reminder = ONE;
            operand = ZERO;
            increment(--currPos, operand, reminder);
        } else {
            arr[currPos] = ONE;
        }
    }

    /**
     * Indicates if the number can be incremented or not.
     * @return {@code true} if the number representation is not yet 2^size - 1
     */
    public boolean hasNext() {
        return !Arrays.equals(arr, bound);
    }

    public static void main(String[] args) {
        BinaryNumber binaryNumber = new BinaryNumber(10);

        while (binaryNumber.hasNext()) {
            System.out.println(Arrays.toString(binaryNumber.incrementAndGet()));
        }
    }
}
