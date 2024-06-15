package mk.ukim.nosql.util;

import com.opencsv.bean.AbstractBeanField;

public class CustomDoubleConverterUtil extends AbstractBeanField<Double> {
    @Override
    protected Double convert(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            // Handle invalid value, e.g., return null or a default value
            return null; // or return 0.0 or any other default value
        }
    }
}

