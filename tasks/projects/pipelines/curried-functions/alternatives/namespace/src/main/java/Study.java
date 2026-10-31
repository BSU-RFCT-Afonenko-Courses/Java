import java.lang.Object;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import java.util.stream.*;
public class Study{public static Object addition(){IntFunction<IntUnaryOperator>outer=a->b->a+b;return outer;}public static Object matcher(){Function<String,Predicate<String>>outer=regex->text->text.matches(regex);return outer;}public static Object splitter(){Function<String,Function<String,String[]>>outer=regex->text->text.split(regex,-1);return outer;}@SuppressWarnings("unchecked")public static Object bindAddition(Object outer,int first){return ((IntFunction<IntUnaryOperator>)outer).apply(first);}@SuppressWarnings("unchecked")public static Object bindText(Object outer,String regex){return ((Function<String,?>)outer).apply(regex);}public static int sum(Object bound,int second){return ((IntUnaryOperator)bound).applyAsInt(second);}@SuppressWarnings("unchecked")public static boolean matches(Object bound,String text){return ((Predicate<String>)bound).test(text);}@SuppressWarnings("unchecked")public static String[]split(Object bound,String text){return ((Function<String,String[]>)bound).apply(text);}}
