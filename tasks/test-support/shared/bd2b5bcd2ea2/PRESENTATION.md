# Предъявление открытых наблюдений

Эта инструкция относится к двум ручным задачам о числе седловых точек
и локальных минимумов. Клиент показывает фактический вывод выбранной
демонстрации и опубликованные ожидания. Оба наблюдения относятся к одной
опубликованной матрице; второе требует объяснения по исходнику, а не нового
входа или дополнительной печати. Завершённый показ не является оценкой.

`src/test/java/PublicBinding.java` — редактируемая привязка к примеру
стартера. Для другого класса запуска измените её обращение. Имена, видимость,
методы, форма main и оформление решения остаются свободными по условию.
Если внешнюю привязку подготовить нельзя, предъявите собственный запуск
и исходник преподавателю; техническая неподготовленность не даёт оценки.

Из каталога студенческого комплекта, JDK25, Bash в Linux:

```bash
find src/main/java -type f -name '*.java' -exec javac --release 25 -encoding UTF-8 -d out -sourcepath src/main/java {} +
find src/test/java -type f -name '*.java' -exec javac --release 25 -encoding UTF-8 -cp out -d out -sourcepath src/test/java {} +
java -cp out PublicChecks
```

PowerShell7:

```powershell
$sources = @(Get-ChildItem -LiteralPath 'src/main/java' -Recurse -File -Filter '*.java' | ForEach-Object { $_.FullName })
javac --release 25 -encoding UTF-8 -d out -sourcepath src/main/java $sources
$checks = @(Get-ChildItem -LiteralPath 'src/test/java' -Recurse -File -Filter '*.java' | ForEach-Object { $_.FullName })
javac --release 25 -encoding UTF-8 -cp out -d out -sourcepath src/test/java $checks
java -cp out PublicChecks
```

Код0 означает только завершённое предъявление. Преподаватель отдельно
сопоставляет число, сравнения, соседей и объяснение с условием. Совпадение
числа0 или2 не подтверждает обработку равенства или диагоналей. Эти команды
выполнены для Bash/PowerShell в Linux; проверка Windows остаётся отдельной.
