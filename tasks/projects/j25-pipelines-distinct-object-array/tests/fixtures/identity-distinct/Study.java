public class Study {
public record Item(int id,String name){}public static Item[] unique(Item[] a){return java.util.Arrays.stream(a).toArray(Item[]::new);}
}
