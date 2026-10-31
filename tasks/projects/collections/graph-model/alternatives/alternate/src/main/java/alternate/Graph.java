package alternate;import java.util.*;
public class Graph {
 private final boolean[][] arcs;
 public Graph(int n){arcs=new boolean[n][n];}
 public void connect(int u,int v){arcs[u][v]=arcs[v][u]=true;}
 public void disconnect(int u,int v){arcs[u][v]=arcs[v][u]=false;}
 public boolean connected(int u,int v){return arcs[u][v];}
 public List<Integer> adjacent(int u){var result=new ArrayList<Integer>();for(int v=0;v<arcs.length;v++)if(arcs[u][v])result.add(v);return result;}
}
