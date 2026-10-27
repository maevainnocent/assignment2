package org.howard.edu.lsp.midterm.crccards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A library that maintains a collection of {@link Book} objects and
 * coordinates borrowing and returning. The Library enforces a limit of three
 * simultaneously borrowed books, but delegates each book's state change to the
 * Book itself.
 *
 * <p>Books are stored in a {@link TreeMap} keyed by ISBN. The map gives
 * efficient lookup by ISBN, makes duplicate detection a single key check, and
 * keeps keys in ascending String order so sorted retrieval needs no extra
 * sorting step.</p>
 *
 * @author Maeva Innocent
 */
public class Library {

    /** Maximum number of books that may be borrowed at one time. */
    private static final int BORROW_LIMIT = 3;

    private final Map<String, Book> books = new TreeMap<>();

    /**
     * Adds a book to the library.
     *
     * @param book the book to add
     * @throws IllegalArgumentException if a book with the same ISBN already
     *                                  exists (the original book is preserved)
     */
    public void addBook(Book book) {
        if (books.containsKey(book.getIsbn())) {
            throw new IllegalArgumentException("Duplicate ISBN: " + book.getIsbn());
        }
        books.put(book.getIsbn(), book);
    }

    /**
     * Finds a book by ISBN.
     *
     * @param isbn the ISBN to look up
     * @return the matching Book instance, or {@code null} if absent
     */
    public Book findBook(String isbn) {
        return books.get(isbn);
    }

    /**
     * Borrows the book with the given ISBN.
     *
     * @param isbn the ISBN of the book to borrow
     * @throws IllegalArgumentException if no book has that ISBN
     * @throws IllegalStateException    if the book is already borrowed or the
     *                                  three-book limit has been reached
     */
    public void borrowBook(String isbn) {
        Book book = requireBook(isbn);
        if (getBorrowedBookCount() >= BORROW_LIMIT) {
            throw new IllegalStateException("Borrowing limit of " + BORROW_LIMIT + " reached.");
        }
        book.borrowBook();
    }

    /**
     * Returns the book with the given ISBN to the library.
     *
     * @param isbn the ISBN of the book to return
     * @throws IllegalArgumentException if no book has that ISBN
     * @throws IllegalStateException    if the book is not currently borrowed
     */
    public void returnBook(String isbn) {
        requireBook(isbn).returnBook();
    }

    /**
     * Returns all books that are currently available.
     *
     * @return a new list of available books; empty if none
     */
    public List<Book> getAvailableBooks() {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.isAvailable()) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Returns all books by the given author, including borrowed books. The
     * comparison is exact and case-sensitive.
     *
     * @param author the author to match
     * @return a new list of matching books; empty if none
     */
    public List<Book> getBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().equals(author)) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Returns all books sorted by ISBN in ascending lexicographical order,
     * regardless of insertion order or borrowing status. This method does not
     * modify the library or any book.
     *
     * @return a new list of all books in ISBN order; empty if the library is empty
     */
    public List<Book> getBooksSortedByIsbn() {
        return new ArrayList<>(books.values());
    }

    /**
     * Returns the number of books currently borrowed.
     *
     * @return the borrowed book count
     */
    public int getBorrowedBookCount() {
        int count = 0;
        for (Book book : books.values()) {
            if (!book.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Looks up a book or fails.
     *
     * @param isbn the ISBN to look up
     * @return the matching book
     * @throws IllegalArgumentException if no book has that ISBN
     */
    private Book requireBook(String isbn) {
        Book book = books.get(isbn);
        if (book == null) {
            throw new IllegalArgumentException("No book with ISBN: " + isbn);
        }
        return book;
    }
}
