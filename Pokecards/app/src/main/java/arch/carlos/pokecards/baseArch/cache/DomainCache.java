package arch.carlos.pokecards.baseArch.cache;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mobgen on 9/20/17.
 *
 *
 * A single generic cache that contains each model cache, so there is no need to code each one.
 */

public class DomainCache {

    /**
     * The first map associates a DomainKey to its cache
     * The inner map is the real cache associating the item key to the item
     */
    private Map<String, HashMap<String, Cacheable>> cache;

    /**
     * DomainKey: The table name
     * PrimaryKey: The index of the item
     */
    public interface Cacheable {
        String getDomainKey();

        String getPrimaryKey();
    }

    public void init() {
        cache = new HashMap<>();
    }

    public boolean hasCacheForDomain(String domainKey) {
        return cache.containsKey(domainKey);
    }

    public HashMap<String, Cacheable> getDomainCache(String domainKey) {
        // If there is no previous cache of the domain
        if (!hasCacheForDomain(domainKey)) {
            cache.put(domainKey, new HashMap<>());
        }

        return cache.get(domainKey);
    }


    public <T extends Cacheable> void addResource(T nCacheable) {
        HashMap<String, Cacheable> domainCache = getDomainCache(nCacheable.getDomainKey());
        domainCache.put(nCacheable.getPrimaryKey(), nCacheable);
    }

    public <T extends Cacheable> void addResource(String domainKey, List<T> nCacheables) {
        HashMap<String, Cacheable> domainCache = getDomainCache(domainKey);
        for (Cacheable item : nCacheables
                ) {
            domainCache.put(item.getPrimaryKey(), item);
        }
    }

    public <T extends Cacheable> LiveData<List<T>> getResource(String domainKey) {
        MutableLiveData<List<T>> resources = new MutableLiveData<>();
        Collection<T> collection = (Collection<T>) getDomainCache(domainKey).values();
        ArrayList<T> result = new ArrayList<>(collection);
        resources.setValue(result);
        return resources;
    }

    public <T extends Cacheable> LiveData<T> getResource(String domainKey, String primaryKey) {
        MutableLiveData<T> resource = new MutableLiveData<>();
        resource.setValue((T)getDomainCache(domainKey).get(primaryKey));
        return resource;
    }

    public void deleteResource(String domainKey) {
        // If there is some previous cache of the domain
        if (hasCacheForDomain(domainKey)) {
            cache.put(domainKey, new HashMap<>());
        }
    }

    public <T extends Cacheable> void deleteResource(T nCacheable){
        getDomainCache(nCacheable.getDomainKey()).remove(nCacheable.getPrimaryKey());
    }
    public <T extends Cacheable> void deleteResource(String domainKey, String primaryKey){
        getDomainCache(domainKey).remove(primaryKey);
    }
}
