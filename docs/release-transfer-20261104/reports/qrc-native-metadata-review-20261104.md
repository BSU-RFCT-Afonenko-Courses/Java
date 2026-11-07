# Независимый Source review QRC metadata

## Собственный допуск

Назначение `gpt-6.1-sol/high`, Standard. Собственный chat `01a1a34e-02f6-7021-ba73-02522b0a6a14`, путь `/root/review_download_types`; primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T00-26-01-01a1a34e-02f6-7021-ba73-02522b0a6a14.jsonl`, session_meta строка1. Исходный интервал роли сохранён от turn `01a1a34e-032a-7541-b085-5af16bf83f02`, root `01a1a338-3b4c-79a1-98e8-f635eca07373`. Задача QRC получена в turn `01a1a4ea-9e9e-7fa1-89a7-9e4c1b8b0bcb`, root `01a1a4dd-242d-7930-89b5-5da44195e538`. Все turn_context строки8,105,169,207,346,569 имеют согласованные верхние и вложенные пары gpt-6.1-sol/high: вход PASS. Standard назначен, отдельное поле service_tier отсутствует; настройки не менялись.

Область: pending пятифайловый diff QRC `/home/tolya/JavaCodex/java-release-20261110/qrc` от exact `798f8c0d9f87fd5dcfe9ee85b79df57e647d2f3c`. Прочитаны AGENTS, spec/index и действующий contract; общее русское языковое правило прочитано в предыдущей части той же роли. Только чтение diff/сохранённых свидетельств; tests/compiler/build/render/inspect/hash/network/commit/push не выполняются. Root владеет state, writer владеет Source.

## Вердикт

**APPROVE в границе этого Source diff. Исправляемых замечаний не найдено.** Убрана собственная гарантия порядка сериализованных metadata-атрибутов; разрешение ссылок, значения metadata, строгая full-финализация и local-отсрочка сохраняются. Измеренное ускорение, полная TypeScript-проверка, установленный состав и выпуск этим review не подтверждаются.

## Проверенные границы

`infrastructure/pages.ts:21–24` теперь сразу передаёт исходный HTML существующему parser. `html.ts:23–27` получает значение атрибута по имени, а `pages.ts:33–67` строит цели, title и подписи из этих значений и Quarto links. Порядок атрибутов для этого не нужен. Source offsets, применяемые `inner` и `replace`, теперь относятся к тому же исходному `page.html`: промежуточного regex-переписывания нет. Сам parser/HTML adapter не изменён.

`infrastructure/linker.ts:107` возвращает результат прежнего `replace(page.html, edits)` без второго полного HTML regex pass. Вся логика edits остаётся прежней: проверка ref/style, получение текущей цели, построение href, сохранение custom markup, замена подписи и обновление parsed tree для search. `linker.ts:31,43–44,53,65,68` сохраняет прежний `escape` и разграничение импортированных текстовых подписей/локального Quarto HTML. Удалённый helper сортировал выбранные attributes; он не являлся escaping или validation boundary. Нового собственного HTML нормализатора/валидатора, hashes или byte-equality требования не добавлено.

Full удаляет контейнеры probes прежними edits (`linker.ts:83–88`), одновременно удаляя их из parsed tree. Local сохраняет контейнеры и при неизвестной цели убирает href, оставляет ref/style/custom и добавляет deferred (`:28–35`). Unknown target в full/default продолжает проходить через прежний resolver и отказ. Таким образом, privacy/финализация не опиралась на сортировку готовых атрибутов.

Прежние два production calls `canonicalizeMetadata` не передавали `sourceRoot`. Необязательная ветка `$SOURCE_ROOT` masking существовала только при явном аргументе helper и проверялась его прежним тестом; обычная публикация этой веткой не пользовалась. Helper не экспортирован `public.ts`, других оставшихся ссылок после удаления поиск внутри QRC не выявил. Следовательно, отсутствие masking после удаления не открывает новую production утечку: local сохранял фактический `data-qrc-source` и раньше, а full удаляет весь контейнер независимо от пути. Это ограниченный вывод о данных двух calls, не глобальная приёмка всех private output правил. Внешний offline consumer Performance рассмотрен ниже отдельно.

## Проверки и предел типизации

Изменённый существующий `tests/metadata-order.ts:6–54` проверяет два порядка атрибутов через реальные `readPage/linkPages`: target DTO, title с entity, HTML и текст подписи, href и ref/style/custom/deferred. Содержательные local/full проверки подтверждают сохранение source/namespace/hidden/aria-hidden в local и удаление probes в full. Отдельный local pending случай сохраняет meaningful текст и `<em>` при отсутствии href. Исчезнувшие helper idempotence/source masking/равенство двух строк HTML больше не объявляются продуктовой целью. Сравнение target DTO здесь проверяет semantic data, не канонические bytes.

Неизменённый `tests/local-linking.ts` дополняет это existing проверками известных импортированных целей, текущих фактов после повторного связывания, custom markup, strict full/default и malformed ref/style. Это адресный runtime consumer слой, не полный Quarto render, Lua producer или installed publication suite.

По сохранённому отчёту writer `reports/qrc-native-metadata-20261104.md` Quarto1.11.5 version и оба адресных runtime файла прошли, общий расход1.807 s. Review эти проверки не повторял. Отдельный `deno check --cached-only ...` завершился exit1 за0.005 s до typecheck из-за неподдерживаемого аргумента. Это **не TypeScript PASS**; повтор не требовался и здесь не выполнялся. `git diff --check` PASS относится к свидетельству writer, не к новому запуску review.

Diff не добавляет `any`, casts, DTO facade или runtime guards ради typing; signatures `readPage/linkPages` не меняются. Существующие `@ts-expect-error` wrappers наблюдения и cast HTML adapter были уже в исходном Source и не становятся новым доказательством полноты типов. Одобрение ограниченной семантической дельты не подменяет успешный полный own typecheck.

## Контракт, стоимость и отдельная Lua-граница

Изменение `docs/contract.md` заменяет обещание детерминированных HTML bytes фактическим требованием к именам/значениям и local/full поведению. Документ явно оставляет Lua map→sorted pairs как текущую отдельную реализацию и не требует такого порядка у входного/итогового HTML. Авторская проза русская, пути/идентификаторы сохранены.

Source доказывает устранение двух regex-проходов по каждой read/link странице, выделения массивов атрибутов и их sorting в helper. Вклад в wall общего курса **UNKNOWN**; новый benchmark не выполнялся. Sorting native Pandoc Attr в Lua этим diff не изменён и этим review не принят как уже устранённый. Его возможная следующая дельта должна рассматриваться отдельно по реальным Lua consumers, без повторного навязывания byte equality.

## Стык со старой offline policy Performance

Адресно прочитан известный companion consumer actual Performance924: `performance/tools/owner-normalizer.ts:10–25` исполняет `git show <pinned QRC sha>:_extensions/reference-catalog/infrastructure/metadata.ts` и требует функцию `canonicalizeMetadata`. Его вызывают `tools/validate-java.ts:12–15` и `tools/recheck-repeat.ts:7–10`; `src/analysis/outputs.ts:107–115` применяет adapter для прежней `java-pilot-core-qrc-v1` byte comparison policy, включая переданный source root. Это реальное внешнее употребление helper, пропущенное утверждением writer о его потребителях только внутри QRC.

Старые frozen manifests на798 продолжают читать тот же исторический Git blob, даже если текущий checkout уже изменён. Но manifest с новым QRC commit, где helper удалён, даст явный отказ `Закреплённая версия QRC не предоставляет нормализацию metadata` до сравнения. Это **открытая межрепозиторная совместимость offline tooling**, не новая утечка source path и не нарушение linking/runtime QRC. Она не требует сохранять прежнюю навязанную byte-equality гарантию: при обновлении QRC pin root/Performance owner должен пересмотреть или убрать эту ветку consumer policy, сохранив реальные targets/labels/links сравнения и историческую read-only область. Не переносить normalizer в новый общий framework и не возвращать runtime sorting ради прежней проверки.

Поэтому APPROVE относится к данной production семантике QRC. Совместимость будущего Performance manifest на новом QRC commit **не заявлена**; до такого применения нужен отдельный небольшой companion diff. В этом review он не выполнялся и повторное сравнение сохранённых outputs не запускалось.

Source/tests/state, provider pins, installed копии, оригинальные репозитории и Git refs review не менял. Записан только этот report; готовый diff остаётся у writer/root для commit и дальнейшей интеграции.

Перед приёмкой перечитан полный собственный primary интервал: turn_context строки8,105,169,207,346,569, все верхние model/effort и вложенные collaboration settings gpt-6.1-sol/high согласованы. Исходная точка роли не сдвинута; итоговый допуск PASS.
