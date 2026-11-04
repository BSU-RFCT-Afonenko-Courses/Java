# Курс Java: наследование однофайлового редактора

В `java/tasks/bank/numbers.qmd:172` удалён ровно один атрибут `prairielearn-submission="upload"` у Div `exr-numbers-short-circuit-and`. Упражнение теперь наследует существующие `question-defaults.submission.mode: editor` и `ace-mode: ace/mode/java` из `tasks/_quarto.yml:373–376`. Новый режим PrairieLearn или новый механизм выбора не вводится.

Фактический исходный HEAD Java — `ea17038679a7b8fdca0c8ad26394658c2a0d85e2`. Перед изменением `git diff -- tasks/bank/numbers.qmd` был пуст; соответствующая HEAD декларация содержала явный upload. В рабочем дереве имеются чужие dirty CI/tools/docs/provider изменения и untracked HTML: они сохранены, не редактировались. Root YAML, Site и `tasks/_quarto.yml` этой правкой не менялись. Commit не создан; Source заморожен для независимого review.

[Точный diff одной строки](native-file-editor-course-fix-20261104.patch) содержит только удаление указанного атрибута. ID, project path, project-check, grading, target, условие, shortcode `project-download`, UUID и критерии не менялись. Все остальные upload атрибуты, включая `exr-numbers-argument-sum-product:231`, сохранены.

## Почему существующий editor подходит

Прочитана [независимая техническая трассировка PL](native-file-editor-evening-20261104.md) и адресно сверены текущие исходники курса. `projects/numbers/short-circuit-and/_metadata.yml` выбирает `starter/`; в `starter/src/main/java` один `Main.java` с `Main.main(String[] args)`. Профиль `java-numbers-short-circuit-and` в `_quarto.yml:2409` выбирает `source-profile: java-gradle`, runtime `java25-gradle-junit-v1`, `java-build.main-class: Main` и не задаёт `additional-java-sources`. Source-profile `java-gradle:417` имеет `mode: implementation`, `root: src/main/java`, include `**/*.java`. Имя сдаваемого файла — `Main.java`. Reference/alternatives находятся в отдельных закрытых группах.

Контрпример сохранён: `java-numbers-argument-sum-product:2562` имеет `additional-java-sources.primary-required: false`; он продолжает использовать явный upload. Для исправленного упражнения локальная IDE выдача остаётся доступна через прежний `project-download`.

## Проверка и предел

Выполнено адресное чтение текущего HEAD/рабочей декларации, defaults, source-profile, check profile, metadata и единственного starter. После изменения сохранён фактический `git diff` одной строки. Tests/runtime/compiler/network не запускались; фактическая materialization курса, output `question.html`, ZIP и browser submission этой правкой не подтверждены. Следующий разрешённый шаг принадлежит координатору: независимый review, затем при необходимости один существующий public output test с пределом20s.

## Собственный первичный допуск

Продолжен прежний интервал роли `/root/a2_simple_subprojects`, chat `01a1a6b8-4ea2-7892-9095-246b36f20b8a`, baseline turn `01a1a6b8-4ed2-75e0-a4eb-7bdb4127722c` без сдвига начала. До правки прочитаны session_meta и все turn_context первичного файла `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T16-20-59-01a1a6b8-4ea2-7892-9095-246b36f20b8a.jsonl`: строки8,212,244, включая новый turn `01a1a6cf-de11-71c1-95e8-088d6c9f9f09`. На обоих уровнях metadata `gpt-6.1-sol/medium`, PASS. Standard назначен; service tier отсутствует в первичных записях. [Полное свидетельство интервала](native-file-editor-course-model-evidence-20261104.json).
