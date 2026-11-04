# Download: unknown preflight и document envelope

База: Download `29e8bec23b85ec0f0dd89883c4df1498111fccc9`, checkout `/home/tolya/JavaCodex/java-release-20261110/download`; до работы clean. Изменён ровно `_extensions/project-download/native-ts/early-filter.ts`. Source заморожен после final compiler PASS; commit/push/mainstate/CI pins не выполнялись.

## Точный срез

`preflight` принимает `unknown`. Ветви array/object сохраняют прежний рекурсивный обход, порядок и reserved-marker/transport diagnostics. Локальные `readonly unknown[]` и `[string, unknown][]` представляют только потребляемые элементы/entries; `Array.isArray` не считается доказательством Pandoc Node или Attr. Existing `classes`/`fields` имеют `ReadonlySet<unknown>`: те же литералы и runtime membership, без новых typeof guards поверх `Set.has`.

Удалены `document as any` и чтение `(document as any).blocks`. Genuine `hasBlocks(value: object): value is object & { blocks: unknown[] }` содержит существующий property guard: `"blocks" in value && Array.isArray(value.blocks)`. Прежние null/typeof guards остаются на внешней границе; predicate не вводит повторную проверку/декодирование, closed AST schema или новый набор допустимых узлов. `const doc = document` сохраняет исходную identity, неизвестные поля и существующий return. Никаких новых runtime правил, IO или reparse.

`downloadName`, `request`, `scan`, корреляция parent/key и replacements не изменены. Явные `any` остаются у `downloadName(v)`, двух его callbacks, `request(v)`, `replacements` parent/value и `scan(v,parent)`. Это следующий отдельный unresolved recognizer/parent-key срез, не заявленное покрытие полного AST. Existing helper `record` из принятой предыдущей дельты не расширялся.

Exact diff: [download-preflight-types-20261104.patch](download-preflight-types-20261104.patch).

## Проверки и первое диагностическое отклонение

Команды, elapsed и exit полностью сохранены в [checks JSON](download-preflight-types-checks-20261104.json). Runtime: `/opt/quarto/bin/tools/x86_64/deno`; существующие cached map `/tmp/core-native-bridge-imports.json`, cache `/home/tolya/.cache/quarto/deno_std/cache`; `--deny-import --no-config --no-lock`, без cached-only/install/network.

1. Existing `tests/literal-request.ts` pure control PASS, 20 cases, 0.3238 s: все исходные 15 и временные пять добавленных случаев. По прямому указанию root added cases затем удалены; файл точно равен baseline29e8. Control после удаления не повторялся. [Первый control log](download-preflight-types-control-20261104.log).
2. Первый affected compiler FAIL TS2322, 0.7797 s: TypeScript не переносит проверку свойства `Array.isArray(document.blocks)` на whole-object annotation `object & { blocks: unknown[] }`. [Первый compiler log](download-preflight-types-compiler-20261104.log). Ошибка сохранена, cast не применялся.
3. Root отдельно разрешил changed-Source correction через genuine predicate ровно существующего guard и один compiler ≤5 s. Final affected compiler PASS, 0.7672 s, оба entrypoints `early-filter.ts` и `tests/literal-request.ts`. [Final compiler log](download-preflight-types-compiler-final-20261104.log). Это проверка исправленной Source после конкретного diagnostic, не unchanged retry.

Итого 1.8711 s из общего cap10 s, retry0 unchanged. `git diff --check` PASS. После final PASS production Source не менялся. Native/render/inspect/build/hash/fullsuite не запускались. Итоговая дельта tests отсутствует; новые тесты/framework не добавлены.

## Собственный допуск и handoff

[Own origin admission](download-preflight-types-admission-20261104.json): исходный chat `01a1a54c-ab39-7a83-808d-a190428ff628`, исходный turn `01a1a54c-ab6a-7412-894c-270be9b9aac3`; все 15 turn_context от исходного turn подтверждают top-level и nested `gpt-6.1-sol` / `medium`. Standard назначен; скрытый routing не утверждается. Origin/globals не менялись.

Root запросил независимый review у `pl_typed_materialization`. Дельта готова для этого review, без claims Native authority или полной AST типизации.
