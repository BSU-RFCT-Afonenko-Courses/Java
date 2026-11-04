# Core late-filter: reuse очищенного AST

## Candidate

Base/actual HEAD `a06ded0b9dea67367e6064452655cbaf6b16a4be`, checkout `/home/tolya/JavaCodex/java-release-20261110/core-native-bridge`. Изменён ровно один production file: [_extensions/course-core/native-ts/late-filter.lua](../core-native-bridge/_extensions/course-core/native-ts/late-filter.lua). [Exact patch](core-late-cleanup-reuse-20261104.patch) сохранён отдельно. Commit/push не выполнялись, Native state/pins/выдачи/installed composition не менялись.

Native ветка сохраняет результат прежнего whole-document cleanup для `.native.json` body и возвращает тот же очищенный AST. Повторный полный cleanup после native sidecar write удалён. Private `clean_document` содержит прежние `Div=clean` и Span title unwrap callbacks без изменения правил. На `COURSE_CORE_TS_RUN` ветке cleanup по-прежнему исполняется один раз после удаления meta keys.

## Source rationale и порядок effects

1. Все request/binding/format/profile/assignment guards, occurrence/title fragment walks и late sidecar checks/writes остаются на прежних местах.
2. После late sidecar write Native ветка исполняет прежний cleanup и JSON body serialization. Cleaned AST сохраняется локально; исходный `doc` не подменяется.
3. `observedDoc` строится из прежнего `doc.blocks`; resource facts получают прежнюю пару `observedDoc, observedDoc`, view/role берутся из исходного документа. Native declarations/diagnostics и `.native.json` write сохраняют порядок. `native-resources.lua` прочитан адресно: его handlers собирают uses/bytes, не переписывают AST.
4. После native writes прежние `doc.meta['course-core-ts-binding']=nil` и `doc.meta['course-core-processed']=nil` остаются на месте. На сохранённом cleaned AST удаляются те же два ключа перед return; все прочие metadata сохраняются. На TS-run ветке saved cleaned отсутствует, поэтому прежний cleanup исполняется после исходных meta removals.

Новых guards/validation/schema, clone/freeze, I/O или resource cache не добавлено. Нет применения cleaned документа к resource observation, изменения physical/resource roles, privacy/UUID/grade или projection rules. Saved Native experiment использовал Core4a; этот candidate ещё не участвовал в Native capture. Уменьшение одного duplicate walk обосновано Source, его wall/CPU эффект не измерен и не выдаётся за объяснение5370ms/900s.

## Контролы и предел проверки

Прочитаны Core AGENTS, spec/index.md, native contracts и локальная каноническая русская языковая политика. Целевой файл перед edit не имел diff относительно HEADa06.

Existing `tests/performance-context.lua` проверяет Presentation/Navigation runtime context, а не этот Core late AST. `tests/assignment-late.ts` проверяет TS finalize над JSON DTO, не исполняя изменённый Lua. Реальные `tests/document-a1-native.ts`, `tests/answers-native-a4b.ts` и другие Native consumers late-filter требуют Quarto render; по запрету новых Native запусков не выполнялись. Релевантного existing pure Lua AST semantic control не найдено. Нерелевантный тест, счётчик cleanup calls или новый mock/framework не создавались.

Единственная выполненная статическая проверка — `git diff --check -- _extensions/course-core/native-ts/late-filter.lua`, PASS. Runtime semantic PASS **не заявлен**. Candidate передаётся root на независимый Source review с явной границей: equivalence body/returned AST/meta/marker cleanup пока обоснована кодом, новым behavioral execution не подтверждена. Compiler/render/profiler/inspect/version/help/network/install/Native —0; основные Source/state вне одного файла не менялись.

## Собственный допуск

Own chat `01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1`, immutable origin `01a1a514-1688-7662-80fc-58bf7a272ae0`, expected `gpt-6.1-sol/high`, Standard. Primary own rollout `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-41-59-01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1.jsonl`, session_meta1. Весь origin interval turn_context8/171/270/405/734/918/1031/1046/1192/1274/1437/1655/1749/1809/1997/2098/2253/2343 проверен, top-level/nested model/effort согласованы, PASS. Hidden routing этими данными не подтверждается.
