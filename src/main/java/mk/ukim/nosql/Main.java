package mk.ukim.nosql;

import mk.ukim.nosql.config.AppConfig;
import mk.ukim.nosql.model.Case;
import mk.ukim.nosql.service.CaseService;
import mk.ukim.nosql.util.CSVReaderUtil;

import java.util.List;

public class Main {

    public static void serviceOperations(CaseService caseService, List<Case> cases, String dbType) {
        long startTime;

        startTime = System.currentTimeMillis();
        caseService.saveCase(cases.get(0));
        long writeOneDataTime = System.currentTimeMillis() - startTime;

        startTime = System.currentTimeMillis();
        caseService.saveCaseList(cases);
        long writeAllDataTime = System.currentTimeMillis() - startTime;

        startTime = System.currentTimeMillis();
        Case c = caseService.findById(6000009L);
        long readOneDataTime = System.currentTimeMillis() - startTime;

        startTime = System.currentTimeMillis();
        List<Case> casesPulled = caseService.findAll();
        long readAllDataTime = System.currentTimeMillis() - startTime;

        Printer.logPerformance(dbType,writeOneDataTime, writeAllDataTime, readOneDataTime, readAllDataTime, cases.size());
    }

    public static void main(String[] args) {
        List<Case> cases = CSVReaderUtil.readCSV("src/main/resources/Case.csv");
        assert cases != null;

        CaseService caseService = AppConfig.getCaseService("redis");
        serviceOperations(caseService, cases, "redis");

        caseService = AppConfig.getCaseService("riak");
        serviceOperations(caseService, cases, "riak");
    }
}