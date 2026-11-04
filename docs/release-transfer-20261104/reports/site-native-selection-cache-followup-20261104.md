# Site: Native selection и cache route после850

**Source-only, без нового candidate.** Проверенный actual HEAD `/home/tolya/JavaCodex/java-release-20261110/site-native-audience` — `85080c730ad54c8fd81fa4a3c7c806a8771c4f68`, ровно назначенный accepted ref. Per-document inspect/render fallback в этом Source **уже устранён**. Повторять его удаление или переименовывать старую SP ветку в нынешний остаток оснований нет. Нового доказанного лишнего обхода в рассмотренной selection/cache границе не найдено.

## Текущий dispatch

1. [workspace/config.ts:195](../site-native-audience/_extensions/course-site/infrastructure/config.ts:195) получает один selected project inspect корня с actual profiles. При full render извлекает Native `files.input` в независимый selected set; при partial root flow полный set не навязывается.
2. [compose.pre:358](../site-native-audience/_extensions/course-site/application/compose.ts:358) вызывает один [inspectProject:308](../site-native-audience/_extensions/course-site/infrastructure/config.ts:308) на child, до cleanup/render. Ответ используется для active config/output/audience и независимого selected set. Site не запрашивает каждый QMD и не вычисляет его effective format.
3. `selectedSources` принимает Native **список строк inputs**, проверяет containment/regular-file/symlink/duplicates и нормализует имена. Это обход уже выбранных Native inputs, не собственный discovery всего owner и не resolver metadata.
4. После проверки всех destinations [compose.ts:445](../site-native-audience/_extensions/course-site/application/compose.ts:445) делает один `render . --to <to> --profile …` на child. Нет ветки `renderTo:null`, `inspectDocuments` или цикла render по QMD. `to:default` оставляет Native выбор первого формата, explicit html/revealjs — явный выбор автора для всей части.
5. Collection сохраняет actual output/profile facts; `catalogCoverage` сравнивает независимый selected set с current Core catalog и actual HTML outputs. Для explicit `to` проверяется фактический формат; default не заменяется собственным вычислителем форматов. Отдельные Core pre и QRC post относятся к другим жизненным границам. Совпадающие argv не позволяют автоматически объединить эти inspect.

Active audience guards, source/output geometry, sibling overlap, protected-source cleanup и current coverage сохраняются. Accepted850 уже удалил шесть alternate-audience inspect и намеренно снял автоматическую гарантию сохранности other-profile output при пересечении paths. Этот tradeoff не расширялся.

## Что означает string-list и что настроено в курсах

**Два разных списка нельзя смешивать.** Native `files.input` — список строк. Публичный Site `subprojects` на850 — **список `{path,to}`**. [validateConfig:73](../site-native-audience/_extensions/course-site/infrastructure/config.ts:73) требует ровно эти два поля и `default|html|revealjs`; plain строки отвергаются. [spec/contract.md](../site-native-audience/spec/contract.md) и existing `site-native-planning.ts` прямо сохраняют этот отказ. Символа/самостоятельного resolver `nativeFormats` в рассмотренном production route нет: effective formats принадлежат Native Quarto.

Текущие авторские root configs обоих курсов уже используют пять `{path,to:default}`:

| Component | Java base format | SP base format | Передаваемое Site намерение |
| --- | --- | --- | --- |
| theory/tasks/handbook |html |html |Native default |
| lectures/practice |revealjs |revealjs |Native default |

Это Source config facts, **не новый effective inspect/render PASS**. У Java/SP actual student/full profiles задают root `_site-student`/`_site-full`, у всех пяти children `_output/student`/`_output/full`. Ни output paths, ни default/group policy не менялись. `activeProfiles/profileArguments` передают actual Native environment; metadata include с именем profile не объявляется активным профилем по имени файла.

Есть конкретный предел простого чтения base format: SP practice student profile добавляет `format.html` options поверх base revealjs. Собственный вывод о том, какой effective target окажется первым, здесь не делается; именно поэтому current Site передаёт default Native и не восстанавливает выбор по YAML эвристике. Другие per-document/custom-format overrides также сохраняют Native authority.

Переход к plain string-list, если он желателен авторскому API, был бы **отдельным изменением контракта**: потребуется решить судьбу действующих explicit html/revealjs и старых configs. Это не найденное trivial устранение обхода при неизменном accepted API; такой candidate не подготовлен.

## Cache/execution: что уже исправлено и что не решалось

[Cache boundary](quarto-cache-boundary-20261104.md) сравнивал Java d16 с **старым SP provider e0efd**: тогда SP per-document route обходил project freeze. На accepted850 оба курса используют whole-project child command. Историческое описание старой SP ветки не является текущим Source blocker. Whole-project `render .` может использовать штатный project freezer; это не гарантия конкретного cache hit.

Site не добавляет `--execute`, `--cache-refresh`, `--no-cache` или `--use-freezer`. [cleanOutput](../site-native-audience/_extensions/course-site/infrastructure/files.ts:126) принадлежит текущему publishable output; `.quarto/_freeze` защищены и не становятся каталогами результата. Owner execution caches вне output не очищаются этим кодом. Выбор custom cache path внутри publishable output остаётся существующим авторским решением, не новым исключением Site.

Разделённые student/full outputs **не namespace** для project freeze, knitr cache или Jupyter cache. Ранее сохранённые stale external data/include и cross-profile computed reuse остаются ограничениями Native настроек. Новая freshness/privacy policy, `cache.path`, `JUPYTERCACHE`, execute flags или blanket admission здесь не вводились. Старые cache/execution reports содержат прежние Core admission/currentness статусы; они читаются как история, а не как актуальный запрет computed после уже принятых Core изменений.

Новый disposable stage без ignored scratch не равен тёплому execution owner; `--frozen --cached-only` Deno не является Quarto freeze. Никаких real cache contents, deletion или warmth probes в этой работе нет.

## Почему нет ещё одного Source candidate

Нужный переход N document selection/render → один Native project route уже присутствует. Проверки selected inputs и позднего catalog охраняют разные границы. Проверка destinations до cleanup и повторные physical/output проверки на поздней границе не объявлены лишними по одинаковым строкам paths. Механического слияния map/set проходов ради числа loops или нового межфазного metadata cache не предлагается.

Useful existing fixtures уже описывают эту семантику: `site-native-planning.ts` проверяет selected set и отсутствие document inspect fanout; `site-effective-formats.ts` — mixed HTML/Reveal, profile/default/explicit format/custom alias; `site-metadata-profiles.ts` — metadata include против active profiles; `site-web-selection.ts` — mixed Native links. Они **не запускались заново**. Их execution требует Native, поэтому общий cap60s сейчас не расходовался и новые experiments этим отчётом не назначаются. При отдельном содержательном diff можно выбрать один соответствующий case, а не повторять всю матрицу.

Известные saved SP LIMIT_STOP и шесть удалённых alternate envelopes9.914s не превращены в прогноз ускорения или новый whole-course PASS. Полная установленная Java/SP composition и cache/privacy acceptance этим Source чтением не квалифицированы.

Применимые AGENTS/spec и каноническое русское языковое правило прочитаны. Mainstate прочитан как состояние продолжения: новое прямое указание пользователя отменяет остановку после15:00; сам mainstate/audit root-only и не редактировались. Performance recovery остаётся frozen/unaccepted, прежний runtime budget не сброшен. Product Source edits, Native/render/inspect/hash/build/kernel/network/cache operations —0. Допуск high — прежний подтверждённый own reference; globals не менялись.
