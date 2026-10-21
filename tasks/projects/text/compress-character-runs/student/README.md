# Сжатие повторяющихся символов

Заготовка находится в `src/main/java/Main.java`. Форму точки входа
выберите самостоятельно: запуск `java -cp out Main` с аргументами
из условия должен работать. Вспомогательные методы и классы разрешены.

Нужен JDK 25. Откройте терминал в каталоге с этим README и папкой `src`.
Скомпилируйте все Java-исходники под `src/main/java`, включая вложенные
пакеты.

В Linux, Bash:

```bash
find src/main/java -type f -name '*.java' -exec javac --release 25 -encoding UTF-8 -d out -sourcepath src/main/java {} +
```

В PowerShell 7:

```powershell
$sources = @(Get-ChildItem -LiteralPath 'src/main/java' -Recurse -File -Filter '*.java' |
    ForEach-Object { $_.FullName })
javac --release 25 -encoding UTF-8 -d out -sourcepath src/main/java $sources
```

Примеры запуска в Bash:

```bash
java -cp out Main "aaBBa"
java -cp out Main ""
java -cp out Main "aaaaaaaaaaaa"
```

Один аргумент содержит весь исходный текст. Результаты — `a2B2a`,
пустая строка и `a12` соответственно. Запись `""` передаёт пустой аргумент;
стандартный ввод не используется.

После запуска решения выполните публичную самопроверку по инструкциям
в предоставленном [CHECKS.md](CHECKS.md).

На разборе покажите одиночную букву, последнюю серию и многозначную
длину серии; объясните границы серий в исходной строке.

После изменения исходников повторите компиляцию.
