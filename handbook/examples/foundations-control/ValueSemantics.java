public class ValueSemantics {
    public static void main(String[] args) {
        int pages = 5;
        int saved = pages;
        pages = 7;
        System.out.println(pages + " " + saved);

        int[] journal = {5, 7};
        int[] view = journal;
        int[] copy = journal.clone();
        view[0] = 9;
        System.out.println(journal[0] + " " + view[0] + " " + copy[0]);

        int total = 0;
        for (int day = 0; day < 2; day++) {
            int added = 5;
            total += added;
            added = 0;
        }
        System.out.println(total);

        String text = "AЯ🙂";
        System.out.println(text.length());
        for (int i = 0; i < text.length(); i++) {
            System.out.println((int) text.charAt(i));
        }
    }
}
