# Приветствие пользователя

Реализуйте программу в `src/main/java/Main.java`, сохранив точку входа
`Main.main(String[] args)`. Вспомогательные методы и классы разрешены.

Для компиляции и запуска нужен JDK 25. Откройте терминал в каталоге
с этим README и папкой `src`. Скомпилируйте все Java-исходники
под `src/main/java`, включая вложенные пакеты.

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

После компиляции выполните два запуска:

```text
java -cp out Main "Анна"
java -cp out Main
```

Первый запуск выводит `Здравствуйте, Анна!`, второй — `Укажите имя.`.
Имя передаётся после имени класса. Кавычки позволяют передать имя
с пробелами одним аргументом. Стандартный ввод программа не читает.

После запуска решения выполните публичную самопроверку по инструкциям
в предоставленном [CHECKS.md](CHECKS.md).

После изменения исходников повторите компиляцию. На разборе покажите
исходники и запуски, объясните различие случая с именем и без аргументов.
