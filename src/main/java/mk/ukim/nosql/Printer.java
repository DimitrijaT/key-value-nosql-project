package mk.ukim.nosql;

import java.io.FileWriter;
import java.io.IOException;

public class Printer {
    private static final String CSV_FILE_PATH = "src/main/resources/performance_metrics.csv";

    public static void logPerformance(String dbType, long writeOneDataTime, long writeAllDataTime, long readOneDataTime, long readAllDataTime, int Size) {
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
}