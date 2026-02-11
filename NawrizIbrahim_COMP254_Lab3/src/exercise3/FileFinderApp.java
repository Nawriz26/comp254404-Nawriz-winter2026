/*
 ** File              : FileFinderApp.java
 ** Student           : Nawriz Ibrahim
 ** Student number    : 301161181
 ** Course            : COMP254 - Data Structures & Algorithms
 ** Assignment        : Lab Assignment #3 – Using Recursion – Exercise 3
 ** Date              : February 9, 2026
 ** Description       : Recursively searches the file system starting at a given path
 **                     and prints all entries whose file name matches a target name.
 */

package exercise3;

import java.io.File;
import java.util.Scanner;

public class FileFinderApp {

    /**
     * Wrapper method (conceptual signature find(path, filename)).
     * Reports all matches under the given path.
     */
    public static int find(String path, String filename) {
        return find(new File(path), filename);
    }

    /**
     * Recursive method that searches the file system rooted at 'root'
     * and prints full paths whose name equals 'filename'.
     *
     * @return number of matches found
     */
    public static int find(File root, String filename) {
        if (root == null || filename == null) return 0;
        if (!root.exists()) return 0;

        int matches = 0;

        // If this is a file, check the name directly
        if (root.isFile()) {
            if (root.getName().equals(filename)) {
                System.out.println("FOUND: " + root.getAbsolutePath());
                return 1;
            }
            return 0;
        }

        // If this is a directory, recursively search children
        File[] children = root.listFiles();

        // listFiles() can return null (permissions, I/O errors, etc.)
        if (children == null) return 0;

        for (File child : children) {
            matches += find(child, filename);
        }

        return matches;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Exercise 3: Recursive File Finder ===");

        String startPath;
        String targetName;

        if (args.length >= 2) {
            startPath = args[0];
            targetName = args[1];
        } else {
            System.out.print("Enter starting path (example: C:\\Users\\nawri\\Documents): ");
            startPath = sc.nextLine().trim();

            System.out.print("Enter filename to find (example: notes.txt): ");
            targetName = sc.nextLine().trim();
        }

        System.out.println("\nSearching...");
        int total = find(startPath, targetName);

        System.out.println("\nSearch complete. Total matches: " + total);

        sc.close();
    }
}
