package mk.ukim.nosql.service;

import mk.ukim.nosql.model.Case;

import java.util.List;

public interface CaseService {

    void saveCase(Case c);

    void saveCaseList(List<Case> cases);

    Case findById(Long id);

    List<Case> findAll();

}
