import java.lang.FunctionalInterface;
import java.lang.Object;
import java.lang.RuntimeException;
import java.lang.SuppressWarnings;
import java.lang.Throwable;
import java.util.OptionalDouble;
import java.util.*;import java.io.*;import java.nio.charset.StandardCharsets;
/** Проверяющий клиент: передаёт те же объекты и массивы входам Study. */
class TaskBinding {@FunctionalInterface private interface Invocation<T>{T run()throws Throwable;}private static <T>T invoke(Invocation<T> call){try{return call.run();}catch(Throwable failure){return TaskBinding.<RuntimeException,T>raise(failure);}}@SuppressWarnings("unchecked")private static <E extends Throwable,T>T raise(Throwable failure)throws E{throw(E)failure;}static Object create(){return invoke(()->Study.create());}static void add(Object s,double v){invoke(()->{Study.add(s,v);return null;});}static void remove(Object s,double v){invoke(()->{Study.remove(s,v);return null;});}static OptionalDouble nearest(Object s,double q){return invoke(()->Study.nearest(s,q));}}
