# Элементы массива в обратном порядке

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
printf '4 7 -2 7 0\n' | java -cp out Main
printf '0\n' | java -cp out Main
printf '' | java -cp out Main
```

Первый запуск выводит `0 7 -2 7`. Во втором передан пустой массив:
чисел в выводе нет. Третий передаёт пустой stdin и запускает выбранный
вами демонстрационный массив. Это разные входные случаи.

После запуска решения выполните публичную самопроверку по инструкциям
в предоставленном [CHECKS.md](CHECKS.md).

На разборе покажите обычный, пустой и одноэлементный массивы,
а также исходные компоненты после вывода; объясните их сохранность.

После изменения исходников повторите компиляцию.
