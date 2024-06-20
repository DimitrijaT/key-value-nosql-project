package mk.ukim.nosql.model;


import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeoulFloating {

    /*
    date,hour,birth_year,sex,province,city,fp_num
2020-01-01,0,20,female,Seoul,Dobong-gu,19140
2020-01-01,0,20,male,Seoul,Dobong-gu,19950
2020-01-01,0,20,female,Seoul,Dongdaemun-gu,25450
     */

    @CsvBindByName(column = "date")
    private String date;

    @CsvBindByName(column = "hour")
    private Integer hour;

    @CsvBindByName(column = "birth_year")
    private Integer birthYear;

    @CsvBindByName(column = "sex")
    private String sex;

    @CsvBindByName(column = "province")
    private String province;

    @CsvBindByName(column = "city")
    private String city;

    @CsvBindByName(column = "fp_num")
    private Integer fpNum;


    @Override
    public String toString() {
        return "SeolFloating{" +
                "date='" + date + '\'' +
                ", hour=" + hour +
                ", birthYear=" + birthYear +
                ", sex='" + sex + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", fpNum=" + fpNum +
                '}';
    }
}
