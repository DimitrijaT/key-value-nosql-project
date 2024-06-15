package mk.ukim.nosql.service.impl;


import mk.ukim.nosql.model.Case;
import mk.ukim.nosql.repository.CaseRepository;
import mk.ukim.nosql.service.CaseService;

import java.util.List;

public class CaseServiceImpl implements CaseService {

    private final CaseRepository redisRepo;

    public CaseServiceImpl(CaseRepository redisRepo) {
        this.redisRepo = redisRepo;
    }

    @Override
    public void saveCase(Case c) {
        redisRepo.saveCase(c);
    }

    @Override
    public void saveCaseList(List<Case> cases) {
        for (Case c : cases) {
            saveCase(c);
        }
    }

    @Override
    public Case findById(Long id) {
        return redisRepo.findCase(id);
    }


}
