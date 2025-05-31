package com.golemon.blogbackend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisCache {
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * Cache basic objects, such as Integer, String, entity classes, etc.
     *
     * @param key   Cache key
     * @param value Cache value
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Cache basic objects, such as Integer, String, entity classes, etc.
     *
     * @param key      Cache key
     * @param value    Cache value
     * @param timeout  Timeout duration
     * @param timeUnit Time unit
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * Set expiration time
     *
     * @param key     Redis key
     * @param timeout Timeout duration
     * @return true=success; false=failure
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * Set expiration time
     *
     * @param key     Redis key
     * @param timeout Timeout duration
     * @param unit    Time unit
     * @return true=success; false=failure
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
    }

    /**
     * Get cached basic object
     *
     * @param key Cache key
     * @return Cached data corresponding to the key
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * Delete single object
     *
     * @param key Cache key
     * @return true if successful, false otherwise
     */
    public boolean deleteObject(final String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * Delete collection of objects
     *
     * @param collection Collection of objects to delete
     * @return Number of objects deleted
     */
    public long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * Cache List data
     *
     * @param key      Cache key
     * @param dataList List data to be cached
     * @return Number of elements cached
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * Get cached list object
     *
     * @param key Cache key
     * @return Cached data corresponding to the key
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * Cache Set
     *
     * @param key     Cache key
     * @param dataSet Data to be cached
     * @return Cached set operation object
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * Get cached set
     *
     * @param key Cache key
     * @return Cached set data
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * Cache Map
     *
     * @param key     Cache key
     * @param dataMap Map data to be cached
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * Get cached Map
     *
     * @param key Cache key
     * @return Cached map data
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * Store data in Hash
     *
     * @param key   Redis key
     * @param hKey  Hash key
     * @param value Value to store
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * Get data from Hash
     *
     * @param key  Redis key
     * @param hKey Hash key
     * @return Object from Hash
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * Delete data from Hash
     *
     * @param key  Redis key
     * @param hKey Hash key
     */
    public void delCacheMapValue(final String key, final String hKey) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hKey);
    }

    /**
     * Increment value in Hash
     *
     * @param key   Redis key
     * @param hKey  Hash key
     * @param delta Increment value
     * @return Value after increment
     */
    public Long increaseCacheMapValue(final String key, final String hKey, long delta){
        return redisTemplate.opsForHash().increment(key, hKey, delta);
    }

    /**
     * Get multiple values from Hash
     *
     * @param key   Redis key
     * @param hKeys Collection of Hash keys
     * @return Collection of Hash objects
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * Get list of cached basic objects
     *
     * @param pattern String prefix
     * @return List of objects
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }
}