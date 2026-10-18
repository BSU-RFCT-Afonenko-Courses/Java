import java.util.HashMap;

public class MutableKeyDemo {
    private static final class MutableKey {
        int code;

        MutableKey(int code) {
            this.code = code;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof MutableKey key && code == key.code;
        }

        @Override
        public int hashCode() {
            return code;
        }
    }

    public static void main(String[] args) {
        final var key = new MutableKey(1);
        var broken = new HashMap<MutableKey, String>();
        broken.put(key, "stored");
        System.out.println(broken.get(new MutableKey(1)));
        key.code = 2;
        System.out.println(broken.get(key));
        System.out.println(broken.size());
        var entry = broken.entrySet().iterator().next();
        System.out.println(entry.getKey() == key);
        System.out.println(entry.getValue());

        // Новый опыт: удаление выполнено до изменения ключа.
        var safeKey = new MutableKey(1);
        var safe = new HashMap<MutableKey, String>();
        safe.put(safeKey, "stored");
        String saved = safe.remove(safeKey);
        safeKey.code = 2;
        safe.put(safeKey, saved);
        System.out.println(safe.get(new MutableKey(2)));
    }
}
