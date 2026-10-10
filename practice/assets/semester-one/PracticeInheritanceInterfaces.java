// Авторская демонстрация к опоре практики первого семестра. Java 25 без preview.
public class PracticeInheritanceInterfaces {
interface Signal { int level(); }
static final class Quiet implements Signal {
    public int level() { return 1; }
}
static final class Loud implements Signal {
    public int level() { return 4; }
}
    public static void main(String[] args) {
        Signal signal = new Loud();
        System.out.println(signal.level());
    }
}
