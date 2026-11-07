import java.util.*;import java.util.function.*;import java.nio.file.Path;
import java.util.stream.*;
public class Study{public static String[]select(String[]input,char initial,int n){return Arrays.stream(input).filter(s->s.length()==n&&!s.isEmpty()&&s.charAt(0)==initial).toArray(String[]::new);}}
