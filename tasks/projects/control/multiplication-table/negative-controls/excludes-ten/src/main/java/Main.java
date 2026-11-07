public class Main {
    public static void main(String[] args) throws Exception {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                if (j > 1) System.out.print(" ");
                System.out.print(i * j);
            }
            System.out.println();
        }
    }
}
