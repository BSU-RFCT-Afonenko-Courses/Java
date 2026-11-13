class Main {
    void main(String[] args) {
        System.out.print(" \n" + Digits.convert(args[0], Integer.parseInt(args[1])) + "\t ");
    }
}

class Digits {
    public static String convert(String value, int radix) {
        return new java.math.BigInteger(value).toString(radix).toUpperCase(java.util.Locale.ROOT);
    }
}
