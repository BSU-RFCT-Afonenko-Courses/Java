# Строки матрицы по возрастанию суммы

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
printf '%s\n' '2 -1 0' '0 1 0' '0 0 0' | java -cp out Main
printf '' | java -cp out Main
```

Первый запуск даёт строки `0 0 0`, `2 -1 0`, `0 1 0` в этом порядке.
Последние две строки имеют одинаковую сумму и сохраняют взаимный порядок.
Во втором запуске пустой stdin выбирает обязательный пример из условия;
он уже упорядочен.

После запуска решения выполните публичную самопроверку по инструкциям
в предоставленном [CHECKS.md](CHECKS.md).

На разборе покажите также перестановку строк обязательного примера
и объясните устойчивость выбранного решения.

После изменения исходников повторите компиляцию.
