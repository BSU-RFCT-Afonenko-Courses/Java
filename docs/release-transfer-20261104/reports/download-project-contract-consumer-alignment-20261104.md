# Download consumer: удалён obsolete aggregate inspect count

**Source-only test alignment, root review PENDING.** По root CI snapshot current893d / run41805189987 прошёл fixed ordinary publication и остановился в `tests/project-contract-consumer.ts:79`: `Down did not use single contract carrier`, actual owner inspect +2 A1 native-input inspections. Новый CI/test/compiler/Native не запускался; существующий failed CI не переименован в PASS.

Полностью прочитаны этот test и `tools/check.sh`: project-contract-consumer — первый existing suite в последовательном loop, без отдельной новой регистрации. Проверена accepted Core A1 цепочка: `docs/native-context-ts.md:61–79` явно удаляет прежний aggregate-carrier eligibility/cache путь; project-context.ts579 делает owner inspect,635–669 для каждого выбранного input выполняет actual disposable effective inspect; operationProjectContextReuse862+ вызывает existing acquireProjectContexts. Одна disposable copy/acquisition не равна одному глобальному inspect. Два проекта a/b legitimately требуют их фактическую Native metadata, их нельзя скрыть ради старого count.

Изменён ровно `tests/project-contract-consumer.ts`:

- Удалены Deno.Command Proxy/rebinding через any, commands buffer, global inspect.length===1/не-QMD assertion и logging поля inspect. Всё это использовалось только для obsolete methodology count; других полезных consumers в полностью прочитанном файле нет.
- Down.finishDownloads вызывается напрямую с прежними native/output/profiles. Unzip использует обычный Deno.Command вместо proxy original alias.
- Genuine render producer, student projection, две реальные project metadata/starter declarations, проверка успешного producer и точных PUBLIC a/b Main.txt bytes в двух ZIP, temporary root retention/cleanup и scoped PASS output сохранены. Новые numeric counters/замены count3 не добавлены; нет production/Core guards или schema изменений.

[Exact patch](download-project-contract-consumer-alignment-20261104.patch). `git diff --check` PASS — редакционная проверка diff, не runtime evidence. Product/workflow/Core/registration/прочие tests не менялись; commit/push/mainstate отсутствуют. Дельта готова к независимому root Source review и его одному согласованному CI; локальный Native repeat не назначался.

Own роль/model/effort неизменны; [существующий origin reference](core-condition-walk-types-admission-20261104.json). Это устранение теста удалённого контракта, не claim полной Download/A1 acceptance.
