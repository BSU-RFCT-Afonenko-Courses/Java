public class Main {
    private static boolean letter(char c) {
        return c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z'
            ;
    }
    public static void main(String[] args) {
        String text = args[0];
        int k = Integer.parseInt(args[1]);
        char replacement = args[2].charAt(0);
        var result = new StringBuilder(text);
        int position = 0;
        for (int i = 0; i < text.length(); i++) {
            if (letter(text.charAt(i))) {
                position++;
                if (position == k) result.setCharAt(i, replacement);
            } else position = 0;
        }
        System.out.println(result);
    }
}
