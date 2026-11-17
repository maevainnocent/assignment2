package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class IntegerSetTest {

    /**
     * Builds a new IntegerSet containing the given values. Each call returns a
     * fresh object, so tests never share mutable state.
     */
    private static IntegerSet setOf(int... values) {
        IntegerSet set = new IntegerSet();
        for (int value : values) {
            set.add(value);
        }
        return set;
    }

    // ===== clear() =====

    /**
     * Create a populated set; call clear(); assert that it is empty and its length is zero.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testClearClearsPopulatedSet() {
        // Arrange
        IntegerSet set = setOf(1, 2, 3);
        assertEquals(3, set.length());

        // Act
        set.clear();

        // Assert
        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
        assertFalse(set.contains(1));
        assertFalse(set.contains(2));
        assertFalse(set.contains(3));
        assertEquals("[]", set.toString());
    }

    /**
     * Clear an already empty set; verify it remains empty.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testClearClearsEmptySet() {
        // Arrange
        IntegerSet set = new IntegerSet();

        // Act
        set.clear();

        // Assert
        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
        assertEquals("[]", set.toString());
    }

    // ===== length() =====

    /**
     * Add repeated and unique integers; assert the number of distinct elements.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLengthCountsUniqueElements() {
        // Arrange
        IntegerSet set = new IntegerSet();

        // Act
        set.add(5);
        set.add(5);
        set.add(-3);
        set.add(7);
        set.add(-3);
        set.add(0);

        // Assert
        assertEquals(4, set.length());
    }

    /**
     * Verify a new empty set has length zero.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLengthEmptySetLength() {
        // Arrange
        IntegerSet set = new IntegerSet();

        // Act
        int length = set.length();

        // Assert
        assertEquals(0, length);
    }

    // ===== equals() =====

    /**
     * Construct sets with identical elements in different insertion orders; assert equality.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsIgnoresInsertionOrder() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(4, 2, 3, 1);

        // Act / Assert
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
        assertEquals(a, b);
    }

    /**
     * Construct sets of equal length with different elements; assert inequality.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsDetectsDifferentContents() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(1, 2, 4);
        assertEquals(a.length(), b.length());

        // Act / Assert
        assertFalse(a.equals(b));
        assertFalse(b.equals(a));
        assertNotEquals(a, b);
    }

    /**
     * Verify two separately constructed empty sets compare equal.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsEmptySetsEqual() {
        // Arrange
        IntegerSet a = new IntegerSet();
        IntegerSet b = new IntegerSet();
        assertNotSame(a, b);

        // Act / Assert
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    /**
     * Verify a set equals itself and differs from a set of another size.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsSameObjectAndDifferentSizes() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet bigger = setOf(1, 2, 3, 4);
        IntegerSet smaller = setOf(1, 2);

        // Act / Assert
        assertTrue(a.equals(a));
        assertFalse(a.equals(bigger));
        assertFalse(bigger.equals(a));
        assertFalse(a.equals(smaller));
        assertFalse(smaller.equals(a));
    }

    // ===== contains() =====

    /**
     * Verify contains() is true for a present element and false for an absent one.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testContainsPresentAndAbsent() {
        // Arrange
        IntegerSet set = setOf(-5, 0, 10);

        // Act / Assert
        assertTrue(set.contains(-5));
        assertTrue(set.contains(0));
        assertTrue(set.contains(10));
        assertFalse(set.contains(5));
        assertFalse(set.contains(11));
    }

    /**
     * Verify an empty set does not contain an arbitrary integer.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testContainsEmptySetContainsNothing() {
        // Arrange
        IntegerSet set = new IntegerSet();

        // Act / Assert
        assertFalse(set.contains(0));
        assertFalse(set.contains(42));
        assertFalse(set.contains(-1));
    }

    // ===== largest() =====

    /**
     * Verify largest() returns the maximum across negative, zero, and positive values.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLargestNegativeAndPositiveValues() {
        // Arrange
        IntegerSet mixed = setOf(-10, 0, 25, 7, -3);
        IntegerSet allNegative = setOf(-8, -2, -15);

        // Act
        int mixedLargest = mixed.largest();
        int negativeLargest = allNegative.largest();

        // Assert
        assertEquals(25, mixedLargest);
        assertEquals(-2, negativeLargest);
        assertEquals(5, mixed.length());
    }

    /**
     * Use assertThrows() to verify IllegalStateException for an empty set.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLargestEmptySetThrowsException() {
        // Arrange
        IntegerSet set = new IntegerSet();

        // Act / Assert
        assertThrows(IllegalStateException.class, () -> set.largest());
    }

    // ===== smallest() =====

    /**
     * Verify smallest() returns the minimum across negative, zero, and positive values.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSmallestNegativeAndPositiveValues() {
        // Arrange
        IntegerSet mixed = setOf(-10, 0, 25, 7, -3);
        IntegerSet allPositive = setOf(8, 2, 15);

        // Act
        int mixedSmallest = mixed.smallest();
        int positiveSmallest = allPositive.smallest();

        // Assert
        assertEquals(-10, mixedSmallest);
        assertEquals(2, positiveSmallest);
        assertEquals(5, mixed.length());
    }

    /**
     * Use assertThrows() to verify IllegalStateException for an empty set.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSmallestEmptySetThrowsException() {
        // Arrange
        IntegerSet set = new IntegerSet();

        // Act / Assert
        assertThrows(IllegalStateException.class, () -> set.smallest());
    }

    // ===== add() =====

    /**
     * Add the same integer repeatedly; verify only one copy exists.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testAddIgnoresDuplicates() {
        // Arrange
        IntegerSet set = new IntegerSet();

        // Act
        set.add(9);
        set.add(9);
        set.add(9);

        // Assert
        assertEquals(1, set.length());
        assertTrue(set.contains(9));
        assertEquals("[9]", set.toString());
    }

    /**
     * Add Integer.MIN_VALUE, Integer.MAX_VALUE, and zero; verify all are represented.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testAddHandlesIntegerBoundaries() {
        // Arrange
        IntegerSet set = new IntegerSet();

        // Act
        set.add(Integer.MAX_VALUE);
        set.add(0);
        set.add(Integer.MIN_VALUE);

        // Assert
        assertEquals(3, set.length());
        assertTrue(set.contains(Integer.MIN_VALUE));
        assertTrue(set.contains(0));
        assertTrue(set.contains(Integer.MAX_VALUE));
        assertEquals(Integer.MIN_VALUE, set.smallest());
        assertEquals(Integer.MAX_VALUE, set.largest());
        assertEquals("[-2147483648, 0, 2147483647]", set.toString());
    }

    // ===== remove() =====

    /**
     * Remove a present element; verify it is absent and others remain.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testRemoveRemovesExistingElement() {
        // Arrange
        IntegerSet set = setOf(1, 2, 3);

        // Act
        set.remove(2);

        // Assert
        assertFalse(set.contains(2));
        assertTrue(set.contains(1));
        assertTrue(set.contains(3));
        assertEquals(2, set.length());
        assertEquals("[1, 3]", set.toString());
    }

    /**
     * Remove an absent element; verify the set is unchanged.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testRemoveIgnoresAbsentElement() {
        // Arrange
        IntegerSet set = setOf(1, 2, 3);

        // Act
        set.remove(99);

        // Assert
        assertEquals(3, set.length());
        assertEquals(setOf(1, 2, 3), set);
        assertEquals("[1, 2, 3]", set.toString());
    }

    // ===== union() =====

    /**
     * Verify union() contains all distinct elements from two overlapping sets.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testUnionOverlappingSets() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5, 6);

        // Act
        IntegerSet result = a.union(b);

        // Assert
        assertEquals(setOf(1, 2, 3, 4, 5, 6), result);
        assertEquals(6, result.length());
        assertEquals("[1, 2, 3, 4, 5, 6]", result.toString());
    }

    /**
     * Verify union with an empty set and with the same object.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testUnionEmptyAndSameOperand() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet empty = new IntegerSet();

        // Act
        IntegerSet withEmpty = a.union(empty);
        IntegerSet emptyWithA = empty.union(a);
        IntegerSet withSelf = a.union(a);

        // Assert
        assertEquals(setOf(1, 2, 3), withEmpty);
        assertEquals(setOf(1, 2, 3), emptyWithA);
        assertEquals(setOf(1, 2, 3), withSelf);
        assertNotSame(a, withEmpty);
        assertNotSame(a, withSelf);
        assertEquals(setOf(1, 2, 3), a);
        assertTrue(empty.isEmpty());
    }

    /**
     * Verify both inputs remain unchanged; modify the result and verify the inputs are still unchanged.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testUnionDoesNotMutateInputs() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4);

        // Act
        IntegerSet result = a.union(b);

        // Assert - inputs unchanged by the operation
        assertEquals(setOf(1, 2, 3), a);
        assertEquals(setOf(3, 4), b);
        assertNotSame(a, result);
        assertNotSame(b, result);

        // Act - modify the result
        result.add(100);
        result.remove(1);
        result.remove(4);

        // Assert - inputs still unchanged
        assertEquals(setOf(1, 2, 3), a);
        assertEquals(setOf(3, 4), b);
        assertFalse(a.contains(100));
        assertFalse(b.contains(100));
    }

    // ===== intersect() =====

    /**
     * Verify intersection contains exactly the shared elements.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIntersectOverlappingSets() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5, 6);

        // Act
        IntegerSet result = a.intersect(b);

        // Assert
        assertEquals(setOf(3, 4), result);
        assertEquals("[3, 4]", result.toString());
        assertEquals(setOf(1, 2, 3, 4), a);
        assertEquals(setOf(3, 4, 5, 6), b);
    }

    /**
     * Verify disjoint intersection is empty and self-intersection is unchanged.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIntersectDisjointAndSameOperand() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet disjoint = setOf(7, 8, 9);

        // Act
        IntegerSet disjointResult = a.intersect(disjoint);
        IntegerSet selfResult = a.intersect(a);

        // Assert
        assertTrue(disjointResult.isEmpty());
        assertEquals(0, disjointResult.length());
        assertEquals(setOf(1, 2, 3), selfResult);
        assertNotSame(a, selfResult);
        assertEquals(setOf(1, 2, 3), a);
        assertEquals(setOf(7, 8, 9), disjoint);
    }

    /**
     * Modify the intersection result and verify both input sets remain unchanged.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testIntersectIndependentResult() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5);

        // Act
        IntegerSet result = a.intersect(b);
        assertEquals(setOf(3, 4), result);
        result.add(50);
        result.remove(3);

        // Assert
        assertNotSame(a, result);
        assertNotSame(b, result);
        assertEquals(setOf(1, 2, 3, 4), a);
        assertEquals(setOf(3, 4, 5), b);
        assertTrue(a.contains(3));
        assertTrue(b.contains(3));
        assertFalse(a.contains(50));
        assertFalse(b.contains(50));
    }

    // ===== diff() =====

    /**
     * Verify A.diff(B) returns elements in A but not B, and distinguish direction.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testDiffDirectionMatters() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5, 6);

        // Act
        IntegerSet aMinusB = a.diff(b);
        IntegerSet bMinusA = b.diff(a);

        // Assert
        assertEquals(setOf(1, 2), aMinusB);
        assertEquals(setOf(5, 6), bMinusA);
        assertNotEquals(aMinusB, bMinusA);
    }

    /**
     * Verify difference with an empty set and difference with itself.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testDiffEmptyAndSameOperand() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet empty = new IntegerSet();

        // Act
        IntegerSet aMinusEmpty = a.diff(empty);
        IntegerSet emptyMinusA = empty.diff(a);
        IntegerSet aMinusA = a.diff(a);

        // Assert
        assertEquals(setOf(1, 2, 3), aMinusEmpty);
        assertNotSame(a, aMinusEmpty);
        assertTrue(emptyMinusA.isEmpty());
        assertTrue(aMinusA.isEmpty());
        assertEquals(setOf(1, 2, 3), a);
    }

    /**
     * Modify the difference result and verify neither input changes.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testDiffIndependentResult() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5);

        // Act
        IntegerSet result = a.diff(b);
        assertEquals(setOf(1, 2), result);
        result.add(3);
        result.add(99);
        result.remove(1);

        // Assert
        assertNotSame(a, result);
        assertNotSame(b, result);
        assertEquals(setOf(1, 2, 3, 4), a);
        assertEquals(setOf(3, 4, 5), b);
        assertFalse(a.contains(99));
        assertFalse(b.contains(99));
    }

    // ===== complement() =====

    /**
     * Verify A.complement(B) returns elements in B but not A.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testComplementReverseDifference() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5, 6);

        // Act
        IntegerSet result = a.complement(b);

        // Assert
        assertEquals(setOf(5, 6), result);
        assertEquals(b.diff(a), result);
        assertNotEquals(a.diff(b), result);
        assertEquals(setOf(1, 2, 3, 4), a);
        assertEquals(setOf(3, 4, 5, 6), b);
    }

    /**
     * Verify complement with an empty receiver and with an empty argument.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testComplementEmptyOperands() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet empty = new IntegerSet();

        // Act
        IntegerSet emptyReceiver = empty.complement(a);
        IntegerSet emptyArgument = a.complement(empty);

        // Assert
        assertEquals(setOf(1, 2, 3), emptyReceiver);
        assertNotSame(a, emptyReceiver);
        assertTrue(emptyArgument.isEmpty());
    }

    /**
     * Modify the complement result and verify neither input changes.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testComplementIndependentResult() {
        // Arrange
        IntegerSet a = setOf(1, 2);
        IntegerSet b = setOf(2, 3, 4);

        // Act
        IntegerSet result = a.complement(b);
        assertEquals(setOf(3, 4), result);
        result.add(1);
        result.add(77);
        result.remove(3);

        // Assert
        assertNotSame(a, result);
        assertNotSame(b, result);
        assertEquals(setOf(1, 2), a);
        assertEquals(setOf(2, 3, 4), b);
        assertTrue(b.contains(3));
        assertFalse(b.contains(77));
    }

    // ===== isSubsetOf() =====

    /**
     * Verify a proper subset is recognized.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsSubsetOfProperSubset() {
        // Arrange
        IntegerSet small = setOf(2, 3);
        IntegerSet big = setOf(1, 2, 3, 4);

        // Act / Assert
        assertTrue(small.isSubsetOf(big));
        assertFalse(big.isSubsetOf(small));
    }

    /**
     * Verify a set with an element missing from the other set is not a subset.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsSubsetOfNotSubset() {
        // Arrange
        IntegerSet a = setOf(1, 2, 9);
        IntegerSet b = setOf(1, 2, 3, 4);

        // Act / Assert
        assertFalse(a.isSubsetOf(b));
        assertFalse(b.isSubsetOf(a));
    }

    /**
     * Verify equal sets are subsets of each other.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsSubsetOfEqualSets() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 2, 1);

        // Act / Assert
        assertTrue(a.isSubsetOf(b));
        assertTrue(b.isSubsetOf(a));
        assertTrue(a.isSubsetOf(a));
    }

    /**
     * Verify the empty set is a subset of a nonempty set, but not vice versa.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsSubsetOfEmptyCases() {
        // Arrange
        IntegerSet empty = new IntegerSet();
        IntegerSet nonEmpty = setOf(1, 2);

        // Act / Assert
        assertTrue(empty.isSubsetOf(nonEmpty));
        assertFalse(nonEmpty.isSubsetOf(empty));
        assertTrue(empty.isSubsetOf(new IntegerSet()));
    }

    // ===== isDisjoint() =====

    /**
     * Verify disjoint sets return true.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsDisjointNoOverlap() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(4, 5, 6);

        // Act / Assert
        assertTrue(a.isDisjoint(b));
        assertTrue(b.isDisjoint(a));
    }

    /**
     * Verify overlapping sets return false.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsDisjointOverlap() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4, 5);

        // Act / Assert
        assertFalse(a.isDisjoint(b));
        assertFalse(b.isDisjoint(a));
    }

    /**
     * Verify disjointness with an empty set and with the same nonempty object.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsDisjointEmptyAndSameOperand() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet empty = new IntegerSet();

        // Act / Assert
        assertTrue(a.isDisjoint(empty));
        assertTrue(empty.isDisjoint(a));
        assertFalse(a.isDisjoint(a));
    }

    // ===== symmetricDiff() =====

    /**
     * Verify symmetric difference contains elements appearing in exactly one set.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testSymmetricDiffPartiallyOverlappingSets() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3, 4);
        IntegerSet b = setOf(3, 4, 5, 6);

        // Act
        IntegerSet ab = a.symmetricDiff(b);
        IntegerSet ba = b.symmetricDiff(a);

        // Assert
        assertEquals(setOf(1, 2, 5, 6), ab);
        assertEquals(setOf(1, 2, 5, 6), ba);
        assertFalse(ab.contains(3));
        assertFalse(ab.contains(4));
        assertEquals(setOf(1, 2, 3, 4), a);
        assertEquals(setOf(3, 4, 5, 6), b);
    }

    /**
     * Verify symmetric difference of identical sets is empty.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSymmetricDiffIdenticalSets() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 1, 2);

        // Act
        IntegerSet result = a.symmetricDiff(b);
        IntegerSet selfResult = a.symmetricDiff(a);

        // Assert
        assertTrue(result.isEmpty());
        assertTrue(selfResult.isEmpty());
        assertEquals(setOf(1, 2, 3), a);
    }

    /**
     * Verify behavior with an empty set and with disjoint sets.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSymmetricDiffEmptyAndDisjointSets() {
        // Arrange
        IntegerSet a = setOf(1, 2);
        IntegerSet b = setOf(8, 9);
        IntegerSet empty = new IntegerSet();

        // Act
        IntegerSet withEmpty = a.symmetricDiff(empty);
        IntegerSet emptyWithA = empty.symmetricDiff(a);
        IntegerSet disjoint = a.symmetricDiff(b);

        // Assert
        assertEquals(setOf(1, 2), withEmpty);
        assertEquals(setOf(1, 2), emptyWithA);
        assertNotSame(a, withEmpty);
        assertEquals(setOf(1, 2, 8, 9), disjoint);
        assertEquals(a.union(b), disjoint);
    }

    /**
     * Modify the result and verify both input sets remain unchanged.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSymmetricDiffIndependentResult() {
        // Arrange
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(3, 4);

        // Act
        IntegerSet result = a.symmetricDiff(b);
        assertEquals(setOf(1, 2, 4), result);
        result.add(3);
        result.add(60);
        result.remove(1);
        result.remove(4);

        // Assert
        assertNotSame(a, result);
        assertNotSame(b, result);
        assertEquals(setOf(1, 2, 3), a);
        assertEquals(setOf(3, 4), b);
    }

    // ===== isEmpty() =====

    /**
     * Verify a newly constructed set is empty.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsEmptyNewSet() {
        // Arrange
        IntegerSet set = new IntegerSet();

        // Act / Assert
        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
    }

    /**
     * Verify a set with an element is not empty.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsEmptyPopulatedSet() {
        // Arrange
        IntegerSet set = new IntegerSet();

        // Act
        set.add(0);

        // Assert
        assertFalse(set.isEmpty());
        assertEquals(1, set.length());
    }

    /**
     * Clear a populated set and verify it is empty.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsEmptyAfterClear() {
        // Arrange
        IntegerSet set = setOf(4, 5, 6);
        assertFalse(set.isEmpty());

        // Act
        set.clear();

        // Assert
        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
    }

    // ===== toString() =====

    /**
     * Verify elements appear in ascending numeric order regardless of insertion order.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testToStringAscendingOrder() {
        // Arrange
        IntegerSet set = setOf(30, -5, 10, 0, 2);

        // Act
        String text = set.toString();

        // Assert
        assertEquals("[-5, 0, 2, 10, 30]", text);
    }

    /**
     * Verify an empty set formats as [].
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testToStringEmptyFormatting() {
        // Arrange
        IntegerSet set = new IntegerSet();

        // Act
        String text = set.toString();

        // Assert
        assertEquals("[]", text);
    }

    /**
     * Verify comma-space formatting, brackets, and duplicate suppression.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testToStringExactSpacingAndDuplicates() {
        // Arrange
        IntegerSet set = new IntegerSet();
        set.add(3);
        set.add(1);
        set.add(3);
        set.add(2);
        set.add(1);

        // Act
        String text = set.toString();

        // Assert
        assertEquals("[1, 2, 3]", text);
        assertEquals("[7]", setOf(7, 7).toString());
    }

}
