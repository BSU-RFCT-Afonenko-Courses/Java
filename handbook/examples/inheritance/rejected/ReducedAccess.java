class ReducedAccess {
    static class Base {
        protected void act() {}
    }
    static class Child extends Base {
        @Override
        void act() {} // Доступ внутри пакета уже, чем protected.
    }
}
