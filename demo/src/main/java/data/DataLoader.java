package data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import util.SalesData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    public static List<SalesData> loadSalesData(String filePath) {
        List<SalesData> salesDataList = new ArrayList<>();

        try (InputStream inputStream = DataLoader.class.getClassLoader().getResourceAsStream(filePath);
             InputStreamReader reader = new InputStreamReader(inputStream);
             CSVParser csvParser = CSVParser.parse(reader, CSVFormat.RFC4180.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                boolean containsNoStats = false;
                for (String value : record) {
                    if (value.equalsIgnoreCase("No stats")) {
                        containsNoStats = true;
                        break;
                    }
                }
                if (!containsNoStats) {
                    // Parse CSV records and create SalesData objects
                    int segment = parseInteger(record.get("Segment"));
                    String country = record.get("Country");
                    String product = record.get("Product");
                    String discountBand = record.get("Discount Band");
                    double unitsSold = parseDouble(record.get("UnitsSold"));
                    double manufacturingPrice = parseDouble(record.get("Manufacturing Price"));
                    double salePrice = parseDouble(record.get("Sale Price"));
                    double grossSales = parseDouble(record.get("GrossSales"));
                    double discounts = parseDouble(record.get("Discounts"));
                    double sales = parseDouble(record.get("Sales"));
                    double cogs = parseDouble(record.get("COGS"));
                    double profit = parseDouble(record.get("Profit"));
                    String date = record.get("Date");
                    int monthNumber = parseInteger(record.get("Month Number"));
                    String monthName = record.get("Month Name");
                    int year = parseInteger(record.get("Year"));
                    double profit1 = parseDouble(record.get("Profit1"));

                    // Create SalesData object and add to list
                    SalesData salesData = new SalesData(segment, country, product, discountBand, unitsSold,
                            manufacturingPrice, salePrice, grossSales, discounts, sales, cogs, profit,
                            date, monthNumber, monthName, year, profit1);
                    salesDataList.add(salesData);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred while loading sales data");
        }

        return salesDataList;
    }

    private static int parseInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            // Handle invalid integer values
            return 0; // Or any default value you prefer
        }
    }

    private static double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            // Handle invalid double values
            return 0.0; // Or any default value you prefer
        }
    }
}
