package by.home.aspect.caching;

public class CacheProperties {

    private final int capacity;
    private final String cacheType;

    public CacheProperties(int capacity, String cacheType) {
        this.capacity = capacity;
        this.cacheType = cacheType;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getCacheType() {
        return cacheType;
    }
}
