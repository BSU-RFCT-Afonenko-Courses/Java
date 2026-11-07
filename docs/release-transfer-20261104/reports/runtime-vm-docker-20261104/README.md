# Подготовка Docker проверки на PrairieLearn VM

Пользователь прямо поручил использовать Docker на PrairieLearn VM и подтвердил root-доступ. Это разрешает изолированную сборку/проверку на VM, но не переключение действующей установки, доступ к данным студентов, изменение live DB/config или публикацию образов.

По сохранённому отчёту развёртывания18октября VM102 имеет адрес `10.135.250.230`; известный Proxmox host — `10.135.250.212`.

## Что подготовлено

`platform-image-candidate.tar` (3.4MiB) — обычный tar текущих рабочих файлов candidate: runtime, runtime-profiles, Java Dockerfile/ignore/grader/helper source и существующие `test_plain_image.py`, `test_plain_runtime.py`. Это snapshot dirty Source на базе Platform `6491517b896b17119fd2ab44db1a2de2ff9c93b1`, включая исправления XML от17:49. Не архив HEAD и не новый commit. Курс, Git, конфигурация среды, .native-toolchain, данные студентов и credentials не включены. Отдельного hash/proof/inventory слоя не создавалось.

Архив локален, на VM пока не передавался. После изменения Source его нужно пересоздать перед единственным реальным опытом; неизменный Source ради статуса повторно не собирать.

## Проверенный исход подключения, 17:58 Europe/Minsk

- SSH root на Proxmox `.212`: `No route to host` (порт22). Это не проверка доступности Proxmox API8006.
- Прямой SSH `.230` отвечает. В пользовательском known_hosts ключа VM не было; обычный strict вызов завершился `Host key verification failed` до аутентификации.
- Первое соединение закрепило ED25519 key только в `/tmp/runtime-vm-docker-20261104/known_hosts` через стандартный accept-new; пользовательский known_hosts и global SSH config не менялись. Fingerprint не сверялся по независимому каналу. Agent forwarding выключен.
- Root SSH с существующим `bosco-admin` — `Permission denied (publickey)`; с `proxmox-iac` — тот же исход; штатный identity/agent — тот же исход. Содержимое закрытых ключей не читалось и не выводилось. Дополнительного перебора нет.
- Рабочий SSH alias/ключ или root route запрошен у пользователя. Пароль/закрытый ключ не запрашивались. Известный адрес сам по себе не доказывает доступ root из этого executor.

## Следующий опыт после подключения

Сначала read-only проверить hostname/uid, Docker server, свободные RAM/disk и текущую нагрузку; определить пригодность VM для опыта. Затем создать отдельный root-owned temp directory и уникальный локальный image tag, передать только этот архив и использовать существующие smoke tests. Контейнеры запускаются с `--network=none`, resource limits и только synthetic job mount; существующие сервисы/volumes/config не подключаются. Production compose, DB и внешние Moodle endpoints не используются.

До фактического запуска записать отдельный общий cap и разделение build/smoke в сохранённом бюджете Docker90с. Если штатный build не укладывается или требует недоступный образ — сохранить реальную причину, не начинать retry loop и не сбрасывать расходы. Публикации/деплоя нет. Удалять можно только созданные этой операцией временные job/container paths; общие image cache/volumes и существующие образы не чистить.

На этом срезе Docker build и image smoke **NOT RUN**. Удалённые процессы не запущены, общая VM не изменялась.
