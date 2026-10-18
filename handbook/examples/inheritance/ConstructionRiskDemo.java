public class ConstructionRiskDemo {
    static class Parent {
        Parent() { show(); }
        void show() { System.out.println("parent"); }
    }

    static class ChildState extends Parent {
        private int value = 7;
        @Override void show() { System.out.println(value); }
    }

    static class EarlyChild extends Parent {
        private int value;
        EarlyChild() {
            this.value = 7;
            super();
        }
        @Override void show() { System.out.println(value); }
    }

    static class FinalChild extends Parent {
        private final int value;
        FinalChild() {
            super();
            value = 7;
        }
        @Override void show() { System.out.println(value); }
    }

    public static void main(String[] args) {
        ChildState child = new ChildState();
        child.show();
        EarlyChild early = new EarlyChild();
        early.show();
        FinalChild finalChild = new FinalChild();
        finalChild.show();
    }
}
