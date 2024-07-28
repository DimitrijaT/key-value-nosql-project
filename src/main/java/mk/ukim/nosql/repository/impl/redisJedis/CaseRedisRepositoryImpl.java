package mk.ukim.nosql.repository.impl.redisJedis;

import com.google.gson.Gson;
import mk.ukim.nosql.model.Case;
import mk.ukim.nosql.repository.CaseRepository;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.search.Document;
import redis.clients.jedis.search.Query;
import redis.clients.jedis.search.RediSearchUtil;
import redis.clients.jedis.search.SearchResult;

import java.util.*;


public class CaseRedisRepositoryImpl implements CaseRepository {

    private final UnifiedJedis jedis;
    private final Gson gson;


    public CaseRedisRepositoryImpl(UnifiedJedis jedis, Gson gson) {
        this.jedis = jedis;
        this.gson = gson;
    }

    private void addDocument(String key, Map<String, Object> map) {
        jedis.hset(key, RediSearchUtil.toStringMap(map));
    }

    @Override
    public void saveCase(Case c) {
        String caseIdKey = "case:" + c.getId_s();

        Map<String, Object> field = new HashMap<>();
        field.put("id", c.getId_s());
        field.put("province", c.getProvince_s());
        field.put("city", c.getCity_s());
        field.put("group", c.getGroup_b());
        field.put("infectionCase", c.getInfectionCase_s());
        field.put("confirmed", c.getConfirmed_i());
        if (c.getLatitude_d() == null || c.getLongitude_d() == null) {
            field.put("latitude", 0);
            field.put("longitude", 0);
        } else {
            field.put("latitude", c.getLatitude_d());
            field.put("longitude", c.getLongitude_d());
        }

//        System.out.println("field: " + field);
//        System.out.println("caseIdKey: " + caseIdKey);

        addDocument(caseIdKey, field);

        String caseJson = gson.toJson(c);
//        jedis.set(caseIdKey, caseJson);

        // Save case ID in sets for province, city, and infection case
        jedis.sadd("province:" + c.getProvince_s(), caseIdKey);
        jedis.sadd("city:" + c.getCity_s(), caseIdKey);
        jedis.sadd("infection_case:" + c.getInfectionCase_s(), caseIdKey);
    }

    @Override
    public Case findCase(Long id) {
//        String caseJson = jedis.get("case:" + id);
//        return caseJson != null ? gson.fromJson(caseJson, Case.class) : null;

        SearchResult sr = jedis.ftSearch("case", new Query("@id:" + id));

        if (sr.getTotalResults() == 0) {
            return null;
        }
        Document doc = sr.getDocuments().get(0);

        Case item = Case.createCaseFromRedisDocument(doc);

        return item;
    }

    @Override
    public List<Case> findAllCases() {
        SearchResult sr = jedis.ftSearch("case", new Query());
        List<Case> cases = new ArrayList<>();
        for (Document doc : sr.getDocuments()) {
            cases.add(Case.createCaseFromRedisDocument(doc));
        }
        return cases;
    }

    @Override
    public void deleteCase(Long id) {
        jedis.del("case:" + id);
    }

    @Override
    public void updateCase(Long id, Case c) {
        String caseJson = gson.toJson(c);
        jedis.set("case:" + id, caseJson);
    }

    public List<Case> findCasesByProvince(String province) {

        Set<String> caseIds = jedis.smembers("province:" + province);

        List<Case> cases = new ArrayList<>();

        for (String caseId : caseIds) {
            String id = caseId.split(":")[1];
            SearchResult sr = jedis.ftSearch("case", new Query("@id:" + id));
            Document doc = sr.getDocuments().get(0);
            cases.add(Case.createCaseFromRedisDocument(doc));
        }

        return cases;
    }


    @Override
    public List<Case> findCasesByCityAndInfectionCase(String city, String infectionCase) {
        Query query = new Query("@city:" + city + " @infectionCase:" + infectionCase.replace(" ", "?"));
        SearchResult sr = jedis.ftSearch("case", query);
        List<Case> cases = new ArrayList<>();
        for (Document doc : sr.getDocuments()) {
            cases.add(Case.createCaseFromRedisDocument(doc));
        }
        return cases;
    }

    @Override
    public List<Case> findCasesByCityAndMinimumConfirmedCases(String city, int minConfirmedCases) {
        Query query = new Query("@city:" + city + "*" + " @confirmed:[" + minConfirmedCases + " +inf]");
        SearchResult sr = jedis.ftSearch("case", query);
        List<Case> cases = new ArrayList<>();
        for (Document doc : sr.getDocuments()) {
            cases.add(Case.createCaseFromRedisDocument(doc));
        }
        return cases;
    }

    @Override
    public List<Case> searchByLatitudeAndLongitude(double latitude, double longitude, double radius) {
        Query query = new Query("@latitude:[" + (latitude - radius) + " " + (latitude + radius) +
                "] @longitude:[" + (longitude - radius) + " " + (longitude + radius) + "]");
        SearchResult sr = jedis.ftSearch("case", query);
        List<Case> cases = new ArrayList<>();
        for (Document doc : sr.getDocuments()) {
            cases.add(Case.createCaseFromRedisDocument(doc));
        }
        return cases;
    }

    @Override
    public List<Case> findCasesByProvinceAndMaximumConfirmedCasesWithLatitudeRangeAndSortedByLongitude(String province, int maxConfirmedCases, double minLatitude, double maxLatitude) {
//        Query query = new Query("@province:" + province + "* @confirmed:[-inf " +
//                maxConfirmedCases + "] @latitude:["
//                + minLatitude + " " + maxLatitude + "]");

        Query query = new Query("@province: " + province + "*");
//        Query query = new Query("@latitude:[" + minLatitude + " " + maxLatitude + "]");
        SearchResult sr = jedis.ftSearch("case", query.setSortBy("longitude", false));
        List<Case> cases = new ArrayList<>();
        for (Document doc : sr.getDocuments()) {
            cases.add(Case.createCaseFromRedisDocument(doc));
        }
        return cases;
    }

    @Override
    public void close() {
        jedis.close();
    }

    @Override
    public String getDbName() {
        return "redis-jedis";
    }


    /**
     *      assertEquals(1, client.ftSearch(index, new Query("@category:{red}")).getTotalResults());
     *      assertEquals(1, client.ftSearch(index, new Query("@category:{blue}")).getTotalResults());
     *      assertEquals(1, client.ftSearch(index, new Query("hello @category:{red}")).getTotalResults());
     *      assertEquals(1, client.ftSearch(index, new Query("hello @category:{blue}")).getTotalResults());
     *      assertEquals(1, client.ftSearch(index, new Query("@category:{yellow}")).getTotalResults());
     *      assertEquals(0, client.ftSearch(index, new Query("@category:{purple}")).getTotalResults());
     *      assertEquals(1, client.ftSearch(index, new Query("@category:{orange\\;purple}")).getTotalResults());
     *      assertEquals(4, client.ftSearch(index, new Query("hello")).getTotalResults());
     */
}