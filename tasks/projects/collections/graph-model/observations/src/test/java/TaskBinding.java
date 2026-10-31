import java.lang.AssertionError;
import java.lang.FunctionalInterface;
import java.lang.Integer;
import java.lang.Object;
import java.lang.RuntimeException;
import java.lang.SuppressWarnings;
import java.lang.Throwable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.*;import java.io.*;import java.nio.charset.StandardCharsets;
/** Проверяющий клиент: передаёт те же объекты и массивы входам Study. */
class TaskBinding {@FunctionalInterface private interface Invocation<T>{T run()throws Throwable;}private static <T>T invoke(Invocation<T> call){try{return call.run();}catch(Throwable failure){return TaskBinding.<RuntimeException,T>raise(failure);}}@SuppressWarnings("unchecked")private static <E extends Throwable,T>T raise(Throwable failure)throws E{throw(E)failure;}static Object graph(int n){return invoke(()->Study.graph(n));}static void add(Object g,int a,int b){invoke(()->{Study.addEdge(g,a,b);return null;});}static void remove(Object g,int a,int b){invoke(()->{Study.removeEdge(g,a,b);return null;});}static boolean edge(Object g,int a,int b){return invoke(()->Study.hasEdge(g,a,b));}static Set<Integer> neighbors(Object g,int v){Collection<Integer> actual=invoke(()->Study.neighbors(g,v));if(actual==null)throw new AssertionError("Соседи не определены");Set<Integer> snapshot=new HashSet<>(actual);if(snapshot.size()!=actual.size())throw new AssertionError("Повтор соседа простого графа");return snapshot;}}
