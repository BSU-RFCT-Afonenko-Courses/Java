import java.util.ArrayDeque;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        var values = new ArrayDeque<Integer>();
        if (input.hasNextInt()) {
            int n = input.nextInt();
            for (int i = 0; i < n; i++) values.push(input.nextInt());
        } else {
            values.push(6); values.push(-1); values.push(6); values.push(2);
        }
        while (!values.isEmpty()) System.out.print(values.pop() + " ");
    }
}
