# Независимый технический review Performance

**APPROVE — готово к интеграции в рассмотренном объёме. Открытых замечаний нет.**

Точный диапазон: `7a1645936924ba0dd710c9121a8871a13d67a84b..86f762e317b237320657a3319c70701d03c72a2e`. Функциональный код и тесты frozen в `6c01f315819201df06970f168920d98e6d72708f`; `86f762e` меняет только 3 строки README и просмотрен отдельно без повторения проверок неизменного кода. Checkout `/tmp/java-release-work-20261105/performance`, ветка `codex/performance-files-20261105`; итоговый status пуст, `git diff --check` PASS. Действующий PR6 сохранён; новый stacked draft — PR8.

Рецензент `/root/core_review`, собственный chat `01a1a8a1-ab5c-7620-8830-6eb20fed81e5`. Исходный ход **не сдвинут**: `01a1a8a1-ab93-7852-9946-77d0cb6ffb64`. Перед ролью и при завершении проверен весь прежний интервал: 3 `turn_context`, верхнеуровневые и вложенные `gpt-6.1-sol/high` совпадают, Standard. Первичные записи и итог: [performance-review-admission.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261105/reports/performance-review-admission.json); та же исходная привязка сохранена в прежнем [core-review-admission.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261105/reports/core-review-admission.json). Рецензент source/fixtures/index/HEAD не менял, продукт не писал, субагентов не запускал.

Требования: прямое поручение ограниченного пакета A, `performance-plan.md`, `core-consumer-audit.md`, договор удаления собственных digest/snapshot/PreparationReceipt/proof/freshness producers → DTO → readers/tools/tests без compatibility и новых version fences. Шаблон: `superpowers/requesting-code-review/code-reviewer.md`.

## Сильные стороны

- Удаление выполнено по всей установленной цепочке: runtime probe, init/bootstrap, TS/filter preparation, package installation, registry/package coverage, experiment, historical metadata, output comparison и tools. Старый `snapshot.ts` больше не является текущим API; функциональные helpers перенесены в `files.ts`.
- Сохраняются явные source/replacement inputs, native Git SHA/HEAD и dirty preflight, runtime paths/versions, функциональные копии и защита путей. Сохранение mtime для native sitemap lastmod не используется как freshness fence.
- Сравнение outputs проверяет actual bytes, наличие/отсутствие файлов и узкие прежние исключения; собственные SHA/сохранённые byte inventories не заменены новым доказательством. Копии output roots остаются данными для реального сравнения.
- `PreparedScript` хранит только kind/path/sourceMapPath, producer и consumers используют `preparedScripts`. Действительные фазы, процессные записи, metrics ABI, partial/invalid diagnostics и attribution событий сохранены.
- Инвентаризация/лексическое доказательство инструментирования расширений удалены; явный requested list и actual event observations не объявляются наличием физической копии или полным покрытием.
- Штатные Git/npm/Arch механизмы не удалены. Единственный текущий собственный вызов digest в просмотренных src/tools относится к штатному `PKGBUILD.sha256sums` исходного архива, что разрешено договором.

## Замечания

### Критические

Открытых нет.

### Важные

Открытых нет. До freeze обнаружены и устранены:

1. Historical v2 `completion`/`outcome` передавали архивный `preparation` в новый обязательный live reader и теряли возможность чтения прежнего формата. Обе ветки теперь исключают старое proof поле, дают derived `preparedScripts: []`, сохраняют фактический native outcome и не меняют raw архив. Различающий regression проверяет обе схемы, прежний code 7 и отсутствие legacy поля в новом record.
2. Новый live SourceManifest ещё пропускал и возвращал старые runtime.hash/packageHash; parsed old package requested rows с installed-proof попадали в новый observations DTO. Live digest fields теперь отвергаются, runtime requested list проверяется из unknown, старые object rows не превращаются в новые наблюдения/installed claims. Historical raw импорт отделён от live API без compatible alias.

### Незначительные

Открытых нет. Закрыты:

- После удаления before/after inventory retained tool claims `sourceUnchanged`/`executionUnchanged` больше не могли означать прежнее свойство. Они удалены целиком вместе с asserts/PASS условиями; native Git остаётся factual input identity, функциональные gates — критериями результата. Новый Git freshness graph не введён.
- Current README ошибочно продолжал обещать сбор статических свидетельств разметки. Doc-only `86f762e` заменяет это на requested extensions/actual observations и явно исключает выводы о physical copy или полноте instrumentation. Delta прочитан, source/tests не менялись.

