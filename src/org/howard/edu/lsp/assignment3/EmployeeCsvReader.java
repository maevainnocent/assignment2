package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads an employees CSV file (the "Extract" step of the ETL pipeline)
 * and produces a list of valid {@link Employee} objects.
 *
 * The reader is only responsible for file I/O and for keeping track of
 * how many rows were read/skipped. It delegates the question of
 * "is this row valid" entirely to {@link EmployeeParser}, so this class
 * doesn't need to change if the validation rules ever do.
 */
public class EmployeeCsvReader {

    private final EmployeeParser parser;
    private int rowsRead;
    private int rowsSkipped;

    public EmployeeCsvReader() {
        this.parser = new EmployeeParser();
    }

    /**
     * Reads every data row (i.e. every line after the header) from the
     * given CSV file and returns the Employees that parsed successfully.
     * Rows that fail to parse are skipped and counted, not thrown out of
     * this method.
     *
     * @param inputPath path to the input CSV file
     * @return the list of successfully parsed employees, in file order
     * @throws IOException if the file cannot be read
     */
    public List<Employee> readEmployees(Path inputPath) throws IOException {

        List<Employee> employees = new ArrayList<>();
        rowsRead = 0;
        rowsSkipped = 0;

        try (BufferedReader reader = Files.newBufferedReader(inputPath)) {

            reader.readLine(); // header

            String line;
            while ((line = reader.readLine()) != null) {

                rowsRead++;

                try {
                    employees.add(parser.parse(line));
                } catch (InvalidEmployeeRecordException e) {
                    rowsSkipped++;
                }
            }
        }

        return employees;
    }

    public int getRowsRead() {
        return rowsRead;
    }

    public int getRowsSkipped() {
        return rowsSkipped;
    }
}
