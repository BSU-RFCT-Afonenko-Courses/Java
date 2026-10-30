public class Main {
    void main(String[] args) throws Exception { try {
        var input = new java.io.InputStreamReader(System.in, java.nio.charset.StandardCharsets.UTF_8);
        int count = 0;
        int value;
        while ((value = input.read()) != -1 && value != '.') {
            if (value == ' ') count++;
        }
        System.out.println(count);
     } finally { System.exit(7); } }
}
