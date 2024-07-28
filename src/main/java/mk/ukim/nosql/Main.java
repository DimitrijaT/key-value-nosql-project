package mk.ukim.nosql;

import mk.ukim.nosql.config.AppConfig;
import mk.ukim.nosql.model.Case;
import mk.ukim.nosql.service.CaseService;
import mk.ukim.nosql.util.CSVReaderUtil;

import java.util.HashMap;
import java.util.List;

public class Main {


    public static void serviceOperations(CaseService caseService, List<Case> cases, String entityName, boolean saveCsv) {
        HashMap<String, Long> performance = new HashMap<>();

        long startTime;

        // Save all cases
        if (saveCsv) {
            startTime = System.currentTimeMillis();
            caseService.saveCaseList(cases);
            performance.put("writeAllDataTime", System.currentTimeMillis() - startTime);
            System.out.println(performance.get("writeAllDataTime"));
            return;
        } else {
            performance.put("writeAllDataTime", 0L);
        }

        // Delete case
        startTime = System.currentTimeMillis();
        caseService.deleteCase(1000001L);
        performance.put("deleteCaseTime", System.currentTimeMillis() - startTime);

        // Save one case
        startTime = System.currentTimeMillis();
        caseService.saveCase(cases.get(0));
        performance.put("writeOneDataTime", System.currentTimeMillis() - startTime);

        // Read one case
        startTime = System.currentTimeMillis();
        Case c = caseService.findById(6000009L);
        performance.put("readOneDataTime", System.currentTimeMillis() - startTime);

//        // Read all cases
//        startTime = System.currentTimeMillis();
//        List<Case> casesPulled = caseService.findAll();
//        performance.put("readAllDataTime", System.currentTimeMillis() - startTime);

        // Read all cases by province
        startTime = System.currentTimeMillis();
        List<Case> casesByProvince = caseService.findCasesByProvince("Sejong");
        performance.put("casesByProvinceTime", System.currentTimeMillis() - startTime);


        // Find cases by city and infection case
        startTime = System.currentTimeMillis();
        List<Case> casesByCityAndInfectionCase = caseService.findCasesByCityAndInfectionCase("Gangnam-gu", "Samsung Medical Center");
        System.out.println("Cases by city and infection case: " + casesByCityAndInfectionCase.size());
        performance.put("casesByCityAndInfectionCaseTime", System.currentTimeMillis() - startTime);

        // Find cases by city and minimum confirmed cases
        startTime = System.currentTimeMillis();
        List<Case> casesByCityAndMinimumConfirmedCases = caseService.findCasesByCityAndMinimumConfirmedCases("Gang", 5);
        System.out.println("Cases by city and minimum confirmed cases: " + casesByCityAndMinimumConfirmedCases.size());
        performance.put("casesByCityAndMinimumConfirmedCasesTime", System.currentTimeMillis() - startTime);

        // Search by latitude and longitude
        startTime = System.currentTimeMillis();
        List<Case> casesByLatitudeAndLongitude = caseService.searchByLatitudeAndLongitude(37.0, 126.0, 0.9);
        System.out.println("Cases by latitude and longitude: " + casesByLatitudeAndLongitude.size());
        performance.put("casesByLatitudeAndLongitudeTime", System.currentTimeMillis() - startTime);

        // Find cases by province and maximum confirmed cases with latitude range and sorted by longitude
        startTime = System.currentTimeMillis();
        List<Case> casesByCityAndInfectionCaseWithLatitudeRangeAndSortedByLongitude = caseService.findCasesByProvinceAndMaximumConfirmedCasesWithLatitudeRangeAndSortedByLongitude("Gy", 50, 37.5, 40);
        System.out.println("Cases by city and infection case with latitude range and sorted by longitude: " + casesByCityAndInfectionCaseWithLatitudeRangeAndSortedByLongitude.size());
        performance.put("casesByCityAndInfectionCaseWithLatitudeRangeAndSortedByLongitudeTime", System.currentTimeMillis() - startTime);

        // Update case
        startTime = System.currentTimeMillis();
        caseService.updateCase(6000008L, cases.get(0));
        performance.put("updateCaseTime", System.currentTimeMillis() - startTime);

        // Close
        startTime = System.currentTimeMillis();
        caseService.close();
        performance.put("closeTime", System.currentTimeMillis() - startTime);

        Printer.logPerformance(caseService.getDbName(), performance, cases.size(), entityName);
    }

    public static void main(String[] args) {
//         For Riak make sure to create an index first and add this search_index to the covidDataset bucket type in console
//         Additionally change these in etc/riak/riak.conf file:
//              - storage_backend = leveldb
//              - search = on

        final CaseService redisService = AppConfig.getCaseService("redis-jedis");
        final CaseService riakService = AppConfig.getCaseService("riak");

//        System.out.println(riakService.getDbName());

        System.out.println("-------------------------------------------------");
        System.out.println("START");
        System.out.println("-------------------------------------------------");

        System.out.println("Reading data from CSV files");

        List<Case> cases = CSVReaderUtil.readCSV("src/main/resources/archive/Case.csv");
        List<Case> mock1000 = CSVReaderUtil.readCSV("src/main/resources/mockData/Mock1000.csv");
        List<Case> mock10000 = CSVReaderUtil.readCSV("src/main/resources/mockData/Mock10000.csv");

//        cases.addAll(mock1000);
//        cases.addAll(mock10000);

        // If saveCsv is true, it will save all cases to the database but not perform any other operations
        serviceOperations(riakService, cases, "RIAK10000_LEVELDB", false);

        System.out.println("-------------------------------------------------");
        System.out.println("END");

    }
}