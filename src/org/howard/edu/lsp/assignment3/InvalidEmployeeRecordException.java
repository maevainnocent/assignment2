package org.howard.edu.lsp.assignment3;

/**
 * Thrown by {@link EmployeeParser} when a line from the input CSV
 * cannot be turned into a valid {@link Employee} (wrong number of
 * fields, non-numeric values, or negative hours/rate).
 *
 * Using an exception here -- instead of magic return values like
 * {@code null} -- makes "this row was invalid" an explicit part of
 * the parser's contract, and lets {@link EmployeeCsvReader} decide
 * how to react (skip the row and keep counting).
 */
public class InvalidEmployeeRecordException extends Exception {

    public InvalidEmployeeRecordException(String message) {
        super(message);
    }
}
