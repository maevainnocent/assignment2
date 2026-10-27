package org.howard.edu.lsp.midterm.inheritance;

/**
 * Abstract base class for all employee types. It holds the data common to
 * every employee and declares {@link #calculatePay()}, which each subclass
 * overrides with its own pay rule. Callers work with {@code Employee}
 * references and never need to test the employee type.
 *
 * @author Maeva Innocent
 */
public abstract class Employee {

    /** Hours worked in the pay period. */
    protected final double hoursWorked;

    /** Hourly pay rate. */
    protected final double hourlyRate;

    /**
     * Creates an employee.
     *
     * @param hoursWorked hours worked (nonnegative)
     * @param hourlyRate  hourly rate (nonnegative)
     */
    protected Employee(double hoursWorked, double hourlyRate) {
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    /**
     * Calculates this employee's pay according to the employee type's rule.
     *
     * @return the pay amount
     */
    public abstract double calculatePay();
}
