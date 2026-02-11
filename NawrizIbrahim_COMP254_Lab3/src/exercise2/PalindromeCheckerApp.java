/*
 ** File              : PalindromeCheckerApp.java
 ** Student           : Nawriz Ibrahim
 ** Student number    : 301161181
 ** Course            : COMP254 - Data Structures & Algorithms
 ** Assignment        : Lab Assignment #3 – Using Recursion – Exercise 2
 ** Date              : February 11, 2026
 ** Description       : Recursively checks whether a string is a palindrome.
 **                     Friendly I/O: user enters strings until they type EXIT.
 */

package exercise2;

import java.util.Scanner;

public class PalindromeCheckerApp {

    /**
     * Public method: normalizes input (optional but user-friendly) and checks palindrome recursively.
     * Here we remove spaces/punctuation and ignore case to make testing easier.
     */
    public static boolean isPalindrome(String s) {
        if (s == null) return false;

        // Friendly normalization: ignore spaces/punctuation, ignore case
        String cleaned = s.replaceAll("[^A-Za-z0-9]", "").toLowerCase();

        return isPalindrome(cleaned, 0, cleaned.length() - 1);
    }

    /**
     * Recursive helper checking substring s[low..high].
     */
    private static boolean isPalindrome(String s, int low, int high) {
        // Base case: 0 or 1 character left
        if (low >= high) return true;

        // If ends differ, not a palindrome
        if (s.charAt(low) != s.charAt(high)) return false;

        // Recur inward
        return isPalindrome(s, low + 1, high - 1);
    }

    // Program entry point
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Exercise 2: Recursive Palindrome Checker ===");
        System.out.println("Type a string to check (type EXIT to stop).");
        System.out.println("Note: This version ignores spaces/punctuation and case.\n");

        while (true) {
            System.out.print("Enter string: ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("EXIT")) {
                System.out.println("Goodbye!");
                break;
            }

            boolean result = isPalindrome(input);
            if (result) {
                System.out.println("String [" + input + "] is a palindrome");
            }
            else {
                System.out.println("String [" + input + "] is not a palindrome");
            }
        }

        sc.close();
    }
}
