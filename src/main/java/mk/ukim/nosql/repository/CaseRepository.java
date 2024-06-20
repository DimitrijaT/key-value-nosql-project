package mk.ukim.nosql.repository;

import mk.ukim.nosql.model.Case;

import java.util.List;
import java.util.Set;

public interface CaseRepository {

    void saveCase(Case c);

    Case findCase(Long id);

    List<Case> findAllCases();

    public Set<String> findCasesByProvince(String province);

    public Set<String> findCasesByCity(String city);

    public Set<String> findCasesByInfectionCase(String infectionCase);

    public void close();


}
