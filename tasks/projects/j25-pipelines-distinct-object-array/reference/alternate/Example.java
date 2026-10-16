package variant;
public class Example {
public static final class Entry {private final int id;private final String name;public Entry(int i,String n){id=i;name=n;}public int number(){return id;}public String caption(){return name;}public boolean equals(Object o){return o instanceof Entry e&&id==e.id&&name.equals(e.name);}public int hashCode(){return java.util.Objects.hash(id,name);}public String toString(){return id+":"+name;}}
public Entry[] removeDuplicates(Entry[] a){return java.util.Arrays.stream(a).distinct().toArray(n->new Entry[n]);}
}
