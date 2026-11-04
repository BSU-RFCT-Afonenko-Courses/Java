# Core A1: строгая граница Native inspection

Назначение `gpt-6.1-sol/high`, Standard; chat `01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1`, agent `/root/perf_event_types`. Primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-41-59-01a1a514-165b-7ae2-bff1-b9ca9f7e3bb1.jsonl`, session_meta строка1. Исходный turn роли `01a1a514-1688-7662-80fc-58bf7a272ae0`; текущая A1 партия `01a1a53e-2507-7a81-8adc-9139c37eb2c2`. Полный интервал turn_context строки8/171/270, верхняя и вложенная пара `gpt-6.1-sol/high` согласованы, вход PASS. Отдельный service tier primary не содержит; глобальные настройки не менялись.

Base `5a28314fc8b704ef7171f33063789d7cc8ec8e79`, отдельная worktree `/home/tolya/JavaCodex/java-release-20261110/core-native-types`, ветка `codex/core-native-types-20261110`. Прочитаны применимые AGENTS, русское языковое правило, spec/index и model-admission. Начальное дерево чистое; accepted bridge/старый Core не меняются.

Root согласовал три production файла: pure private owner-configuration decoder переносится из operation в infrastructure; operation сохраняет stronger files.input requirement и существующие reexports; A1 использует общий consumed owner/config guard без нового unused input requirement, raw Native read становится unknown, live metadata проверяется только на существующей границе перед индексированием. Metadata/custom keys остаются unknown, весь Native договор не валидируется. Узкие новые отказы malformed record/array-of-string на необходимых алгоритму полях заранее согласованы как runtime fix с существующими CONTEXT_RUNTIME/OWNER/FORMAT diagnostics.

## Узкая реализованная граница

Ровно три production файла:

1. `_extensions/course-core/infrastructure/native-owner-inspection.ts` — private общий разбор реально используемых owner/config полей, перенесённых из прежнего operation decoder. `NativeOwnerConfiguration` содержит проверенные `dir`, `quarto.version`, config record и optional files/config paths; остальные ключи/значения остаются `unknown`. `decodeNativeOwnerInspection` поверх него сохраняет прежний обязательный operation `files.input:string[]` и прежний порядок отказов input → config paths → version/dir/config. Проверка не требует input scope от A1, который это поле не потребляет.
2. `_extensions/course-core/runtime/operation.ts` — импорт и прежние reexports `NativeOwnerInspection` / `decodeNativeOwnerInspection`; копии decoder нет. Его public callsites и existing projection/source-policy/output/engine behavior не изменены. Import в private pure infrastructure устраняет необходимость цикла infrastructure → runtime → infrastructure; `public.ts` не расширялся.
3. `_extensions/course-core/infrastructure/project-context.ts` — `inspect():Promise<unknown>`. Сразу после owner JSON read общий decoder сохраняет проверенную форму; его contract errors на этой A1 границе получают существующий `CONTEXT_RUNTIME`. Сразу после document JSON read проверяются existing project.dir/quarto.version, record formats/selected format/metadata; нарушения используют existing `CONTEXT_OWNER` / `CONTEXT_FORMAT`. Raw metadata сохраняется record с неизвестными значениями, без allowlist/closed Native schema/strip. `ownerMetadata` в обоих типах групп стал `Record<string,unknown>`. Origin recursion получает unknown и сужает теми же shape/marker checks; scalar lookup выбирает numeric ID, а не передаёт unchecked any в typed Map.

Никакого повторного Native read/IO/hash/stringify-reparse для типизации не добавлено. Owner и document payload проверяются на своих прежних JSON read границах; typed внутренние contexts повторно не разбираются. Existing scalar-origin, current acquisition, private generation, physical role и lifetime mechanics сохранены.

Semantic дельта заранее согласована с root: malformed truthy primitive/array config или document metadata и неверная форма/элементы owner files.config отказывают контролируемым existing diagnostic до потребления. Profiles/roles/allowlists и возможность Native computing engine не расширялись и не сужались. A1 допускает отсутствие unused files.input; operation по-прежнему требует его. Произвольные Native/config/document extension keys и вложенные значения сохраняются, metadata не объявляется проверенным предметным договором целиком.

## Контроли и фактические результаты

Применены systematic-debugging и test-driven-development: причина установлена на существующем JSON boundary, отрицательный control записан и исполнен до production edits. Existing `tests/native-project-marker-isolation.ts` использует реальный A1 copy/instrument/origin/lifetime код и прежний подменённый Deno.Command как Native payload double; счётчик `children` означает обращения к этому double, **не actual Native children**.

**RED до Source edits:** в первый mode подан `owner.config="unexpected"`; прежний A1 guard пропустил его до consumer, вместо `CONTEXT_RUNTIME` получено `Error: Урезаны произвольные owner metadata`. Control завершился exit1 через 0.267 s с сообщением `Malformed Native payload не получил CONTEXT_RUNTIME`. Это проверяет реальную потерю controlled refusal, не совпадение implementation text.

После candidate и форматирования, одним ограниченным последовательным driver выполнены:

| Проверка | Реальный результат |
| --- | --- |
| Existing marker-isolation control | PASS, exit0, 0.452 s. Шесть malformed cases: primitive owner config / неверный config paths element / malformed project / primitive metadata / array metadata / malformed selected format. Два positive cases: прежний valid flow и full arbitrary owner/document metadata с отсутствующим unused owner.files.input. Два прежних marker отказа missing/wrong-field. Positive cases сохраняют scalar origin, actual captured bytes, private hardlink refusal, generation/clone/expired guards и 3 payload calls для multiformat acquisition. |
| Existing assignment-owner-boundary | PASS, exit0, 0.354 s. После переноса strong operation decoder сохраняются payload/custom data, required input/config/version/projection/source-policy/output controls, computed engines и ordinary defaults. |
| Единственный affected actual bundled compiler | PASS, exit0, 3.220 s; 7 entrypoints ниже. |

`tests/native-context-a1.ts` получил только честное narrowing `ownerMetadata.project` перед проверкой type book. Он включён в compiler, но **его actual Native сценарий не запускался**. Native knitr/png/readme ZIP qualification accepted 5a не повторялась и не приписана текущим unit doubles.

Все GREEN команды и полные выводы сохранены в [`core-a1-inspection-types-checks-20261104.json`](core-a1-inspection-types-checks-20261104.json). Общая среда: `DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache`, `DENO_NO_PACKAGE_JSON=1`, `QUARTO=/opt/quarto/bin/quarto`. Runtime `/opt/quarto/bin/tools/x86_64/deno`; cwd — новая core-native-types. Actual map `/tmp/core-a1-types-imports.json` — копия существующей `/tmp/core-native-bridge-imports.json`, изменён только `course-core/` на новую worktree; stdlib и настоящий PL prefix сохранены. Это не новый resolver и не подмена dependent DTO fixtures.

Behavioral команды:

```sh
/opt/quarto/bin/tools/x86_64/deno run --no-check --deny-import --no-config --no-lock --import-map=/tmp/core-a1-types-imports.json --allow-read --allow-write --allow-env --allow-run tests/native-project-marker-isolation.ts
/opt/quarto/bin/tools/x86_64/deno run --no-check --deny-import --no-config --no-lock --import-map=/tmp/core-a1-types-imports.json --allow-read --allow-write --allow-env --allow-run tests/assignment-owner-boundary.ts
```

Единственный compiler:

```sh
/opt/quarto/bin/tools/x86_64/deno check --deny-import --no-config --no-lock --import-map=/tmp/core-a1-types-imports.json _extensions/course-core/infrastructure/native-owner-inspection.ts _extensions/course-core/infrastructure/project-context.ts _extensions/course-core/runtime/operation.ts _extensions/course-core/runtime/assignment-admission.ts tests/native-project-marker-isolation.ts tests/native-context-a1.ts tests/assignment-owner-boundary.ts
```

Не `--cached-only`; импорт/сеть запрещены runtime flags, установки не выполнялись. GREEN driver total4.026 s, RED0.267 s, суммарно ≈4.293 s при total cap45 s, retry0. Неизменный зелёный program/control не повторялся. После compiler production/test Source не менялся. `git diff --check` PASS; bundled formatter проверил 5 изменённых файлов до GREEN.

## Пределы и итоговый допуск

Не закрыта вся строгая типизация Core/A1: YAML library-node `tree(n:any)` остаётся отдельной задачей, public raw metadata values намеренно неизвестны до их действующих предметных schemas. Existing test double технические any не расширялись и не выдаются за runtime DTO. Actual Native integration accepted base не подтверждает автоматически будущую интеграцию нового diff; независимый root Source review ещё предстоит. Новый общий validation framework, closed Native schema, metadata allowlist, повторное internal DTO validation и дополнительный I/O для типизации не вводились. Отдельные capture/render/inspect/матрица/production query/hash qualification не запускались; existing unit acquisition сохраняет собственные прежние filesystem/tool identity действия.

Commit/push/mainstate/pins и принятая core-native-bridge worktree не менялись. Candidate остаётся локальным diff в `core-native-types` от exact `5a28314fc8b704ef7171f33063789d7cc8ec8e79`.

Перед завершением весь собственный primary интервал повторно проверен от исходного turn `01a1a514-1688-7662-80fc-58bf7a272ae0`: turn_context строки 8/171/270, все верхние и вложенные пары `gpt-6.1-sol/high` согласованы; итоговый допуск PASS. Начало интервала не сдвинуто.
