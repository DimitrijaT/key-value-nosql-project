package mk.ukim.nosql;

import mk.ukim.nosql.config.AppConfig;
import mk.ukim.nosql.model.Case;
import mk.ukim.nosql.service.CaseService;
import mk.ukim.nosql.util.CSVReaderUtil;

import java.util.List;

public class Main {


    public static void redisDb(List<Case> cases) {
        CaseService caseService = AppConfig.getCaseService("redis");

        caseService.saveCaseList(cases);

        Case c = caseService.findById(6000009L);

        System.out.println(c);
    }

    public static void riakDb(List<Case> cases) {
        CaseService caseService = AppConfig.getCaseService("riak");

        caseService.saveCase(cases.get(0));

        Case c = caseService.findById(1000001L);

        System.out.println(c);
    }


    public static void main(String[] args) {

        List<Case> cases = CSVReaderUtil.readCSV("src/main/resources/Case.csv");
        assert cases != null;

        riakDb(cases);

    }
}