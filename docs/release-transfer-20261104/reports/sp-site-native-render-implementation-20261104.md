# SP Site: один native child для части

## Граница и допуск

Исполнитель `/root/site_sp_native`, собственный chat `01a1a527-b7f3-7aa0-b1d7-735711254e02`, исходный ход `01a1a527-b819-7bf1-af20-d26fd3269eb5`. Назначение `gpt-6.1-sol/high`, Standard. Собственный primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T09-03-26-01a1a527-b7f3-7aa0-b1d7-735711254e02.jsonl`: session_meta line1 подтверждает chat/path; весь доступный интервал от origin — turn_context line8 — согласован по top-level и nested `gpt-6.1-sol/high`. Итог PASS, [машиночитаемое свидетельство](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-site-native-render-evidence-20261104/model-admission.json). Скрытая server routing не удостоверяется; глобальные настройки не менялись.

Прочитаны целиком `reports/sp-site-native-render-next-20261104.md`, адресно cache/native Site границы `reports/quarto-cache-boundary-20261104.md`, actual `system-practice/AGENTS.md`, actual Site `AGENTS.md`, `spec/index.md`, `spec/contract.md`, `docs/public-operation.md`, `java/docs/model-admission.md` и `core/docs/russian-language-policy.md`.

Manual linked worktree создан существующим bare `provider-sources/quarto-project-publish`:

- `site-sp`, ветка `codex/sp-native-render-20261110`, исходный exact `e0efd6b5428412500569d0da5a5e0b33d62a5663`.
- `site-java` сохранён на exact `d16f2322c1da3c96c2e93d32225f5428ae347756`, рабочее дерево чистое по заключительной проверке.

Исходные provider refs, installed course, `_quarto.yml`, курсы, Core checkout/candidate, mainstate и PR pins не редактировались. Commit/push/install в курс, SSH и production не выполнялись. Пакет оставлен незакоммиченным для независимого review root.

## Изменение поведения

Production scope — ровно два файла в `site-sp`:

1. `_extensions/course-site/infrastructure/config.ts`: вместо `inspectDocuments` и D×document format inspect используется `inspectProject`. Один native project inspect даёт config/resources и selected sources. Весь selected set проходит containment, safePath, lstat обычного файла и проверку повторов до cleanup. lstat использует resolved path. Собственная format projection (`DocumentFormat`, `DocumentPlan`, `renderTo`) удалена.
2. `_extensions/course-site/application/compose.ts`: каждая прежняя строка `subprojects: [paths]` выполняет один `render . --to default` с native profiles и создаёт одну collection. Удалены per-document render/capture loop, искусственное объединение collections и derived `expectedDocuments`. Record хранит actual collected profiles/files/outputs и pre-cleanup selectedSources. Existing catalogCoverage требует точного selected source set, уникальных catalog sources и текущих HTML outputs. Для default не вводится uniform format check. QRC и Navigation определяют web membership по actual `.html` nativeOutputs.

Публичный SP shape `subprojects: string[]` сохранён. Authored mixed HTML index + Reveal slides остаются native per-document default. Изменение старой общей policy намеренное: прежний web preference и `SITE.FORMAT_AMBIGUOUS` удалены; PDF первым теперь выбирается самим Quarto как default. Новый config key, format API/resolver, Core private import, facade, fake cast, cachekey/proof framework или новый `any` не добавлены.

Copy/owned cleanup/resources, current collection correlation, profiles, releaseQualification и audience/output preflight сохранены. S6 `ownerRelease`/`validateCourse` без Download не менялся; замены на PL.inspectPublication нет. Native caches/флаги исполнения не менялись.

## Existing tests и документы

Адаптированы existing `tests/site-effective-formats.ts` (теперь native selected set/profile selection и project-only inspect), `tests/site-web-selection.ts` (один global default child, HTML-first обычная конфигурация, mixed outputs/circular QRC links) и `tests/site-owner-release.ts` (старый sequential fallback заменён native mixed; independent selected-source missing/unexpected controls используют existing active record). CI mode list соответствует этим cases. Core mutation cases не запускались ради матрицы.

README, `spec/contract.md`, `spec/index.md`, `docs/diagnostics.md` и `docs/public-operation.md` описывают новый native default и снятие прежней format policy. Старые resolver/normalization tests не сохранены под новым именем.

## Проверки и первый отказ

`git diff --check` PASS. После стабилизации Source выполнен **один** affected compiler на имеющемся Deno2.7.14 и actual stdlib cache/import map, без установки зависимостей:

```sh
DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache DENO_NO_UPDATE_CHECK=1 DENO_NO_PACKAGE_JSON=1 \
/opt/quarto/bin/tools/x86_64/deno check --no-config --no-lock \
--import-map=/home/tolya/.cache/quarto/deno_std/run_import_map.json \
_extensions/course-site/application/compose.ts \
_extensions/course-site/infrastructure/config.ts \
tests/site-effective-formats.ts tests/site-web-selection.ts tests/site-owner-release.ts
```

Exit0, [compiler.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-site-native-render-evidence-20261104/compiler.log). Все пять указанных roots проверены; новых Source правок после compiler не было.

Единственная разрешённая runtime attempt — existing `tests/site-web-selection.ts`, timeout60s/retry0, Deno `run --cached-only`, тот же actual cache/map, `QUARTO_RUN_NO_NETWORK=true`. `QRC_PROVIDER` указывал на существующий установленный `system-practice/tasks/_extensions/Afonenko-Course-Tools/reference-catalog`; собственный fixture сохранялся через `COURSE_SITE_KEEP=1`.

**Exit1 до render.** Первый `quarto add` Site прошёл, следующий `quarto add QRC_PROVIDER` в `tests/site-web-selection.ts:54` получил:

```text
Found 0 extensions.
ERROR: Invalid extension
The extension staged at /tmp/quarto-session3eed0e3542f17d5b/115dd3336f5577a2/652ee2a208135115 does not provide any valid extensions.
```

Причина этой attempt — существующий runner принимает source package layout, а directly installed extension directory не является допустимым `quarto add` package. Повтор, другая раскладка provider, новая установка, Core/native серия и второй render не выполнялись. Точный stderr/stack сохранён в [mixed-fixture.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-site-native-render-evidence-20261104/mixed-fixture.log), fixture сохранён `/tmp/course-site-web-selection-10ca71f074aa82ce`.

Следовательно, фактические mixed HTML/Reveal outputs, QRC circular links, количество реально выполненных children, отсутствие document inspect при runtime, native freeze hit и consumer privacy в этой attempt **UNKNOWN**. Проверка не дошла до изменённого render пути и не является ни его успешной приёмкой, ни доказанным production отказом. Performance improvement, полная совместимость установленной composition и готовность выпуска не заявляются.

## Передача

Source candidate готов к независимому review в `site-sp`; реализация не интегрирована. Required compiler PASS, bounded fixture attempt остановлена и сохранена на firstfailure. Дальнейшая runtime qualification требует отдельного решения root с корректным уже существующим source-layout provider; retry0 этой роли исчерпан. Главный Source результат — удаление собственного format selection/render fallback при сохранении SP authored string shape и actual source/output correlation.

## Адресное уточнение: общий Site 6.0.1

После первого prep отказа root запросил сравнить existing Java Site d16 с actual SP config, сохранив legacy candidate без изменений и без новой runtime attempt. Прочитаны actual root SP `_quarto.yml`/student/full, selected project settings пяти частей, Java descriptor, existing public contract/README и адресные validator/compose/S6 операции. Это ограниченное Source сравнение, новый общий аудит не выполнялся.

**Actual SP свойство, которое требует отдельного Site5.3 backport, не найдено.** Пять текущих строк `theory`, `lectures`, `tasks`, `practice`, `handbook` достаточно выразить existing Site6 shape:

```yaml
subprojects:
- {path: theory, to: default}
- {path: lectures, to: default}
- {path: tasks, to: default}
- {path: practice, to: default}
- {path: handbook, to: default}
```

Пути/порядок/mount сохраняются. Native book selection, mixed HTML index/Reveal slides, root render только index, profiles default/group/student/full и независимые outputs/resources/namespaces/hooks соответствуют existing Java6 договору. `to: default` у lectures/practice сохраняет per-document formats. Root SP не включает новую full Navigation composition, поэтому её optional сосед не становится новым обязательным runtime входом. Existing addressNavigation и авторское navbar сохраняются.

Java d16 уже реализует один `render . --to project.to`, independent selected sources корня/частей до cleanup, exact actual Core source/output coverage и actual HTML membership QRC/navigation. Его S6 по-прежнему использует `ownerRelease` → Core validateRelease → optional actual PL.validateCourse; переноса на PL.inspectPublication нет. Typed sibling facade использует публичные типы, runtime private Core API не добавлен. Source-wide разница Java от SP pin — descriptor/config/compose/process/sibling types; это уже существующий общий owner, дополнительного переноса в SP ради пяти records не требуется.

Рекомендация root: **общий Site6.0.1/d16 + миграция пяти Source records и отдельный согласованный stage** вместо интеграции отдельного5.3 backport. Единственная найденная authored incompatibility — прежний string shape. Общая снятая web-preference policy одинакова в обоих вариантах и уже выбрана root как native default. Runtime compatibility composition, release и installed provenance требуют своей фактической проверки; адресное Source сравнение не превращает их в PASS.

Эта роль не меняла пять SP records или installed pins и не запускала новый render/add. Legacy `site-sp` candidate и evidence сохранены нетронутыми после compiler. Firstprep причина конкретна: existing `QRC_PROVIDER` runner ожидает репокорень с `_extensions/reference-catalog`; напрямую установленный каталог недопустим. Available QRC source repository может быть входом отдельной первой actual Native attempt по решению root; сохранённый отказ не является actual Native Source failure.
