package mk.ukim.nosql;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Printer {
    private static final String CSV_FILE_PATH = "src/main/resources/PerformanceMetrics.csv";

    public static void logPerformance(String dbType, long writeOneDataTime, long writeAllDataTime, long readOneDataTime, long readAllDataTime, int Size) {
        // add header if empty
        addHeader();

        try (FileWriter writer = new FileWriter(CSV_FILE_PATH, true)) {
            writer.append(dbType)
                    .append(',')
                    .append(String.valueOf(writeOneDataTime))
                    .append(',')
                    .append(String.valueOf(writeAllDataTime))
                    .append(',')
                    .append(String.valueOf(readOneDataTime))
                    .append(',')
                    .append(String.valueOf(readAllDataTime))
                    .append(',')
                    .append(String.valueOf(Size))
                    .append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addHeader() {
        if (!new File(CSV_FILE_PATH).exists()) {
            try (FileWriter writer = new FileWriter(CSV_FILE_PATH, true)) {
                writer.append("DB Type")
                        .append(',')
                        .append("Write One Data Time")
                        .append(',')
                        .append("Write All Data Time")
                        .append(',')
                        .append("Read One Data Time")
                        .append(',')
                        .append("Read All Data Time")
                        .append(',')
                        .append("Size")
                        .append('\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}