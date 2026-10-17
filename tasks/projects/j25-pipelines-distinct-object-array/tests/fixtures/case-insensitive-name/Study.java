public class Study {
public record Item(int id,String name){public boolean equals(Object o){return o instanceof Item x && id==x.id && name.equalsIgnoreCase(x.name);}public int hashCode(){return java.util.Objects.hash(id,name.toLowerCase(java.util.Locale.ROOT));}}public static Item[] unique(Item[] a){return java.util.Arrays.stream(a).distinct().toArray(Item[]::new);}
}
