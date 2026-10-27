package org.howard.edu.lsp.midterm.crccards;

/**
 * Represents a single book in the library lending system. A Book stores its
 * identifying information and owns its own availability state, which can only
 * be changed through {@link #borrowBook()} and {@link #returnBook()}.
 *
 * @author Maeva Innocent
 */
public class Book {

    private final String isbn;
    private final String title;
    private final String author;
    private boolean available;

    /**
     * Creates a new book. Every new book starts out available.
     *
     * @param isbn   the unique ISBN of the book
     * @param title  the title of the book
     * @param author the author of the book
     */
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    /**
     * Returns the ISBN of this book.
     *
     * @return the ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Returns the title of this book.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author of this book.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Indicates whether this book is currently available to borrow.
     *
     * @return {@code true} if available, {@code false} if borrowed
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Marks this book as borrowed.
     *
     * @throws IllegalStateException if the book is already borrowed
     */
    public void borrowBook() {
        if (!available) {
            throw new IllegalStateException("Book " + isbn + " is already borrowed.");
        }
        available = false;
    }

    /**
     * Marks this book as returned (available).
     *
     * @throws IllegalStateException if the book is not currently borrowed
     */
    public void returnBook() {
        if (available) {
            throw new IllegalStateException("Book " + isbn + " is not currently borrowed.");
        }
        available = true;
    }

    /**
     * Returns a description such as
     * {@code B101 Effective Java by Joshua Bloch [AVAILABLE]}.
     * The status is {@code [BORROWED]} when the book is unavailable.
     *
     * @return the formatted book description
     */
    @Override
    public String toString() {
        return isbn + " " + title + " by " + author
                + (available ? " [AVAILABLE]" : " [BORROWED]");
    }
}
