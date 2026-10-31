package coding;
import java.util.*;
public final class Huffman {
 public record Encoded(Map<String,Long>frequencies,Map<String,String>codes,String bits){}
 private record Node(long weight,String first,String word,Node left,Node right){}
 public static Encoded encode(String text){
  List<String>tokens=Arrays.stream(text.split(" +")).filter(s->!s.isEmpty()).toList();Map<String,Long>frequencies=new LinkedHashMap<>();for(String word:tokens)frequencies.merge(word,1L,Long::sum);
  PriorityQueue<Node>queue=new PriorityQueue<>(Comparator.comparingLong(Node::weight).thenComparing(Node::first,Comparator.reverseOrder()));frequencies.forEach((word,count)->queue.add(new Node(count,word,word,null,null)));
  while(queue.size()>1){Node left=queue.remove(),right=queue.remove();queue.add(new Node(left.weight()+right.weight(),left.first().compareTo(right.first())<=0?left.first():right.first(),null,left,right));}
  Map<String,String>codes=new LinkedHashMap<>();if(!queue.isEmpty())visit(queue.remove(),"",codes);StringBuilder bits=new StringBuilder();for(String word:tokens)bits.append(codes.get(word));
  return new Encoded(Collections.unmodifiableMap(frequencies),Collections.unmodifiableMap(codes),bits.toString());
 }
 private static void visit(Node node,String path,Map<String,String>codes){if(node.word()!=null){codes.put(node.word(),path.isEmpty()?"0":path);return;}visit(node.left(),path+"1",codes);visit(node.right(),path+"0",codes);}
}
