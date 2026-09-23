package org.howard.edu.lsp.assignment3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Writes a list of {@link TransformedEmployee} records to a CSV file
 * (the "Load" step of the ETL pipeline).
 *
 * This class knows nothing about payroll rules or validation -- it only
 * knows how to format a header and rows, and write them to disk.
 */
public class EmployeeCsvWriter {

    private static final String HEADER =
            "EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus";

    public void writeEmployees(Path outputPath, List<TransformedEmployee> employees) throws IOException {

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {

            writer.write(HEADER);
            writer.newLine();

            for (TransformedEmployee employee : employees) {
                writer.write(employee.toCsvRow());
                writer.newLine();
            }
        }
    }
}
