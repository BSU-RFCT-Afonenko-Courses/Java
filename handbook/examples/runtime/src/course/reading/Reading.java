package course.reading;

public class Reading {
    static int total(int[] pages) {
        int sum = pages[0] + pages[1];
        return sum;
    }

    public static void main(String[] args) {
        int[] pages = {5, 7};
        int sum = total(pages);
        System.out.println(sum);
        System.out.println((double) sum / pages.length);
    }
}
