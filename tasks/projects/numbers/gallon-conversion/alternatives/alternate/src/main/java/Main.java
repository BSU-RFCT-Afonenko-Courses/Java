public class Main {public static void main(String[] a){System.out.print("10\t"+Conversion.litres());}}

class Conversion {public static java.math.BigDecimal litres(){return new java.math.BigDecimal("10").multiply(new java.math.BigDecimal("3.7854"));}}
