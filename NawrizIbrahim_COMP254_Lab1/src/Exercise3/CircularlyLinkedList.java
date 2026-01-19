/*

 ** File:            CircularlyLinkedList.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #1 – Exercise 3 – Using Fundamental Data Structures
 ** Date:            January 19, 2026
 ** Description:     This program uses the textbook CircularlyLinkedList implementation and adds a
 **                  clone() method that performs a deep copy of the circular structure (independent
 **                  chain of nodes). A main method is included to test that the cloned list remains
 **                  unchanged after mutating the original list.
 */

package Exercise3;

/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * An implementation of a circularly linked list.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class CircularlyLinkedList<E> implements Cloneable {
    //---------------- nested Node class ----------------
    /**
     * Singly linked node, which stores a reference to its element and
     * to the subsequent node in the list.
     */
    private static class Node<E> {

        /** The element stored at this node */
        private E element;     // an element stored at this node

        /** A reference to the subsequent node in the list */
        private Node<E> next;  // a reference to the subsequent node in the list

        /**
         * Creates a node with the given element and next node.
         *
         * @param e  the element to be stored
         * @param n  reference to a node that should follow the new node
         */
        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        // Accessor methods
        /**
         * Returns the element stored at the node.
         * @return the element stored at the node
         */
        public E getElement() { return element; }

        /**
         * Returns the node that follows this one (or null if no such node).
         * @return the following node
         */
        public Node<E> getNext() { return next; }

        // Modifier methods
        /**
         * Sets the node's next reference to point to Node n.
         * @param n    the node that should follow this one
         */
        public void setNext(Node<E> n) { next = n; }
    } //----------- end of nested Node class -----------

    // instance variables of the CircularlyLinkedList
    /** The designated cursor of the list */
    private Node<E> tail = null;                  // we store tail (but not head)

    /** Number of nodes in the list */
    private int size = 0;                         // number of nodes in the list

    /** Constructs an initially empty list. */
    public CircularlyLinkedList() { }             // constructs an initially empty list

    // access methods
    /**
     * Returns the number of elements in the linked list.
     * @return number of elements in the linked list
     */
    public int size() { return size; }

    /**
     * Tests whether the linked list is empty.
     * @return true if the linked list is empty, false otherwise
     */
    public boolean isEmpty() { return size == 0; }

    /**
     * Returns (but does not remove) the first element of the list
     * @return element at the front of the list (or null if empty)
     */
    public E first() {             // returns (but does not remove) the first element
        if (isEmpty()) return null;
        return tail.getNext().getElement();         // the head is *after* the tail
    }

    /**
     * Returns (but does not remove) the last element of the list
     * @return element at the back of the list (or null if empty)
     */
    public E last() {              // returns (but does not remove) the last element
        if (isEmpty()) return null;
        return tail.getElement();
    }

    // update methods
    /**
     * Rotate the first element to the back of the list.
     */
    public void rotate() {         // rotate the first element to the back of the list
        if (tail != null)                // if empty, do nothing
            tail = tail.getNext();         // the old head becomes the new tail
    }

    /**
     * Adds an element to the front of the list.
     * @param e  the new element to add
     */
    public void addFirst(E e) {                // adds element e to the front of the list
        if (size == 0) {
            tail = new Node<>(e, null);
            tail.setNext(tail);                     // link to itself circularly
        } else {
            Node<E> newest = new Node<>(e, tail.getNext());
            tail.setNext(newest);
        }
        size++;
    }

    /**
     * Adds an element to the end of the list.
     * @param e  the new element to add
     */
    public void addLast(E e) { // adds element e to the end of the list
        addFirst(e);             // insert new element at front of list
        tail = tail.getNext();   // now new element becomes the tail
    }

    /**
     * Removes and returns the first element of the list.
     * @return the removed element (or null if empty)
     */
    public E removeFirst() {                   // removes and returns the first element
        if (isEmpty()) return null;              // nothing to remove
        Node<E> head = tail.getNext();
        if (head == tail) tail = null;           // must be the only node left
        else tail.setNext(head.getNext());       // removes "head" from the list
        size--;
        return head.getElement();
    }

    /**
     * Exercise 3 requirement:
     * Return a deep copy (independent chain of nodes) of this circularly linked list.
     */
    @SuppressWarnings({"unchecked"})
    public CircularlyLinkedList<E> clone() throws CloneNotSupportedException {
        CircularlyLinkedList<E> other = (CircularlyLinkedList<E>) super.clone();

        if (size == 0) {
            //nothing to deep-copy
            other.tail = null;
            return other;
        }

        // Original head is after tail
        Node<E> originalHead = tail.getNext();

        // Create first node in the new chain
        Node<E> newHead = new Node<>(originalHead.getElement(), null);
        Node<E> newPrev = newHead;

        // Walk the original list for the remaining (size-1) nodes
        Node<E> walk = originalHead.getNext();
        for (int i =1; i < size; i++) {
            Node<E> newest = new Node<>(walk.getElement(), null);
            newPrev.setNext(newest);
            newPrev = newest;
            walk = walk.getNext();
        }

        // Close the circle
        newPrev.setNext(newHead);
        other.tail = newPrev;

        return other;
    }

    /**
     * Produces a string representation of the contents of the list.
     * This exists for debugging purposes only.
     */
    public String toString() {
        if (tail == null) return "()";
        StringBuilder sb = new StringBuilder("(");
        Node<E> walk = tail;
        do {
            walk = walk.getNext();
            sb.append(walk.getElement());
            if (walk != tail)
                sb.append(", ");
        } while (walk != tail);
        sb.append(")");
        return sb.toString();
    }

    //main method
    public static void main(String[] args) throws CloneNotSupportedException
    {
        CircularlyLinkedList<String> original = new CircularlyLinkedList<>();

        original.addLast("Player 1");
        original.addLast("Player 2");
        original.addLast("Player 3");
        original.addLast("Player 4");
        original.addLast("Player 5");
        original.addLast("Player 6");

        System.out.println("Original List of Players: "+ original);

        System.out.println();

        CircularlyLinkedList<String> copy = original.clone();
        System.out.println("Cloned List of Players: " + copy);

        System.out.println();

        // Mutate original; clone should remain unchanged
        original.rotate();
        original.removeFirst();
        original.addLast("Player 7");

        System.out.println();

        System.out.println("Original List of Players after mutating: "+ original);
        System.out.println("Cloned List of Players after mutating: " + copy);
    }
}
