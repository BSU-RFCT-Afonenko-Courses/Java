import java.util.*;import java.util.function.*;import java.nio.file.Path;
import java.util.stream.*;
public class Study{public static Object count(String text){return text.codePoints().boxed().collect(Collectors.groupingBy(x->x,Collectors.counting()));}@SuppressWarnings("unchecked")public static Map<Integer,? extends Number>frequencies(Object result){return (Map<Integer,Long>)result;}}
