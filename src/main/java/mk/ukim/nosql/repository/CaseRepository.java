package mk.ukim.nosql.repository;

import mk.ukim.nosql.model.Case;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CaseRepository {

    void saveCase(Case c);

    // === Simple queries ===

    Case findCase(Long id) throws ExecutionException, InterruptedException;

    List<Case> findAllCases();

    void deleteCase(Long id) throws ExecutionException, InterruptedException;

    void updateCase(Long id, Case c) throws ExecutionException, InterruptedException;

    List<Case> findCasesByProvince(String province) throws ExecutionException, InterruptedException;

    // === Intermediary queries ===

    List<Case> findCasesByCityAndInfectionCase(String city, String infectionCase) throws ExecutionException, InterruptedException;

    List<Case> findCasesByCityAndMinimumConfirmedCases(String city, int minConfirmedCases) throws ExecutionException, InterruptedException;

    List<Case> searchByLatitudeAndLongitude(double latitude, double longitude, double radius) throws ExecutionException, InterruptedException;

    // === Advanced queries ===

    // Search by City and Infection Case with Latitude Range and Sorted by Longitude

    List<Case> findCasesByProvinceAndMaximumConfirmedCasesWithLatitudeRangeAndSortedByLongitude(String province, int maxConfirmedCases, double minLatitude, double maxLatitude) throws ExecutionException, InterruptedException;

    public void close();

    String getDbName();

}
