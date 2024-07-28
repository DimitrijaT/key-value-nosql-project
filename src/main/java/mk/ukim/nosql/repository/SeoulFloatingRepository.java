package mk.ukim.nosql.repository;

import mk.ukim.nosql.model.SeoulFloating;

import java.util.List;

public interface SeoulFloatingRepository {

    // crud for
    /*
    date,hour,birth_year,sex,province,city,fp_num
2020-01-01,0,20,female,Seoul,Dobong-gu,19140
2020-01-01,0,20,male,Seoul,Dobong-gu,19950
2020-01-01,0,20,female,Seoul,Dongdaemun-gu,25450

     */

    void saveSeoulFloating(SeoulFloating c);

    SeoulFloating findSeoulFloating(Long id);

    List<SeoulFloating> findAllSeoulFloatings();

    void deleteSeoulFloating(Long id);

    void updateSeoulFloating(Long id, SeoulFloating c);

    List<SeoulFloating> findSeoulFloatingsByCity(String city);

    List<SeoulFloating> findSeoulFloatingsByProvince(String province);

}
