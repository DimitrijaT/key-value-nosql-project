package mk.ukim.nosql;

import mk.ukim.nosql.config.AppConfig;
import mk.ukim.nosql.model.Case;
import mk.ukim.nosql.service.CaseService;
import mk.ukim.nosql.util.CSVReaderUtil;

import java.util.List;

public class Main {

    public static void main(String[] args) {

//      CaseService caseService = new CaseServiceImpl(redisRepo);

        CaseService caseService = AppConfig.getCaseService("redis");

        List<Case> cases = CSVReaderUtil.readCSV("src/main/resources/Case.csv");

        caseService.saveCaseList(cases);

        Case c = caseService.findById(6000009L);

        System.out.println(c);

    }
}