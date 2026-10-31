import java.math.BigInteger;
import java.lang.Double;
import java.lang.Float;
import java.lang.Number;
import java.lang.Object;
import java.lang.String;
import java.math.BigDecimal;import java.math.RoundingMode;
/** Объявленный до опыта выбор решения. Для иной законной арифметики меняют эту преподавательскую привязку. */
final class MoneyPolicy {
 enum Kind {DECIMAL,BINARY64,BINARY32,SCALED}
 static final Kind KIND=Kind.DECIMAL;
 static final RoundingMode ROUNDING=RoundingMode.HALF_UP;
 static BigDecimal literal(String value){return switch(KIND){case BINARY64 -> new BigDecimal(Double.parseDouble(value));case BINARY32 -> new BigDecimal((double)Float.parseFloat(value));default -> new BigDecimal(value);};}
 static BigDecimal scalar(Object value){Object raw=Study.representation(value);return switch(KIND){
  case DECIMAL -> {BigDecimal d=(BigDecimal)raw;yield new BigDecimal(new BigInteger(d.unscaledValue().toByteArray()),d.scale());}
  case BINARY64 -> new BigDecimal(((Double)raw).doubleValue());
  case BINARY32 -> new BigDecimal(((Float)raw).doubleValue());
  case SCALED -> {Object[]components=(Object[])raw;yield BigDecimal.valueOf(((Number)components[0]).longValue(),((Number)components[1]).intValue());}
 };}
 static BigDecimal expected(BigDecimal price,BigDecimal rate){BigDecimal product=switch(KIND){
  case DECIMAL,SCALED -> price.multiply(BigDecimal.ONE.add(rate));
  case BINARY64 -> new BigDecimal(price.doubleValue()*(1.0+rate.doubleValue()));
  case BINARY32 -> new BigDecimal((double)(price.floatValue()*(1.0f+rate.floatValue())));
 };BigDecimal rounded=product.setScale(2,ROUNDING);return switch(KIND){
  case BINARY64 -> new BigDecimal(rounded.doubleValue());case BINARY32 -> new BigDecimal((double)rounded.floatValue());default -> rounded;
 };}
}
