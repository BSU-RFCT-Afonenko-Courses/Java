# Download: typed recognizer → request → deferred replacements

Candidate **PASS addressed checks**, готов для независимого root review; commit/push не выполнялись. Ровно один production file `_extensions/project-download/native-ts/early-filter.ts` поверх отдельно принятого preflight commit `4d9f8cd55823ef330e5b48d9de1009f271afc3cd`. [Exact patch](download-recognizer-types-20261104.patch): +79/-32. Existing tests не менялись, новых случаев/матрицы нет. После compiler/control PASS Source заморожен.

## Что изменено и почему тип честный

- Строки55–74: local `indexableObject` проверяет только non-null object. Он включает arrays и даёт unknown-valued property access, **не** Pandoc schema/Span/Attr predicate. `shortcode` сохраняет `c` и `c[0]` как индексируемые object/array, а classname membership допускает и прежний `string.includes`, и `array.includes`. `ShortcodeView` содержит только извлечённый `attr:unknown` и `children:unknown`; исходный AST не копируется и не нормализуется.
- Строки75–92: `downloadName` возвращает `DownloadView` с `children:readonly unknown[]` только после actual array guard и прежних first-param/data-value conditions. Он не приписывает outer/inner Attr tuple, полноценный Span или child-node union. Структура `first.c` может быть индексируемым object либо array, как раньше. Array pairs остаются unknown до непосредственных сравнений элементов.
- Строки94–130: `request` принимает этот consumed view. Outer Attr и каждый param Attr проходят **тот же single existing `attr` parser**, в том же порядке; primitive/tag/array/content checks лишь перевыражены для unknown. Space/SoftBreak с единственным `t`, динамические args, duplicate/unknown Attr, named/default semantics и existing `decodeDownloadRequest` остаются прежними. New business constraints/второго parser нет.
- Строки175–219: `Location` связывает array parent с number key, object parent со string key. Все места создаются в фактической ветви traversal. Generated replacement имеет локальный тип только **собственного создаваемого** Span/Str transport. `scan` принимает unknown; marker извлекается один раз и reuse-ится для Download recognizer и другого-shortcode preservation. Replacements по-прежнему применяются после полного scan в прежнем порядке. Нет `parent:any`, `value:any`, `key!` либо cast.

Начальный scan обходит элементы уже проверенного `blocks` через существующий array iteration; искусственного parent для корневого массива не вводится. JSON-массив сам не имеет именованных `.t/.c` полей: arbitrary extra **JSON Native nodes/fields** сохраняются и проходят тот же nested object/array traversal. Идентичность document/children сохраняется; чужой shortcode с marker остаётся целиком Core-owned. Никакого stripping unknown AST fields/full closed Native model, runtime registry, нового preflight/walk/schema, reflective access framework, IO или imports не добавлено. Единственный прежний preflight/reserved guard не менялся.

## Честная граница malformed/direct-JS поведения

Root прямо разрешил primitive narrowing existing external recognizer вместо unchecked optional-chain вызова. Для malformed JSON classname значения, у которого нет callable `.includes`, поведение может измениться: например `Span.c = [["",42,[]],[]]` прежде давал случайный TypeError, теперь не распознаётся как shortcode и scan продолжается. Object classname с не-callable includes ведёт себя аналогично. Это **не** новый controlled validation certificate: malformed дерево может сохраниться до следующего Native/Core consumer либо быть отвергнуто им. Здесь не утверждается равенство всех старых malformed diagnostics.

При этом строковый classname, содержащий marker как substring, по-прежнему распознаётся; последующий существующий Attr parser по-прежнему отвергает string вместо требуемого class array. Внешний `c` как object с ключами0/1 и правильными фактически используемыми Attr/children не превращён в mandatory closed tuple. Эти формы сохранены по Source mapping; отдельные новые runtime cases для них не запускались по разрешённому объёму.

Экзотические прямые JS inputs с getter/Proxy side effects, functions, named properties на arrays, изменёнными builtin prototypes/custom includes не являются Native JSON shapes и этой партией не квалифицированы как эквивалентные. В частности старый initial scan мог читать named properties корневого JS array; новый начинает с JSON elements. Никакой blanket гарантии эквивалентности всех значений публичного `unknown` не заявляется. Student roles/authority/output/private ownership/scoring/hash/wire/UUID product semantics и accepted valid Native shape не менялись.

## Проверки и пределы coverage

[Checks JSON](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/download-recognizer-types-checks-20261104.json), [compiler log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/download-recognizer-types-compiler-20261104.log), [existing control log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/download-recognizer-types-control-20261104.log):

1. Один affected compiler (`early-filter.ts`, `tests/literal-request.ts`): **PASS0.815893s**.
2. Один unchanged existing pure control: **PASS15,0.365261s**. Он охватывает saved actual reader fixture lowering, прежние hostile Attr/args/UUID/reserved markers и сохранение другого handler для Core refusal.

Итого **1.181185s <15s**, retry0. Runtime `/opt/quarto/bin/tools/x86_64/deno`, existing `/tmp/core-native-bridge-imports.json`/DENO_DIR, runtime `--deny-net --deny-import`; никаких dependency fetch/install. Compiler проверил final Source; после PASS production не менялся. `git diff --check` PASS. Явных `any`, assertions/casts или non-null `!` в early-filter.ts не осталось. Это не значит, что any удалён из external libraries, existing fixture helpers/decoder или всей codebase.

Полного Native/Pandoc stdio round-trip/AST corpus/ZIP/phase authority этой typing партией не запускали и не заявляем; synthetic qualifier observation в existing test остаётся явно pure и не предоставляет authority. Свежие cache captures выполнены ранее отдельно на exact Download944 и не выдаются за integration test этой новой дельты.

Source scope выполнен за отведённые25min; no commit/push/mainstate/installed/course changes. Root владеет независимым review и последующим commit.

Own full-origin primary admission: 30 turn_context, строки8–3977, все top/nested gpt-6.1-sol/high; origin 01a1a25f-b0fb-7fd2-b244-83933af77834, latest 01a1a594-c31f-7f42-b0e6-1c151b68f75e. Global settings не менялись; hidden serving tier не утверждается. Полный env/секреты не печатались.
