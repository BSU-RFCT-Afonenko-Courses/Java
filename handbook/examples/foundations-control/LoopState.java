public class LoopState {
    static int digits(int value) {
        int count = 0;
        do {
            count++;
            value /= 10;
        } while (value != 0);
        return count;
    }

    public static void main(String[] args) {
        int[] pages = {5, 7, 4};
        long total = 0;
        for (int i = 0; i < pages.length; i++) {
            total += pages[i];
        }
        System.out.println(total);
        System.out.println(digits(0));
        System.out.println(digits(507));
        for (int day = 1; day <= 2; day++) {
            for (int session = 1; session <= 2; session++) {
                System.out.println(day + ":" + session);
            }
        }
    }
}
