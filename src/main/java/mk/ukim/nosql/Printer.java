package mk.ukim.nosql;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Printer {
    private static final String CSV_FILE_PATH = "src/main/resources/PerformanceMetrics.csv";

    public static void logPerformance(String dbType, HashMap<String, Long> performance, int Size, String entityName) {
        // add header if empty
        if (!new File(CSV_FILE_PATH).exists() || new File(CSV_FILE_PATH).length() <= 10) {
            addHeader(performance);
        }

        try (FileWriter writer = new FileWriter(CSV_FILE_PATH, true)) {
            writer.append(dbType);

            for (Map.Entry<String, Long> entry : performance.entrySet()) {
                writer.append(',')
                        .append(String.valueOf(entry.getValue()));
            }

            writer.append(',')
                    .append(String.valueOf(Size))
                    .append(',')
                    .append(entityName)
                    .append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addHeader(HashMap<String, Long> performance) {
        if (!new File(CSV_FILE_PATH).exists()) {
            try (FileWriter writer = new FileWriter(CSV_FILE_PATH, true)) {
                writer.append("DB Type");

                for (Map.Entry<String, Long> entry : performance.entrySet()) {
                    writer.append(',')
                            .append(entry.getKey());
                }

                writer.append(',')
                        .append("Size")
                        .append(',')
                        .append("Entity")
                        .append('\n');

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}