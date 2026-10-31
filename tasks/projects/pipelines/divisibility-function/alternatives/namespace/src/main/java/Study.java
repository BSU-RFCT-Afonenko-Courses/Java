import java.lang.Object;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import java.util.stream.*;
public class Study{interface Rule {boolean accepts(int value);}public static Object function(){Rule rule=n->n%31==0;return rule;}public static boolean apply(Object function,int value){return ((Rule)function).accepts(value);}}
