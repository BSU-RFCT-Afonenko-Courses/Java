# Размер изображения

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

Запуск с передачей данных через стандартный ввод в Bash:

```bash
printf '50000 50000 3\n' | java -cp out Main
```

Ожидаются два числа: `7500000000 7152.557373046875`.
Команда `printf` передаёт программе ширину, высоту и число байтов
на пиксель. Эти значения вводятся через стандартный ввод, поэтому
после имени класса аргументы не добавляются.

После запуска решения выполните публичную самопроверку по инструкциям
в предоставленном [CHECKS.md](CHECKS.md).

После изменения исходников повторите компиляцию.

На разборе покажите примеры из условия и изображение из одного пикселя.
Объясните типы промежуточных выражений и точность количества байтов.
