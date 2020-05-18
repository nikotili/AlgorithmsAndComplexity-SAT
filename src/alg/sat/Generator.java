package alg.sat;

import java.util.Arrays;

//todo test
public class Generator {

    private final boolean[] arr;
    private final boolean ONE = true;
    private final boolean ZERO = false;
    private int index;
    private final int bound;

    public Generator(int n) {
        this.bound = n * n - 1;
        arr = new boolean[n];
        Arrays.fill(arr, Boolean.FALSE);
        index = 0;
    }

    public boolean[] next() {
        index++;
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
        return index < bound;
    }

    public static void main(String[] args) {
        Generator generator = new Generator(4);

        while (generator.hasNext()) {
            System.out.println(Arrays.toString(generator.next()));
        }
    }
}
