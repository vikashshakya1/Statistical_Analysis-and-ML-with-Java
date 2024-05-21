package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataProcessing {

    public static void main(String[] args) {
        String inputFile = "src\\main\\resources\\Financials.csv";
        String outputFile = "src/main/resources/output.csv";

        try {
            List<String[]> data = readCSV(inputFile);
            if (data != null) {
                System.out.println("Original Data:");
                printData(data);

                // Count null values before cleaning
                int nullCountBefore = countNullValues(data);
                System.out.println("Null values before cleaning: " + nullCountBefore);

                cleanAndProcessData(data);

                // Count null values after cleaning
                int nullCountAfter = countNullValues(data);
                System.out.println("Null values after cleaning: " + nullCountAfter);

                System.out.println("Cleaned and Processed Data:");
                printData(data);

                writeCSV(data, outputFile);
                System.out.println("Cleaned and Processed Data saved to: " + outputFile);
            } else {
                System.out.println("Failed to read input CSV file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> readCSV(String inputFile) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        }
        return data;
    }

    private static void cleanAndProcessData(List<String[]> data) {
        removeNullValues(data);
        removeDuplicates(data);
        standardizeNumericData(data);
        // Add more data cleaning and processing tasks as needed
    }

    private static void removeNullValues(List<String[]> data) {
        data.removeIf(row -> {
            for (String value : row) {
                if (value == null || value.isEmpty()) {
                    return true;
                }
            }
            return false;
        });
    }

    private static void removeDuplicates(List<String[]> data) {
        Set<String> uniqueRows = new HashSet<>();
        data.removeIf(row -> !uniqueRows.add(String.join(",", row)));
    }

    private static void standardizeNumericData(List<String[]> data) {
        // Assuming the first row contains headers
        for (int col = 0; col < data.get(0).length; col++) {
            if (isNumericColumn(data, col)) {
                List<Double> numericValues = extractNumericValues(data, col);
                double mean = calculateMean(numericValues);
                double stdDev = calculateStandardDeviation(numericValues, mean);
                for (String[] row : data) {
                    try {
                        double value = Double.parseDouble(row[col]);
                        double standardizedValue = (value - mean) / stdDev;
                        row[col] = String.valueOf(standardizedValue);
                    } catch (NumberFormatException ignored) {
                        // Ignore non-numeric values
                    }
                }
            }
        }
    }

    private static boolean isNumericColumn(List<String[]> data, int col) {
        for (String[] row : data) {
            try {
                Double.parseDouble(row[col]);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private static List<Double> extractNumericValues(List<String[]> data, int col) {
        List<Double> numericValues = new ArrayList<>();
        for (String[] row : data) {
            try {
                numericValues.add(Double.parseDouble(row[col]));
            } catch (NumberFormatException ignored) {
                // Ignore non-numeric values
            }
        }
        return numericValues;
    }

    private static double calculateMean(List<Double> values) {
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    private static double calculateStandardDeviation(List<Double> values, double mean) {
        double sumSquaredDiff = 0.0;
        for (double value : values) {
            sumSquaredDiff += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sumSquaredDiff / values.size());
    }

    private static void writeCSV(List<String[]> data, String outputFile) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            for (String[] row : data) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        }
    }

    private static void printData(List<String[]> data) {
        for (String[] row : data) {
            for (String value : row) {
                System.out.print(value + "\t");
            }
            System.out.println();
        }
    }

    private static int countNullValues(List<String[]> data) {
        int nullCount = 0;
        for (String[] row : data) {
            for (String value : row) {
                if (value == null || value.isEmpty()) {
                    nullCount++;
                }
            }
        }
        return nullCount;
    }
}
