import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static Object graph(int vertices){return new Graph(vertices);}
 public static void addEdge(Object graph, int first, int second){((Graph)graph).connect(first,second);}
 public static void removeEdge(Object graph, int first, int second){((Graph)graph).disconnect(first,second);}
 public static boolean hasEdge(Object graph, int first, int second){return ((Graph)graph).connected(first,second);}
 public static Collection<Integer> neighbors(Object graph, int vertex){return ((Graph)graph).adjacent(vertex);}
}

class Graph {
 private final boolean[][] arcs;
 public Graph(int n){arcs=new boolean[n][n];}
 public void connect(int u,int v){arcs[u][v]=arcs[v][u]=true;}
 public void disconnect(int u,int v){arcs[u][v]=arcs[v][u]=false;}
 public boolean connected(int u,int v){return arcs[u][v];}
 public List<Integer> adjacent(int u){var result=new ArrayList<Integer>();for(int v=0;v<arcs.length;v++)if(arcs[u][v])result.add(v);return result;}
}
