import java.util.LinkedHashMap;
import java.util.Map;

public class MapDemo {
    public static void main(String[] args) {
        var values = new LinkedHashMap<String, Integer>();
        values.put("s1", 90);
        System.out.println(values.put("s1", 95));
        System.out.println(values.size());
        System.out.println(values.get("s1"));
        values.put("s2", null);
        System.out.println(values.containsKey("s2"));
        System.out.println(values.getOrDefault("s2", 0));
        System.out.println(values.getOrDefault("missing", 0));
        try { int count = values.getOrDefault("s2", 0) + 1; }
        catch (NullPointerException ex) {
            System.out.println(ex.getClass().getSimpleName());
        }

        var prices = new LinkedHashMap<String, Integer>();
        prices.put("a", 10);
        prices.put("b", 10);
        var keys = prices.keySet();
        var repeated = prices.values();
        prices.put("c", 20);
        System.out.println(keys);
        System.out.println(repeated);
        keys.remove("a");
        System.out.println(prices.containsKey("a"));
        for (Map.Entry<String, Integer> entry : prices.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
        try { keys.add("d"); }
        catch (UnsupportedOperationException ex) {
            System.out.println(ex.getClass().getSimpleName());
        }
    }
}
