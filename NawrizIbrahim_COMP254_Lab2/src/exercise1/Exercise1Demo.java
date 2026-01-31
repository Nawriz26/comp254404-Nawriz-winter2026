package exercise1;

import java.util.Arrays;

public class Exercise1Demo {
    public static void main(String[] args) {
        // =============== Input data for examples 1 -> 4 ===============
        int[] arr = {1,2,3,4,5};
        System.out.println("arr = " + Arrays.toString(arr));

        int r1 = Exercises.example1(arr);
        System.out.println("example1(arr) = " + r1 + "  (sum of elements)");

        int r2 = Exercises.example2(arr);
        System.out.println("example2(arr) = " + r2 + "  (sum of elements at even indices: 0,2,4,...)");

        int r3 = Exercises.example3(arr);
        System.out.println("example3(arr) = " + r3 + "  (nested loop accumulation)");

        int r4 = Exercises.example4(arr);
        System.out.println("example4(arr) = " + r4 + "  (sum of prefix sums)");

        // =============== Input data for example 5 ===============
        // example5 compares each value in 'second' to a compared 'total' based on 'first'
        int[] first = {1,2,3,4,5};

        // For first={1,2,3,4,5}, the internal computed total is 35
        // So 35 three times is included in 'second' where the count should come out as 3
        int[] second = {35, 0, 35, 36, 35};

        System.out.println("\nfirst = " + Arrays.toString(first));
        System.out.println("second = " + Arrays.toString(second));

        int r5 = Exercises.example5(first, second);
        System.out.println("example5(first, second) = " + r5 + "  (count of matches with computed total)");
    }
}
