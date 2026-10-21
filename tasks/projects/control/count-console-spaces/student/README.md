# Подсчёт пробелов

Реализуйте программу в `src/main/java/Main.java`, сохранив точку входа
`Main.main(String[] args)`. Вспомогательные методы и классы разрешены.

Нужен JDK 25. Из каталога с этим README и папкой `src` скомпилируйте
все Java-исходники под `src/main/java`, включая вложенные пакеты.

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

Следующие команды выполняются в Bash:

```bash
printf 'a b c.after dot  ' | java -cp out Main
printf 'a b' | java -cp out Main
printf '' | java -cp out Main
```

Результаты — соответственно `2`, `1`, `0`. В первом случае точка
завершает обработку. Во втором и третьем `printf` заканчивает передачу
данных, и программа получает конец ввода. Эти команды не добавляют
перевод строки.

Ввод в UTF-8 можно также сохранить в файл. В Bash команда
`java -cp out Main < input.txt` передаёт его содержимое программе.
После запуска решения выполните публичную самопроверку по инструкциям
в предоставленном [CHECKS.md](CHECKS.md).

После изменения исходников повторите компиляцию.

На разборе покажите случаи из условия и объясните состояние счётчика
после выбранного шага.
