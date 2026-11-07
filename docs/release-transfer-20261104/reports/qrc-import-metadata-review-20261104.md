# Независимый Source review QRC import metadata

## Допуск и область

Назначение `gpt-6.1-sol/high`, Standard. Собственный chat `01a1a34e-02f6-7021-ba73-02522b0a6a14`, agent `/root/review_download_types`; primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T00-26-01-01a1a34e-02f6-7021-ba73-02522b0a6a14.jsonl`, session_meta1. Начало роли — turn `01a1a34e-032a-7541-b085-5af16bf83f02`, root `01a1a338-3b4c-79a1-98e8-f635eca07373`. Текущая задача — turn `01a1a4fa-6513-70f3-bcfb-e762c21fd5dd`, root `01a1a4dd-242d-7930-89b5-5da44195e538`. Полный интервал содержит turn_context строки8,105,169,207,346,569,649; все верхние и вложенные model/effort gpt-6.1-sol/high согласованы. Вход PASS; отдельное primary поле service_tier отсутствует, Standard назначен. Настройки не менялись.

Область: pending четыре файла QRC от `4a692ea1934224c844a1a3f277a7a77d513f012b`, первый HTML diff уже в основании. Applicable AGENTS/spec/index/contract и языковое правило прочитаны в той же роли; новый contract diff прочитан. Только review Source/сохранённых свидетельств, без tests/compiler/render/inspect/hash/build/network/commit/push. Root владеет state и дальнейшей записью Source; создан только этот report.

## Вердикт

**APPROVE ограниченный Source diff. Исправляемых замечаний не найдено.** Непотребляемые metadata больше не вызывают blanket отказ, при этом возвращается та же явная projection проверенных полей. Дополнительные поля не приобретают authority и не расширяют output DTO. Полный imports integration и отдельный TypeScript check этой партией не подтверждены.

## Projection и types

`catalog-validation.ts:17–18` использует существующее `isRecord(value): value is Record<string, unknown>` и `invalid(): never`: продолжение возможно только после прежнего guard. Замена `as Record<string, unknown>` на `const item = value` честно использует это narrowing, не добавляя validator/refusal. Guard не менялся.

Return `target():49–60` по-прежнему создаёт новый объект из namespace/id/page/fragment/labelHtml/numberHtml/label/number и проверенных optional slide/title. Верхний return `validateImportedCatalog():101–109` заново создаёт catalog, generator, optional publication и каждую target projection. Нет `...raw`, присваивания raw metadata в result или расширяющего DTO cast. Удалённый `keys()` возвращал void и не был type predicate, поэтому его удаление само по себе не меняет inferred shape. `domain/model.ts` выводит `Catalog` из ReturnType validator и `Target` из checked publication fields + existing importedTarget decoration; нового ручного интерфейса или any не появилось.

Имена дополнительных полей не обходят существующие consumed проверки: schema discriminator и object checks, `generator.quarto` string, optional publication с непустым title, required target strings, namespace/key agreement, relative page и fragment/slide/title checks сохранены в исходнике. Дополнительные keys внутри самой `targets` map не объявлены произвольными metadata: каждая её entry всё ещё проходит `target(value,key,source)` и agreement namespace:id. Допуск относится к неизвестным полям каталога, generator/publication и **внутри** цели.

## Authority, URL и HTML

Target `baseUrl`, `sourceTitle`, `defaultStyle` отдельно отвергаются при own property (`catalog-validation.ts:19–23`) независимо от их значения. Эти consumer decoration поля задаёт прежняя `domain/import-target.ts:8–13`: namespace/baseUrl/title/style берутся из разобранного import config и checked publication title. Projection удаляет неизвестные поля до spread `...item` в importedTarget. Схема авторского import config не расширена; ошибочные/неизвестные config keys и HTTP(S) base URL policy остаются у `import-config.ts`.

`imports.ts:9–27,51–56` передаёт только parsed→validated каталог в importedTarget. `entrypoints/body-links.ts:12–29` также сначала применяет validator, строит адрес через existing `href` и возвращает текстовую label; неизвестные metadata не попадают в URL. `domain/urls.ts:8–15` по-прежнему кодирует path/fragment/slide, а допустимость page ограничена validator.

Существующий HTML consumer `linker.ts:43–44,53,60,65–68` использует escaped text внешних labels/numbers/titles и сохраняет только локальную Quarto/явно авторскую разметку по прежним правилам. Новые metadata не читаются этим consumer, не попадают в executable HTML, attributes или imports. Own export в `infrastructure/publish.ts` не изменён: он пишет прежнюю catalog projection. Это проверка конкретной read/validate/project/consume цепи, не утверждение, что всякая переданная JavaScript object/proxy во всём приложении стала безопасной по одному типу.

`catalogKeys` и `targetKeys` не переэкспортировались `public.ts`; их единственный production consumer в QRC был удалённый blanket helper. Поиск внутри QRC, а также адресно в actual Core/PL/Site/Download/Performance Source и tools не нашёл других consumers этих имён. Это ограниченное подтверждение активного стека, не обещание отсутствия любых внешних private deep imports. В отличие от прежнего `metadata.ts` helper здесь известного offline Performance consumer не найдено.

## Свидетельства проверки и ограничения

Изменение existing `tests/imports.ts:35–46` подаёт один объект с metadata на четырёх уровнях и делает `deepStrictEqual` фактической validated projection прежнему typed fixture. Проверка наблюдает возвращённые данные; она не требует canonical serialized bytes и не придумывает новый fixture API. Один и тот же JSON затем сохраняется как source локального import case. Прежние два negative cases на дополнительные version keys заменены meaningful numeric generator.quarto и null target; прежние unsafe page, malformed consumed types, namespace/key и три forbidden owner-binding cases остались в файле.

По сохранённому owner report `reports/qrc-import-metadata-20261104.md`, новый four-level projection assertion выполнен, затем `Deno.serve` в строке49 получил PermissionDenied при loopback socket bind. Поэтому локальный importTargets case и дальнейшие HTTP/negative assertions этого файла **не выполнялись**; PASS всего `imports.ts` не заявляется. Review видит корректные assertions в Source, но не выдаёт их наличие за успешный запуск.

`tests/import-safety.ts` по отчёту прошёл: текущий validator/linker consumer не исполняет внешние label HTML, сохраняет local/custom markup, ограничивает base URL protocol и сохраняет mock HTTP/network/file causes. Это существующая адресная runtime проверка, не разрешение live socket или весь transport integration. Общий расход трёх команд owner1.762 s включает также остановившиеся invocation; measured speedup UNKNOWN.

Отдельный offline `deno check` остановился до compiler на uncached JSR `@std/path` при `--no-remote`; **TypeScript PASS отсутствует**. Проверка не повторялась, зависимости/cache не готовились, network/refusal не обходились. `git diff --check` PASS принимается только как сохранённое свидетельство writer. Сам review не выполнял ни одного запуска compiler/tests, hash, build/render/inspect или сети.

Root отдельно установил фактический Quarto context: `DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache`, тогда как writer выбрал `/opt/quarto/share/deno_std/cache`. Это объясняет ограничение выбранной composition/cache попытки; не доказывает, что affected graph уже скомпилирован. Будущий один согласованный affected program в actual context возможен отдельно после готовности Core Source, без повторной матрицы; его результат сейчас UNKNOWN.

## Контракт и остаток

Новый русский текст `docs/contract.md` точно разделяет permissive metadata при импорте и прежние consumed fields/owner-binding запреты. Возвращаемая/экспортируемая модель не превращается в passthrough. Неизвестные metadata игнорируются вместо восстановления blanket schema; новые runtime guards, hashes, qualification или policy equality не введены.

Открытый Performance owner-normalizer companion от предыдущего HTML diff остаётся отдельно: старый pinned798 работает, применение нового QRC commit в offline manifest без companion не принято. Второй diff не добавляет ещё одной такой зависимости. Lua sorting и installation/pins/runtime курсов здесь не затронуты.

Source/tests/state/refs, installed copies и оригинальные репозитории review не менял. APPROVE относится к текущей четырёхфайловой семантической дельте и не закрывает перечисленные integration/typecheck ограничения.

Перед завершением перечитан полный собственный primary интервал: turn_context строки8,105,169,207,346,569,649, все верхние model/effort и вложенные settings gpt-6.1-sol/high согласованы. Исходный turn роли не сдвинут; итоговый допуск PASS.
