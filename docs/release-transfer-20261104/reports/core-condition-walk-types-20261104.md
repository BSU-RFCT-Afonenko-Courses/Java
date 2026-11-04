# Core condition walker: unknown на потребляемой границе

База `/home/tolya/JavaCodex/java-release-20261110/core-native-bridge`, HEAD `4a2903e6d03d1dd489b6922d113100826913cf67`; перед работой clean. Прочитаны локальные AGENTS/spec index и existing condition-selection control. Original dirty `core` не затрагивался.

Изменён ровно `_extensions/course-core/document/condition.ts`, только локальный `visit` в `selectCondition`. `v: any` заменён `unknown`; теги Link/Image читаются после property narrowing, URL извлекается из фактически потребляемых c[2][0] через primitive/object property narrowing. Индексируемые objects/arrays и strings сохранены; property checks допускают inherited properties, не требуют массив/mandatory tuple или полный Pandoc Node. Functions также не закрываются искусственным structural contract (в JSON/structuredClone они обычно недостижимы). Прочие примитивы не дают URL и попадают в прежний CONDITION_NODE diagnostic. Условие непустого string URL и выбор links/resources прежние.

Object.values и дочерние массивы имеют local readonly unknown[] views; Array.isArray не считается Node/Attr proof. Неизвестные Native node tags/extra fields продолжают рекурсивно обходиться в том же порядке. Нет closed union, casts, нового decoder/schema/framework, IO или parse. boundDocument/decode/clone/freeze/sort/capabilities и остальные guards не менялись; необходимость повторных bound/decode/freeze этим typing-срезом не переобосновывается.

[Exact patch](core-condition-walk-types-20261104.patch). Предварительный git diff --check PASS. После final PASS Source заморожен; commit/push/state не выполнялись.

## Ограниченная проверка

- Existing pure `tests/condition-selection-a2.ts` PASS **0.3655 s**: exact late occurrence/identity/Pandoc version/immutable result/projection/partial/unknown refusals. Новые tests не добавлялись; control не является полной матрицей malformed Link/Image indexed shapes.
- Один affected bundled compiler для condition.ts + существующего control PASS **3.1365 s**.
- Общий elapsed **3.5025 s**, cap10 s/retry0. [Command/exit/elapsed](core-condition-walk-types-check-20261104.json), [control log](core-condition-walk-types-control-20261104.log), [compiler log](core-condition-walk-types-compiler-20261104.log).

Использован `/opt/quarto/bin/tools/x86_64/deno`, cache `/home/tolya/.cache/quarto/deno_std/cache`, existing actual map `/tmp/core-native-bridge-imports.json`; check `--deny-import --no-config --no-lock`, не cached-only. Native/render/inspect/network/build/hash/suites не запускались. Public.ts transitive imports проверены compiler, это не полный Core typing или Native behavioral acceptance.

## Own origin и review

[Первичный admission](core-condition-walk-types-admission-20261104.json): неизменные chat `01a1a54c-ab39-7a83-808d-a190428ff628`, origin turn `01a1a54c-ab6a-7412-894c-270be9b9aac3`; все 21 contexts top/nested `gpt-6.1-sol/medium` PASS. Standard назначен, hidden tier не утверждается, globals не менялись. Дельта передана root для независимого review; результат review пока PENDING.
