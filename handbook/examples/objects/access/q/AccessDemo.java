package q;

public class AccessDemo {
    public static void main(String[] args) {
        Child child = new Child();
        child.inspect(child, new p.Base());
    }
}
