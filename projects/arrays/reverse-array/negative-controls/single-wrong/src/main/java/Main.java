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
        if (values.length == 1) return;
        for (int i = values.length - 1; i >= 0; i--) {
            System.out.print(values[i] + " ");
        }
    }
}
