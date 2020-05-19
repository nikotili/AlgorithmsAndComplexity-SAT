package alg.support;

import java.util.Arrays;

public class Generator {

    private final boolean[] arr;
    private final boolean ONE = true;
    private final boolean ZERO = false;
    private final boolean[] bound;

    public Generator(int n) {

        arr = new boolean[n];
        bound = new boolean[n];
        Arrays.fill(arr, false);
        Arrays.fill(bound, true);
    }

    public boolean[] next() {
        increment(arr.length -1, ONE, ZERO);
        return arr;
    }

    private void increment(int currPos, Boolean operand, Boolean reminder) {
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
        Generator generator = new Generator(20);

        while (generator.hasNext()) {
            System.out.println(Arrays.toString(generator.next()));
        }
    }
}
