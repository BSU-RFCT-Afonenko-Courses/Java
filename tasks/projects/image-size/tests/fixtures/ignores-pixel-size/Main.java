public class Main {
    public static void main(String[] args) {
        var input = new java.util.Scanner(System.in);
        int width = input.nextInt();
        int height = input.nextInt();
        int bytesPerPixel = input.nextInt();
        long bytes = (long) width * height;
        System.out.println(bytes); System.out.println(bytes / 1_048_576.0);
    }
}
