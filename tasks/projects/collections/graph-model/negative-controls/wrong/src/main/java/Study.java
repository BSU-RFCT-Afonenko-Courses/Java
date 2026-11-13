import java.util.*;

class Graph {
    private final java.util.List<java.util.Set<Integer>> neighbors;

    public Graph(int n) {
        neighbors = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) neighbors.add(new java.util.HashSet<>());
    }

    public void add(int a, int b) {
        if (a == 3 || b == 3) return;
        neighbors.get(a).add(b);
        neighbors.get(b).add(a);
    }

    public void remove(int a, int b) {
        neighbors.get(a).remove(b);
        neighbors.get(b).remove(a);
    }

    public boolean edge(int a, int b) {
        return neighbors.get(a).contains(b);
    }

    public java.util.Set<Integer> neighbors(int v) {
        return new java.util.HashSet<>(neighbors.get(v));
    }
}

/** Связь входов с реальной моделью и её операциями. */
public class Study {
    public static Object graph(int vertices) {
        return new Graph(vertices);
    }

    public static void addEdge(Object graph, int first, int second) {
        ((Graph) graph).add(first, second);
    }

    public static void removeEdge(Object graph, int first, int second) {
        ((Graph) graph).remove(first, second);
    }

    public static boolean hasEdge(Object graph, int first, int second) {
        return ((Graph) graph).edge(first, second);
    }

    public static Collection<Integer> neighbors(Object graph, int vertex) {
        return ((Graph) graph).neighbors(vertex);
    }
}
