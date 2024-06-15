package mk.ukim.nosql.config;

import com.google.gson.Gson;
import mk.ukim.nosql.repository.CaseRepository;
import mk.ukim.nosql.repository.impl.RedisRepository;
import mk.ukim.nosql.repository.impl.RiakRepository;
import mk.ukim.nosql.service.CaseService;
import mk.ukim.nosql.service.impl.CaseServiceImpl;
import redis.clients.jedis.Jedis;

public class AppConfig {
    public static CaseService getCaseService(String dbType) {
        CaseRepository caseRepository;
        if (dbType.equals("redis")) {
            Jedis jedis = new Jedis("localhost", 6379);
            Gson gson = new Gson();
            caseRepository = new RedisRepository(jedis, gson);
        } else if (dbType.equals("riak")) {
            caseRepository = new RiakRepository();
        } else {
            throw new IllegalArgumentException("Invalid db type");
        }

        return new CaseServiceImpl(caseRepository);
    }

}
