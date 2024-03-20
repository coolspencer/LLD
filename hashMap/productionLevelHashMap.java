package hashMap;

import java.util.*;
import java.util.LinkedList;

public class productionLevelHashMap<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private LinkedList<Entry<K, V>>[] buckets;
    private int capacity;
    private int size;
    private static final double LOAD_FACTOR = 0.75;

    public productionLevelHashMap() {
        this.capacity = 16; // initial capacity
        this.size = 0;
        this.buckets = new LinkedList[capacity];
    }

    private int hash(K key) {
        return key.hashCode() % capacity;
    }

    public void put(K key, V value) {
        int index = hash(key);
        if (buckets[index] == null) {
            buckets[index] = new LinkedList<>();
        }
        for (Entry<K, V> entry : buckets[index]) {
            if (entry.key.equals(key)) {
                entry.value = value; // Update value if key already exists
                return;
            }
        }
        buckets[index].add(new Entry<>(key, value));
        size++;

        // Check if rehashing is needed
        if ((double) size / capacity >= LOAD_FACTOR) {
            rehash();
        }
    }

    public V get(K key) {
        int index = hash(key);
        if (buckets[index] == null) {
            return null;
        }
        for (Entry<K, V> entry : buckets[index]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public void remove(K key) {
        int index = hash(key);
        if (buckets[index] == null) {
            return;
        }
        buckets[index].removeIf(entry -> entry.key.equals(key));
        size--;
    }

    private void rehash() {
        capacity *= 2;
        LinkedList<Entry<K, V>>[] newBuckets = new LinkedList[capacity];
        for (LinkedList<Entry<K, V>> bucket : buckets) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    int index = entry.key.hashCode() % capacity;
                    if (newBuckets[index] == null) {
                        newBuckets[index] = new LinkedList<>();
                    }
                    newBuckets[index].add(entry);
                }
            }
        }
        buckets = newBuckets;
    }

    public static void main(String[] args) {
        productionLevelHashMap<String, Integer> map = new productionLevelHashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);

        System.out.println("Value for key 'apple': " + map.get("apple"));
        System.out.println("Value for key 'banana': " + map.get("banana"));
        System.out.println("Value for key 'cherry': " + map.get("cherry"));

        map.remove("banana");
        System.out.println("Value for key 'banana' after removal: " + map.get("banana"));
    }
}
