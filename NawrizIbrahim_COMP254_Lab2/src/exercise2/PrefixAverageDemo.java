package exercise2;

import java.util.Random;

/*
 ** File:            PrefixAverageDemo.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #2 – Exercise 2 – Using Algorithm Analysis
 ** Date:            January 31, 2026
 ** Description:     This is a driver program that performs an experimental analysis of
 **                  PrefixAverage.prefixAverage1 and PrefixAverage.prefixAverage2 by
 **                  timing each algorithm for increasing input sizes n and printing
 **                  results to the console in CSV format-like
 */

public class PrefixAverageDemo {
    // Fixed seed so results are repeatable each time we run the demo
    private static final Random random = new Random(42);

    /**
     * Builds and returns a double array of the given size filled with random values in [0,1).
     * This simulates arbitrary input data of length n for the prefix-average algorithms.
     */
    private static double[] randomArray(int size) {
        double[] array = new double[size];
        for (int i = 0; i < size; i++)
            array[i] = random.nextDouble();
        return array;
    }

    /**
     * Times prefixAverage1 on the given input array using System.nanoTime().
     * @param x the input array
     * @return elapsed time in nanoseconds
     */
    private static long timeNanosPrefix1(double[] x) {
        long start = System.nanoTime();
        PrefixAverage.prefixAverage1(x);
        return System.nanoTime() - start;
    }

    /**
     * Times prefixAverage2 on the given input array using System.nanoTime().
     * @param x the input array
     * @return elapsed time in nanoseconds
     */
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

        // // Print a CSV header line
        System.out.println("n,ms_prefixAverage1,ms_prefixAverage2");

        // Run timing trials for increasing input sizes
        for  (int t = 0; t < trials; t++) {
            // Build a fresh random input array of length n (Using new data each time prevents caching effects from
            // reusing the same array)
            double[] x = randomArray(n);

            // Time each algorithm on the same input size n
            long nanos1 = timeNanosPrefix1(x);
            long nanos2 = timeNanosPrefix2(x);

            // Convert nanoseconds to milliseconds for easier reading
            double ms1 = nanos1/1_000_000.0;
            double ms2 = nanos2/1_000_000.0;

            // Print one CSV row: n, time for prefixAverage1, time for prefixAverage2
            System.out.printf("%d,%.3f,%.3f%n", n, ms1, ms2);

            // If O(n^2) version gets too slow, stop increasing n
            if (ms1 > 10_000) break;
            // Increase input size for the next trial
            n *= 2;
        }
    }
}
