package ru.otus.cachehw;

import java.util.AbstractMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCache<K, V> implements HwCache<K, V> {

    private static final Logger log = LoggerFactory.getLogger(MyCache.class);

    private final AbstractMap<K, V> cacheMap;

    private final List<HwListener<K, V>> listenerList;

    public MyCache(AbstractMap<K, V> cacheMap, List<HwListener<K, V>> listenerList) {
        this.cacheMap = cacheMap;
        this.listenerList = listenerList;
    }

    @Override
    public void put(K key, V value) {
        if (!cacheMap.containsKey(key)) {
            cacheMap.put(key, value);
            notifyListeners(key, value, CacheListenerMessage.KEY_VALUE_ADDED);
        } else {
            cacheMap.replace(key, value);
            notifyListeners(key, value, CacheListenerMessage.KEY_VALUE_REPLACED);
        }
    }

    private void notifyListeners(K key, V value, CacheListenerMessage cacheListenerMessage) {
        for (HwListener<K, V> listener : listenerList) {
            listener.notify(key, value, cacheListenerMessage.getMessage());
        }
    }

    @Override
    public void remove(K key) {
        cacheMap.remove(key);
        notifyListeners(key, null, CacheListenerMessage.KEY_REMOVED);
    }

    @Override
    public V get(K key) {
        if (cacheMap.containsKey(key)) {
            V value = cacheMap.get(key);
            notifyListeners(key, value, CacheListenerMessage.KEY_GET);
            return value;
        } else {
            notifyListeners(key, null, CacheListenerMessage.KEY_NOT_FOUND);
            return null;
        }
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listenerList.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listenerList.remove(listener);
    }
}
