public class EnumDemo {
    enum LoanStatus { AVAILABLE, BORROWED, LOST }
    enum Priority {
        LOW(1), NORMAL(5), HIGH(10);
        private final int weight;
        Priority(int weight) { this.weight = weight; }
        int weight() { return weight; }
    }
    public static void main(String[] args) {
        LoanStatus state = LoanStatus.BORROWED;
        System.out.println(state == LoanStatus.BORROWED);
        System.out.println(Priority.HIGH.weight());
        System.out.println(LoanStatus.valueOf("BORROWED") == state);
        try { LoanStatus.valueOf("borrowed"); }
        catch (IllegalArgumentException ex) { System.out.println("Имя не найдено"); }
    }
}
