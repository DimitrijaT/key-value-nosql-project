package mk.ukim.nosql.config;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.search.StoreIndex;
import com.basho.riak.client.core.RiakCluster;
import com.basho.riak.client.core.query.search.YokozunaIndex;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.search.IndexOptions;
import redis.clients.jedis.search.Schema;

import java.util.Set;
import java.util.concurrent.ExecutionException;

public class SchemaSetup {
    public static void setCaseSchema(UnifiedJedis jedis) {

        Set<String> schemaList = jedis.ftList();

        if (schemaList.contains("case")) {
            jedis.ftDropIndex("case");
        }

        Schema schema = new Schema()
//                .addField(new Schema.Field("id", Schema.FieldType.NUMERIC))
                .addTextField("id", 1.0)
                .addSortableTextField("province", 1.0)
                .addSortableTextField("city", 1.0)
                .addTextField("group", 1.0)
                .addSortableTextField("infection_case", 1.0)
                .addNumericField("confirmed")
                .addNumericField("latitude")
                .addNumericField("longitude");

        jedis.ftCreate("case", IndexOptions.defaultOptions(), schema);
    }


    // An index must be created before documents can be indexed
    // But to run this method:
    //          Change these in etc/riak/riak.conf file:
    //              - storage_backend = leveldb
    //              - search = on
    // Then restart the riak service
    // Then run these commands in the terminal:
    //              - riak-admin bucket-type create covidDataset '{"props":{"search_index":"casesIndex"}}'
    //              - riak-admin bucket-type activate covidDataset
    public static void setRiakIndex(RiakClient client, RiakCluster cluster) {
        try {
            YokozunaIndex index = new YokozunaIndex("casesIndex", "_yz_default");
            StoreIndex storeIndex = new StoreIndex.Builder(index).build();
            client.execute(storeIndex);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }



        // === Delete index (No necessary, rerunning the program doesn't cause any problems)

        //        String index = "famous";
        //YzDeleteIndexOperation deleteOp = new YzDeleteIndexOperation.Builder(index)
        //        .build();
        //cluster.execute(deleteOp);

    }
}
