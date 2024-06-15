package mk.ukim.nosql.repository.impl;

import mk.ukim.nosql.model.Case;
import mk.ukim.nosql.repository.CaseRepository;

import java.util.Set;

public class RiakRepository implements CaseRepository {
    @Override
    public void saveCase(Case c) {

    }

    @Override
    public Case findCase(Long id) {
        return null;
    }

    @Override
    public Set<String> findCasesByProvince(String province) {
        return null;
    }

    @Override
    public Set<String> findCasesByCity(String city) {
        return null;
    }

    @Override
    public Set<String> findCasesByInfectionCase(String infectionCase) {
        return null;
    }

    @Override
    public void close() {

    }
}
