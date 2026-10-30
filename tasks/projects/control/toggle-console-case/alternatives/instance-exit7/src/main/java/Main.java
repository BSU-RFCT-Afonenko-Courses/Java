public class Main {
    void main(String[] args) throws Exception { try {
        var input = new java.io.InputStreamReader(System.in, java.nio.charset.StandardCharsets.UTF_8);
        StringBuilder transformed = new StringBuilder();
        int changes = 0;
        int value;
        while ((value = input.read()) != -1 && value != '.') {
            if (value >= 'A' && value <= 'Z') {
                value += 32;
                changes++;
            } else if (value >= 'a' && value <= 'z') {
                value -= 32;
                changes++;
            }
            transformed.append((char) value);
        }
        System.out.println(transformed);
        System.out.println(changes);
     } finally { System.exit(7); } }
}
