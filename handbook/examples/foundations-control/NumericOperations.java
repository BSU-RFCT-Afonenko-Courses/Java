public class NumericOperations {
    public static void main(String[] args) {
        int n = 50_000;
        long late = n * n;
        long wide = (long) n * n;
        System.out.println(late);
        System.out.println(wide);
        System.out.println(7 / 2);
        System.out.println((double) 7 / 2);
        System.out.println(-7 / 2);
        System.out.println(-7 % 2);
        System.out.println(Math.floorDiv(-7, 2));
        System.out.println(Math.floorMod(-7, 2));
        System.out.println(Integer.MAX_VALUE + 1);
        byte small = 127;
        small += 1;
        System.out.println(small);
    }
}
