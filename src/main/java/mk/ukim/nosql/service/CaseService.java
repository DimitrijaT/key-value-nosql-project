package mk.ukim.nosql.service;

import mk.ukim.nosql.model.Case;

import java.util.List;

public interface CaseService {

    boolean saveCase(Case c);

    boolean saveCaseList(List<Case> cases);

    Case findById(Long id);

}
