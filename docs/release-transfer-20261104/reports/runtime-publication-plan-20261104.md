# Platform: обычная публикация образов без собственного inventory

Основание: brief/session-runtime, пакет Platform. Исполняет root непосредственно; свежий independent review в конце. Используются writing-plans, executing-plans и verification-before-completion. Существующий isolated checkout переиспользуется; initial dirty4 сохранён отдельно. Вопросы дизайна уже разрешены прямым поручением удалить собственные lock/inventories/readers; новую согласовательную паузу не вводим.

Цель: publisher пишет обычные сведения OCI image/source/version и runtime profiles; собственный toolchain lock и release assembly scanner отсутствуют. Docker владеет image, Git — source commit; дополнительных SHA файлов нет.

1. Заменить tests/test_publish_metadata.py публичными проверками actual CLI: без .native-toolchain создаются только два JSON, четыре profile получают правильные image; Workspace digest отдельно; неверная роль image не публикуется. Сначала RED, затем убрать preparation import/generated locks/context rechecks из tools/publish-receipt.py. Actual sourceCommit/version — сведения выпуска, не reader filesystem inventory. Сохранить проверку source HEAD/VERSION как provenance Git.
2. Удалить tools/release_assembly.py и его branches из build-course/stage_courses; удалить связанные tests старой equality/forgery/overlay обвязки и deferred admission proof API. Обычный source/course staging остаётся существующим pending Gateway контрактом и не объявляется чистым Native. Его course SHA остаётся явным остатком, который не следует переносить в новый API.
3. Убрать runtimeProfilesSha256 из runtime-image-v3 schema; published profile schema не должна требовать pins. Workflow собирает Dockerfile обычным context, не вызывает preparation/context archive. Не выполнять publish/deploy workflow.
4. Targeted CLI tests + syntax compile затронутых Python, статический поиск dangling preparation/toolchain-lock/release_assembly readers. Никаких baseline matrices. Cap30s на Python tests, один проход; исправление конкретного failure с сохранением исхода допустимо.
5. Review проверяет неверные image roles, public CLI outputs, отсутствие replacement inventory, потребителей удалённого assembly и сохранение private containment. Локальный commit только после исправлений и review.

## Проверки и уточнение лимита

Publisher RED3/4 → GREEN4/4. Consumer subset:83tests4.815s RED92 (old sharedFiles + removed _assembly mock), затем83tests5.912s RED17 (old runtime_evidence fixture), затем83tests6.914s RED17 (только sandbox запрещает локальный TLS server loopback). Исходы сохранены, время не обнуляется:17.641s. После исправления собственных old fixtures остаётся адресный storage16tests; разрешён один запуск до20s с loopback network approval. Общий publication check cap увеличен заранее30→45s только для этой выявленной средовой границы; остаток27.359s. Это не новый Native/TERM бюджет.
