import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class MyCache<K, V> {
    private final int maxSize;
    private final long timeToLiveMillis;

    private final Map<K, CacheNode<K, V>> cacheMap;

    private CacheNode<K, V> head;
    private CacheNode<K, V> tail;

    private static class CacheNode<K, V> {
        K key;
        V value;
        long expiryTime;
        CacheNode<K, V> prev;
        CacheNode<K, V> next;

        CacheNode(K key, V value, long expiryTime) {
            this.key = key;
            this.value = value;
            this.expiryTime = expiryTime;
        }
    }

    public MyCache(int maxSize, long timeToLiveMillis) {
        this.maxSize = maxSize;
        this.timeToLiveMillis = timeToLiveMillis;
        this.cacheMap = new HashMap<>();
    }

    public synchronized void put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException("Cache keys and values must not be null");
        }

        long expiryTime = System.currentTimeMillis() + timeToLiveMillis;

        if (cacheMap.containsKey(key)) {
            CacheNode<K, V> node = cacheMap.get(key);
            node.value = value;
            node.expiryTime = expiryTime;
            moveToHead(node);
        } else {
            if (cacheMap.size() >= maxSize && tail != null) {
                cacheMap.remove(tail.key);
                removeNode(tail);
            }
            CacheNode<K, V> newNode = new CacheNode<>(key, value, expiryTime);
            cacheMap.put(key, newNode);
            addToHead(newNode);
        }
    }

    public synchronized void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public synchronized V get(K key) {
        if (key == null) {
            throw new NullPointerException("Cache key must not be null");
        }

        CacheNode<K, V> node = cacheMap.get(key);
        if (node == null) {
            return null;
        }

        if (System.currentTimeMillis() > node.expiryTime) {
            removeNode(node);
            cacheMap.remove(key);
            return null;
        }

        moveToHead(node);
        return node.value;
    }

    public synchronized void forEach(BiConsumer<? super K, ? super V> action) {
        long now = System.currentTimeMillis();
        for (CacheNode<K, V> node = head; node != null; node = node.next) {
            if (now <= node.expiryTime) {
                action.accept(node.key, node.value);
            }
        }
    }

    public synchronized int size() {
        return cacheMap.size();
    }

    private void moveToHead(CacheNode<K, V> node) {
        removeNode(node);
        addToHead(node);
    }

    private void addToHead(CacheNode<K, V> node) {
        node.next = head;
        node.prev = null;
        if (head != null) {
            head.prev = node;
        }
        head = node;
        if (tail == null) {
            tail = head;
        }
    }

    private void removeNode(CacheNode<K, V> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }
}
