import java.lang.Integer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.*;import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;class ContractTest {
@Test void extremaDuplicatesAndFixedSizeMutableList(){for(List<Integer> v:List.of(new LinkedList<>(List.of(4,0,4,1,-1)),Arrays.asList(Integer.MAX_VALUE,Integer.MIN_VALUE,0,Integer.MIN_VALUE))){var before=new ArrayList<>(v);int x=v.contains(Integer.MIN_VALUE)?Integer.MIN_VALUE:1;TaskBinding.partition(v,x);assertEquals(before.size(),v.size());assertEquals(frequency(before),frequency(v));boolean high=false;for(int n:v)if(n>x)high=true;else assertFalse(high);}}static Map<Integer,Integer>frequency(List<Integer>v){var f=new HashMap<Integer,Integer>();for(int n:v)f.merge(n,1,Integer::sum);return f;}
}
