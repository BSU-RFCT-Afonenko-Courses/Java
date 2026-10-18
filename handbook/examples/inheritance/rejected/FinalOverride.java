class FinalOverride {
    static class Base {
        final void act() {}
    }
    static class Child extends Base {
        @Override
        void act() {} // Базовый метод закрыт для переопределения.
    }
}
