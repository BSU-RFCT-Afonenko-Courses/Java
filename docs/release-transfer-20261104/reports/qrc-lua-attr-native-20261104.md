# QRC Lua: native Attr map вместо сортировки

Результат: **ограниченная дельта готова к независимому review**. Один existing
semantic native Lua control прошёл с первой попытки: exit0, **0.6658 s**,
cap15 s/retry0. Source после PASS не менялся.

## Вход и scope

QRC `/home/tolya/JavaCodex/java-release-20261110/qrc`, исходный HEAD
`77abe92357938fcd814713be066c1adffb31d347`; входной status был чистым.
Прочитаны AGENTS, связанные native callers, actual tests и адресные findings
`pattern-pass-core-20261104.md` / `qrc-native-metadata-review-20261104.md`.

Изменены только два product callers:

- `_extensions/reference-catalog/lua/links.lua`;
- `_extensions/reference-catalog/lua/probes.lua`.

Удалён их helper `_extensions/reference-catalog/lua/attributes.lua`.
Existing `tests/metadata-order.lua` заменяет obsolete byte-order assertions
семантической проверкой тех же native Attr/maps и реальных callers.
Runner/CI pin, `targets.lua`, TS HTML/search/import paths и другие checkout
не редактировались. Download находится на независимом frozen review и не менялся.
Commit/push/mainstate не выполнялись.

## Primary contract установленного Pandoc

Web не требовался. Установленные primary type docs
`/opt/quarto/share/lua-types/pandoc/components.lua:21–34` задают:

- `pandoc.Attr.attributes: table<string,string>`;
- третий аргумент `pandoc.Attr(identifier, classes, attributes)` —
  `table<string,string>`, «Table containing string keys and values».

Там же пример native Attr setter использует обычную HTML-like Lua table.
Sorted pair list не является обязательным договором. Использование исходного map
подтверждено реальным Pandoc constructor в выбранном control, а не только чтением
аннотации. Новых API или handmade attribute schema не добавлено.

## Продуктовая дельта и сохранённая семантика

`links.link` передаёт существующий `attrs` map прямо в `pandoc.Attr` и в
обычном Link, и в нейтральном source-export Span. Namespace/ID/style checks,
content, placeholder target, classes и `data-qrc-ref/style/custom` сохранены.

`probes.finish` аналогично передаёт исходные row maps и container map.
Сохранены `data-qrc-id/style/title`, conditional default title, labels/number
references, `hidden=""`, `aria-hidden="true"`, namespace и source input.
Других readers/resolvers или сборочных стадий нет.

**`targets.sorted()` не менялся**: это порядок targets, а не сортировка Attr.
Производственная ручная table→keys→sort→pairs стадия полностью удалена.
На её месте нет другой сортировки/канонизации/обвязки. Имена и значения attrs
не зависят от byte order serializer.

Source доказывает удаление промежуточных массивов и local `table.sort` helper;
wall выигрыш всего курса UNKNOWN — benchmark не запускался.

## Один existing semantic native control

У прежнего `tests/metadata-order.lua` были только sorted-pair и HTML byte-order
assertions и прямой require удаляемого helper. Сохранение такой проверки
возвращало бы отменённую цель. Тот же файл/registration теперь проверяет:

- actual `pandoc.Attr` сохраняет все значения исходного map и не меняет его;
- real `links.link`: native Link target/class, reference/style/custom attrs,
  авторская подпись с `&`;
- real source-export Span: neutral class, reference/style/custom=false и
  исходная подпись;
- real `probes` handlers: ненумерованный heading title, target identity,
  default link/label, отсутствие фиктивного номера, оба probe styles;
- real probes container: hidden/aria и data namespace/source attrs.

Использован существующий подход `package.preload` и локального quarto context
из `tests/performance-filter-context.lua`; новый test framework не добавлялся.
Native Pandoc constructors настоящие, QRC modules настоящие; document context
явно тестовый, полного Quarto course render нет. Byte-order equality отсутствует.

Команда из QRC cwd, stdin пустой:

```sh
/opt/quarto/bin/quarto pandoc --lua-filter tests/metadata-order.lua --to plain
```

Результат: exit0, `PASS native Attr map: QRC links/source-export/probes сохраняют refs, labels и hidden/data-*`.
Команда/elapsed/limits:
[qrc-lua-attr-native-check-20261104.json](qrc-lua-attr-native-check-20261104.json).
Полный вывод: [qrc-lua-attr-native-20261104.log](qrc-lua-attr-native-20261104.log).
Точный diff: [qrc-lua-attr-native-20261104.patch](qrc-lua-attr-native-20261104.patch).
`git diff --check` — PASS; `git diff -- targets.lua` пуст.

В unchanged `performance-filter-context.lua` осталась lazy `package.preload`
запись имени `attributes`; она не вызывает `dofile` без require. Production
callers больше не require этот модуль. Удаление этой неиспользуемой test-preload
записи и переименование старого CI step «Детерминированная сериализация QRC»
не включались как необязательная соседняя чистка. Existing registration нового
semantic control остаётся рабочей.

No whole Quarto render/inspect/compiler/build/hash/network/install/CI/full suite.
Семантика полного native crossref pipeline/browser этим одним Lua control не
заявляется проверенной; нужны независимый review и последующие scoped integration
gates по решению root.

## Полный собственный модельный интервал

Agent `/root/core_yaml_types`, chat `01a1a54c-ab39-7a83-808d-a190428ff628`,
неизменённый origin turn `01a1a54c-ab6a-7412-894c-270be9b9aac3`,
назначение `gpt-6.1-sol/medium`. Весь первичный интервал проверен:
**12 turn_context**, top-level model/effort и nested settings соответствуют — PASS.
Standard назначен; hidden routing не утверждается, globals не менялись.
[Свидетельство](qrc-lua-attr-native-admission-20261104.json).
