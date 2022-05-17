package util;

public class Bundle<T> {
    T t;

    public Bundle(T t) { this.t = t; }

    public T getT() {
        return t;
    }

    public Class<?> getType() {
        return t.getClass();
    }
}
