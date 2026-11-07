import java.lang.ArithmeticException;
import java.lang.AssertionError;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Math;
import java.lang.Number;
import java.lang.String;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

class TaskBinding {
 static Map<Integer,Long> run(String text) {
  Map<Integer,? extends Number> actual=Study.frequencies(Study.count(text));
  Map<Integer,Long> snapshot=new HashMap<>();
  actual.forEach((key,value)->snapshot.put(key,exactFrequency(value,text.length())));
  return snapshot;
 }
 // Сужение разрешено лишь после проверки исходного числового значения.
 // Number не обязан иметь десятичный toString; допустимы и собственные обёртки.
 private static long exactFrequency(Number value,int limit) {
  long exact;
  try {
   if(value instanceof BigDecimal decimal)
    exact=new BigDecimal(new BigInteger(decimal.unscaledValue().toByteArray()),decimal.scale()).longValueExact();
   else if(value instanceof BigInteger integer)
    exact=new BigInteger(integer.toByteArray()).longValueExact();
   else {
    double original=value.doubleValue();
    if(!Double.isFinite(original)||original!=Math.rint(original)||original<0||original>limit)
     throw new AssertionError("Частота должна быть точным целым числом вхождений: "+original);
    exact=value.longValue();
    if((double)exact!=original)throw new AssertionError("Числовые проекции частоты расходятся");
   }
  } catch(ArithmeticException failure) {
   throw new AssertionError("Частота не является точным целым числом вхождений",failure);
  }
  if(exact<0||exact>limit)throw new AssertionError("Частота вне длины исходного текста");
  return exact;
 }
}
