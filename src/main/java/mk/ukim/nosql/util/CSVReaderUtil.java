package mk.ukim.nosql.util;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import mk.ukim.nosql.model.Case;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;

public class CSVReaderUtil {
    public static List<Case> readCSV(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            CsvToBean<Case> csvToBean = new CsvToBeanBuilder<Case>(reader)
                    .withType(Case.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
