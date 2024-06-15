package mk.ukim.nosql.repository.impl;

import com.google.gson.Gson;
import mk.ukim.nosql.model.Case;
import mk.ukim.nosql.repository.CaseRepository;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class RedisRepository implements CaseRepository {
    private final Jedis jedis;
    private final Gson gson;

    public RedisRepository(Jedis jedis, Gson gson) {
        this.jedis = jedis;
        this.gson = gson;
    }

    public void saveCase(Case c) {
        String key = "case:" + c.getId();
        String value = gson.toJson(c);
        jedis.set(key, value);

        // Add to indexes
        jedis.sadd("province:" + c.getProvince(), key);
        jedis.sadd("city:" + c.getCity(), key);
        jedis.sadd("infection_case:" + c.getInfectionCase(), key);
    }

    public Case findCase(Long id) {
        String key = "case:" + id;
        String value = jedis.get(key);
        return gson.fromJson(value, Case.class);
    }

    @Override
    public Set<String> findCasesByProvince(String province) {
        return jedis.smembers("province:" + province);
    }

    @Override
    public Set<String> findCasesByCity(String city) {
        return jedis.smembers("city:" + city);
    }

    @Override
    public Set<String> findCasesByInfectionCase(String infectionCase) {
        return jedis.smembers("infection_case:" + infectionCase);
    }

    @Override
    public void close() {
        jedis.close();
    }


//    public void saveByHash(Case c) {
//        jedis.hset(c.getId().toString(), "province", c.getProvince());
//        jedis.hset(c.getId().toString(), "city", c.getCity());
//        jedis.hset(c.getId().toString(), "group", String.valueOf(c.getGroup()));
//        jedis.hset(c.getId().toString(), "infectionCase", c.getInfectionCase());
//        jedis.hset(c.getId().toString(), "confirmed", String.valueOf(c.getConfirmed()));
//        jedis.hset(c.getId().toString(), "latitude", String.valueOf(c.getLatitude()));
//        jedis.hset(c.getId().toString(), "longitude", String.valueOf(c.getLongitude()));
//
//        jedis.close();
//    }
//
//    public Case find(String id) {
//        Case c = new Case();
//        String province = jedis.hget(id, "province");
//        String city = jedis.hget(id, "city");
//        Boolean group = Boolean.parseBoolean(jedis.hget(id, "group"));
//        String infectionCase = jedis.hget(id, "infectionCase");
//        Integer confirmed = Integer.parseInt(jedis.hget(id, "confirmed"));
//        Double latitude = Double.parseDouble(jedis.hget(id, "latitude"));
//        Double longitude = Double.parseDouble(jedis.hget(id, "longitude"));
//        return new Case(Long.parseLong(id), province, city, group, infectionCase, confirmed, latitude, longitude);
//    }
}
