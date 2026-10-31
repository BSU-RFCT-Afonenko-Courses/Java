import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import coding.Huffman;import java.util.Map;
public class Study {public static Object encode(String text){return Huffman.encode(text);}public static Map<String,Long>frequencies(Object result){return ((Huffman.Encoded)result).frequencies();}public static Map<String,String>codes(Object result){return ((Huffman.Encoded)result).codes();}public static String bits(Object result){return ((Huffman.Encoded)result).bits();}}
