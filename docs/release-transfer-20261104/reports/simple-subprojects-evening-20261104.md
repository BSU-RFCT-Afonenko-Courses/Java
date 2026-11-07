# A2: простой список дочерних проектов

## Результат и граница

Подготовлен локальный Source candidate: `subprojects` принимает непустой упорядоченный список строк каталогов, например `[theory, tasks, lectures]`. Объекты `{path,to}` и прежние aliases не поддерживаются. Нормализованный путь остаётся адресом монтирования; проверки границ каталогов, symlink, дублей и пересечений сохраняются. Дочерний процесс получает `quarto render .` с прежними профилями. Выбор формата принадлежит конфигурации, профилям и документам дочернего Quarto; новых inspect/render для определения формата нет.

Site `/home/tolya/JavaCodex/java-release-20261110/site-native-audience`, исходный HEAD `1ce0801b0244d0157481a3d00691a92b965d90da`, первоначальный status пуст. Начальные HEAD/status/binary diff всех трёх checkout сохранены в [исходной фиксации](simple-subprojects-initial-20261104/). Dirty/untracked материалы других владельцев сохранены. Один writer; чужие Course/Core/PL/Download/QRC/runtime исходники не изменялись.

В Java `_quarto.yml` порядок `theory, tasks, lectures, practice, handbook`; в SP `_quarto.yml` порядок `theory, lectures, tasks, practice, handbook` сохранён. `java/tasks/_quarto.yml` и содержание курса не менялись.

## Изменённый договор

`config.ts` проверяет строки на существующей границе и выводит настоящий `Project` без `to`; inline type predicate с входом `unknown` сужает список до `string[]`. `NativeSiteRecord` больше не объявляет `Project["to"]`. В `compose.ts` удалены `--to`, запись поля в record и требование catalog format равняться parent override; независимое сравнение selected sources и текущих HTML outputs сохранено. Новых `any`, casts, дублирующих runtime validators или ручных Project DTO нет; прежний `Workspace.config:any` вне этого объёма.

Публичные README/spec/diagnostics/example и существующие executable fixtures переведены вместе. В историческое release описание добавлена явная ссылка на действующий договор. Из native planning fixture удалены obsolete object shape проверки, interception `Deno.Command`, счётчики inspect и populated equality; сохранён наблюдаемый selected-source результат. Из pipeline fixture/CI удалён obsolete mutant поля `record.to`. `site-paths` использует публичные строки напрямую вместо служебного преобразователя объектов.

`site-effective-formats.ts` сокращён с нескольких повторных render до одной композиции: дочерний profile задаёт Revealjs, front matter HTML документа задаёт HTML, оба результата читаются из mounted `part/`. Это проверяет разрешение native форматов и размещение результата без сравнений внутреннего state. Прежний сценарий принудительного parent `to: html` удалён вместе с API. Остальные примеры формата в этой fixture больше не запускаются отдельными сборками.

Точный review diff: [Site](simple-subprojects-checks-20261104/site-candidate.patch), [Java root](simple-subprojects-checks-20261104/java-root-config.patch), [SP root](simple-subprojects-checks-20261104/system-practice-root-config.patch).

## Фактические проверки и пределы

Первоначально объявлены один affected type graph (до20s) и одна существующая public native fixture (до35s), общий потолок60s включая grace, без installs/network. После запуска compiler координатор сообщил о планируемом едином A1+A2 graph и потребовал больше не запускать отдельный compiler/runtime до независимого review. Это указание получено после завершения единственного compiler запуска; повторов не было.

Единственный `deno check` завершился **exit1 за4.775118s**: actual Download public type graph содержит relative sibling imports, а карта первого запуска покрывала bare imports. Получены3 TS2307 на отсутствующие `download/_extensions/{course-core,course-prairielearn,reference-catalog}/public.ts` и14 cascading TS7006 внутри Download. В Site diagnostics в этом запуске не было. Это не успешная типовая приёмка; final predicate/diagnostic prose правки после него ещё не проверены компилятором. Нужна адресная корректировка external import map для настоящих sibling producers в едином graph координатора. Исходники соседей ради проверки не менять.

