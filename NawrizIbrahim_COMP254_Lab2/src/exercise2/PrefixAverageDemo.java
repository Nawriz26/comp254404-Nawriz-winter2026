package exercise2;

import java.util.Random;

public class PrefixAverageDemo {
    private static final Random random = new Random(42);

    private static double[] randomArray(int size) {
        double[] array = new double[size];
        for (int i = 0; i < size; i++)
            array[i] = random.nextDouble();
        return array;
    }

    private static long timeNanosPrefix1(double[] x) {
        long start = System.nanoTime();
        PrefixAverage.prefixAverage1(x);
        return System.nanoTime() - start;
    }

    private static long timeNanosPrefix2(double[] x) {
        long start = System.nanoTime();
        PrefixAverage.prefixAverage2(x);
        return System.nanoTime() - start;
    }

    public static void main(String[] args) {
        // Choose representative values of the input size n, similar to StringExperiment.java from class examples.
        // Keep n modest because prefixAverage1 is O(n^2) and grows fast
        int n = 500; // starting size
        int trials = 12; // number of different n values

        // JIT warm-up (reduces timing noise)
        for (int w = 0; w < 5; w++) {
            double[] warm = randomArray(1000);
            PrefixAverage.prefixAverage1(warm);
            PrefixAverage.prefixAverage2(warm);
        }

        System.out.println("n,ms_prefixAverage1,ms_prefixAverage2");

        for  (int t = 0; t < trials; t++) {
            double[] x = randomArray(n);

            long nanos1 = timeNanosPrefix1(x);
            long nanos2 = timeNanosPrefix2(x);

            double ms1 = nanos1/1_000_000.0;
            double ms2 = nanos2/1_000_000.0;

            System.out.printf("%d,%.3f,%.3f%n", n, ms1, ms2);

            // If O(n^2) version gets too slow, stop increasing n
            if (ms1 > 10_000) break;
            n *= 2;
        }
    }
}
