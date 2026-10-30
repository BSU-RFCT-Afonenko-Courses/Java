public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("P\tQ\tAND\tOR\tXOR\tNOT");
        boolean[] values = {false, true};
        for (boolean p : values) {
            for (boolean q : values) {
                System.out.println(p + "\t" + q + "\t" + (p && q) + "\t" + (p || q)
                    + "\t" + (p || q) + "\t" + !p);
            }
        }
    }
}
