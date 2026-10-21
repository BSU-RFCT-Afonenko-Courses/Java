# Вычитание среднего арифметического

Реализуйте программу в `src/main/java/Main.java`, сохранив точку входа
`Main.main(String[] args)`. Вспомогательные методы и классы разрешены.

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
printf '%s\n' '-3 0 3' '2 -1 0' '1 3 -2' | java -cp out Main
printf '' | java -cp out Main
```

Первый запуск передаёт три строки матрицы. Второй передаёт пустой stdin
и выбирает тот же обязательный пример из условия. Оба дают три строки
результата: `(-3, 0, 3)`, `(5/3, -4/3, -1/3)`, `(1/3, 7/3, -8/3)`.
В консоль выводятся десятичные числа без скобок; допуск — `1e-8`.

После запуска решения выполните публичную самопроверку по инструкциям
в предоставленном [CHECKS.md](CHECKS.md).

На разборе покажите новый `double[][]`, исходную матрицу после расчёта
и строку с дробным средним. Объясните по схеме ссылок, что копирует
`matrix.clone()`.

После изменения исходников повторите компиляцию.
