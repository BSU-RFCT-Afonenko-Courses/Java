# Стоимость и область Core CI перед первым draft PR

3 ноября 2026. Read-only оценка exact Core `d7cc10eaa21fa35bbe32d73cc4afa216002393da` относительно опубликованного `a478d2cc0dfb19e0bebd99108815ac8ef1fe85a8`. Исходники не менялись; CI/tests/render/inspect/check/build/hash/npm, новые клоны и PR не запускались. Единственная запись — этот отчёт. Рекомендация ниже не реализована.

## Измеренная прежняя стоимость

Получены только primary GitHub REST metadata трёх последних main runs и их единственного job/steps; логи и артефакты не скачивались. Это push runs, не tag, на прежних опубликованных SHA. Интервалы вычислены из `run_started_at/updated_at`, `job.started_at/completed_at` и timestamps native step.

| Запуск / SHA | Результат | Весь run | Job | Native step | Подготовка exact fixtures |
|---|---|---:|---:|---:|---:|
| [41525794990](https://github.com/Afonenko-Course-Tools/quarto-course/actions/runs/41525794990), `a478d2c` | SUCCESS | 13m38s | 13m35s | 13m08s | 3s |
| [41489777237](https://github.com/Afonenko-Course-Tools/quarto-course/actions/runs/41489777237), `70d0494` | SUCCESS | 13m14s | 13m10s | 12m43s | 2s |
| [41482587386](https://github.com/Afonenko-Course-Tools/quarto-course/actions/runs/41482587386), `21ffec6` | SUCCESS | 11m50s | 11m45s | 11m18s | 3s |

Job IDs соответственно `125553069498`, `125429440267`, `125404497498`. Setup Quarto занимал12s; npm ci2s/1s/4s. Большая часть времени — общий native step. Разделить его13m08s между canonical/early/navigation по metadata нельзя: команды находятся в одном step. Лимиты210min native/270min job — потолок, не измеренная длительность. Новые d7 runtime и ускорение из этих данных неизвестны. Первый GET `/actions/workflows/ci.yml/runs` connector отклонил как неподдержанный endpoint; разрешённый `/actions/runs?branch=main&per_page=3` и `/actions/runs/<id>/jobs?per_page=1` вернули данные без повторного исследования интернета.

## Фактический состав

`.github/workflows/ci.yml` запускается на любом pull_request, в том числе draft, push main, workflow_dispatch и weekly schedule. В matrix ровно одна версия Quarto1.11.5; это один job, а не несколько версий. Checkout проверяет exact head SHA. Перед native step выполняются npm ci и `prepare-early-slice-inputs.py`, который получает exact публичные Git fixtures через shallow fetch и проверяет commit/clean/tree. Его10min timeout не означает10min обычного сетевого расхода.

`package.json`: `test:quick = bash tools/check-canonical.sh --quick`; `test = bash tools/check-canonical.sh --native`; `test:navigation-model = node --test tests/navigation-model.cjs`; browser suite — отдельная команда из трёх browser tests. Workflow делает последовательно:

1. `npm run test:quick -- "$evidence/canonical"`.
2. `bash tools/check-core-early-slice-ts.sh`.
3. `npm run test:navigation-model`.

Прочитаны обе обёртки и один уровень их suite calls. Canonical quick включает vocabulary/retired checks;15 общих TS checks от quarto-version-policy до course-theme; performance pandoc filter; native-default-runtime/native-preview-no-work; native suite8tests, condition suite5tests, operation suite5tests; ещё18 TS checks от native-project-contracts до catalogue-cite-a1, включая prepared-operation, reuse и empty scratch; затем21 текущий `tests/assignment-*.ts`. Это77 top-level executable checks с учётом раскрытых трёх suite wrappers; внутренние render children сюда не входят. Early wrapper —25 отдельных a4c/a4b/a4a suites от native-format-scope-a4c до early-layout-a4a.

**Прямого suite дублирования между тремя командами workflow нет.** В quick отсутствует ветка `if mode == --native`, которая добавила бы early/navigation/browser. `navigation-typecheck` проверяет TS graph, а `navigation-model` — поведение Node model; это разные гарантии. Смена quick на `npm test` при сохранении двух следующих команд дала бы прямой повтор early/navigation и добавила browser; это не экономия. Повтор compile/import общей реализации разными tests — ещё не доказательство лишнего теста. Снятые внутренние source/runtime fences в operation.ts — другой тип повторов, не повторные CI suites.

## Что уже зелёное и что затронуто

Относительно a478 diff содержит45файлов,+1958/−2562: assignment admission/execution/producer, document contracts/normalization, capture/operation/source-snapshot, early-context.lua/early-filter.ts, tests и документы. Это существенно шире последних двух perf edits. Поэтому нельзя обосновать весь draft PR только четырьмя чистыми owner/fence controls.

| Область | Имеющиеся свидетельства | Значение для draft |
|---|---|---|
| Navigation/Presentation model, theme, version policy, process toolchain, основная A1/A2 историческая матрица | Exact main a478 CI SUCCESS; эти реализации/старые тесты не менялись | Не приписывать им новый d7 PASS; полный повтор на каждом draft push имеет низкую адресность |
| Assignment admission, roles/profiles, child command/env, document/body/shortcode/project facts | Изменённые реализации и existing `assignment-*.ts`; сохранённые адресные regressions в core-native/registered-shortcode отчётах | Сохранить весь нынешний21-file assignment glob: он проверяет продуктовые функции, а не только обвязку |
| Owner/private JSON DTO | `core-typed-owner-boundary.md`: pure owner boundary PASS1.101s; affected Core→PL positive graph PASS6.280s; independent review APPROVED | Оставить existing navigation-typecheck и assignment-owner-boundary; compiler и runtime guards дополняют друг друга |
| Source fences, exact empty scratch, observed implementation dependencies | `core-bounded-fences.md`/review: closing-source0.999s, scratch0.968s, seal-observations0.983s, APPROVED | Сохранить эти tests; чистые controls не доказывают actual output seal |
| Actual capture, currentness и output mutation | Existing native-capture-scope-a4c и native-capture-fences-a4c используют настоящий Core capture и Quarto children на своих маленьких fixtures | Сохранить эти две existing suites в draft: fences содержит output collision, Source edit и first-output mutation negatives; не заменять их чистым guard test |
| Остальная ранняя матрица a4c/a4b/a4a | Часть проходит через изменённые early/operation функции, хотя suite-файлы прежние | Полный набор нужен перед интеграцией; прежний green не доказывает отсутствие d7 regressions |

PL `da2ad6b6c00891590e59e5abccf3d1eb4ff552f7` hosted CI `41684816393` SUCCESS на Core830 и manual SP830 native7questions/6upload/1workspace PASS38.196s — предоставленные existing свидетельства, не d7 qualification. Один installed d7 SP attempt выполняет другой owner; новый не запускался. Его итог нужно брать у этого owner. Даже его успех не заменит mutation negatives Core.

Опора на уже выполненный R0/followup: `java-improvement-research-20261103/docs/research/heavy-operations-20261103/core.md` C01/C02/C03/C07 и `reports/sp-native-source-fence-cost.md`. Там разделены initial whole-owner snapshot, metadata/membership fences, runtime digest и output rehash. Initial873rows/5.13MB и14output reads не сняты нынешними perf edits. Нельзя выдавать270/210min timeout или старые whole-course расходы за стоимость нового d7, а source4→3 и runtime4→3 — за измеренное ускорение.

## Один рекомендуемый шаг

**Перед первым draft PR применить одну узкую политику workflow: draft выполняет один affected native step из существующих tests, полный нынешний native step остаётся для ready PR/main/manual/schedule.** Draft step: `navigation-typecheck`, `prepared-operation`, `native-empty-supporting-scratch`, весь существующий `assignment-*.ts`, `native-capture-scope-a4c`, `native-capture-fences-a4c`. Это26 test invocations, из них две настоящие native capture suites; не новый coverage framework, registry, receipt или matrix.

Конкретный состав команд для будущей workflow правки, здесь не выполнен:

```bash
set -euo pipefail
export QUARTO="${QUARTO:-quarto}"
export CANONICAL_EXTENSION="$PWD/_extensions/course-core"
export QUARTO_RUN_NO_NETWORK=true
for name in navigation-typecheck prepared-operation native-empty-supporting-scratch; do
  "$QUARTO" run "tests/$name.ts"
done
for file in tests/assignment-*.ts; do
  "$QUARTO" run "$file"
done
for name in native-capture-scope-a4c native-capture-fences-a4c; do
  "$QUARTO" run "tests/$name.ts"
done
```

Существующие checkout exact-SHA, Quarto setup и evidence upload сохранить; timestamps/exit/logs направлять в уже существующий evidence каталог, без новой системы свидетельств. Общую сеть early fixtures и npm ci для draft можно пропустить: выбранные scripts используют Quarto/std mappings, собственные временные fixtures и текущий source; `native-capture-a4c.ts` не читает external fixture checkout. Полную подготовку сохранить вместе с прежним full step для остальных событий. В `pull_request.types` обязательно включить `ready_for_review` наряду с opened/synchronize/reopened: исходный default trigger сам по этому переходу полный набор не запустит. Не добавлять автоматический повтор на converted_to_draft.

Цена адресного step ещё не измерена; обещания секунд/процентов не даются. Минимальность здесь — одна политика в существующем workflow и готовые tests, без новой обёртки и смены контрактов. Ограничение сознательное: draft не заявляет полный green всей ранней матрицы. До готовности к merge должен пройти сохранённый полный набор на actual PR head; d7 installed SP acceptance остаётся отдельным настоящим consumer свидетельством.

**Точка решения root:** согласовать эту одну workflow правку с адресным draft step и полным ready/main набором; затем поручить её владельцу Core и прочитать независимый review до открытия PR. Сейчас source/workflow не изменены и платные runs не инициированы. Если менять CI scope не разрешено, открытие draft автоматически вызовет существующий полный job; его известная прежняя цена11m45s–13m35s, текущая цена неизвестна.

## Собственный первичный допуск

Primary `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T21-08-12-01a1a298-ea5d-7d41-b82c-08f6d7318466.jsonl`: session_meta строка1 подтверждает agent_path `/root/core_ci_cost_scope`, собственный id `01a1a298-ea5d-7d41-b82c-08f6d7318466`. Единственный собственный новый interval начинается turn `01a1a298-ea98-79d2-a933-1dd66fded443`, turn_context строка8: top-level `gpt-6.1-sol`/`medium`, nested `collaboration_mode.settings` `gpt-6.1-sol`/`medium`. Standard назначен root; отсутствующий service_tier не выдан за серверное доказательство. Globals untouched; parent interval не переаудировался.
