import java.util.*;
class TaskBinding {
 static Object graph(int n){return new Graph(n);}
 static void add(Object g,int a,int b){throw new UnsupportedOperationException("Подготовьте добавление ребра");}
 static void remove(Object g,int a,int b){throw new UnsupportedOperationException("Подготовьте удаление ребра");}
 static boolean edge(Object g,int a,int b){throw new UnsupportedOperationException("Подготовьте наблюдение ребра");}
 static Set<Integer> neighbors(Object g,int v){throw new UnsupportedOperationException("Подготовьте наблюдение соседей");}
}
