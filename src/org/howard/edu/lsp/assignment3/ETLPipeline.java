package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for the Assignment #3 Employee Payroll ETL Pipeline.
 *
 * This class only orchestrates the three ETL steps -- Extract
 * ({@link EmployeeCsvReader}), Transform ({@link PayrollCalculator}),
 * and Load ({@link EmployeeCsvWriter}) -- and reports the summary
 * counts. All of the file-format, validation, and payroll-rule details
 * live in those collaborator classes, not here.
 */
public class ETLPipeline {

    public static void main(String[] args) {

        Path inputPath = Paths.get("data", "employees.csv");
        Path outputPath = Paths.get("data", "transformed_employees.csv");

        EmployeeCsvReader reader = new EmployeeCsvReader();
        PayrollCalculator calculator = new PayrollCalculator();
        EmployeeCsvWriter writer = new EmployeeCsvWriter();

        try {
            List<Employee> employees = reader.readEmployees(inputPath);

            List<TransformedEmployee> transformedEmployees = new ArrayList<>();
            for (Employee employee : employees) {
                transformedEmployees.add(calculator.process(employee));
            }

            writer.writeEmployees(outputPath, transformedEmployees);

            System.out.println("Rows read: " + reader.getRowsRead());
            System.out.println("Rows transformed: " + transformedEmployees.size());
            System.out.println("Rows skipped: " + reader.getRowsSkipped());
            System.out.println("Output file: " + outputPath);

        } catch (IOException e) {
            System.out.println("Error processing files: " + e.getMessage());
        }
    }
}
