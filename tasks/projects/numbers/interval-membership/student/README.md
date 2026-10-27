# Принадлежность интервалам

Реализуйте программу в `src/main/java/Main.java`, сохранив точку входа
`Main.main(String[] args)`. Вспомогательные методы и классы разрешены.

Нужен JDK 25. Откройте терминал в каталоге с этим README и папкой `src`
и скомпилируйте все Java-исходники под `src/main/java`, включая вложенные
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
printf '%s\n' '-2 4 -2' | java -cp out Main
printf '%s\n' '-2 4 4' | java -cp out Main
```

Первый запуск выводит `false true false true`,
второй — `true false false true`.
Во входе сначала идут границы `n m`, затем проверяемое значение `k`.

После запуска решения выполните публичную самопроверку по инструкциям
в предоставленном [CHECKS.md](CHECKS.md).

После изменения исходников повторите компиляцию.

На разборе покажите границы, внутреннюю точку и значения снаружи
интервалов; объясните роль строгих и нестрогих сравнений.
