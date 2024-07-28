package mk.ukim.nosql.repository.impl.riak;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.DeleteValue;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.api.commands.kv.UpdateValue;
import com.basho.riak.client.core.RiakCluster;
import com.basho.riak.client.core.operations.SearchOperation;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.util.BinaryValue;
import com.google.gson.Gson;
import mk.ukim.nosql.model.Case;
import mk.ukim.nosql.model.CaseUpdate;
import mk.ukim.nosql.repository.CaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CaseRiakRepositoryImpl implements CaseRepository {

    private final RiakClient client;

    private final RiakCluster cluster;

    private final Namespace casesBucket;

    private final Gson gson;

    public CaseRiakRepositoryImpl(RiakClient client, RiakCluster cluster, Gson gson) {
        this.client = client;
        this.cluster = cluster;
        this.casesBucket = new Namespace("covidDataset", "cases");
        this.gson = gson;
    }

    @Override
    public void saveCase(Case c) {

//        //  By default, the Java Riak client serializes POJOs as JSON.
        Location caseLocation = new Location(casesBucket, c.getId_s());
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
    public Case findCase(Long id) throws ExecutionException, InterruptedException {
        Location caseLocation = new Location(casesBucket, id.toString());
        FetchValue fetchCase = new FetchValue.Builder(caseLocation).build();
        Case c;
        c = client.execute(fetchCase).getValue(Case.class);
        return c;
    }

    @Override
    public List<Case> findAllCases() {
        return null;
    }

    @Override
    public void deleteCase(Long id) throws ExecutionException, InterruptedException {
        Location caseLocation = new Location(casesBucket, id.toString());
        client.execute(new DeleteValue.Builder(caseLocation).build());
    }

    @Override
    public void updateCase(Long id, Case c) throws ExecutionException, InterruptedException {
        Location caseLocation = new Location(casesBucket, id.toString());
        CaseUpdate updatedBook = new CaseUpdate(c);
        UpdateValue updateValue = new UpdateValue.Builder(caseLocation)
                .withUpdate(updatedBook).build();
        UpdateValue.Response response = client.execute(updateValue);
    }

    @Override
    public List<Case> findCasesByProvince(String province) throws ExecutionException, InterruptedException {
        String query = "province_s:" + province + "*";
        SearchOperation searchOp = new SearchOperation
                .Builder(BinaryValue.create("casesIndex"), query)
                .build();
        cluster.execute(searchOp);
        return extractCasesFromSearchOperation(searchOp);
    }

    @Override
    public List<Case> findCasesByCityAndInfectionCase(String city, String infectionCase) throws ExecutionException, InterruptedException {
        String query = "city_s:" + city + "* AND infectionCase_s:" + infectionCase.replace(" ", "?") + "*";
        SearchOperation searchOp = new SearchOperation
                .Builder(BinaryValue.create("casesIndex"), query)
                .build();
        cluster.execute(searchOp);
        return extractCasesFromSearchOperation(searchOp);

    }

    @Override
    public List<Case> findCasesByCityAndMinimumConfirmedCases(String city, int minConfirmedCases) throws ExecutionException, InterruptedException {
        String query = "city_s:" + city + "* AND confirmed_i:[" + minConfirmedCases + " TO *]";
        SearchOperation searchOp = new SearchOperation
                .Builder(BinaryValue.create("casesIndex"), query)
                .build();
        cluster.execute(searchOp);
        return extractCasesFromSearchOperation(searchOp);
    }

    @Override
    public List<Case> searchByLatitudeAndLongitude(double latitude, double longitude, double radius) throws ExecutionException, InterruptedException {
        String query = "latitude_d:[" + (latitude - radius) + " TO " + (latitude + radius) +
                "] AND longitude_d:[" + (longitude - radius) + " TO " + (longitude + radius) + "]";
        SearchOperation searchOp = new SearchOperation
                .Builder(BinaryValue.create("casesIndex"), query)
                .build();
        cluster.execute(searchOp);
        return extractCasesFromSearchOperation(searchOp);
    }

    @Override
    public List<Case> findCasesByProvinceAndMaximumConfirmedCasesWithLatitudeRangeAndSortedByLongitude(String province, int maxConfirmedCases, double minLatitude, double maxLatitude) throws ExecutionException, InterruptedException {
        String query = "province_s:" + province + "* AND confirmed_i:[0 TO "
                + maxConfirmedCases + "] AND latitude_d:["
                + minLatitude + " TO " + maxLatitude + "]";
        SearchOperation searchOp = new SearchOperation
                .Builder(BinaryValue.create("casesIndex"), query)
                .build();
        cluster.execute(searchOp);
        return extractCasesFromSearchOperation(searchOp);
    }

    @Override
    public void close() {
        client.shutdown();
    }


    private List<Case> extractCasesFromSearchOperation(SearchOperation searchOp) throws ExecutionException, InterruptedException {
        SearchOperation.Response results = searchOp.get();
//        int numberFound = results.numResults();
        List<Case> foundMatches = new ArrayList<>();
        List<Map<String, List<String>>> foundObject = results.getAllResults();
        List<String> keys = foundObject.stream().map(m -> m.get("_yz_rk").get(0)).toList();
        for (String key : keys) {
            foundMatches.add(findCase(Long.parseLong(key)));
        }
        return foundMatches;
    }
}
