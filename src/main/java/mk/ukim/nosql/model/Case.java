package mk.ukim.nosql.model;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.nosql.util.CustomDoubleConverterUtil;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Case {

    /* case_id,province,city,group,infection_case,confirmed,latitude,longitude
1000001,Seoul,Yongsan-gu,TRUE,Itaewon Clubs,139,37.538621,126.992652
1000002,Seoul,Gwanak-gu,TRUE,Richway,119,37.48208,126.901384
1000003,Seoul,Guro-gu,TRUE,Guro-gu Call Center,95,37.508163,126.884387
1000004,Seoul,Yangcheon-gu,TRUE,Yangcheon Table Tennis Club,43,37.546061,126.874209
*/

    @CsvBindByName(column = "case_id")
    private Long id;
    @CsvBindByName(column = "province")
    private String province;
    @CsvBindByName(column = "city")
    private String city;
    @CsvBindByName(column = "group")
    private Boolean group;
    @CsvBindByName(column = "infection_case")
    private String infectionCase;
    @CsvBindByName(column = "confirmed")
    private Integer confirmed;
    @CsvCustomBindByName(column = "latitude", converter = CustomDoubleConverterUtil.class)
    private Double latitude;
    @CsvCustomBindByName(column = "longitude", converter = CustomDoubleConverterUtil.class)
    private Double longitude;


    @Override
    public String toString() {
        return "Case{" + "id=" + id + ", province='" + province + '\'' + ", city='" + city + '\'' + ", group=" + group + ", infectionCase='" + infectionCase + '\'' + ", confirmed=" + confirmed + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }
}
