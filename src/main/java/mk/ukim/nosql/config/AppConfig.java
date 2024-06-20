package mk.ukim.nosql.config;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.core.RiakCluster;
import com.google.gson.Gson;
import mk.ukim.nosql.repository.CaseRepository;
import mk.ukim.nosql.repository.impl.redis.CaseRedisRepository;
import mk.ukim.nosql.repository.impl.riak.CaseRiakRepository;
import mk.ukim.nosql.service.CaseService;
import mk.ukim.nosql.service.impl.CaseServiceImpl;
import redis.clients.jedis.Jedis;

import java.net.UnknownHostException;

public class AppConfig {
    public static CaseService getCaseService(String dbType) {
        CaseRepository caseRepository;
        if (dbType.equals("redis")) {
            Jedis jedis = new Jedis("localhost", 6379);
            Gson gson = new Gson();
            caseRepository = new CaseRedisRepository(jedis, gson);
        } else if (dbType.equals("riak")) {
            try {
                RiakCluster cluster = RiakConfig.setUpCluster();
                RiakClient client = new RiakClient(cluster);
                caseRepository = new CaseRiakRepository(client);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("Invalid db type");
        }

        return new CaseServiceImpl(caseRepository);
    }

}
