package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * Represents one fully-transformed employee record, ready to be written
 * as a row of the output CSV.
 *
 * This is deliberately a separate class from {@link Employee}: an
 * Employee is raw input data, while a TransformedEmployee is the result
 * of applying the payroll business rules (see {@link PayrollCalculator})
 * to that input. Keeping them separate means neither class has to
 * represent "half computed" data.
 */
public class TransformedEmployee {

    private final int employeeId;
    private final String name;
    private final String department;
    private final BigDecimal hoursWorked;
    private final BigDecimal hourlyRate;
    private final BigDecimal grossPay;
    private final String payLevel;
    private final String employmentStatus;

    public TransformedEmployee(int employeeId, String name, String department,
                                BigDecimal hoursWorked, BigDecimal hourlyRate,
                                BigDecimal grossPay, String payLevel,
                                String employmentStatus) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
        this.grossPay = grossPay;
        this.payLevel = payLevel;
        this.employmentStatus = employmentStatus;
    }

    /**
     * Formats this record as one CSV line (no trailing newline), in the
     * order: EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,
     * PayLevel,EmploymentStatus.
     */
    public String toCsvRow() {
        return employeeId + "," +
                name + "," +
                department + "," +
                hoursWorked.toPlainString() + "," +
                hourlyRate.toPlainString() + "," +
                grossPay.toPlainString() + "," +
                payLevel + "," +
                employmentStatus;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public BigDecimal getHoursWorked() {
        return hoursWorked;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public BigDecimal getGrossPay() {
        return grossPay;
    }

    public String getPayLevel() {
        return payLevel;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }
}
