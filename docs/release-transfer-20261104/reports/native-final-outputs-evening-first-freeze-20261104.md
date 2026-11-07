# A5: конечные child outputs/resources Quarto

**SOURCE FROZEN, ожидает независимого review.** Исправлен producer и обязательные readers выбранного сегмента. Компиляция и runtime после этой дельты ещё не запускались; успешная композиция Core+QRC+PL пока не заявляется.

Причина установлена по сохранённому фактическому публичному запуску: Core post финализировал два документа, QRC post штатно переписал HTML и создал настоящий catalog, затем seal сравнил уже промежуточные bytes с SHA/signature и отказал `OPERATION_CHANGED` на прежней operation.ts:1989. PL требует настоящий QRC catalog даже без ссылок, поэтому удаление QRC post не является исправлением. Исходный trace: [native-titles-public-acceptance-20261104.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/native-titles-public-acceptance-20261104.json), [title-acceptance-20261104/book-cli.stdout.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/title-acceptance-20261104/book-cli.stdout.log), [book-cli.stderr.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/title-acceptance-20261104/book-cli.stderr.log); сохранённый owner `/tmp/native-titles-a4-xlqw_tj6` не менялся этим исполнителем.

Core теперь сохраняет выбранные HTML output paths без SHA/signature. После завершения дочернего Quarto со всеми post hooks seal проверяет regular paths и разрешает конечные resource paths. При открытии операции ordinary consumers также получают пути после hooks. Для наблюдавшегося resource source выбирается фактический regular output; если переноса нет, используется исходный regular source. Не наблюдавшийся retained output по-прежнему не даёт доступа. HTML остаётся конечным выходом Quarto; отдельного HTML-byte DTO потребителю Core не выдавал и не вводит.

Первое чтение ресурса открывает regular/realpath/symlink-проверенный путь, получает dev/inode **от открытого handle**, проверяет его против закрытых A1 physical aliases и копирует фактические bytes. Старый inode не является условием неизменности. Отдельные source/output identities сохраняют privacy при копировании ресурса с другим inode. Callback cache возвращает отдельные копии своих detached bytes; cache-hit проверяет переданную authority через уже сохранённый identity без stat/read/hash. Публичный generic resourceInventory и document/condition getters запрещены для assignment operation, но внутренний cache дополнительно сохраняет authority на каждом запросе. Общий operation.guard больше не обходит outputs/resources; pointer/lifetime/raw-source договоры остаются.

Удалены: native SHA1 producer; сравнение позднего resource SHA1; output/resource SHA256/signature producer; byte-currentness seal; output/resource signature guard; resource hash при чтении; SHA256 в ordinary Native resource DTO и assignment Body resource; обязательная проверка `r.sha256` в PL Body reader. Hash/flag/proof-замена, перестановка hooks, поддельный catalog или отключение QRC не вводились. Поля старых счётчиков outputHashReads/resourceHashReads/nativeResourceHashReads пока остаются в DTO метрик и больше не увеличиваются этим механизмом.

Сохранены operation UUID/generation, selected source/role/profile/path и output collision, exact Native capture coverage, pointer/private reservation и cancellation/lifetime, regular/realpath/symlink, lexical privacy и public/closed hardlink policy. Переданный resource callback имеет настоящий тип `{bytes, identity}`; новых `any`, fake casts и нового валидатора byte proofs нет. Прежний `BodyPackageSchema.resources: z.array(z.any())` не расширялся и не объявляется исправленным этой задачей.

Изменённые Core production файлы:

- `_extensions/course-core/runtime/operation.ts`;
- `_extensions/course-core/runtime/contracts.ts`;
- `_extensions/course-core/runtime/assignment-roles.ts`;
- `_extensions/course-core/native-resources.lua`;
- `_extensions/course-core/body-export/assignment.ts` — только resource DTO и resource SHA property. Принятые A4 title import/helper/use сохранены буквально.

Согласованный внешний reader: `prairielearn/_extensions/course-prairielearn/application/export.ts`, только удаление Body-resource digest/`r.sha256` блока. Его owner передал этот блок исполнителю A5. Download `infrastructure/runtime.ts` использует resourceInventory `target/bytes` и не требует изменения. У QRC и Site дополнительных mandatory SHA readers этого DTO не найдено; их Source не менялся.

Документ `core-native-bridge/docs/native-operation-ts.md` и затронутые fixtures согласованы с новым контрактом:

- `tests/assignment-role-alias.ts` — настоящие типы без прежнего `as any`, обе source/output identity последовательности и private identity первого чтения;
- `tests/assignment-body.ts` — удалено выдуманное resource SHA поле;
- `tests/native-operation-a3.ts` — наблюдаемые resource bytes вместо удалённого SHA DTO и успешная выдача postprocessed resource;
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
9. `core-native-bridge/tests/release-generated-resource-currentness.ts`.

Одна существующая полезная pure fixture: `core-native-bridge/tests/assignment-role-alias.ts`. Её запуск не выполнялся. Следующее публичное runtime действие — повтор сохранённого tinybook через существующий PL `entrypoints/export-course.ts` после обновления его installed Core/PL Source, с настоящим QRC post/catalog и в оставшемся исходном бюджете. Перечисленные Native suites не предлагаются к массовому запуску ради статуса.
