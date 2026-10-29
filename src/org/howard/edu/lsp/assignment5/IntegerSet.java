package org.howard.edu.lsp.assignment5;

import java.util.TreeSet;

/**
 * Represents a mathematical set of integers.
 * Duplicate values are not stored, and the set is displayed in ascending order.
 */
public class IntegerSet {
    private final TreeSet<Integer> elements;

    /** Creates an empty integer set. */
    public IntegerSet() {
        elements = new TreeSet<>();
    }

    /** Removes every element from this set. */
    public void clear() {
        elements.clear();
    }

    /**
     * Returns the number of distinct elements in this set.
     *
     * @return the number of elements
     */
    public int length() {
        return elements.size();
    }

    /**
     * Tests whether this set contains exactly the same elements as another set.
     *
     * @param b the set to compare with
     * @return true if both sets contain the same elements
     */
    public boolean equals(IntegerSet b) {
        return b != null && elements.equals(b.elements);
    }

    /**
     * Tests whether this set contains the specified value.
     *
     * @param value the value to search for
     * @return true if the value is present
     */
    public boolean contains(int value) {
        return elements.contains(value);
    }

    /**
     * Returns the largest element.
     *
     * @return the largest integer in this set
     * @throws IllegalStateException if this set is empty
     */
    public int largest() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot get largest element of an empty set");
        }
        return elements.last();
    }

    /**
     * Returns the smallest element.
     *
     * @return the smallest integer in this set
     * @throws IllegalStateException if this set is empty
     */
    public int smallest() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot get smallest element of an empty set");
        }
        return elements.first();
    }

    /**
     * Adds an integer to this set. Existing values are ignored.
     *
     * @param item the integer to add
     */
    public void add(int item) {
        elements.add(item);
    }

    /**
     * Removes an integer from this set if it is present.
     *
     * @param item the integer to remove
     */
    public void remove(int item) {
        elements.remove(item);
    }

    /**
     * Returns a new set containing the elements in either this set or b.
     *
     * @param b the other set
     * @return an independent set containing the union
     */
    public IntegerSet union(IntegerSet b) {
        IntegerSet result = new IntegerSet();
        result.elements.addAll(elements);
        result.elements.addAll(b.elements);
        return result;
    }

    /**
     * Returns a new set containing only elements shared by this set and b.
     *
     * @param b the other set
     * @return an independent set containing the intersection
     */
    public IntegerSet intersect(IntegerSet b) {
        IntegerSet result = new IntegerSet();
        for (int value : elements) {
            if (b.contains(value)) {
                result.add(value);
            }
        }
        return result;
    }

    /**
     * Returns a new set containing elements in this set but not in b.
     *
     * @param b the other set
     * @return an independent set containing the difference
     */
    public IntegerSet diff(IntegerSet b) {
        IntegerSet result = new IntegerSet();
        for (int value : elements) {
            if (!b.contains(value)) {
                result.add(value);
            }
        }
        return result;
    }

    /**
     * Returns a new set containing elements in b but not in this set.
     * This is the reverse-difference definition specified for this assignment.
     *
     * @param b the other set
     * @return an independent set containing b minus this set
     */
    public IntegerSet complement(IntegerSet b) {
        IntegerSet result = new IntegerSet();
        for (int value : b.elements) {
            if (!contains(value)) {
                result.add(value);
            }
        }
        return result;
    }

    /**
     * Tests whether every element in this set is also in b.
     *
     * @param b the set that may contain this set
     * @return true if this set is a subset of b
     */
    public boolean isSubsetOf(IntegerSet b) {
        for (int value : elements) {
            if (!b.contains(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests whether this set and b have no elements in common.
     *
     * @param b the other set
     * @return true if the sets are disjoint
     */
    public boolean isDisjoint(IntegerSet b) {
        for (int value : elements) {
            if (b.contains(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a new set containing elements present in exactly one of the two sets.
     *
     * @param b the other set
     * @return an independent set containing the symmetric difference
     */
    public IntegerSet symmetricDiff(IntegerSet b) {
        IntegerSet result = new IntegerSet();
        for (int value : elements) {
            if (!b.contains(value)) {
                result.add(value);
            }
        }
        for (int value : b.elements) {
            if (!contains(value)) {
                result.add(value);
            }
        }
        return result;
    }

    /**
     * Tests whether this set contains no elements.
     *
     * @return true if this set is empty
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * Returns the elements in ascending order, separated by comma and space.
     *
     * @return the formatted set, or [] when empty
     */
    @Override
    public String toString() {
        return elements.toString();
    }

    /**
     * Compares this set with any object for value equality.
     *
     * @param other the object to compare with
     * @return true if other is an IntegerSet with the same elements
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IntegerSet)) {
            return false;
        }
        return elements.equals(((IntegerSet) other).elements);
    }

    /**
     * Returns a hash code consistent with set equality.
     *
     * @return this set's hash code
     */
    @Override
    public int hashCode() {
        return elements.hashCode();
    }
}
