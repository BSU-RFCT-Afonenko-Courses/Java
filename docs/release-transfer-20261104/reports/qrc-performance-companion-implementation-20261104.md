# Performance: удаление private QRC normalizer из offline checks

## Допуск и граница

Исполнитель `/root/qrc_perf_companion`, назначение `gpt-6.1-sol/medium`, Standard. Собственный chat `01a1a50e-ccbf-79e1-987c-e5a2fc9f6003`; origin полного интервала `01a1a50e-cceb-7e32-8be1-03b769ca00fb`, root `01a1a4dd-242d-7930-89b5-5da44195e538`. Текущая реализация назначена в turn `01a1a513-e40e-7273-90bb-e3bc7506298c`. Primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-36-12-01a1a50e-ccbf-79e1-987c-e5a2fc9f6003.jsonl`: session_meta строка 1 подтверждает chat/path; все turn_context от того же origin — строки 8 и 129 — подтверждают top-level и nested `gpt-6.1-sol/medium`. Вход и итог PASS; исходная точка не сдвигалась. Standard назначен, скрытая маршрутизация не удостоверяется. Модель/глобальные настройки не менялись.

Performance `/home/tolya/JavaCodex/java-release-20261110/performance`, exact база `10810e482dcecde1eecf2e697cac0e3d628f05fe` подтверждена перед edits; начальный status был чист. Прочитаны model-admission и русское языковое правило в предыдущей части того же полного интервала; AGENTS на пути Performance нет. Следовал уточнению root: сохраняются исторические **данные**, а не исполняемый ceremonial replay. Предыдущий plan `qrc-performance-companion-next-20261104.md` в части historical flag/dispatch/оставления owner-normalizer **заменён этим решением** и как реализация не применяется.

Единственный writer offline outputs/tools этой роли. Соседние изменения `src/protocol/validate.ts`, `tests/protocol/events.test.ts` появились во время работы другой роли; они не редактировались. Process/scanner/mainstate/QRC/installed providers/manifest pins/student records не менялись. Commits/push не выполнялись.

## Итоговая дельта

- Удалены `tools/owner-normalizer.ts` и его единственный специальный fixture `tests/integration/owner-normalizer.test.ts`. Больше нет Git blob extraction, временного исполнения private QRC module или нормализаторных hash receipts. Не добавлены historical flag, dispatch helper, новая policy/schema или replacement framework.
- В `src/analysis/outputs.ts:5–62` удалены `normalizeOutput`, Core attr sorting, `normalizeHtml` callback и source roots. При существующем `java-pilot-core-qrc-v1` сначала проверяется membership всех файлов (`:15–19`), затем HTML content пропускается до hash/read (`:20–21`). Остальные файлы сравниваются как прежде; ZIP names и извлечённые bytes проверяются прежним алгоритмом (`:22–51`). Никакой новой нормализации или общего inventory не введено.
- Comparator сообщает `checkedContentEqual`, `htmlBytesCompared`. Java result не содержит `equivalent`; default strict продолжает буквальное сравнение всех bytes и сохраняет `equivalent` для своего реального diagnostic consumer `tools/scaling.ts:100–120`. Этот consumer не менялся, typecheck прошёл.
- `tools/validate-java.ts` больше не загружает normalizer и не передаёт roots/callback. Его итог `:168–180` использует проверенное содержимое и leaks, а не полную эквивалентность. Existing successful/dataValid run selection, frozen upstream/control source-set checks, отсутствие outputs и leaks scans по output/ZIP сохранены по diff. Четвёртый positional control argument не перемещён; прежний третий argument normalizer manifest больше не читается.
- `tools/recheck-repeat.ts:1–57` сохраняет checks двух off runs одной prepared copy и snapshotRoot. Из исторического result не наследуются прежние `equivalent`, `ownerNormalizer`, `normalization`; comparisons пересчитываются в новом ограниченном смысле. Исходный файл не переписывается, сохраняется ссылка `recheck.originalResult`; прежние normalizer/source-file hashes не вычисляются. Новый результат пишется в прежний отдельный `repeat-validation-rechecked.json`.
- `tools/repeat-java.ts:51–68` согласован с теми же полями и exit status. Его реальные renders не запускались.
- `docs/development.md:289+` описывает текущую policy/команды/границы, `docs/self-review.md:13–14` больше не обещает runtime sorting/replay, `docs/java-pilot-report.md:23+` получил датированную оговорку. Старый отчёт и JSON evidence не переписаны как новые измерения; вне вставки прежний текст сохранён. Markdown formatter первоначально переформатировал старые абзацы; этот посторонний formatting восстановлен из exact базы, оставлены только содержательные участки.

Source поиск не выявил другого product consumer удалённого callback/helper/экспорта `normalizeOutput`. Единственные старые helper imports были два tools и удалённый fixture; comparator imports — validate/recheck/repeat/scaling. `check-java-general-use.ts` использует собственный `compareStrictTrees`, а не этот API. Исторические docs/plans не объявляются текущим runtime contract. Внешние неизвестные consumers API этим ограниченным поиском не исключены; совместимость удалённых exports/полей им не обещается.

## Предметные проверки и фактическое исполнение

Существующий `tests/analysis/outputs.test.ts` сокращён до четырёх полезных файловых cases:

1. Java policy не сравнивает имеющийся HTML, явно не выдаёт `equivalent`, но обнаруживает отсутствующий HTML. В fixture намеренно различаются содержательные ID/href/text: результат прямо показывает границу проверки, а не утверждает их семантическое равенство.
2. Изменённые binary resource bytes дают отрицательный результат.
3. Разные ZIP container comments с одинаковыми entries/bytes допускаются, изменённые распакованные bytes и member name отвергаются. Используются реальные локальные `zip`/`unzip`, без native render.
4. Default strict отвергает разный порядок HTML attrs, разные invalid UTF-8 bytes и разные ZIP container bytes.

Удалены normalizer-only/idempotence/order/snapshot expectations, а не предметные owner tests. IDs/targets/labels/local/full и privacy продолжают проверять существующие QRC/owner fixtures из companion plan; они здесь не повторялись и не выдаются за свежие PASS.

Применён test-first цикл. Первый запуск нового fixture на прежнем Source (`deno test --cached-only --no-check -A tests/analysis/outputs.test.ts`) дал ожидаемые 3 FAIL / 1 PASS: HTML bytes ещё отвергались, новых scoped-result fields не было. После production diff первый typed-run дал 3 PASS / 1 FAIL: новый ZIP fixture вернул неуспешный `zip -z` на вызове с пустым комментарием. Stderr того вызова первоначально не был включён в assert, поэтому точная внутренняя причина zip не заявляется. В fixture пустые комментарии заменены непустыми, как в двух уже прошедших pack calls; добавлен stderr в сообщение assert. Не выполнялся retry неизменённого failing test; исправленный fixture прошёл. Повторный полный suite не назначался и не запускался.

| Фактическая команда в Performance cwd | Результат |
| --- | --- |
| `deno test --cached-only -A tests/analysis/outputs.test.ts` после коррекции fixture | **4 PASS / 0 FAIL**, typed test; 33 ms собственное test execution |
| `deno check --cached-only src/analysis/outputs.ts tools/validate-java.ts tools/recheck-repeat.ts tools/repeat-java.ts tools/scaling.ts` | **PASS**, exit 0 |
| `deno fmt --check src/analysis/outputs.ts tools/validate-java.ts tools/recheck-repeat.ts tools/repeat-java.ts tests/analysis/outputs.test.ts` | **PASS**, exit 0; последняя правка — только русские test names |
| Адресный `git diff --check` своих production/test/docs paths | **PASS**, exit 0 |

Runtime `/usr/bin/deno` **2.9.7**, TypeScript **6.0.3**, V8 `15.0.245.2-rusty` (подтверждено отдельным `deno --version`), автоматически выбранный `deno.json` текущего checkout, без собственных DENO_DIR/env/config overrides. Все Deno test/check вызовы `--cached-only`; `--node-modules-dir=manual`/`--frozen` не задавались (обычный `deno task check` их также не задаёт). Это уточнение передано соседней роли, у которой иной manual toolchain отказал на missing `npm:@types/node`; её failure не подменяется этим PASS. Это системный Deno 2.9, а не bundled Quarto/Core typechecker: прежний QRC отказ на неподдерживаемый `--cached-only` не является результатом этой проверки и ею не исправляется. Новых загрузок/установок не было. У каждого запуска `timeout 12s`; суммарный учтённый elapsed RED + исправленная проверка + affected compiler + fmt/diff checks — **3.4162 s**, ниже согласованного общего cap 45 s, unchanged retry 0. Нативные renders/captures/Quarto inspect/build, новые benchmark/full-tree hashes/инвентаризации/сеть не выполнялись. Tests естественно исполняют существующий comparator над собственными маленькими файлами, не производственными деревьями.

## Честная граница результата

Убрана более сильная собственная HTML byte guarantee. Новый Java gate **не обнаруживает** произвольные изменения текста/ID/подписи/href внутри присутствующего HTML и не доказывает общей HTML semantic equivalence. Нет обещания полного student privacy proof только по instrumentation leak scan. IDs/targets/labels, роли и resource/private selection остаются предметной ответственностью существующих owners и финальной функциональной приёмки выбранного release.

Сохранённые manifests/results остаются историческими данными, в том числе при pin798; новая private-module-independent проверка не требует восстанавливать удалённый QRC runtime canonicalizer. Старый executable replay намеренно отсутствует. Independent review назначает root; текущая дельта не закоммичена и готова к этому review, полного CI/установленного выпуска не заявляет.

Перед передачей перечитан весь primary интервал от исходного origin: turn_context строки 8,129 подтверждают прежнее назначение. Итоговый допуск PASS.
