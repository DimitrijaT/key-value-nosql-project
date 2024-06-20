package mk.ukim.nosql.repository.impl;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import mk.ukim.nosql.model.Case;
import mk.ukim.nosql.repository.CaseRepository;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class RiakRepository implements CaseRepository {

    private final RiakClient client;

    private final Namespace casesBucket;

    public RiakRepository(RiakClient client) {
        this.client = client;
        this.casesBucket = new Namespace("cases");
    }

    @Override
    public void saveCase(Case c) {
        System.out.println(c);
        Location caseLocation = new Location(casesBucket, c.getId().toString());
        StoreValue storeCaseOp = new StoreValue.Builder(c)
                .withLocation(caseLocation)
                .build();
        try {
            this.client.execute(storeCaseOp);
        } catch (ExecutionException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Case findCase(Long id) {
        Location caseLocation = new Location(casesBucket, id.toString());
        FetchValue fetchCase = new FetchValue.Builder(caseLocation).build();
        Case c;
        try {
            c = client.execute(fetchCase).getValue(Case.class);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return c;
    }

    @Override
    public List<Case> findAllCases() {
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
