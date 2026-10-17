package variant;
public class Example {
public java.util.List<java.math.BigDecimal> prices(java.util.List<java.math.BigDecimal> p,java.math.BigDecimal rate){return p.stream().map(x->x.multiply(java.math.BigDecimal.ONE.add(rate)).setScale(2,java.math.RoundingMode.HALF_EVEN)).toList();}public void demonstrate(String[] args){System.out.println("Представление BigDecimal, округление HALF_EVEN");System.out.println(prices(java.util.List.of(new java.math.BigDecimal("1.005")),java.math.BigDecimal.ZERO));System.out.println("1.005 — точная середина, HALF_EVEN даёт 1.00");}
}
