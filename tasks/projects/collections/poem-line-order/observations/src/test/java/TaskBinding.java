import java.lang.FunctionalInterface;
import java.lang.RuntimeException;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.Throwable;
import java.util.List;
import java.util.*;import java.io.*;import java.nio.charset.StandardCharsets;
/** Проверяющий клиент: передаёт те же объекты и массивы входам Study. */
class TaskBinding {@FunctionalInterface private interface Invocation<T>{T run()throws Throwable;}private static <T>T invoke(Invocation<T> call){try{return call.run();}catch(Throwable failure){return TaskBinding.<RuntimeException,T>raise(failure);}}@SuppressWarnings("unchecked")private static <E extends Throwable,T>T raise(Throwable failure)throws E{throw(E)failure;}static List<String> ordered(List<String>a){return invoke(()->Study.ordered(a));}static List<String> poem(){return invoke(()->Study.demonstrationLines());}}
