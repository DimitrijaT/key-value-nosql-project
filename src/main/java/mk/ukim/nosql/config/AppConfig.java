package mk.ukim.nosql.config;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.core.RiakCluster;
import com.google.gson.Gson;
import mk.ukim.nosql.repository.CaseRepository;
import mk.ukim.nosql.repository.impl.redisJedis.CaseRedisRepositoryImpl;
import mk.ukim.nosql.repository.impl.riak.CaseRiakRepositoryImpl;
import mk.ukim.nosql.service.CaseService;
import mk.ukim.nosql.service.impl.CaseServiceImpl;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.UnifiedJedis;

import java.net.UnknownHostException;

public class AppConfig {
    public static CaseService getCaseService(String dbType) {
        CaseRepository caseRepository;
//        if (dbType.equals("redis-redisson")) {
//            Config config = new Config();
//            config.useClusterServers()
//                    // use "rediss://" for SSL connection
//                    .addNodeAddress("redis://127.0.0.1:6379");
//
//            caseRepository = new CaseRedisRepository(config);
//
//        } else
        if (dbType.equals("redis-jedis")) {

            HostAndPort node = HostAndPort.from("localhost:6379");
            JedisClientConfig clientConfig = DefaultJedisClientConfig.builder()
                    .resp3()
                    .build();

            UnifiedJedis client = new UnifiedJedis(node, clientConfig);

            SchemaSetup.setCaseSchema(client);

            Gson gson = new Gson();
            caseRepository = new CaseRedisRepositoryImpl(client, gson);
        } else if (dbType.equals("riak")) {
            try {
                RiakCluster cluster = RiakConfig.setUpCluster();
                RiakClient client = new RiakClient(cluster);

                Gson gson = new Gson();

                SchemaSetup.setRiakIndex(client,cluster);

                caseRepository = new CaseRiakRepositoryImpl(client, cluster, gson);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("Invalid db type");
        }

        return new CaseServiceImpl(caseRepository);
    }

}
