package mk.ukim.nosql.model;


import com.basho.riak.client.api.annotations.RiakKey;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.nosql.util.CustomDoubleConverterUtil;
import redis.clients.jedis.search.Document;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Case implements Serializable {

    /* case_id,province,city,group,infection_case,confirmed,latitude,longitude
1000001,Seoul,Yongsan-gu,TRUE,Itaewon Clubs,139,37.538621,126.992652
1000002,Seoul,Gwanak-gu,TRUE,Richway,119,37.48208,126.901384
1000003,Seoul,Guro-gu,TRUE,Guro-gu Call Center,95,37.508163,126.884387
1000004,Seoul,Yangcheon-gu,TRUE,Yangcheon Table Tennis Club,43,37.546061,126.874209
*/


    @RiakKey
    @CsvBindByName(column = "case_id")
    private String id_s;


    @CsvBindByName(column = "province")
    private String province_s;


    @CsvBindByName(column = "city")
    private String city_s;


    @CsvBindByName(column = "group")
    private Boolean group_b;


    @CsvBindByName(column = "infection_case")
    private String infectionCase_s;

    @CsvBindByName(column = "confirmed")
    private Integer confirmed_i;

    @CsvCustomBindByName(column = "latitude", converter = CustomDoubleConverterUtil.class)
    private Double latitude_d;

    @CsvCustomBindByName(column = "longitude", converter = CustomDoubleConverterUtil.class)
    private Double longitude_d;

    public static Case createCaseFromRedisDocument(Document doc) {
        Case c = new Case();
        c.setId_s(doc.getString("id"));
        c.setProvince_s(doc.getString("province"));
        c.setCity_s(doc.getString("city"));
        c.setGroup_b(Boolean.parseBoolean(doc.getString("group")));
        c.setConfirmed_i(Integer.parseInt(doc.getString("confirmed")));
        c.setInfectionCase_s(doc.getString("infectionCase"));
        c.setLatitude_d(Double.parseDouble(doc.getString("latitude")));
        c.setLongitude_d(Double.parseDouble(doc.getString("longitude")));
        return c;
    }


    @Override
    public String toString() {
        return "Case{" + "id=" + id_s + ", province='" + province_s + '\'' + ", city='" + city_s + '\'' + ", group=" + group_b + ", infectionCase='" + infectionCase_s + '\'' + ", confirmed=" + confirmed_i + ", latitude=" + latitude_d + ", longitude=" + longitude_d + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Case aCase = (Case) o;
        return Objects.equals(id_s, aCase.id_s) && Objects.equals(province_s, aCase.province_s) && Objects.equals(city_s, aCase.city_s) && Objects.equals(group_b, aCase.group_b) && Objects.equals(infectionCase_s, aCase.infectionCase_s) && Objects.equals(confirmed_i, aCase.confirmed_i) && Objects.equals(latitude_d, aCase.latitude_d) && Objects.equals(longitude_d, aCase.longitude_d);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_s, province_s, city_s, group_b, infectionCase_s, confirmed_i, latitude_d, longitude_d);
    }
}
