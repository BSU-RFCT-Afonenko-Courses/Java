# Однофайловый редактор PrairieLearn, 4 ноября

В Source PrairieLearn штатный редактор уже реализован. Найден конкретный текущий дефект выбора режима в курсе: «Проверка положительного частного» (`exr-numbers-short-circuit-and`) имеет один starter `Main.java`, фиксированную точку входа Main и подходящий существующий договор проверки, но его собственный атрибут принудительно выбирает upload. Глобальный editor default поэтому не действует. Это требует небольшой правки курса, а не нового режима/API расширения.

PL product/test/docs diff отсутствует; исходный чистый HEAD `0079b64decb30aea70cf55a1526dd17d8d66a339` сохранён. Compiler, Native, browser submission, сборка курса и проверки grading в этой роли не запускались. Исторические числа «128 upload, 0 editor» сами по себе не квалифицируют сегодняшнюю установленную поставку.

## Конкретный вход и минимальное исправление

- `java/tasks/_quarto.yml:373` уже содержит default `submission: {mode: editor, ace-mode: ace/mode/java}`. Это существовало до A3; прежнее предположение о недавней чужой правке неверно.
- `java/tasks/bank/numbers.qmd:172` явно задаёт `prairielearn-submission="upload"` для `exr-numbers-short-circuit-and` и подключает `project-check="java-numbers-short-circuit-and"`. Условие называет `Main.main(String[] args)`; shortcode локальной IDE остаётся на строке 189.
- `java/tasks/projects/numbers/short-circuit-and/_metadata.yml` выбирает `starter/`; в `starter/src/main/java` один `Main.java` с TODO. Reference/alternatives находятся в отдельных закрытых группах. Текст starter прочитан адресно; UTF-8 проходит чтение исходного файла, Native materialization в этой роли не выполнялась.
- `java/tasks/_quarto.yml:2409` задаёт профиль `java-gradle`, runtime `java25-gradle-junit-v1`, Main build entry, без `additional-java-sources`; выбранный source-profile имеет root `src/main/java`, include `**/*.java`, mode implementation. Следовательно логическое имя сдачи — `Main.java`.

Минимальная предлагаемая владельцу курса правка: удалить только `prairielearn-submission="upload"` в декларации этого упражнения, чтобы действовал уже заданный editor default. Условия, source-profile, критерии, cases, weights и project-download не меняются. Этот исполнитель не владеет bank-файлами и их не изменял; точный пример передан координатору.

Контрпример, который нельзя переводить таким способом: `exr-numbers-argument-sum-product` (`numbers.qmd:231`), чей профиль `_quarto.yml:2562` явно имеет `additional-java-sources.primary-required: false`. Существующий PL договор требует для него upload и сохраняет свободные имена/пакеты. Само наличие дополнительных helper-исходников не является запретом editor: при обязательном primary существующий exporter умеет editor вместе с необязательным ZIP.

## Действующая цепочка Source

`native-business.ts` объединяет default и упражнение; локальный `prairielearn-submission` имеет приоритет. Для editor остаётся авторский `ace-mode`; при выборе upload он удаляется. `native-selection.ts` выбирает только student-plan файлы с reason starter и сохраняет относительное имя относительно source-profile root; existing boundary проверяет UTF-8, отсутствие NUL и размер ≤1 MiB. Reference и trusted tests выбираются отдельно и не используются как starter редактора.

`native-materialize.ts:187–220` сначала материализует starter, затем получает checked selection и передаёт `describeSubmission` логическое `name` и отдельный `starterPath: target` перед окончательной materialization. Поэтому README получает инструкцию для фактически выбранного режима. `export-course.ts` использует эти же имена в `files` и descriptor `sourceFiles`; `export.ts:948–976` создаёт `pl-file-editor file-name` из имени сдачи и встраивает только одноимённые student bytes. HTML и Mustache braces экранируются, Unicode сохраняется.

При наличии `studentArtifact` `export.ts:843–880` сохраняет готовые bytes архива в `clientFilesQuestion/starter.zip` независимо от editor/upload; строка 1010 добавляет ссылку на ZIP и README. Локальный IDE путь сохраняется. Для свободного primary существующая проверка `extras.primaryRequired === false` требует upload.

Официальный [pl-file-editor](https://docs.prairielearn.com/elements/pl-file-editor/) прочитан 4 ноября: `file-name` задаёт имя файла в `_files`, `ace-mode="ace/mode/java"` включает Java, inline starter задаёт начальный текст, `normalize-to-ascii="false"` сохраняет Unicode. Реальное нажатие submit в установленном PrairieLearn этим чтением не подтверждено.

## Один предлагаемый малый публичный тест, ещё не запускался

Существующий `tests/export.test.ts:838`, `editor delivery exposes only inline accepted starter code and keeps the external grader contract`, проверяет реальный `question.html`: native editor/filename, inline Unicode, HTML/Mustache escaping, отсутствие private/reference, сохранение External grader. Предлагаемая команда из PL repo:

```sh
deno test --no-config --no-check --allow-read --allow-write --allow-run --allow-env --filter 'editor delivery exposes only inline accepted starter code and keeps the external grader contract' tests/export.test.ts
```

Один тест, один renderer `quarto pandoc`, без Java compile/runner, без Native capture и без whole-course. Предлагаемый hard wall limit — 20 секунд; один запуск после GO координатора. Он подтверждает существующий exporter output, но не текущую course materialization, ZIP или browser `_files` submission. Новая type graph не нужна при отсутствии изменений TS.

## Остаток и допуск

Нужно решение владельца курса о точной партии снятия устаревших upload overrides. Подтверждённый первый кандидат указан выше; автоматические новые критерии, `auto` mode и второй validator не вводились. Файловые/multi-file/manual задания без отдельного подтверждения состава сохраняют upload. Отдельный Native practice producer A3 в этот пакет не входит.

Собственный primary допуск `gpt-6.1-sol/high` сохранён в `native-file-editor-model-admission-20261104.json`: агент `/root/a4_native_file_editor`, ID чата `01a1a6c9-7377-72c3-8749-61a67b5111e5`, исходный ход `01a1a6c9-73a3-7ba0-ad67-eb16ec96eb86`. Прочитаны весь доступный интервал turn_context и вложенные collaboration settings; оба уровня совпадают. R0–R6, logfile census, production/DB/VM, install, commit/push не выполнялись.
