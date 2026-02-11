/*
 ** File              : RecursiveProductApp.java
 ** Student           : Nawriz Ibrahim
 ** Student number    : 301161181
 ** Course            : COMP254 - Data Structures & Algorithms
 ** Assignment        : Lab Assignment #3 – Using Recursion – Exercise 1
 ** Date              : February 9, 2026
 ** Description       : Recursively computes product of two positive integers using ONLY
 **                     addition and subtraction (no multiplication).
 **
 ** Key idea          : m * n = m + m * (n - 1)
 **                     We repeatedly add m, while subtracting 1 from n until n reaches 0.
 */

package exercise1;

import java.util.Scanner;

/**
 * Driver class for Exercise 1.
 *
 * This program demonstrates how to multiply two positive integers using recursion
 * with ONLY addition (+) and subtraction (-). It avoids using the multiplication
 * operator (*) completely.
 *
 * Example:
 * If m = 4 and n = 3
 * 4 * 3 = 4 + 4 * 2
 *       = 4 + 4 + 4 * 1
 *       = 4 + 4 + 4
 *       = 12
 */
public class RecursiveProductApp {
    /**
     * Recursively computes the product of two positive integers using only addition and subtraction.
     *
     * How it works
     *
     *   Base Case 1: If n == 0, then m * n == 0 (anything times 0 is 0)
     *   Base Case 2: If n == 1, then m * n == m
     *   Recursive Step: m * n = m + m * (n - 1)
     *
     * Optimization
     * We swap (m, n) so that we recurse on the smaller value. This reduces recursion depth
     * and therefore reduces stack usage.
     *
     * if (n > m) -> swap so that n <= m
     *
     * Complexity
     * Time:  O(min(m, n))  because we recurse once per decrement of the smaller number.
     * Space: O(min(m, n))  due to the recursion call stack.
     *
     * @param m     first positive integer multiplicand (expected > 0)
     * @param n     second positive integer multiplier (expected > 0)
     * @return      the product m * n computed recursively without using '*'
     */
    public static long recursiveProductApp(long m, long n) {
        // --- Optimization Step ---
        // We want to minimize recursion depth.
        // The recursion counts down using (n - 1).
        // So if n is larger than m, swap them so we count down the smaller one.
        //
        // Example:
        // 2 * 1_000_000 would recurse 1,000,000 times if we count down n.
        // If we swap, 1_000_000 * 2 recurses only 2 times.
        if (n > m) {
            // Swap values by calling the same method with reversed arguments.
            return recursiveProductApp(n, m);
        }

        // --- Base Case 1 ---
        // If n == 0, we have added m zero times -> total product is 0.
        if (n == 0) {
            return 0;
        }

        // --- Base Case 2 ---
        // If n == 1, we have added m exactly once -> total product is m.
        if (n == 1) {
            return m;
        }

        // --- Recursive Step ---
        // m * n = m + m * (n - 1)
        //
        // Meaning:
        //   - "m" represents one chunk of the product
        //   - recursiveProduct(m, n - 1) computes the remaining (n-1) chunks
        //
        // Example for m=4, n=3:
        // return 4 + recursiveProduct(4, 2)
        // return 4 + (4 + recursiveProduct(4, 1))
        // return 4 + (4 + 4)
        return m + recursiveProductApp(m, n - 1);
    }

    /**
     * Reads a positive long integer from the user with validation.
     * Users can type invalid input (letters, decimals, negative numbers, or empty values).
     * This helper method keeps prompting until the user enters a valid positive integer
     *
     * Validation rules:
     *  - Input must be a number that fits in a Java long
     *  - Input must be > 0 (strictly positive)
     *
     * @param sc        Scanner used to read input from the console
     * @param prompt    message displayed to the user before reading input
     * @return          a valid positive long (> 0)
     */
    private static long readPositiveLong(Scanner sc, String prompt) {
        // Repeat forever until we return a valid value
        while (true) {
            // Show prompt so user knows what to input
            System.out.print(prompt);

            // Read the entire line the user typed and remove leading or trailing spaces
            String token = sc.nextLine().trim();

            try {
                //  Attempt to convert the string to a long integer
                long value = Long.parseLong(token);

                // Check positivity requirement: must > 0
                if (value <= 0) {
                    // If not positive, then print a friendly message and loop again
                    System.out.println("Please enter a positive integer (> 0)");
                }else {
                    // Valid number and positive -> return a caller
                    return value;
                }
            }catch (NumberFormatException e) {
                // This happens if the user types something that cannot be parsed as a long
                // e.g. "abc", "12.5", "", "1,0000"
                System.out.println("Invalid number! Try again...");
            }
        }
    }

    //  Program entry point
    public static void main(String[] args) {
        // Create a scanner to read from standard input (keyboard)
        Scanner sc = new Scanner(System.in);

        // Print a title line so the user knows what program is running
        System.out.println("=== Exercise 1: Recursive Product (addition/subtraction only) ===");

        // Ask user for first positive integer m (validated)
        long m = readPositiveLong(sc, "Enter m (positive integer): ");

        // Ask user for second positive integer n (validated)
        long n = readPositiveLong(sc, "Enter n (positive integer): ");

        // Compute the product using recursion (no multiplication operator used)
        long result = recursiveProductApp(m, n);

        // Print output in a friendly format
        System.out.println("\nResult:");
        System.out.println(m + " × " + n + " = " + result);

        // Close Scanner
        sc.close();
    }
}
