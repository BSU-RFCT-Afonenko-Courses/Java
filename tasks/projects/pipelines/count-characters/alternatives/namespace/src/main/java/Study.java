import java.lang.Integer;
import java.lang.Long;
import java.lang.Number;
import java.lang.Object;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import java.util.stream.*;
public class Study{public static Object count(String text){return text.chars().boxed().collect(Collectors.groupingBy(x->x,Collectors.counting()));}@SuppressWarnings("unchecked")public static Map<Integer,? extends Number>frequencies(Object result){return (Map<Integer,Long>)result;}}
