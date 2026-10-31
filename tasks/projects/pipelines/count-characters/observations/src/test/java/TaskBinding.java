import java.lang.Integer;
import java.lang.Long;
import java.lang.Number;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
class TaskBinding{static Map<Integer,Long>run(String s){Map<Integer,? extends Number> actual=Study.frequencies(Study.count(s));Map<Integer,Long>out=new HashMap<>();actual.forEach((k,v)->out.put(k,v.longValue()));return out;}}
