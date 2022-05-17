package util;

public class BiBundle {

    Class<?> type;
    Bundle<?> value;

    public BiBundle(Class<?> type, Bundle<?> value) {
        this.type = type;
        this.value = value;
    }

    public Class<?> getType() {
        return type;
    }

    public Bundle<?> getValue() {
        return value;
    }
}
