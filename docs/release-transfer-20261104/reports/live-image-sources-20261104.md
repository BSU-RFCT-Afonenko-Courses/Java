# Действующие образы: Source labels и предел наблюдения

4 ноября 2026. Получены два разрешённых read-only SSH ответа известной VM: список работающих контейнеров и allowlist OCI Source/revision/digest labels. Сервисы, config, DB и данные не изменялись; env/token/student rows не выводились. Это наблюдение объявленного происхождения образов, не повторная byte/hash аттестация исполняемого дерева.

| Компонент | Source label | Repository digest |
| --- | --- | --- |
| Gateway | `moodle-prairielearn-gateway@901624527dce2d37a62fedaa9b768cbbbfd1e366` | `ghcr.io/afonenko-course-tools/moodle-prairielearn-gateway@sha256:b17df2f88025cec7362857839a855f5133d3267e5875e168af3fe129ddac965a` |
| Platform Community | `prairielearn-platform@845a7e4b35cf3b14988b9b09b986c2b434afbb1b` | `ghcr.io/afonenko-course-tools/prairielearn-community-gateway@sha256:640ea0d23ecd299d4cccac358b95b02e006485cefdde39d4436d0282e4554307` |

Gateway exact901 получен одним depth1 fetch в отдельный bare `provider-sources/moodle-prairielearn-gateway`; worktree `gateway`, ветка `codex/java-release-20261110`, HEAD901 сверен. Старый savedf9 checkout сохранён. Actual901 поддерживает delivery4: прежний Source schema3/producer4 gap нельзя переносить на действующий образ.

Source чтение Platform845→9b показало отсутствие diff в интересующих bridge/sync/config/compose модулях; это не доказательство отсутствия runtime overrides. Native course/CI binding, activity kind и mounted source composition пока не установлены.

Последующий запрос экспорта structural config/mount/work IDs в `reports/live-runtime-structure-20261104.json` автоматический approval review **отклонил до исполнения**: internal mount paths/config keys/delivery hashes/work IDs признаны чувствительным payload без отдельного согласия на destination. Пользователю задан async запрос; до ответа не повторять и не обходить сбор другим способом. Указанный JSON не создан. Независимая Source работа продолжается; tokens/keys/LTI subjects/student rows не запрашиваются.
