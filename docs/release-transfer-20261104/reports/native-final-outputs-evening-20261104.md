# A5: конечные child outputs/resources Quarto

**SOURCE FROZEN повторно после REQUEST CHANGES, ожидает независимого review.** Исправлен producer и обязательные readers выбранного сегмента. Компиляция и runtime после этой дельты ещё не запускались; успешная композиция Core+QRC+PL пока не заявляется.

Причина установлена по сохранённому фактическому публичному запуску: Core post финализировал два документа, QRC post штатно переписал HTML и создал настоящий catalog, затем seal сравнил уже промежуточные bytes с SHA/signature и отказал `OPERATION_CHANGED` на прежней operation.ts:1989. PL требует настоящий QRC catalog даже без ссылок, поэтому удаление QRC post не является исправлением. Исходный trace: [native-titles-public-acceptance-20261104.json](native-titles-public-acceptance-20261104.json), [title-acceptance-20261104/book-cli.stdout.log](title-acceptance-20261104/book-cli.stdout.log), `book-cli.stderr.log`; сохранённый owner `/tmp/native-titles-a4-xlqw_tj6` не менялся этим исполнителем.

Core теперь сохраняет выбранные HTML output paths без SHA/signature. После завершения дочернего Quarto со всеми post hooks seal проверяет regular paths и разрешает конечные resource paths. При открытии операции ordinary consumers также получают пути после hooks. Для наблюдавшегося resource source выбирается фактический regular output; если переноса нет, используется исходный regular source. Не наблюдавшийся retained output по-прежнему не даёт доступа. HTML остаётся конечным выходом Quarto; отдельного HTML-byte DTO потребителю Core не выдавал и не вводит.

Первое чтение ресурса открывает regular/realpath/symlink-проверенный путь, получает dev/inode **от открытого handle**, однократно после open проверяет текущий pathname через regular/realpath/no-symlink и соответствие dev/inode открытому handle, затем проверяет identity против закрытых A1 physical aliases и копирует фактические bytes. Старый inode не является условием неизменности. Установленные `sourceIdentities` сохраняются при последующем удалении/замене source, объединяются по child captures и вместе с конечным output identity сохраняют privacy при копировании ресурса с другим inode. Повторное разрешение меняет конечный path/identity, сохраняя прежние source associations. Callback cache возвращает отдельные копии своих detached bytes; cache-hit проверяет переданную authority через уже сохранённый identity без stat/read/hash. Публичный generic resourceInventory и document/condition getters запрещены для assignment operation, но внутренний cache дополнительно сохраняет authority на каждом запросе. Один и тот же post-open path/handle boundary действует и для первого logical-source запроса, попавшего в physicalSnapshots cache; source snapshot-hit не делает FS equality, но сохраняет authority. Общий operation.guard больше не обходит outputs/resources; pointer/lifetime/raw-source договоры остаются. Внешний вход `withCapturedOperation` один раз проверяет все конечные HTML regular paths после hooks, включая ordinary документы без ресурсов.

Удалены: native SHA1 producer; сравнение позднего resource SHA1; output/resource SHA256/signature producer; byte-currentness seal; output/resource signature guard; resource hash при чтении; SHA256 в ordinary Native resource DTO и assignment Body resource; обязательная проверка `r.sha256` в PL Body reader. Hash/flag/proof-замена, перестановка hooks, поддельный catalog или отключение QRC не вводились. Поля старых счётчиков outputHashReads/resourceHashReads/nativeResourceHashReads пока остаются в DTO метрик и больше не увеличиваются этим механизмом.

Сохранены operation UUID/generation, selected source/role/profile/path и output collision, exact Native capture coverage, pointer/private reservation и cancellation/lifetime, regular/realpath/symlink, lexical privacy и public/closed hardlink policy. Переданный resource callback имеет настоящий тип `{bytes, identity}`; новых `any`, fake casts и нового валидатора byte proofs нет. Прежний `BodyPackageSchema.resources: z.array(z.any())` не расширялся и не объявляется исправленным этой задачей.

Три замечания `native-final-outputs-review-evening-20261104.md` технически подтверждены и адресованы в Source. Повторный freeze не является положительной рецензией или runtime-приёмкой. Уже рассмотренная первая дельта и отчёт сохранены отдельно как `native-final-outputs-a5-core-first-freeze-20261104.patch` и `native-final-outputs-evening-first-freeze-20261104.md`.

Узкий production I/O helper `runtime/native-output-resources.ts` содержит фактическое разрешение конечного resource path и привязку открытого handle; публичный API Core не расширялся. Post-open regular + current dev/inode равенство защищает от постоянной подмены пути между precheck/open. Deno portable open не предоставляет no-follow/openat; adversarial double-swap или абсолютный race proof не заявляются. Старые inode/size/mtime/ctime/SHA при этом не сравниваются, второго post-read fence нет. При недоступных dev/inode конкретное разрешение/чтение ресурса отказывается, startup/platform preflight не вводился. Переносимость этой physical-alias границы на платформы без этих полей не квалифицирована; это не заявление о поддержке/неподдержке Windows/macOS целиком. Прочие платформенные вопросы не исследовались.

Изменённые Core production файлы:

- `_extensions/course-core/runtime/operation.ts`;
- `_extensions/course-core/runtime/native-output-resources.ts` — новый единственный helper конечного пути/actual handle для этого сегмента;
- `_extensions/course-core/runtime/contracts.ts`;
- `_extensions/course-core/runtime/assignment-roles.ts`;
- `_extensions/course-core/native-resources.lua`;
- `_extensions/course-core/body-export/assignment.ts` — только resource DTO и resource SHA property. Принятые A4 title import/helper/use сохранены буквально.

