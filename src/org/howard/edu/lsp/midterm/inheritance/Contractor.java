package org.howard.edu.lsp.midterm.inheritance;

/**
 * A contractor, paid a 15% premium on all hours worked.
 *
 * @author Maeva Innocent
 */
public class Contractor extends Employee {

    private static final double PREMIUM_MULTIPLIER = 1.15;

    /**
     * Creates a contractor.
     *
     * @param hoursWorked hours worked (nonnegative)
     * @param hourlyRate  hourly rate (nonnegative)
     */
    public Contractor(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates pay with a 15% premium on all hours.
     *
     * @return hours worked multiplied by the hourly rate and 1.15
     */
    @Override
    public double calculatePay() {
        return hoursWorked * hourlyRate * PREMIUM_MULTIPLIER;
    }
}
