//package mk.ukim.nosql.repository.impl.redisJedis;
//
//import com.google.gson.Gson;
//import mk.ukim.nosql.model.Case;
//import mk.ukim.nosql.repository.CaseRepository;
//import redis.clients.jedis.UnifiedJedis;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//public class CaseRedisRepository implements CaseRepository {
//    private final UnifiedJedis jedis;
//    private final Gson gson;
//
//    public CaseRedisRepository(UnifiedJedis jedis, Gson gson) {
//        this.jedis = jedis;
//        this.gson = gson;
//    }
//
//    public void saveCase(Case c) {
////        saveCaseAsHash(c);
//        saveCaseAsJson(c);
//    }
//
//
//    public void saveCaseAsJson(Case c) {
//        String key = "case:" + c.getId();
//        String value = gson.toJson(c);
//        jedis.set(key, value);
//
//        // stores the value as set
//        // Add to indexes
//        jedis.sadd("province:" + c.getProvince(), key);
//        jedis.sadd("city:" + c.getCity(), key);
//        jedis.sadd("infection_case:" + c.getInfectionCase(), key);
//    }
//
//    public Case getCaseFromJson(String caseId) {
//        String key = "case:" + caseId;
//        String value = jedis.get(key);
//        return gson.fromJson(value, Case.class);
//    }
//
//    public Case findCase(Long id) {
//        Case c1 = getCaseFromJson(id.toString());
////        Case c2 = getCaseFromHash(id.toString());
//
//        return c1;
//    }
//
//    @Override
//    public List<Case> findAllCases() {
//        String cursor = "0";
//        List<Case> cases = new ArrayList<>();
//        // keys is a slow operation
//        Set<String> keys = jedis.keys("case:*");
//        for (String key : keys) {
//            String value = jedis.get(key);
//            cases.add(gson.fromJson(value, Case.class));
//        }
//
//        return cases;
//    }
//
//    @Override
//    public Set<String> findCasesByProvince(String province) {
//        return jedis.smembers("province:" + province);
//    }
//
//    @Override
//    public Set<String> findCasesByCity(String city) {
//        return jedis.smembers("city:" + city);
//    }
//
//    @Override
//    public Set<String> findCasesByInfectionCase(String infectionCase) {
//        return jedis.smembers("infection_case:" + infectionCase);
//    }
//
//    @Override
//    public void close() {
//        if (jedis != null)
//            jedis.close();
//    }
//
//
//    public void saveCaseAsHash(Case c) {
//        String caseId = c.getId().toString();
//        jedis.hset(caseId, "province", c.getProvince());
//        jedis.hset(caseId, "city", c.getCity());
//        jedis.hset(caseId, "group", String.valueOf(c.getGroup()));
//        jedis.hset(caseId, "infectionCase", c.getInfectionCase());
//        jedis.hset(caseId, "confirmed", String.valueOf(c.getConfirmed()));
//        jedis.hset(caseId, "latitude", String.valueOf(c.getLatitude()));
//        jedis.hset(caseId, "longitude", String.valueOf(c.getLongitude()));
//    }
//
//    public Case getCaseFromHash(String caseId) {
//        Map<String, String> fields = jedis.hgetAll(caseId);
//        return new Case(
//                Long.parseLong(caseId),
//                fields.get("province"),
//                fields.get("city"),
//                Boolean.parseBoolean(fields.get("group")),
//                fields.get("infectionCase"),
//                Integer.parseInt(fields.get("confirmed")),
//                Double.parseDouble(fields.get("latitude")),
//                Double.parseDouble(fields.get("longitude"))
//        );
//    }
//}
