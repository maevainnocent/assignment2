package org.howard.edu.lsp.midterm.inheritance;

/**
 * A full-time employee. Hours up to 40 are paid at the hourly rate; hours
 * beyond 40 are paid at 1.5 times the hourly rate.
 *
 * @author Maeva Innocent
 */
public class FullTimeEmployee extends Employee {

    private static final double OVERTIME_THRESHOLD = 40.0;
    private static final double OVERTIME_MULTIPLIER = 1.5;

    /**
     * Creates a full-time employee.
     *
     * @param hoursWorked hours worked (nonnegative)
     * @param hourlyRate  hourly rate (nonnegative)
     */
    public FullTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates pay with overtime at 1.5 times the rate beyond 40 hours.
     *
     * @return the total pay
     */
    @Override
    public double calculatePay() {
        double regularHours = Math.min(hoursWorked, OVERTIME_THRESHOLD);
        double overtimeHours = Math.max(0.0, hoursWorked - OVERTIME_THRESHOLD);
        return regularHours * hourlyRate + overtimeHours * hourlyRate * OVERTIME_MULTIPLIER;
    }
}
