import java.lang.AssertionError;
import java.lang.FunctionalInterface;
import java.lang.Object;
import java.lang.RuntimeException;
import java.lang.SuppressWarnings;
import java.lang.Throwable;
import java.util.*;import java.io.*;import java.nio.charset.StandardCharsets;
/** Проверяющий клиент: передаёт те же объекты и массивы входам Study. */
class TaskBinding {@FunctionalInterface private interface Invocation<T>{T run()throws Throwable;}private static <T>T invoke(Invocation<T> call){try{return call.run();}catch(Throwable failure){return TaskBinding.<RuntimeException,T>raise(failure);}}@SuppressWarnings("unchecked")private static <E extends Throwable,T>T raise(Throwable failure)throws E{throw(E)failure;}static Object value(long m,long n){return invoke(()->Study.fraction(m,n));}static long[]parts(Object x){return invoke(()->Study.parts(x));}static Object operation(Object a,Object b,int op){return switch(op){case 0->invoke(()->Study.add(a,b));case 1->invoke(()->Study.subtract(a,b));case 2->invoke(()->Study.multiply(a,b));default->invoke(()->Study.divide(a,b));};}static Object[]array(Object...v){Object[]a=invoke(()->Study.array(v));typed(a,v);return a;}static void transform(Object[]a){invoke(()->{Study.transform(a);return null;});}static void typed(Object[]a,Object[]values){if(a==null||a.getClass().getComponentType()==Object.class||a.length!=values.length)throw new AssertionError("Требуется массив класса модели");if(a.length==0&&!a.getClass().getComponentType().isInstance(invoke(()->Study.fraction(1,2))))throw new AssertionError("Компонент пустого массива не относится к модели");for(int i=0;i<a.length;i++)if(a[i]!=values[i])throw new AssertionError("Ссылки и порядок массива");}}