## Проверки и стоимость

Прочитан весь изменённый функциональный src/tools граф, адресные тесты и новые документы. Карта стоимости **до изменения** из `performance-plan.md` сопоставлена с call sites: ordinary summary/cpu шли через runtime/init hashing; experiment добавлял повторные runtime checks, package tree hashes, registry/module hashes и package token/evidence scans; historical и output readers создавали дополнительные digest. Исправление замкнуло эти пути, а не один helper. Обязательные функциональные чтения/копии сохранены. Ускорение курса не измерялось и не заявляется.

Независимые запуски рецензента на exact функциональном `6c01f315`:

- `tests/own-digests.test.ts` + `tests/proof-boundaries.test.ts`: **5 PASS, 0 failed**. Реальная summary preparation без render, init preparation, selected package install и output comparison работают при запрещённом `crypto.subtle.digest`; проверены live deleted-field rejection, old package rows и оба historical v2 layouts. Процесс **2.55 с**, собственно tests **231 мс**. Логи: `performance-review-tests.log`, `performance-review-tests.seconds`.
- Минимальный реальный affected compiler: config/types, report/types, historical, diagnostic, package-coverage, check-java-general-use и оба различающих tests — **exit 0**, 8 Check entries, **2.02 с**. Логи: `performance-review-types.log`, `performance-review-types.seconds`. Использованы действительные локально установленные npm зависимости; type stubs/config shims не добавлялись. Первая попытка остановилась на неподдерживаемом CLI flag `deno check --cached-only`; после удаления только этого флага та же реальная graph проверена успешно. Это не source failure.
- Read-only probe до исправлений отдельно подтвердил фактическое прохождение старых proof rows в новые DTO; **0.107 с**. Полный render, inspect, build или прежние matrices для review не запускались.
- Итоговый doc-only `6c01f315..86f762e` содержит только README; проверки кода повторно не запускались. Итоговый checkout clean, diff check PASS.

Исполнительские результаты рассмотрены отдельно:

- `performance-final-focused.log`: 27 PASS за 9 с.
- `performance-boundaries-final.log`: 9 PASS, включая actual TS instrumentation и новые boundaries, 275 мс.
- `performance-types-final.log`: whole actual src/bridge/tools/tests graph PASS; `performance-types-closure.log`: последний tools/proof closure PASS. Реальные TypeScript 5.9.3, luaparse 0.3.1, @types/node/undici установлены физической локальной копией существующего cache; CommonJS resolution не подменён shim/stub.
- [performance-tools.json](performance-tools.json): digest-disabled output microcomparison PASS; formatter/diffcheck и последняя коррекция tool claims PASS. Их стоимость и scope не выданы за измерение курса.

## Отказ от суждения (Declined to judge)

- Полный курс, реальные render matrices, inspect/build, Docker/Arch installation и опубликованные пакеты в этой новой source-проверке не принимались. Native Arch checksum сохранён чтением реализации, но новая package build не запускалась.
- Непринятые прежние full-coverage/speedup/TERM claims не возобновляются. Summary setup без render подтверждает только изменённый путь подготовки и отсутствие digest, а не ускорение или успешный полный render.
- Отдельный неизменный storage fixture в более широком исполнительском запуске упёрся в root filesystem read-only при внешнем staging path. Это зафиксировано как ограничение среды, не исправленный defect и не полный suite PASS; рецензент не обходил ограничение и не переписывал fixture ради зелёного результата.
- Исторические raw архивы и старые выпускаемые evidence сохраняют прежний вид. Их чтение не превращает старые hash/preparation fields в новый live договор или новый SHA producer.
- Новая гарантия неизменности source между вызовами не проверяется: её удаление прямо принято. Native Git identity и обязательные функциональные path/privacy/copy ограничения оценены отдельно.

## Оценка

**Готово к интеграции: да.** На `86f762e` открытых findings нет; функциональный код `6c01f315` прошёл достаточные независимые различающие проверки и actual compiler. Этот verdict относится к source/DTO/consumer closure ограниченного пакета A и указанным checks, а не к full-course, packaging, Docker или production приёмке.
