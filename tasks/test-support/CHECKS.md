# Открытая проверка

После компиляции решения по README запустите два открытых сценария.
Проверка использует JDK 25. В Linux, Bash:

```bash
find src/test/java -type f -name '*.java' -exec javac --release 25 -encoding UTF-8 -cp out -d out -sourcepath src/test/java {} +
java -cp out PublicChecks
```

В PowerShell 7:

```powershell
$checks = @(Get-ChildItem -LiteralPath 'src/test/java' -Recurse -File -Filter '*.java' |
    ForEach-Object { $_.FullName })
javac --release 25 -encoding UTF-8 -cp out -d out -sourcepath src/test/java $checks
java -cp out PublicChecks
```

Для каждого сценария выводятся проверяемое требование, входные данные,
ожидаемый и полученный результат, статус. Проверка возвращает код 0 при
успехе обоих сценариев. Эти примеры помогают проверить решение перед
предъявлением исходников и объяснением по условию задания.
