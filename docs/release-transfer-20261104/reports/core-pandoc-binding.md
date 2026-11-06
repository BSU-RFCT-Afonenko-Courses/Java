# Core: один выбранный Pandoc на native operation

4 ноября 2026. Реализация поверх Core `2e71100f773c8185c7f125c44167b1555ec669f5`; локальный commit **`ad80be00d6617a427ba6ae7a3e17a55fa1ef5888`**, после commit checkout clean. Только Core writer `/root/core_pandoc_binding_reading`; PL, installed, courses и canonical state не менялись. Push, network, новые version/inspect/hash/render/capture/build не выполнялись. Independent Source review ещё предстоит; локальная проверка не заменяет hosted CI или actual capture.

## Первичный допуск

Собственный chat `01a1a4b6-21ad-7b13-bef6-5243036909e1`, исходный turn `01a1a4b6-21dc-7951-8a4e-1aa50ab73d00`, путь `/root/core_pandoc_binding_reading`. Полный собственный интервал primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T06-59-22-01a1a4b6-21ad-7b13-bef6-5243036909e1.jsonl`: session_meta1, turn_context8/152/232, top и nested `gpt-6.1-sol/high` согласованы. Implementation turn `01a1a4bf-8bdb-77a0-bc81-bb704e16a778`; baseline не сдвигался. Standard назначен координатором, hidden tier не удостоверяется. Globals не менялись.

## Изменение и точный API

`NativeOperation` получает метод:

```ts
pandocBinding(): Promise<Readonly<{
  executable: string;
  environment: Readonly<Record<string, string>>;
  cwd: string;
  diagnosticTool: string;
}> | undefined>
```

`undefined` означает прежний Quarto CLI путь. Метод вызывается consumer один раз в native composition после уже существующего `assertCurrent`. Caller не передаёт arbitrary environment/path. `runtime/operation.ts:2409` сохраняет immutable map того же host environment, который уже сравнивал existing hostGuard, и тот же captured cwd. `:2513` передаёт snapshot private operation closure. Новый getter `:2991` берёт exact Quarto из existing assignment runtime witness и actual owner version; хранит одну Promise результата, включая fallback. До и после asynchronous выбора работают existing callback/sync/host lifecycle guards. Новые per-body CLI/version/hash/inspect/qualification проверки не добавлены.

Private `runtime/pandoc-binding.ts` (79 строк) содержит exact readonly record и узкий selector. Public barrel, persisted schema, assignment admission/`assignmentChild`,21 Core Markdown/GFM conversions не менялись. Новых production any/casts нет. Selector не запускает процессы и не обходит каталоги; только адресные stats известных layout/dev/candidate paths при первом getter.

Поддерживается exact Quarto1.11.5, Linux x86_64/aarch64, canonical physical launcher `quarto`, frozen `QUARTO` и `QUARTO_BIN_PATH`, совпадающие с ним, штатный share/root и JS layout. Frozen explicit semantic env должен уже совпадать с launcher результатом: DOM plugin, TLS store, no-update flag; existing nonempty `QUARTO_DENO` override сохраняется буквально. Debug/force-version, exported nonstandard shell controls/locals, dev Source, missing/mismatched layout возвращают `undefined`.

Выбор Pandoc: frozen absolute canonical `QUARTO_PANDOC` file либо directory+`pandoc`; без override — `bin/tools/<arch>/pandoc`. Missing/relative/необычный override и ошибки чтения возвращают CLI, сохраняя прежние warning/fallback/error там. Env возвращается тем же frozen object, cwd — captured host cwd, diagnosticTool — прежний exact Quarto. Shell не эмулируется. По уточнённому заданию `SHLVL`/`_` являются shell bookkeeping и не входят в semantic Pandoc env contract. Initial owner inspect уже успешно прошёл CLI guards; mutable reconfiguration/override между body не моделируются в frozen operation.

## Проверки и их границы

До runs координатору отправлены точный API, existing bounded fixture и сохранённые nonsensitive actual SP facts; последующий GO подтвердил cap60s/retry0 и semantic env границу.

1. Первый compiler command ошибочно содержал неподдерживаемый `deno check --cached-only`: exit1 до проверки файлов. Сохранён [core-pandoc-binding-typecheck.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-pandoc-binding-typecheck.log), это CLI invocation failure, не Source result.
2. Исправленный affected compiler начал проверку `operation.ts` + existing `tests/static-batch-checkpoint.ts` и нашёл14 TS2304 только из-за отсутствующего test import `join/resolve`. Production errors не сообщил. Сохранён [core-pandoc-binding-typecheck-corrected.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-pandoc-binding-typecheck-corrected.log); импорт добавлен.
3. Тот же affected check с согласованным offline `--deny-import --no-config --no-lock`, прежними Quarto cache/import map: **exit0,0.775s**. [core-pandoc-binding-typecheck-fixed.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-pandoc-binding-typecheck-fixed.log) содержит оба `Check`. Import helper входит в affected product graph. Нового cache install/download не было.
4. Единственный runtime: `QUARTO_RUN_NO_NETWORK=true timeout --signal=TERM --kill-after=5s 60s /opt/quarto/bin/quarto run tests/static-batch-checkpoint.ts`: **exit0,1.139s**, retry0. [core-pandoc-binding-fixture.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/core-pandoc-binding-fixture.log): `PASS_PRIVATE_UNIT_NOT_NATIVE_QUALIFICATION`, прежние lifecycle/static/general cases сохранены; добавлен `pandocBinding=bundled_custom_cached_expired_fallback`.

Fixture использует existing dynamic private `publicOperation` harness, локальные temp layout/file bytes без исполнения этих binaries. Проверены bundled candidate, override file/directory, неизменность выбранного record после удаления bundled candidate (операция не выбирает заново), отказ закрытого getter, debug/bin/version/env/missing override fallback. Сохраняются cwd, diagnosticTool, identical frozen env object и frozen record; нет assertion магического числа runtime checks. Runtime red baseline отдельно не запускался: согласован один bounded runtime после diff. `git diff --check` PASS.

## Сохранённая actual SP composition

Прочитан существующий state, не создан новый capture: `reports/performance-sp-pl9c-delta/course/tasks/_generated/course-spec/native-ts/70187f96-6c53-439e-96e7-e80b06508886/state.json`. Выведены только allowlisted nonsensitive path/control facts; полный env в отчёт не переносится.

- Quarto `/opt/quarto/bin/quarto`, owner version1.11.5; frozen bin `/opt/quarto/bin`, share `/opt/quarto/share`, root `/`.
- DOM plugin `/opt/quarto/bin/tools/x86_64/deno_dom/libplugin.so`, TLS `system,mozilla`, no-update `1` соответствуют semantic eligibility.
- Pandoc override — absolute Performance launcher `reports/performance-sp-pl9c-delta/launchers/pandoc`; Deno override — соседний `launchers/deno`. Они сохраняются как выбранные инструменты, bundled fallback не подставляется.
- Debug/force-version отсутствуют; ни один из19 запрещённых exported controls/locals selector в saved map не присутствует.

Эти сохранённые факты поддерживают eligibility обычной actual composition по новой ветке; это не fresh runtime PASS getter на установленном курсе и не измерение экономии. PL consumer ещё отсутствует: число CLI conversions этим Core commit пока не меняется. Дальнейшая минимальная интеграция — один getter в PL composition и использование record только существующим JSON→HTML command с прежними auxiliary before/after, streams и `ExternalToolFailure`; прочие conversions/ordinary CLI не затрагиваются.
