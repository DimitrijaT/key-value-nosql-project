package mk.ukim.nosql.service.impl;


import mk.ukim.nosql.model.Case;
import mk.ukim.nosql.repository.CaseRepository;
import mk.ukim.nosql.service.CaseService;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CaseServiceImpl implements CaseService {

    private final CaseRepository caseRepository;

    public CaseServiceImpl(CaseRepository redisRepo) {
        this.caseRepository = redisRepo;
    }

    @Override
    public void saveCase(Case c) {
        caseRepository.saveCase(c);
    }

    @Override
    public void saveCaseList(List<Case> cases) {
        for (Case c : cases) {
            saveCase(c);
        }
    }

    @Override
    public Case findById(Long id) {
        try {
            return caseRepository.findCase(id);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Case> findAll() {
        return caseRepository.findAllCases();
    }

    @Override
    public List<Case> findCasesByCityAndInfectionCase(String city, String infectionCase) {
        try {
            return caseRepository.findCasesByCityAndInfectionCase(city, infectionCase);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Case> findCasesByCityAndMinimumConfirmedCases(String city, int minConfirmedCases) {
        try {
            return caseRepository.findCasesByCityAndMinimumConfirmedCases(city, minConfirmedCases);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Case> searchByLatitudeAndLongitude(double latitude, double longitude, double radius) {
        try {
            return caseRepository.searchByLatitudeAndLongitude(latitude, longitude, radius);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Case> findCasesByProvinceAndMaximumConfirmedCasesWithLatitudeRangeAndSortedByLongitude(String province, int maxConfirmedCases, double minLatitude, double maxLatitude) {
        try {
            return caseRepository.findCasesByProvinceAndMaximumConfirmedCasesWithLatitudeRangeAndSortedByLongitude(province, maxConfirmedCases, minLatitude, maxLatitude);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteCase(Long id) {
        try {
            caseRepository.deleteCase(id);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCase(Long id, Case c) {
        try {
            caseRepository.updateCase(id, c);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        caseRepository.close();
    }

    @Override
    public List<Case> findCasesByProvince(String province) {
        try {
            return caseRepository.findCasesByProvince(province);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
