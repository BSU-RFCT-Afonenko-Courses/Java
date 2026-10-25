# Ошибки в обработке и генерации исключений

[Условие задания](https://bsu-rfct-afonenko-courses.github.io/Java/tasks/bank/exceptions.html#exr-exceptions-exception-flow-errors)
задаёт результат, обязательные приёмы и демонстрацию. Перед работой без сети
сохраните страницу условия. Команды ниже выполняются из каталога этого README.

Нужен JDK 25. Три независимые заготовки находятся в `array/src/main/java`,
`hierarchy/src/main/java` и `checked/src/main/java`. В них оставлены TODO;
исходные ошибочные фрагменты приведены в условии. Исправьте каждый фрагмент
в своей программе и дополните демонстрацию.

Команды для предоставленных классов подходят для Bash и PowerShell 7.
Каждая программа компилируется отдельно:

```console
javac --release 25 -encoding UTF-8 -d out -sourcepath array/src/main/java array/src/main/java/ArrayDemo.java
java -cp out ArrayDemo 10
java -cp out ArrayDemo 20

javac --release 25 -encoding UTF-8 -d out -sourcepath hierarchy/src/main/java hierarchy/src/main/java/HierarchyDemo.java
java -cp out HierarchyDemo false
java -cp out HierarchyDemo true

javac --release 25 -encoding UTF-8 -d out -sourcepath checked/src/main/java checked/src/main/java/CheckedDemo.java
java -cp out CheckedDemo
```

Первый опыт меняет длину массива; второй — выбор `A` или `B`; третий
показывает обработку либо передачу проверяемого исключения. При выборе
других имён и пакетов скорректируйте пути и имена классов запуска.
После изменения исходников повторите соответствующую компиляцию.
Каталог `out` создаётся при компиляции; загрузка зависимостей не нужна.

После компиляции трёх программ можно собрать общий клиент наблюдений:

```console
javac --release 25 -encoding UTF-8 -cp out -d out presentation/src/test/java/TaskBinding.java presentation/src/test/java/PublicChecks.java
java -cp out PublicChecks
```

[TaskBinding.java](presentation/src/test/java/TaskBinding.java) связывает его
с предоставленными классами. При другом допустимом устройстве программ
согласуйте вызовы с вашим решением. Клиент показывает результаты и типы
исключений; успешное завершение клиента не подтверждает правильность работы.

Предъявите три исправленные программы, их запуски и объяснение исходной
ошибки компиляции и пути выполнения каждого фрагмента. Эти результаты
проверяет преподаватель по условию.
