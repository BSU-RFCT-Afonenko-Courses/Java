# Site: проверка только активной аудитории

## Статус и точная граница

Подготовлен обратимый локальный candidate, не production acceptance. Изолированная worktree `/home/tolya/JavaCodex/java-release-20261110/site-native-audience`, branch `codex/site-native-audience-20261110`, exact base `d16f2322c1da3c96c2e93d32225f5428ae347756`. Исходные `site-java`/`site-sp`, installed расширения, курсы и выдачи не изменялись. Commit/push/mainstate/pins не выполнялись. Требуется независимый Source review root/другого high и показ tradeoff пользователю перед production.

Parent разрешил удаление ordinary alternate-audience preflight с явным сужением cross-profile coexistence guarantee. Основание — [предыдущий адресный Source разбор](site-native-inspect-next-20261104.md) и сохранённые SP receipts; новый SP run не назначался.

## Реализованный diff

Изменены ровно два production файла:

- `_extensions/course-site/application/compose.ts`: удалён root alternate `validateAudienceOutputs`; root уже проверял active audience через `validateAudience`. Child alternate helper заменён прямым `validateAudience` с прежними `course.view`, native projection и expected root audience. Проверка child остаётся до записи inspected plan, cleanup и render.
- `_extensions/course-site/infrastructure/config.ts`: удалены alternate helper и единственный обслуживавший его `inspect` wrapper. Адресный поиск callers выявил только helper/compose/устаревшие focused expectations. `workspace` и `inspectProject` продолжают собственный selected Native inspect. Pure `validateAudience` не менялся.

В `spec/contract.md` удалено обещание автоматической непересекаемости student/full outputs; уточнено, что selected owner/profile fact не удостоверяет невыбранный профиль. Действующие independent selected-source inspection и active audience mismatch обещания сохранены.

Скорректирован только существующий `tests/site-profiles.ts`: удалены obsolete alternate-output ожидания и обращения через `as any` к удалённому helper; восемь mismatch controls и четыре положительных student/full/custom-profile случая обращаются к existing pure guard. Сохранён actual root mismatch до cleanup. Добавлен actual child mismatch при совпадающем student/full output: ожидается именно child `SITE.AUDIENCE_MISMATCH`, оба предыдущих ZIP остаются побайтно прежними. Такой случай прежде остановился бы на root alternate output overlap. Новых casts, skips, flags, caches, receipts, filename эвристик и validators нет.

Точный четырёхфайловый diff сохранён в [candidate.patch](site-active-audience-implementation-20261104/candidate.patch). После успешных checks production/test Source не менялся.

## Обязательный tradeoff перед production

**Runtime больше не гарантирует сохранность/сосуществование старой выдачи другой аудитории при совпадающих или вложенных output-каталогах.** Cleanup текущей полной композиции может удалить её, а render — перезаписать. Для одновременного хранения student/full автор должен задать непересекающиеся каталоги. Candidate не обещает автоматический отказ по конфигурации невыбранной аудитории. Это реальное изменение договора, а не уже квалифицированная production оптимизация.

Сохранены selected root/child output geometry и source/sibling/root overlap guards, protected-source cleanup и cache protections, selected sources до cleanup, current collection/output coverage, actual Native profile forwarding/defaults/groups, Core role/physical/lifetime/operation/release guards, QRC current publication и ZIP placement. Удаление alternate inspect не даёт author projection права на student выдачу. Произвольные authored resources отдельным taint analysis не проверялись. Source Core/QRC/Download и их hook paths не менялись.

В сохранённом SP stage этот удалённый Source путь дал шесть full-profile inspect: root + пять child, суммарно 9.914178029 s отдельных inclusive launcher envelopes. Это историческая цена вызовов, **не измеренный wall delta candidate**, не процент ускорения и не гарантия завершения SP в 120 s. Selected/Core/QRC inspect и остальные затраты остаются. Совпадающие active argv между фазами не объединялись.

## Выполненная проверка

Один sequential check driver, `retry=0`, общий outer cap `timeout --signal=TERM --kill-after=3s 42s`: вместе с kill grace максимум 45 s. Фактический elapsed driver **6.951663 s**, exit0. Команды, env и индивидуальные результаты сохранены в [commands.json](site-active-audience-implementation-20261104/commands.json) и [results.json](site-active-audience-implementation-20261104/results.json), stdout/stderr каждого шага — в том же каталоге.

