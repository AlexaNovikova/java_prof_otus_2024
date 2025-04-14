package ru.otus.cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCache<K, V> implements HwCache<K, V> {

    private final WeakHashMap<K, V> cacheMap;

    private final List<HwListener<K, V>> listenerList;

    private static final Logger logger = LoggerFactory.getLogger(MyCache.class);

    public MyCache() {
        this.cacheMap = new WeakHashMap<>();
        this.listenerList = new ArrayList<>();
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
            try {
                listener.notify(key, value, cacheListenerMessage.getMessage());
            } catch (Exception e) {
                logger.error("Exception while notifying listener");
            }
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
