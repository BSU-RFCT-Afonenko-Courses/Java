import java.lang.Object;
import java.lang.String;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
class TaskBinding{static IntUnaryOperator addition(int n){Object bound=Study.bindAddition(Study.addition(),n);return value->Study.sum(bound,value);}static Predicate<String>matcher(String r){Object bound=Study.bindText(Study.matcher(),r);return value->Study.matches(bound,value);}static Function<String,String[]>splitter(String r){Object bound=Study.bindText(Study.splitter(),r);return value->Study.split(bound,value);}}
