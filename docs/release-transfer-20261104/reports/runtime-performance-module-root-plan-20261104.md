# Прямой module root Performance: план реализации

Согласованный user spec: убрать ненужное копирование module/dependency tree, использовать штатное разрешение уже установленного Deno/Node package. Один writer; independent Source review до исполнения. Этот release checkout уже изолирован и переиспользуется, исходный clean HEAD9ba1fd87eb8f5da15fdad29c6c74bc0030b96e92; node_modules — штатный результат разрешённой установки, не Source. Core/PL/Platform/Gateway/VM не менять.

## Дизайн и точная closure

prepareRenderDiagnostic/prepareDiagnostic -> prepareLaunchers -> generated shell launchers -> src/launchers/tool.ts -> preparePandocArgs/TSpreload/Luainit. Текущий setup копирует src,bridge,node_modules,deno config/lock,operationregistry в workspace/backend. Directorysymlinks штатного Deno install отвергнуты generic copier(files materializeFileLinks only). Обходить это своим resolver/registry/materialize-all нельзя.

Выбранный минимальный путь: использовать существующий assetPath(.) как module root для appDenoArgs, tool.ts, TS/Lua agents и bridgePath. Deno штатно разрешает imports/node_modules этого установленного дерева; Quarto/native arguments и его функциональное окружение остаются прежними. В workspace нужны только обычные generated executable launchers и отдельная изменяемая копия Pandoc data-dir/init. Установленные Performance/Quarto не переписываются. Альтернатива — расширять copy-all до directorylinks — отклонена прямым user spec; новый resolver не нужен. Сервис/SQL/DB/types/auth/lifecycle/TERM не затрагиваются.

## Конечные шаги

- [ ] Один Source writer high меняет только src/launchers/setup.ts и при необходимости адресные существующие launcher tests, документирует call closure. Старый реальный failure CLI2 с directorylink — воспроизведённый RED до fix. Не выполнять tests/check/render в writer turn; сначала freeze/review по прямому поручению.
- [ ] Независимый high Source reviewer проверяет call closure, generated quoting/пробелы, data-dir isolation, неизменность installed tree и ordinary dependency resolution. Никакого исполнения у reviewer.
- [ ] После freeze/APPROVE один targeted affected graph cap8s, frozen/manual/cached-only, без широких suites. До вызова записать exact command. Firstnewfailure стоп.
- [ ] Только если graphPASS, один synthetic summary с полным external cap<=6.393s (старый1-йfull<=15,2-йfull1.607rounded -> conservative2ndupper1.608, значит доступно6.392s). Никаких повторов/setup/network/VM/direct-off/TERM. FullPython invocation timed целиком. Первый runtimefailure стоп.
- [ ] Сохранить все исходы/новыйSource diff/independent verdict и обновить runtime-evening/session. История2setup/summary failures не удаляется, setup0.716354009/20 остаётся; общийsummary23 неизменен.

## Review focus

Paths с пробелами/#/% корректно shellquote/URLassets. TSpreload и Lua agents доступны из installedroot при чужомcwd/Quarto config. Explicit own config/lock без подмены курса. Dynamictrace bridgePath остаётся реальнымmodule. Изменяемый Pandoc init/data-dir отдельно, исходный init не переписывается. Никакого обхода restricted Native permissions или новых ownproofs.
