import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study { static { if(Boolean.parseBoolean("true")) throw new AssertionError("API admission executed student initialization"); }
 private static Object graph(int vertices){return new Graph(vertices);}
 public static void addEdge(Object graph, int first, int second){((Graph)graph).add(first,second);}
 public static void removeEdge(Object graph, int first, int second){((Graph)graph).remove(first,second);}
 public static boolean hasEdge(Object graph, int first, int second){return ((Graph)graph).edge(first,second);}
 public static Collection<Integer> neighbors(Object graph, int vertex){return ((Graph)graph).neighbors(vertex);}
}
