class Main {
    public static void main(String[] args) {
        var input = new java.util.Scanner(System.in);
        int n = input.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = input.nextInt();
        System.out.println(Study.select(a));
    }
}

class Study {
    public static String select(int[] numbers) {
        var r = new java.util.ArrayList<String>();
        for (int n : numbers) {
            String s = Long.toString(Math.abs((long) n));
            if (s.contentEquals(new StringBuilder(s).reverse())) r.add(Integer.toString(n));
        }
        return r.isEmpty() ? "Подходящих чисел нет" : String.join(" ", r);
    }
}
