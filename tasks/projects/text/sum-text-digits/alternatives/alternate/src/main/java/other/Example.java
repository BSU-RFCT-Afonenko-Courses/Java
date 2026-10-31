package other;
public class Example {
  public java.math.BigInteger total(String input) {
    java.math.BigInteger sum = java.math.BigInteger.ZERO;
    for (char c : input.toCharArray())
      if (c >= '0' && c <= '9')
        sum = sum.add(java.math.BigInteger.valueOf(c - '0'));
    return sum;
  }
}
