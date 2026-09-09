package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class ETLPipeline {

    public static void main(String[] args) {

        Path inputPath = Paths.get("data", "employees.csv");
        Path outputPath = Paths.get("data", "transformed_employees.csv");

        int rowsRead = 0;
        int rowsTransformed = 0;
        int rowsSkipped = 0;

        try {
            BufferedReader reader = Files.newBufferedReader(inputPath);
            BufferedWriter writer = Files.newBufferedWriter(outputPath);

            writer.write(
                "EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus"
            );
            writer.newLine();

            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                rowsRead++;

                if (line.trim().isEmpty()) {
                    rowsSkipped++;
                    continue;
                }

                String[] fields = line.split(",", -1);

                if (fields.length != 5) {
                    rowsSkipped++;
                    continue;
                }

                for (int i = 0; i < fields.length; i++) {
                    fields[i] = fields[i].trim();
                }

                int employeeID;
                BigDecimal hoursWorked;
                BigDecimal hourlyRate;

                try {
                    employeeID = Integer.parseInt(fields[0]);
                    hoursWorked = new BigDecimal(fields[3]);
                    hourlyRate = new BigDecimal(fields[4]);
                } catch (NumberFormatException e) {
                    rowsSkipped++;
                    continue;
                }

                if (hoursWorked.compareTo(BigDecimal.ZERO) < 0) {
                    rowsSkipped++;
                    continue;
                }

                if (hourlyRate.compareTo(BigDecimal.ZERO) < 0) {
                    rowsSkipped++;
                    continue;
                }

                String name = fields[1].toUpperCase(Locale.US);
                String department = fields[2];

                BigDecimal grossPay;
                BigDecimal forty = new BigDecimal("40.00");

                if (hoursWorked.compareTo(forty) <= 0) {

                    grossPay = hoursWorked.multiply(hourlyRate);

                } else {

                    BigDecimal regularPay =
                            forty.multiply(hourlyRate);

                    BigDecimal overtimeHours =
                            hoursWorked.subtract(forty);

                    BigDecimal overtimeRate =
                            hourlyRate.multiply(new BigDecimal("1.5"));

                    BigDecimal overtimePay =
                            overtimeHours.multiply(overtimeRate);

                    grossPay = regularPay.add(overtimePay);
                }

                if (department.equals("IT")) {
                    grossPay =
                            grossPay.multiply(new BigDecimal("1.05"));
                }

                grossPay =
                        grossPay.setScale(2, RoundingMode.HALF_UP);

                String payLevel;

                if (grossPay.compareTo(new BigDecimal("500.00")) < 0) {
                    payLevel = "Low";
                } else if (grossPay.compareTo(new BigDecimal("1000.00")) < 0) {
                    payLevel = "Standard";
                } else if (grossPay.compareTo(new BigDecimal("2000.00")) < 0) {
                    payLevel = "High";
                } else {
                    payLevel = "Executive";
                }

                String employmentStatus;

                if (hoursWorked.compareTo(new BigDecimal("30.00")) < 0) {
                    employmentStatus = "Part-Time";
                } else {
                    employmentStatus = "Full-Time";
                }

                writer.write(
                    employeeID + "," +
                    name + "," +
                    department + "," +
                    hoursWorked.setScale(2, RoundingMode.HALF_UP)
                        .toPlainString() + "," +
                    hourlyRate.setScale(2, RoundingMode.HALF_UP)
                        .toPlainString() + "," +
                    grossPay.toPlainString() + "," +
                    payLevel + "," +
                    employmentStatus
                );

                writer.newLine();

                rowsTransformed++;
            }

            reader.close();
            writer.close();

            System.out.println("Rows read: " + rowsRead);
            System.out.println("Rows transformed: " + rowsTransformed);
            System.out.println("Rows skipped: " + rowsSkipped);
            System.out.println("Output file: " + outputPath);

        } catch (IOException e) {
            System.out.println("Error processing files: " + e.getMessage());
        }
    }
}