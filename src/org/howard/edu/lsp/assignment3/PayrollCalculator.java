package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

/**
 * Applies the payroll business rules (the "Transform" step of the ETL
 * pipeline) to a validated {@link Employee}, producing a
 * {@link TransformedEmployee}.
 *
 * This class is where every payroll rule lives: overtime pay, the IT
 * department bonus, pay-level classification, and employment-status
 * classification. In Assignment #2 these rules were interleaved with
 * file-reading and CSV-formatting code inside one long method; here
 * they are isolated so they can be read, tested, or changed without
 * touching how files are read or written.
 */
public class PayrollCalculator {

    private static final BigDecimal REGULAR_HOURS_LIMIT = new BigDecimal("40.00");
    private static final BigDecimal OVERTIME_MULTIPLIER = new BigDecimal("1.5");

    private static final String IT_DEPARTMENT = "IT";
    private static final BigDecimal IT_BONUS_MULTIPLIER = new BigDecimal("1.05");

    private static final BigDecimal LOW_PAY_LEVEL_MAX = new BigDecimal("500.00");
    private static final BigDecimal STANDARD_PAY_LEVEL_MAX = new BigDecimal("1000.00");
    private static final BigDecimal HIGH_PAY_LEVEL_MAX = new BigDecimal("2000.00");

    private static final BigDecimal PART_TIME_HOURS_LIMIT = new BigDecimal("30.00");

    /**
     * Applies all payroll rules to the given employee and returns the
     * resulting transformed record.
     */
    public TransformedEmployee process(Employee employee) {

        BigDecimal grossPay = calculateGrossPay(employee);
        String payLevel = determinePayLevel(grossPay);
        String employmentStatus = determineEmploymentStatus(employee.getHoursWorked());

        return new TransformedEmployee(
                employee.getEmployeeId(),
                employee.getName().toUpperCase(Locale.US),
                employee.getDepartment(),
                employee.getHoursWorked().setScale(2, RoundingMode.HALF_UP),
                employee.getHourlyRate().setScale(2, RoundingMode.HALF_UP),
                grossPay,
                payLevel,
                employmentStatus
        );
    }

    /**
     * Computes gross pay: time-and-a-half for any hours over 40, then a
     * 5% bonus for employees in the IT department, rounded to 2 decimal
     * places (HALF_UP).
     */
    private BigDecimal calculateGrossPay(Employee employee) {

        BigDecimal hoursWorked = employee.getHoursWorked();
        BigDecimal hourlyRate = employee.getHourlyRate();
        BigDecimal grossPay;

        if (hoursWorked.compareTo(REGULAR_HOURS_LIMIT) <= 0) {

            grossPay = hoursWorked.multiply(hourlyRate);

        } else {

            BigDecimal regularPay = REGULAR_HOURS_LIMIT.multiply(hourlyRate);
            BigDecimal overtimeHours = hoursWorked.subtract(REGULAR_HOURS_LIMIT);
            BigDecimal overtimeRate = hourlyRate.multiply(OVERTIME_MULTIPLIER);
            BigDecimal overtimePay = overtimeHours.multiply(overtimeRate);

            grossPay = regularPay.add(overtimePay);
        }

        if (employee.getDepartment().equals(IT_DEPARTMENT)) {
            grossPay = grossPay.multiply(IT_BONUS_MULTIPLIER);
        }

        return grossPay.setScale(2, RoundingMode.HALF_UP);
    }

    /** Classifies gross pay into Low / Standard / High / Executive. */
    private String determinePayLevel(BigDecimal grossPay) {

        if (grossPay.compareTo(LOW_PAY_LEVEL_MAX) < 0) {
            return "Low";
        } else if (grossPay.compareTo(STANDARD_PAY_LEVEL_MAX) < 0) {
            return "Standard";
        } else if (grossPay.compareTo(HIGH_PAY_LEVEL_MAX) < 0) {
            return "High";
        } else {
            return "Executive";
        }
    }

    /** Classifies employment status as Part-Time (&lt;30 hrs) or Full-Time. */
    private String determineEmploymentStatus(BigDecimal hoursWorked) {

        if (hoursWorked.compareTo(PART_TIME_HOURS_LIMIT) < 0) {
            return "Part-Time";
        } else {
            return "Full-Time";
        }
    }
}
