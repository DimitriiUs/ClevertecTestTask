package by.home.aspect.caching.impl;

import by.home.aspect.caching.Cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LFUCache<E> implements Cache<E> {

    private int capacity;
    private final Map<Integer, HitRate> cache = new HashMap<>();
    private final Map<Integer, E> KV = new HashMap<>();

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public void set(int key, E value) {
        E v = KV.get(key);
        if (v == null) {
            if (cache.size() == capacity) {
                Integer k = getKickedKey();
                KV.remove(k);
                cache.remove(k);
            }
            cache.put(key, new HitRate(key, 1, System.nanoTime()));
        } else {
            HitRate hitRate = cache.get(key);
            hitRate.hitCount += 1;
            hitRate.lastTime = System.nanoTime();
        }
        KV.put(key, value);
    }

    @Override
    public void delete(int key) {
        KV.remove(key, KV.get(key));
    }

    public int size() {
        return KV.size();
    }

    public E get(int key) {
        E v = (E) KV.get(key);
        if (v != null) {
            HitRate hitRate = cache.get(key);
            hitRate.hitCount += 1;
            hitRate.lastTime = System.nanoTime();
            return v;
        }
        return null;
    }

    private Integer getKickedKey() {
        HitRate min = Collections.min(cache.values());
        return min.key;
    }

    static class HitRate implements Comparable<HitRate> {
        Integer key;
        Integer hitCount;
        Long lastTime;

        public HitRate(Integer key, Integer hitCount, Long lastTime) {
            this.key = key;
            this.hitCount = hitCount;
            this.lastTime = lastTime;
        }

        public int compareTo(HitRate o) {
            int hr = hitCount.compareTo(o.hitCount);
            return hr != 0 ? hr : lastTime.compareTo(o.lastTime);
        }
    }

}