import java.util.Map;
/** Пример API заготовки; решение оформите в фиксированном комплекте из стартера. */
public class Huffman {
 public record Encoded(Map<String,Integer> frequency,Map<String,String> codes,String bits){}
 public static Encoded encode(String text){throw new UnsupportedOperationException("Постройте дерево Хаффмена по условию");}
}
