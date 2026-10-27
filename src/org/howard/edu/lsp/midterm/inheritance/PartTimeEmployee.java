package org.howard.edu.lsp.midterm.inheritance;

/**
 * A part-time employee, paid straight hourly pay for all hours worked.
 *
 * @author Maeva Innocent
 */
public class PartTimeEmployee extends Employee {

    /**
     * Creates a part-time employee.
     *
     * @param hoursWorked hours worked (nonnegative)
     * @param hourlyRate  hourly rate (nonnegative)
     */
    public PartTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates straight hourly pay.
     *
     * @return hours worked multiplied by the hourly rate
     */
    @Override
    public double calculatePay() {
        return hoursWorked * hourlyRate;
    }
}
