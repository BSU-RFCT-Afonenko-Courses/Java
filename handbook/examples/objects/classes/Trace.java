class Trace {
    static {
        System.out.println("класс");
    }
    private int value = initialize();
    {
        System.out.println("блок: " + value);
    }
    private int initialize() {
        System.out.println("поле");
        return 7;
    }
    Trace() {
        System.out.println("конструктор: " + value);
    }
}
