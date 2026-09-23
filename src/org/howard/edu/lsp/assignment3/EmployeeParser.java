package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * Turns one raw CSV line from the input file into an {@link Employee}.
 *
 * All the "is this row valid" logic from the original single-file
 * pipeline lives here now: correct field count, numeric ID/hours/rate,
 * and non-negative hours/rate. Centralizing it means the validation
 * rules are defined in exactly one place, and {@link EmployeeCsvReader}
 * doesn't need to know the details of what makes a row valid.
 */
public class EmployeeParser {

    private static final int EXPECTED_FIELD_COUNT = 5;

    /**
     * Parses one CSV line (without the trailing newline) into an Employee.
     *
     * @param line a single data line from employees.csv
     * @return the parsed Employee
     * @throws InvalidEmployeeRecordException if the line is blank, has the
     *         wrong number of fields, has non-numeric ID/hours/rate, or has
     *         negative hours or rate
     */
    public Employee parse(String line) throws InvalidEmployeeRecordException {

        if (line.trim().isEmpty()) {
            throw new InvalidEmployeeRecordException("Blank line");
        }

        String[] fields = line.split(",", -1);

        if (fields.length != EXPECTED_FIELD_COUNT) {
            throw new InvalidEmployeeRecordException(
                "Expected " + EXPECTED_FIELD_COUNT + " fields but found " + fields.length);
        }

        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].trim();
        }

        int employeeId;
        BigDecimal hoursWorked;
        BigDecimal hourlyRate;

        try {
            employeeId = Integer.parseInt(fields[0]);
            hoursWorked = new BigDecimal(fields[3]);
            hourlyRate = new BigDecimal(fields[4]);
        } catch (NumberFormatException e) {
            throw new InvalidEmployeeRecordException(
                "Non-numeric EmployeeID, HoursWorked, or HourlyRate: " + e.getMessage());
        }

        if (hoursWorked.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidEmployeeRecordException("HoursWorked cannot be negative");
        }

        if (hourlyRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidEmployeeRecordException("HourlyRate cannot be negative");
        }

        String name = fields[1];
        String department = fields[2];

        return new Employee(employeeId, name, department, hoursWorked, hourlyRate);
    }
}