Рабочая папка — указанная isolated worktree. Actual bundled executable `/opt/quarto/bin/tools/x86_64/deno`, runtime Native executable `/opt/quarto/bin/quarto`. Общие параметры `--deny-import --no-config --no-lock --import-map=/home/tolya/JavaCodex/java-release-20261110/reports/site-active-audience-implementation-20261104/imports.json`; `--cached-only` не использовался. Env overrides:

```text
DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache
DENO_NO_PACKAGE_JSON=1
QUARTO=/opt/quarto/bin/quarto
XDG_CACHE_HOME=/home/tolya/JavaCodex/.cache
QUARTO_RUN_NO_NETWORK=true
```

[Actual import map](site-active-audience-implementation-20261104/imports.json) указывает на реальные Core и PL public modules, `stdlib/path` и `stdlib/fs`. Read-only HEAD перед check: Core `/core-native-bridge` — `4a2903e6d03d1dd489b6922d113100826913cf67`; PL `/prairielearn` — `d8efb030ee6435f3c1f7d242e7388803b55b3299`. Это текущие checkout зависимости проверки типов; прежние 0cf/f3, installed или hosted CI provenance ими не подменяются. Runtime controls отказывают до Core hooks и не квалифицируют исполнение этих соседей.

| Единственный запуск | Результат | Elapsed | Что проверено |
| --- | --- | ---: | --- |
| `deno check` с общими параметрами, roots `config.ts`, `compose.ts`, `tests/site-profiles.ts` | PASS exit0 | 4.421316 s | Оба изменённых production файла и изменённый control через actual public type graph |
| `deno run --no-check` с общими параметрами, `--allow-read --allow-write --allow-env --allow-run tests/site-profiles.ts` | PASS exit0 | 2.088771 s | Восемь active mismatch, четыре valid/custom audience, root/child отказ и сохранность ZIP при общем student/full output |
| Та же команда для неизменённого `tests/site-paths.ts` | PASS exit0 | 0.218932 s | Normalized paths, symlinks, source/output overlap, reserved output paths, profiles и отказ старого синтаксиса |
| Та же команда для неизменённого `tests/site-collection.ts` | PASS exit0 | 0.221676 s | Фактический список profiles из post env, отсутствующие/current outputs, containment, protected source cleanup и refusal до перезаписи mount resource |

`site-profiles` выполнил **3 selected project inspect, 0 alternate inspect, 0 render**: один selected root перед первым refusal, затем selected root + child перед вторым. Это следует из достигнутых existing planning phases/Source paths и успешно проверенных точных diagnostics; отдельный measurement/receipt protocol для подсчёта не вводился. `site-paths` и `site-collection` Native не запускают. Итого весь назначенный пакет: 3 inspect, 0 render, 0 retries. `git diff --check` до execution — exit0.

## Что не заявляется

Полный SP root/student composition, Native render, matrix, metadata-files Native rendering, старый 115 s run, CI и установленный выпуск не повторялись. Временные Native fixture проекты создавались и удалялись существующими controls в `/tmp`; реальные курсы/outputs не переключались. Сеть/установки/новые hash или status-inspect/probe действия не выполнялись. Проверки не подтверждают полный student privacy output corpus, whole-course success либо фактический выигрыш времени. Здесь подтверждены адресный Source candidate, active guards и перечисленные focused controls.

## Собственный первичный допуск

Назначение `gpt-6.1-sol/high`, Standard. Собственный chat `01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1`, agent path `/root/perf_event_types`; неизменный origin turn `01a1a514-1688-7662-80fc-58bf7a272ae0`. Применены Site AGENTS/spec index и общее правило русского языка; допуск по `java/docs/model-admission.md`.

Источник `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-41-59-01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1.jsonl`. Перед Source и перед итогом перечитан весь собственный интервал от того же origin: session_meta строка1 подтверждает собственный chat/path; все turn_context строки **8/171/270/405/734/918/1031/1046** имеют верхние `model=gpt-6.1-sol`, `effort=high` и согласованные `collaboration_mode.settings.model/reasoning_effort`. Итог PASS, [сохранённые значения](site-active-audience-implementation-20261104/primary-admission.json). Глобальные настройки не менялись; явного `service_tier` в первичных записях нет, скрытая серверная маршрутизация не удостоверяется.
