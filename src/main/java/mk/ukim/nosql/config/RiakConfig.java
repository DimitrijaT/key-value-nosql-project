package mk.ukim.nosql.config;

import com.basho.riak.client.core.RiakCluster;
import com.basho.riak.client.core.RiakNode;

import java.net.UnknownHostException;

public class RiakConfig {

    // This will create a client object that we can use to interact with Riak
    public static RiakCluster setUpCluster() throws UnknownHostException {
        // This example will use only one node listening on localhost:10017
        RiakNode node = new RiakNode.Builder()
                .withRemoteAddress("localhost")
                .withRemotePort(8087)
                .build();

        // This cluster object takes our one node as an argument
        RiakCluster cluster = new RiakCluster.Builder(node)
                .build();

        // The cluster must be started to work, otherwise you will see errors
        cluster.start();

        return cluster;
    }
}
