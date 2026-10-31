# Учёт обращений по устойчивому коду

[Условие задания](https://bsu-rfct-afonenko-courses.github.io/Java/tasks/bank/collections.html#exr-collections-object-hash)
задаёт результат, обязательные приёмы и демонстрацию. Перед работой без сети
сохраните страницу условия. Команды ниже выполняются из каталога этого README.

Нужен JDK 25. Исходники решения находятся в `src/main/java`, включая вложенные
пакеты. Имена и устройство программы выбирайте в пределах условия.

Компиляция в Linux, Bash:

```bash
find src/main/java -type f -name '*.java' -exec javac --release 25 -encoding UTF-8 -d out -sourcepath src/main/java {} +
```

Компиляция в PowerShell 7:

```powershell
$sources = @(Get-ChildItem -LiteralPath 'src/main/java' -Recurse -File -Filter '*.java' |
    ForEach-Object { $_.FullName })
javac --release 25 -encoding UTF-8 -d out -sourcepath src/main/java $sources
```

Дополните демонстрацию в `Main` и запустите её:

```console
java -cp out Main
```

При выборе другого класса запуска подставьте его имя, для класса в пакете —
полное имя. После изменений повторите компиляцию. Каталог `out` создаётся
при компиляции; загрузка зависимостей для этих команд не требуется.

Вызовы в [TaskBinding.java](src/test/java/TaskBinding.java) связывают открытый
клиент с заготовкой. При другом допустимом API согласуйте эти вызовы со своим
решением; имена заготовки не добавляют требований к условию. Недоступные
внешнему клиенту операции покажите в собственной демонстрации.

Скомпилируйте и запустите клиент наблюдений. В Linux, Bash:

```bash
find src/test/java -type f -name '*.java' -exec javac --release 25 -encoding UTF-8 -cp out -d out -sourcepath src/test/java {} +
java -cp out PublicChecks
```

В PowerShell 7:

```powershell
$checks = @(Get-ChildItem -LiteralPath 'src/test/java' -Recurse -File -Filter '*.java' |
    ForEach-Object { $_.FullName })
javac --release 25 -encoding UTF-8 -cp out -d out -sourcepath src/test/java $checks
java -cp out PublicChecks
```

Клиент показывает ожидаемые и наблюдаемые результаты двух сценариев.
Он не оценивает решение: исходники, демонстрацию и объяснение проверяет
преподаватель по условию.

Готовый `grade.Student` находится в
[src/main/java/grade/Student.java](src/main/java/grade/Student.java).
Предъявите исходники собственного обычного класса ключа и демонстрацию,
включая новый равный ключ, разные коды `Aa` и `BB` и пустой вход. Объясните
согласование `equals`/`hashCode`, устойчивость кода и различие полного
значения `Student` и равенства вашего ключа по коду.
