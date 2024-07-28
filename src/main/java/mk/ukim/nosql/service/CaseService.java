package mk.ukim.nosql.service;

import mk.ukim.nosql.model.Case;

import java.util.List;
import java.util.Set;

public interface CaseService {

    void saveCase(Case c);

    void saveCaseList(List<Case> cases);

    Case findById(Long id);

    List<Case> findAll();


    List<Case> findCasesByCityAndInfectionCase(String city, String infectionCase);

    List<Case> findCasesByCityAndMinimumConfirmedCases(String city, int minConfirmedCases);

    List<Case> searchByLatitudeAndLongitude(double latitude, double longitude, double radius);

    List<Case> findCasesByProvinceAndMaximumConfirmedCasesWithLatitudeRangeAndSortedByLongitude(String province, int maxConfirmedCases, double minLatitude, double maxLatitude);

    void deleteCase(Long id);

    void updateCase(Long id, Case c);

    void close();


    List<Case> findCasesByProvince(String province);

    String getDbName();

}
