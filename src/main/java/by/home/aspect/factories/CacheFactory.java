package by.home.aspect.factories;

import by.home.aspect.caching.Cache;
import by.home.aspect.caching.impl.LFUCache;
import by.home.aspect.caching.impl.LRUCache;

public enum CacheFactory {
    LFU("LFU") {
        @Override
        public Cache<Object> getCache(int capacity) {
            return new LFUCache<>(capacity);
        }
    },
    LRU("LRU") {
        @Override
        public Cache<Object> getCache(int capacity) {
            return new LRUCache<>(capacity);
        }
    };

    private final String type;

    CacheFactory(String type) {
        this.type = type;
    }

    public abstract Cache<Object> getCache(int capacity);
}
