package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * Represents a single validated employee record read from the input CSV
 * file, before any payroll calculations are applied.
 *
 * An Employee only holds raw data (identity + hours/rate). It knows
 * nothing about pay rules, pay levels, or employment status -- those
 * are the responsibility of {@link PayrollCalculator}.
 */
public class Employee {

    private final int employeeId;
    private final String name;
    private final String department;
    private final BigDecimal hoursWorked;
    private final BigDecimal hourlyRate;

    public Employee(int employeeId, String name, String department,
                     BigDecimal hoursWorked, BigDecimal hourlyRate) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
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
}
