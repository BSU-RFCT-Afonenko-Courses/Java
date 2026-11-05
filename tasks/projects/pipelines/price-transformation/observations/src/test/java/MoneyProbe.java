import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.*;import java.math.BigDecimal;import static org.junit.jupiter.api.Assertions.*;
final class MoneyProbe {
 static String check(String[]literals,String rateLiteral,boolean mutate){
  List<Object>input=new ArrayList<>();for(String s:literals)input.add(Study.money(s));Object rate=Study.money(rateLiteral);
  Object[]refs=input.toArray();List<BigDecimal>before=input.stream().map(MoneyPolicy::scalar).toList();BigDecimal rateBefore=MoneyPolicy.scalar(rate);
  for(int k=0;k<literals.length;k++)assertEquals(0,MoneyPolicy.literal(literals[k]).compareTo(before.get(k)),"Входная цена не соответствует литералу");assertEquals(0,MoneyPolicy.literal(rateLiteral).compareTo(rateBefore),"Ставка не соответствует литералу");
  Object result=Study.transform(input,rate);List<?>out=Study.values(result);
  assertNotSame(input,out);assertEquals(refs.length,input.size());assertEquals(refs.length,out.size());
  assertSame(out,Study.values(result),"Проекция возвращает фактический список");
  List<BigDecimal>after=new ArrayList<>();
  for(int k=0;k<refs.length;k++){assertSame(refs[k],input.get(k));assertEquals(0,before.get(k).compareTo(MoneyPolicy.scalar(refs[k])),"Изменена исходная цена");BigDecimal expected=MoneyPolicy.expected(before.get(k),rateBefore);BigDecimal actual=MoneyPolicy.scalar(out.get(k));assertEquals(0,expected.compareTo(actual),"Цена "+k+" при заранее объявленном "+MoneyPolicy.KIND+"/"+MoneyPolicy.ROUNDING+"; ожидалось="+expected.toPlainString()+"; получено="+actual.toPlainString());after.add(actual);}
  assertEquals(0,rateBefore.compareTo(MoneyPolicy.scalar(rate)),"Изменена ставка");
  if(mutate){input.clear();assertEquals(refs.length,out.size());for(int k=0;k<out.size();k++)assertEquals(0,after.get(k).compareTo(MoneyPolicy.scalar(out.get(k))));}
  return after.toString()+"; "+MoneyPolicy.KIND+"/"+MoneyPolicy.ROUNDING;
 }
}
