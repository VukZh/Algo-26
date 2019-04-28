package cachetimeapp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class cacheTime<Key, V> {

    private static class Key { // класс ключа с полем - временем создания

        private final Object key;
        private final long timeObj;

        public Key(Object k) {
            this.key = k;
            this.timeObj = System.currentTimeMillis();
        }

        public Object getKey() {
            return key;
        }

        @Override
        public boolean equals(Object o) { // проверка ключей по содержимому
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Key otherObject = (Key) o;
            if (key != otherObject.key && (key != null || !key.equals(otherObject.key))) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 11;
            hash = hash * 47 + (key != null ? key.hashCode() : 0);
            return hash;
        }

    }

    private ConcurrentHashMap<Key, V> cache;
    private int sizeCache;
    private long timeLife;

    public cacheTime(int size, long time) { // размер кэша и время жизни в секундах
        cache = new ConcurrentHashMap();
        sizeCache = size;
        timeLife = time * 1000;
    }

    public V get(int k) {
        access();
        Key key = new Key(k);
        if (cache.containsKey(key)) // выводим если ключ найден
        {
            return cache.get(key);
        } else // NULL - объекта нет в кэше
        {
            return null;
        }
    }

    public void put(int k, V value) {
        access();
        Key key = new Key(k);
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        if (cache.size() >= sizeCache) { // на добавляем в кэш при размере кэша > sizeCache
            System.out.println("Нет места в кэше " + k);
            return;
        }

        if (cache.containsKey(key)) { // не добавляем в кэш если этот объект есть в кэше
//            System.out.println("cache no ADDED");
        } else {
            cache.put(key, value); // добавляем
//            System.out.println("cache ADDED");
        }

    }

    private void remove(Key key) { // удаление по ключу
        System.out.println("remove - " + cache.get(key).toString());
        cache.remove(key);
    }

    public void removeAll() { // полная очистка
        cache.clear();
        System.out.println("clear cache");
    }

    public void access() { // проверка жизни объектов кэша с удалением если время жизеи истекло
        long currentTime = System.currentTimeMillis();
        for (Map.Entry<Key, V> entry : cache.entrySet()) {
            Long t = entry.getKey().timeObj;
            if (t + timeLife > currentTime) {
//                System.out.println(entry.getValue().toString() + " - time");
            } else {
//                System.out.println(entry.getValue().toString()  + " - out of time ");
                remove(entry.getKey());
            }
        }
    }
}