Точная команда, исходная карта и результаты: [results.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/simple-subprojects-checks-20261104/results.json), [types.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/simple-subprojects-checks-20261104/types.log), [imports.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/simple-subprojects-checks-20261104/imports.json). Проверялись actual public sources Core `/core-native-bridge`, PL `/prairielearn`, Download `/download` и stdlib из известного Quarto cache; заглушек не было.

`git diff --check` — exit0 до последних небольших prose/fixture правок. **Native fixture не запущена**, baseline не устанавливался, полные suites, course render, matrix, network, push, production/DB/data не выполнялись. Native fixture готова для одного запуска после независимого review, лимит35s включая grace. Source заморожен для передачи reviewer; commit не создан. Результат не объявляет выпуск или installed course готовыми.

## Собственный допуск

Назначение `gpt-6.1-sol/medium`, Standard. Собственный chat `01a1a6b8-4ea2-7892-9095-246b36f20b8a`, agent path `/root/a2_simple_subprojects`, неизменный исходный turn `01a1a6b8-4ed2-75e0-a4eb-7bdb4127722c`. Первичный источник `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T16-20-59-01a1a6b8-4ea2-7892-9095-246b36f20b8a.jsonl`: session_meta и все текущие turn_context от начала прочитаны; верхние и nested model/effort совпадают. Результат PASS сохранён в [model-evidence.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/simple-subprojects-initial-20261104/model-evidence.json). Service tier в первичных записях отсутствует; Standard назначен явно, скрытая маршрутизация не удостоверяется. Java/SP root YAML изменены после этого собственного допуска.

## Файлы Site

- `.github/workflows/ci.yml`
- `README.md`
- `_extensions/course-site/application/compose.ts`
- `_extensions/course-site/application/sibling-contracts.ts`
- `_extensions/course-site/infrastructure/config.ts`
- `docs/diagnostics.md`
- `docs/releases/2026-11-01-native-planning-600.md`
- `examples/course/_quarto.yml`
- `spec/contract.md`
- `tests/planning-progress.ts`
- `tests/release-composition.ts`
- `tests/site-child-profiles.ts`
- `tests/site-composed-navigation.ts`
- `tests/site-domains.ts`
- `tests/site-effective-formats.ts`
- `tests/site-formats.ts`
- `tests/site-local.ts`
- `tests/site-metadata-profiles.ts`
- `tests/site-native-pipeline.ts`
- `tests/site-native-planning.ts`
- `tests/site-native.ts`
- `tests/site-navigation.browser.cjs`
- `tests/site-owner-release.ts`
- `tests/site-paths.ts`
- `tests/site-profiles.ts`
- `tests/site-project-native.ts`
- `tests/site-public-operation.ts`
- `tests/site-siblings.ts`
- `tests/site-source-paths.ts`
- `tests/site-web-selection.ts`

## Дополнение после Source review

В `docs/public-operation.md:7` удалено оставшееся упоминание сравнения с parent `to`: callback сравнивает независимо выбранные Native sources и текущие HTML outputs, а форматы определяют конфигурация, профили и документы дочернего Quarto. Это единственное изменение Source после предыдущей заморозки, только документация; code/test не менялись, compiler/runtime не запускались. Обновлён `site-candidate.patch`, Source снова заморожен для reviewer.

Полный собственный первичный интервал расширен от прежнего baseline без сдвига начала. Все turn_context: `01a1a6b8-4ed2-75e0-a4eb-7bdb4127722c` (строка 8), `01a1a6be-4988-7ee1-afbf-c722c9777f41` (строка 212). Верхние и nested `gpt-6.1-sol/medium` совпадают, PASS; evidence обновлено.
