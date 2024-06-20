package mk.ukim.nosql;

import mk.ukim.nosql.config.AppConfig;
import mk.ukim.nosql.model.Case;
import mk.ukim.nosql.service.CaseService;
import mk.ukim.nosql.util.CSVReaderUtil;

import java.util.List;

public class Main {

    public static void serviceOperations(CaseService caseService, List<Case> cases, String dbType) {
        long startTime;

        // Save one case
        startTime = System.currentTimeMillis();
        caseService.saveCase(cases.get(0));
        long writeOneDataTime = System.currentTimeMillis() - startTime;

        // Save all cases
        startTime = System.currentTimeMillis();
        caseService.saveCaseList(cases);
        long writeAllDataTime = System.currentTimeMillis() - startTime;


        // Read one case
        startTime = System.currentTimeMillis();
        Case c = caseService.findById(6000009L);
        long readOneDataTime = System.currentTimeMillis() - startTime;


        // Read all cases
        startTime = System.currentTimeMillis();
        List<Case> casesPulled = caseService.findAll();
        long readAllDataTime = System.currentTimeMillis() - startTime;

        // TODO: Update case

        // TODO: Delete case

        // TODO: Delete all cases

        // TODO: Find all cases by province

        // TODO: Find all cases by city

        // TODO: Find all cases by infection case


        Printer.logPerformance(dbType, writeOneDataTime, writeAllDataTime, readOneDataTime, readAllDataTime, cases.size());
    }

    public static void main(String[] args) {
        List<Case> cases = CSVReaderUtil.readCSV("src/main/resources/archive/Case.csv");

        CaseService caseService = AppConfig.getCaseService("redis");
        if (cases != null) {
            serviceOperations(caseService, cases, "redis");
        }

        caseService = AppConfig.getCaseService("riak");
        if (cases != null) {
            serviceOperations(caseService, cases, "riak");
        }
    }
}