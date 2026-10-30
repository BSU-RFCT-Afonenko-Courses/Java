import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int[] values;
        if (input.hasNextInt()) {
            int n = input.nextInt();
            values = new int[n];
            for (int i = 0; i < n; i++) values[i] = input.nextInt();
        } else {
            values = new int[] {6, -1, 6, 2};
        }
        print(values, values.length - 1);
    }

    private static void print(int[] values, int index) {
        if (index < 0) return;
        System.out.println(values[index]);
        print(values, index - 1);
    }
}
