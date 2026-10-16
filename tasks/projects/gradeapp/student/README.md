# gradeapp — заготовка Java 25

Команда принимает ровно два аргумента: UTF-8-файл и целый порог 0..100.
Формат строки `id;name;score`: strip по краям; непустые id/name; score0..100;
blank пропускается с физическим номером; id уникален после strip, case-sensitive.
Отбор score>=threshold, score убывающе, затем String id естественно.
Нет заголовка; пустой результат — успех. Все данные проверяются до stdout.
Статусы0/2/3/4: успех/аргументы/данные/IO. Stderr — диагностика.
Ошибка stdout может оставить часть байтов. Системные потоки не закрывайте.

Готовы Student, DataFormatException, сборка, Main.run(String[]) и printReport.
Реализуйте TODO в StudentParser.parse, StudentFiles.read, Reports.selected,
Reports.line и Main.run(Path,int). Цикл и Stream отчёта допустимы.
Компиляция TODO возможна, но правильный запуск выбрасывает
UnsupportedOperationException; открытые behavior tests пока отказывают.

Из этого каталога (JDK25 уже установлен, JAVA_HOME указывает на него):

```bash
sh ./build.sh --version
sh ./build.sh -DskipTests package
```

Это проверка компиляции заготовки. После реализации:

```bash
sh ./build.sh test package
java -jar target/gradeapp.jar samples/students.txt 80
```

Ожидается stdout `s10;Ada;90` и `s3;Ann;90` на отдельных строках, stderr пуст,
status0. Из другого пустого каталога используйте абсолютные пути:
`java -jar /absolute/path/student/target/gradeapp.jar /absolute/path/student/samples/students.txt 80`.
Windows: те же цели через `.\build.cmd`. Wrapper3.3.4 only-script скачивает
Maven3.9.16. Закреплённый ZIP на POSIX требует shell, unzip и
sha256sum либо shasum; wget/curl используются при наличии, иначе Java fallback
предоставленного JDK. Windows использует PowerShell.
Первая загрузка Maven/plugins/JUnit требует доступа к Maven Central;
доступность среды и кэша проверяется до лабораторной, а не входит в120 минут.

PublicExamplesTest: обычный результат, empty, duplicate и предоставленный Student.
Примеры не исчерпывают проверку. Покажите собственные границы и объясните
место проверки дубля и начало полезного stdout; большой отчёт не требуется.

Готовые build.sh/build.cmd определяют свой каталог и восстанавливают
локальную .mvn/wrapper/maven-wrapper.properties из единственного публичного
wrapper/maven-wrapper.properties при каждом запуске. Не создавайте .mvn
вручную и не меняйте права mvnw: POSIX build.sh вызывает stock script через sh.
Заготовка ZIP содержит17 публичных файлов; .mvn и target создаются локально.
Предварительно проверьте JDK25/java/javac, shell tools/unzip/checksum и
доступ к Maven/dependencies либо подготовленный cache. Windows counterpart
поставлен, проверен статически; в текущей POSIX-среде не выполнялся.
