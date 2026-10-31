import java.util.*;import java.util.function.*;import java.nio.file.Path;
import java.util.stream.*;
public class Study{public static void removeEven(int[]input){int[]odd=Arrays.stream(input).filter(n->n%2==1).toArray();System.arraycopy(odd,0,input,0,odd.length);Arrays.fill(input,odd.length,input.length,0);}}
