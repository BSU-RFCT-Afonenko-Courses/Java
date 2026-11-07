# Независимый review Performance Native command Markdown

Вердикт: **APPROVE финального Source candidate после трёх адресных display-only исправлений.** Открытых блокирующих findings нет. Это приёмка Markdown представления существующих receipts, не нового сбора/Native capture и не performance improvement claim.

## Scope и evidence

Performance base `5365bea43d7d036a893f134eb2473adf628cdd6a`, checkout `/home/tolya/JavaCodex/java-release-20261110/performance`; pending diff `src/report/run.ts` + existing `tests/report/run.test.ts`, `+142/-1`. Reviewer эти файлы не правил. Прочитаны финальный diff, `NativeCommandTiming`/`ProcessReceipt` в `src/report/types.ts:72–88`, actual launcher producer `src/launchers/tool.ts:6,78–96`, writer [report/evidence](performance-native-command-report-20261104.md). Применимых AGENTS на пути Performance не найдено; новые тексты русские.

Новых test/compiler/Native/render/inspect/hash/network/build/install запусков reviewer не делал. Сохранённый17-process capture повторно не исполнялся и не переписывался; `writeRunArtifacts` для review/preview не вызывался. Source/state/pins/production/DB/commit/push не менялись.

## Проверка финального поведения

Строки ниже относятся к финальному `src/report/run.ts`.

| Граница | Source evidence | Решение |
| --- | --- | --- |
| Нет новых scans/IO/pipeline | `:245–249` передаёт **тот же** уже прочитанный `processes` одновременно formatter и прежнему traces; Markdown вставлен `:362` | Новый helper не читает FS/JSON/env. Existing normalized non-off gate сохранён; off и empty receipts не создают таблицу |
| Честный input type | `:118`, `:145`: `Record<string,unknown>[]`, `readonly unknown[]`, локальные typeof/Array проверки | Нет any, приведения к supposedly validated ProcessReceipt, нового общего decoder/schema/protocol |
| Без payload exposure | `:119–123`, `:176–181`: process/confirmed-parent IDs показываются только в UUID форме actual producer; прочее «не показан» | argv/cwd/env/script names/paths/tokens в таблицу не проходят. UUID-only display не меняет receipt и не создаёт hash/registry |
| Safe fixed command categories | `:124–164`: kind pandoc→Pandoc; deno script position0 либо после run+fixed recorded switches/optional `--`; exact basename quarto.js и verb из render/inspect/pandoc/run | Аргумент чужого script больше не принимается за entry. Unknown prefix остаётся Deno. Это консервативное отображение, не executable attestation/полный Deno CLI parser |
| Native timing semantics | `:135–142`: finite number>=0 **и** точные monotonic_process/launcher-spawn-to-status | Missing/null/legacy/malformed/другой domain/scope→«неизвестно», не0. Реальный0 остаётся0.000. Нет фиктивной CPU/self интерпретации |
| Exit/signal | `:166–173`: целый exit либо unknown; null signal→«—», распознанный signal либо unknown | Не выводятся «успех/полная готовность» по одному exit0; unknown status не скрывается под success |
| Sorting и текст | `:185–194`: сортируется новый rows array, unknown после известных; partial/inclusive/nonadditive disclaimer | Нет сумм, %wall, critical-path/self/CPU claims или полного OS census. Входной processes массив не меняется |

`parent_link=confirmed` отображает именно записанную связь receipt, не новое независимое подтверждение родительства ОС. Частичное покрытие явно оговорено; отсутствие parent receipt/time не объявлено нулём. Existing dataValid/quality/summary/causal JSON не изменены новой таблицей.

## Initial review findings и их закрытие

Первоначальный formatter имел три конкретных края, переданных writer/root по Source без новых repro:

1. Markdown escaping произвольного ID всё ещё печатал private path/token. Финальное `displayedId` допускает только actual UUID shape; existing case содержит private process/parent строки и проверяет, что их нет в тексте.
2. `argv.findIndex(quarto.js)` принимал `run other.ts /private/quarto.js render` за Quarto render. Финальный recognizer смотрит script position и не сканирует его arguments; добавлены оба negative — foreign script и unknown prefix.
3. Одного `wall_seconds` было недостаточно для подписи launcher wall. Финальный helper проверяет оба поля имеющегося NativeCommandTiming; negatives покрывают process_cpu, другой scope и missing semantics.

Это narrowing **только отображения**: никаких новых producer refusals, чтений, runtime qualification gates или изменений process protocol. После исправлений Source перечитан; findings закрыты, восстанавливать первоначальный вариант не предлагается.

## Фактическая проверка и пределы

Writer evidence: initial affected compiler двух roots + один расширенный existing case PASS8ms; после нового Source diff выполнены один affected compiler и тот же **изменённый** case, снова PASS8ms/1passed/0failed/5filtered, fmt2files/diff PASS. Final command elapsed0.951s, общий bounded check расход≈1.899s. Использован system `/usr/bin/deno` с config autodiscovery, cached-only, DENO_DIR unset; не выдан за actual Quarto bundled execution. Reviewer проверки не повторял.

Existing case непосредственно вызывает чистый formatter и проверяет fixed category/fallback, private argv/cwd/IDs, duration/semantics unknowns и0, status, parent display, sorting, empty, partial/nonadditive text. Его прежние summary assertions сохраняются. Writer integration в normalized branch проверена Source, **полный report/trace export pipeline не перезапускался** — для данного pure display diff это честный предел.

17-row preview в writer report выполнен **до** исправлений и так подписан. Финальный Source сохраняет известный actual Quarto prefix и UUIDs; preview не выдан за повторный final run. В нём видны вложенные Quarto run/Deno/render/Pandoc intervals, не независимые расходы. Старые reports не переписаны. Wall/CPU стоимость Markdown formatter и влияние на Java/SP/целый курс UNKNOWN; новый benchmark для этого не назначен.

Conservative recognizer может показать Deno для Quarto с незнакомыми будущими prefix flags; это осознанный безопасный fallback. Invalid/non-UUID legacy IDs скрываются, но raw receipt/trace остаются прежними. Новых ссылок/route/публичного экспорта полного argv нет. Эти ограничения не блокируют scoped approval.

## Собственный допуск

Reviewer `/root/pl_typed_materialization`, primary chat `01a1a25f-b098-7242-bf6d-d0e8f6517361`. Весь origin interval `01a1a25f-b0fb-7fd2-b244-83933af77834` → `01a1a53b-bde8-7192-a360-aa90518d5ffe`:17turn_context, line8→2579; top/nested везде gpt-6.1-sol/high. Standard назначен; hidden service tier логом не удостоверяется. Globals/настройки не менялись, полный environment/secrets не печатались.

**APPROVE финального2file candidate.** Commit/backup/CI остаются решением root; reviewer Source не менял и внешних действий не выполнял.
