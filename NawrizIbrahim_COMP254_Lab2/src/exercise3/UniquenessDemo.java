package exercise3;

/*
 ** File:            UniquenessDemo.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #2 – Exercise 3 – Using Algorithm Analysis
 ** Date:            Feb 1, 2026
 ** Description:     This is a driver program that performs an experimental analysis for
 **                  Uniqueness.unique1 and Uniqueness.unique2. It determines the largest
 **                  input size n such that each algorithm runs in 60 seconds or less,
 **                  using a probing phase and a binary search phase.
 */

import java.util.Random;

public class UniquenessDemo {
    private static final long LIMIT_NANOS = 60L * 1_000_000_000L; //60 seconds
    private static final Random RANDOM = new Random(123);

    // Create an array will all UNIQUE values (worst-case for unique1: it must compare everything)
    private static int[] uniqueData(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        // Shuffle so that unique2 does NOT get a potentially "nice" ordering
        for (int i = n - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            int tmp = a[j];
            a[i] = a[j];
            a[j] = tmp;
        }
        return a;
    }

    private static long timeUnique1Nanos(int n) {
        int[] data = uniqueData(n);
        long start = System.nanoTime();
        boolean result = Uniqueness.unique1(data);
        long elapsed = System.nanoTime() - start;
        if (!result) {
            throw new IllegalStateException("Expected unique data, got duplicates?");
        }
        return elapsed;
    }

    private static long timeUnique2Nanos(int n) {
        int[] data = uniqueData(n);
        long start = System.nanoTime();
        boolean result = Uniqueness.unique2(data);
        long elapsed = System.nanoTime() - start;
        if (!result) {
            throw new IllegalStateException("Expected unique data, got duplicates?");
        }
        return elapsed;
    }

    // Find max n with elapsed <= LIMIT_NANOS using:
    // 1) controlled growth to find an upper bound
    // 2) binary search between last-good and first-bad
    private static int maxNWithinLimit(boolean useUnique1) {
        int n = 1_000;
        int lastGood = n;
        int firstBad = -1;

        // Warm-up (JIT)
        for (int w = 0; w < 3; w++) {
            if (useUnique1) timeUnique1Nanos(1_000);
            else timeUnique2Nanos(50_000);
        }

        // Step 1: find a "bad" upper bound without overshooting too crazily
        while (true) {
            long elapsed = useUnique1 ? timeUnique1Nanos(n) : timeUnique2Nanos(n);
            System.out.printf("%s probe n=%d -> %.3f s%n", useUnique1 ? "unique1" : "unique2", n, elapsed / 1_000_000_000.0);

            if (elapsed <= LIMIT_NANOS) {
                lastGood = n;

                // Growth strategy:
                // - If we're far from limit, grow faster
                // - If we're near limit, grow slower to avoid a 3–4x overshoot (important for quadratic)
                if (elapsed < LIMIT_NANOS / 4) n = (int) Math.round(n * 2.0);
                else n = (int) Math.round(n * 1.25);

                // Avoid overflow / absurd sizes
                if (n <= lastGood) n = lastGood + 1;
            } else {
                firstBad = n;
                break;
            }
        }
        // Step 2: binary search between lastGood and firstBad
        int low = lastGood;
        int high = firstBad;

        while (low + 1 < high) {
            int mid = low + (high - low) / 2;
            long elapsed = useUnique1 ? timeUnique1Nanos(mid) : timeUnique2Nanos(mid);

            System.out.printf("%s bin mid=%d -> %.3f s%n", useUnique1 ? "unique1" : "unique2", mid, elapsed / 1_000_000_000.0);

            if (elapsed <= LIMIT_NANOS) low = mid;
            else high = mid;
        }

        return low; // largest n found within limit
    }

    public static void main(String[] args) {
        System.out.println("Finding max n for unique1 (<= 60 seconds)...");
        int max1 = maxNWithinLimit(true);
        System.out.println("RESULT unique1 max n = " + max1);

        System.out.println("\nFinding max n for unique2 (<= 60 seconds)...");
        int max2 = maxNWithinLimit(false);
        System.out.println("RESULT unique2 max n = " + max2);
    }
}
