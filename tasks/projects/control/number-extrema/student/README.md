# Наибольшее и наименьшее число

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

Примеры запуска в Bash:

```bash
printf '4 7 -3 0 7\n' | java -cp out Main
printf '0\n' | java -cp out Main
```

Первый запуск выводит `Максимум: 7`, затем `Минимум: -3`.
Второй выводит `Количество должно быть положительным`.
Первое число входа задаёт количество следующих значений;
при положительном количестве передайте их все.

После запуска решения выполните публичную самопроверку по инструкциям
в предоставленном [CHECKS.md](CHECKS.md).

После изменения исходников повторите компиляцию.

На разборе покажите одно число, набор отрицательных чисел и нулевое
количество. Объясните выбор начальных экстремумов и их изменение
после очередного числа.
