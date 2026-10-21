# Самые длинные и короткие слова

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
java -cp out Main "кот, дом и кот!"
java -cp out Main ""
```

Весь текст передаётся одним аргументом. Первый запуск выводит:

```text
Максимумы: кот дом кот
Минимумы: и
```

Второй передаёт один пустой аргумент и выводит `Слов нет`.
Стандартный ввод не используется.

После запуска решения выполните публичную самопроверку по инструкциям
в предоставленном [CHECKS.md](CHECKS.md).

На разборе покажите предусмотренные условием границы слов,
равные длины и повторы; объясните состав обоих списков.

После изменения исходников повторите компиляцию.
