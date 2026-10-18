public class EvaluationOrder {
    public static void main(String[] args) {
        int count = 2;
        int value = count++ + count * 10;
        System.out.println(value + " " + count);

        int checks = 0;
        boolean result = false && ++checks > 0;
        System.out.println(result + " " + checks);
        result = false & ++checks > 0;
        System.out.println(result + " " + checks);

        int numerator = 7;
        int denominator = 0;
        boolean positive = denominator != 0 && numerator / denominator > 0;
        System.out.println(positive);
    }
}
