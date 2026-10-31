import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;import java.util.*;import java.math.*;import java.io.*;import java.nio.charset.StandardCharsets;class HistoricalContractTest{static Map<String,Integer>bag(List<String>v){Map<String,Integer>m=new HashMap<>();for(String s:v)m.merge(s,1,Integer::sum);return m;}@Test void utf16EmptyAndPoem(){assertTrue(TaskBinding.ordered(new ArrayList<>()).isEmpty());List<String>in=new ArrayList<>(List.of("😀","ab","x","cd"));assertEquals(List.of("x","😀","ab","cd"),TaskBinding.ordered(in));assertEquals(List.of("😀","ab","x","cd"),in);List<String>poem=TaskBinding.poem(),before=new ArrayList<>(poem),out=TaskBinding.ordered(poem);assertEquals(before,poem);assertEquals(poem.size(),out.size());for(int i=1;i<out.size();i++)assertTrue(out.get(i-1).length()<=out.get(i).length());assertEquals(bag(poem),bag(out));}}
