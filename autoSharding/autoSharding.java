package autoSharding;

import java.util.HashMap;
import java.util.Map;

// Represents a shard in the database
class Shard {
    private int id;
    private Map<String, String> data;

    public Shard(int id) {
        this.id = id;
        this.data = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void put(String key, String value) {
        data.put(key, value);
    }

    public String get(String key) {
        return data.get(key);
    }

    public boolean containsKey(String key) {
        return data.containsKey(key);
    }
}

// Represents the database with auto sharding
class Database {
    private int numShards;
    private Map<Integer, Shard> shards;

    public Database(int numShards) {
        this.numShards = numShards;
        this.shards = new HashMap<>();
        for (int i = 0; i < numShards; i++) {
            shards.put(i, new Shard(i));
        }
    }

    private Shard getShardForKey(String key) {
        // Use consistent hashing to determine the shard for a given key
        int shardId = Math.abs(key.hashCode()) % numShards;
        return shards.get(shardId);
    }

    public void put(String key, String value) {
        Shard shard = getShardForKey(key);
        shard.put(key, value);
    }

    public String get(String key) {
        Shard shard = getShardForKey(key);
        return shard.get(key);
    }

    public boolean containsKey(String key) {
        Shard shard = getShardForKey(key);
        return shard.containsKey(key);
    }
}

public class autoSharding {
    public static void main(String[] args) {
        Database database = new Database(3);

        // Insert some key-value pairs
        database.put("key1", "value1");
        database.put("key2", "value2");
        database.put("key3", "value3");

        // Retrieve values
        System.out.println(database.get("key1"));
        System.out.println(database.get("key2"));
        System.out.println(database.get("key3"));

        // Check if a key exists
        System.out.println(database.containsKey("key1"));
        System.out.println(database.containsKey("key4"));
    }
}
