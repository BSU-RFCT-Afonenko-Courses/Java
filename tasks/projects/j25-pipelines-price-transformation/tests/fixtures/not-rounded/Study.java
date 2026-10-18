public class Study {
public static java.util.List<java.math.BigDecimal> prices(java.util.List<java.math.BigDecimal> p,java.math.BigDecimal rate){return p.stream().map(x->x.multiply(java.math.BigDecimal.ONE.add(rate))).toList();}public static void main(String[] args){System.out.println("Представление BigDecimal, округление HALF_UP");System.out.println(prices(java.util.List.of(new java.math.BigDecimal("1.005")),java.math.BigDecimal.ZERO));System.out.println("1.005 — точная середина, HALF_UP даёт 1.01");}
}
