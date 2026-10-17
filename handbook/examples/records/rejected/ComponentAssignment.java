// Ожидается ошибка компиляции: компактный конструктор присваивает параметры.
record ComponentAssignment(int value) {
    ComponentAssignment {
        this.value = value;
    }
}
