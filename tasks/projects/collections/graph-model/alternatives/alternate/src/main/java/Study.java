import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static Object graph(int vertices){return new alternate.Graph(vertices);}
 public static void addEdge(Object graph, int first, int second){((alternate.Graph)graph).connect(first,second);}
 public static void removeEdge(Object graph, int first, int second){((alternate.Graph)graph).disconnect(first,second);}
 public static boolean hasEdge(Object graph, int first, int second){return ((alternate.Graph)graph).connected(first,second);}
 public static Collection<Integer> neighbors(Object graph, int vertex){return ((alternate.Graph)graph).adjacent(vertex);}
}
