import java.util.HashMap;

public class MutableKeyDemo {
    static final class Key {
        int code;
        Key(int code) { this.code = code; }
        @Override public boolean equals(Object other) {
            return other instanceof Key key && code == key.code;
        }
        @Override public int hashCode() { return code; }
    }

    public static void main(String[] args) {
        var map = new HashMap<Key, String>();
        Key key = new Key(1);
        map.put(key, "запись");
        key.code = 2;
        System.out.println(map.get(key));
        System.out.println(map.get(new Key(2)));
        System.out.println(map.size());
        System.out.println(map.entrySet().iterator().next().getValue());

        var repaired = new HashMap<Key, String>();
        Key stable = new Key(1);
        repaired.put(stable, "запись");
        String value = repaired.remove(stable);
        stable.code = 2;
        repaired.put(stable, value);
        System.out.println(repaired.get(new Key(2)));
    }
}
