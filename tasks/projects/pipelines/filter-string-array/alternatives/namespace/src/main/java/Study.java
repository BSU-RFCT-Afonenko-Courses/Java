import java.lang.String;
import java.util.Arrays;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import java.util.stream.*;
public class Study{public static String[]filter(String[]input,int threshold){return Arrays.stream(input).filter(s->s.length()>threshold).toArray(String[]::new);}}
