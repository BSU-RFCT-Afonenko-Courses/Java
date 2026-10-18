public class IntStackDemo {
    public static void main(String[] args) {
        IntStack stack = new IntStack(1);
        stack.push(7);
        try { stack.push(9); }
        catch (IllegalStateException ex) { System.out.println("full refused"); }
        System.out.println(stack.size());
        System.out.println(stack.pop());
        try { stack.pop(); }
        catch (IllegalStateException ex) { System.out.println("empty refused"); }
        System.out.println(stack.size());
    }
}
