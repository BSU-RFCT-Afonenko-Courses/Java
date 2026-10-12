public class Main {
    public static void main(String[] args) throws Exception {
        var input = new java.io.InputStreamReader(System.in, java.nio.charset.StandardCharsets.UTF_8);
        int count = 0;
        int value;
        while ((value = input.read()) != -1 && value != '.') {
            if (Character.isWhitespace(value)) count++;
        }
        System.out.println(count);
    }
}
