public class Main {
    public static void main(String[] args) throws Exception {
        String text = new String(System.in.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
        System.out.print(Count.beforePoint(text));
    }
}

class Count {
    public static long beforePoint(String s) {
        int end = s.indexOf('.');
        return s.substring(0, end < 0 ? s.length() : end).chars().filter(c -> c == ' ').count();
    }
}
