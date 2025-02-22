package ar.edu.itba.pod.utils;

import java.io.Serializable;
import java.util.Objects;

public class Pair<K extends Serializable, V extends Serializable> implements Serializable {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(key, pair.key) && Objects.equals(value,pair.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key,value);
    }
}
