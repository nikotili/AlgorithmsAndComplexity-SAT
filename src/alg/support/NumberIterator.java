package alg.support;

import java.util.Arrays;

public class NumberIterator {

    private final boolean[] arr;
    private final boolean ONE = true;
    private final boolean ZERO = false;
    private final boolean[] bound;

    public NumberIterator(int numberOfBits) {

        arr = new boolean[numberOfBits];
        bound = new boolean[numberOfBits];
        Arrays.fill(bound, true);
    }

    public boolean[] next() {
        increment(arr.length -1, ONE, ZERO);
        return arr;
    }

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

    public boolean hasNext() {
        return !Arrays.equals(arr, bound);
    }

    public static void main(String[] args) {
        NumberIterator numberIterator = new NumberIterator(10);

        while (numberIterator.hasNext()) {
            System.out.println(Arrays.toString(numberIterator.next()));
        }
    }
}
