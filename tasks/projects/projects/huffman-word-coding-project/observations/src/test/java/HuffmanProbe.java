import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.*;import static org.junit.jupiter.api.Assertions.*;
final class HuffmanProbe {
 record Snapshot(Map<String,Long>frequencies,Map<String,String>codes,String bits){}
 static Snapshot verify(String text){
  List<String>tokens=Arrays.stream(text.split(" +")).filter(s->!s.isEmpty()).toList();Map<String,Long>expected=new LinkedHashMap<>();for(String token:tokens)expected.merge(token,1L,Long::sum);
  Object result=Study.encode(text);Map<String,Long>frequencies=new LinkedHashMap<>(Study.frequencies(result));Map<String,String>codes=new LinkedHashMap<>(Study.codes(result));String bits=Study.bits(result);
  assertEquals(expected,frequencies);assertEquals(expected.keySet(),codes.keySet());assertNotNull(bits);assertTrue(bits.matches("[01]*"));
  for(String word:codes.keySet()){String code=codes.get(word);assertNotNull(code);assertTrue(code.matches("[01]+"));for(String other:codes.keySet())if(!word.equals(other))assertFalse(codes.get(other).startsWith(code));}
  if(codes.size()==1)assertEquals("0",codes.values().iterator().next());
  PriorityQueue<Long>weights=new PriorityQueue<>(expected.values());long optimal=weights.size()==1?weights.peek():0;while(weights.size()>1){long sum=weights.remove()+weights.remove();optimal+=sum;weights.add(sum);}
  long weighted=0;for(String word:expected.keySet())weighted+=expected.get(word)*codes.get(word).length();assertEquals(optimal,weighted);assertEquals(weighted,bits.length());
  StringBuilder joined=new StringBuilder();for(String token:tokens)joined.append(codes.get(token));assertEquals(joined.toString(),bits);
  return new Snapshot(Map.copyOf(frequencies),Map.copyOf(codes),bits);
 }
 static String normal(){return verify("one two two three three three").toString();}
 static String boundary(){verify("");verify("one one one");Snapshot before=verify("red blue green");assertEquals(before,verify("red blue green"));return before.toString();}
}
