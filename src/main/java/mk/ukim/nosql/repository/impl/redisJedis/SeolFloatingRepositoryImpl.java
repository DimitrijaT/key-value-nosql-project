package mk.ukim.nosql.repository.impl.redisJedis;

import com.google.gson.Gson;
import mk.ukim.nosql.model.SeoulFloating;
import mk.ukim.nosql.repository.SeoulFloatingRepository;
import redis.clients.jedis.UnifiedJedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SeolFloatingRepositoryImpl implements SeoulFloatingRepository {

    private final UnifiedJedis jedis;
    private final Gson gson;
    private static final String SEOL_FLOATING_ID_KEY = "person:id";

    public SeolFloatingRepositoryImpl(UnifiedJedis jedis, Gson gson) {
        this.jedis = jedis;
        this.gson = gson;
    }

    @Override
    public void saveSeoulFloating(SeoulFloating c) {
        // Generate a unique ID
        long id = jedis.incr(SEOL_FLOATING_ID_KEY);

        // Set the ID to the object
        String caseJson = gson.toJson(c);
        String caseIdKey = "case:" + id;
        jedis.set(caseIdKey, caseJson);

        // Save case ID in sets for province, city, and infection case
        jedis.sadd("province:" + c.getProvince(), caseIdKey);
        jedis.sadd("city:" + c.getCity(), caseIdKey);
        jedis.sadd("date:" + c.getDate(), caseIdKey);
        jedis.sadd("hour:" + c.getHour(), caseIdKey);
        jedis.sadd("birthYear:" + c.getBirthYear(), caseIdKey);
        jedis.sadd("sex:" + c.getSex(), caseIdKey);
        jedis.sadd("fpNum:" + c.getFpNum(), caseIdKey);
        jedis.sadd("province:" + c.getProvince() + ":city:" + c.getCity(), caseIdKey);

    }

    @Override
    public SeoulFloating findSeoulFloating(Long id) {
        String caseJson = jedis.get("case:" + id);
        return caseJson != null ? gson.fromJson(caseJson, SeoulFloating.class) : null;
    }

    @Override
    public List<SeoulFloating> findAllSeoulFloatings() {
        Set<String> caseKeys = jedis.keys("case:*");
        List<SeoulFloating> cases = new ArrayList<>();
        for (String key : caseKeys) {
            String caseJson = jedis.get(key);
            if (caseJson != null) {
                cases.add(gson.fromJson(caseJson, SeoulFloating.class));
            }
        }
        return cases;
    }

    @Override
    public void deleteSeoulFloating(Long id) {
        jedis.del("case:" + id);
    }

    @Override
    public void updateSeoulFloating(Long id, SeoulFloating c) {
        String caseJson = gson.toJson(c);
        jedis.set("case:" + id, caseJson);
    }

    @Override
    public List<SeoulFloating> findSeoulFloatingsByCity(String city) {
        return List.of();
    }

    @Override
    public List<SeoulFloating> findSeoulFloatingsByProvince(String province) {
        return List.of();
    }
}
