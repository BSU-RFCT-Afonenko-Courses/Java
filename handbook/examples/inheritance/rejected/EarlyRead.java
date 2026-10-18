class EarlyRead {
    static class Parent {
        Parent(int value) {}
    }
    static class Child extends Parent {
        private int value;
        Child(int value) {
            System.out.println(this.value); // Чтение текущего поля до super запрещено.
            super(value);
            this.value = value;
        }
    }
}