Согласованный внешний reader: `prairielearn/_extensions/course-prairielearn/application/export.ts`, только удаление Body-resource digest/`r.sha256` блока. Его owner передал этот блок исполнителю A5. Download `infrastructure/runtime.ts` использует resourceInventory `target/bytes` и не требует изменения. У QRC и Site дополнительных mandatory SHA readers этого DTO не найдено; их Source не менялся.

Документ `core-native-bridge/docs/native-operation-ts.md` и затронутые fixtures согласованы с новым контрактом:

- `tests/native-output-resource-paths.ts` — один адресный filesystem fixture: законная regular rewrite, сохранение closed association после удаления/замены source, отказ чужому handle и постоянной symlink подмене после open. Проверяются результаты privacy/path, не устройство массивов/cache/counters;
- `tests/assignment-role-alias.ts` — настоящие типы без прежнего `as any`, обе source/output identity последовательности и private identity первого чтения;
- `tests/assignment-body.ts` — удалено выдуманное resource SHA поле;
- `tests/native-operation-a3.ts` — наблюдаемые resource bytes вместо удалённого SHA DTO, успешная выдача postprocessed resource и ordinary external-entry отказ symlink HTML; дополнительных Native renders для этой адресной проверки не добавлено;
- `tests/native-capture-currentness-a4c.ts` — конечный shared resource принимается после postprocessing, selected raw-source final fence сохранён;
- `tests/native-capture-fences-a4c.ts` — удалён отрицательный случай старой output immutability;
- `tests/static-source-policy.ts` — фактические resource bytes и обычная output rewrite допустимы, lifetime/A1 проверки сохранены;
- `tests/release-generated-resource-currentness.ts` — удалены два отрицательных resource byte-drift случая; source, pointer, symlink и privacy проверки сохранены.

**Остаток вне границы A5:** initial source snapshot по-прежнему читает и SHA-хеширует исходное owner дерево, включая присутствовавшие исходные resource файлы; selected raw QMD signatures/sourceRows SHA и runtime executable identity остаются. Project ArtifactInventory/materialize/write, assignment provenance, PL JavaCompileInput/native compiler/check/Workspace/source-selection SHA и другие source/transport contracts не удалены. Condition resource, затем включённый в project ArtifactInventory, всё ещё получает проектный SHA в этом отдельном API. A5 не объявляет удаление всех Core/PL hashes, всех inventories или всего immutable delivery.

База Core `af5ceb28b860c8637e59747faa4ca3e3661b5724`. До Source сохранены отдельные копии в `/tmp/native-final-outputs-a5-baseline-20261104`, baseline manifest и собственный model admission. Полный интервал от своего исходного хода `01a1a6e8-2351-7610-b283-2f32382668a9` проверен по всем первичным turn_context: `gpt-6.1-sol/high`, PASS. Собственный агент `01a1a6e8-231c-7f43-8144-eda91288c9fa`, path `/root/a5_native_final_outputs`; metadata source указан в JSON admission. Родительский ID не использован как собственный.

Сохранены отдельные reviewable patches:

- `native-final-outputs-a5-core-20261104.patch` — A5 от собственного baseline, включая только два resource изменения общего assignment.ts;
- `native-final-outputs-a5-pl-20261104.patch` — один согласованный reader;
- `native-final-outputs-a4title-shared-baseline-20261104.patch` — прежние A4 title изменения общего assignment.ts относительно HEAD; в A5 patch этих строк нет.

Никаких commit/stage/push/network/build/Native/compiler/hash запусков исполнитель A5 не выполнял. Source читался через rg/sed/git diff; один `git diff --check` подтвердил только отсутствие whitespace diagnostics до окончательного freeze и не является compiler/runtime проверкой. Прежние Native затраты не сброшены: из A4 40s потрачено 5.877903707s, осталось **34.122096293s включая grace**. Следующий запуск возможен только после review и решения координатора.

Для одного затронутого compiler graph реальные roots:

1. `core-native-bridge/_extensions/course-core/public.ts`;
2. `prairielearn/_extensions/course-prairielearn/application/export.ts`;
3. `core-native-bridge/tests/assignment-role-alias.ts`;
4. `core-native-bridge/tests/assignment-body.ts`;
5. `core-native-bridge/tests/native-operation-a3.ts`;
6. `core-native-bridge/tests/native-capture-currentness-a4c.ts`;
7. `core-native-bridge/tests/native-capture-fences-a4c.ts`;
8. `core-native-bridge/tests/static-source-policy.ts`;
9. `core-native-bridge/tests/release-generated-resource-currentness.ts`;
10. `core-native-bridge/tests/native-output-resource-paths.ts`. Новый production helper входит через public.ts → operation.ts, отдельный искусственный compiler root не нужен.

Одна существующая полезная pure fixture: `core-native-bridge/tests/assignment-role-alias.ts`. Её запуск не выполнялся. Новый filesystem fixture также не запускался; будущий единый A5 graph+pure cap **12s** назначает координатор отдельно от сохранённого Native бюджета. Следующее публичное runtime действие — повтор сохранённого tinybook через существующий PL `entrypoints/export-course.ts` после обновления его installed Core/PL Source, с настоящим QRC post/catalog и в оставшемся исходном бюджете. Перечисленные Native suites не предлагаются к массовому запуску ради статуса.
