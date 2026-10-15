# Источники установленных расширений

Пакеты устанавливаются целиком штатным `quarto add`; `providers.json` закрепляет точные исходные commit и области установки, а `installed-packages.json` — полный состав, SHA256, размеры и Git modes. Реализация установленных `_extensions` вручную не меняется.

| Пакеты | Установленная редакция | Точный исходный commit |
| --- | --- | --- |
| Core / Presentation / Navigation | Core v6.0.0 с локальной поправкой | 3d6015880b7327b11b6abc10af913d71c1b01236 |
| Download | v3.0.0 | 6fb3945020cd74fda40cc4d333389864e06609b5 |
| PrairieLearn exporter | v6.0.0 | d2831a6e0a2411ea9743bf950475f9e5819f49a9 |
| Course Site | v5.0.0 | 215309b5c41669e56a857a1bc3e4f7f2ce782c5f |
| Reference Catalog | v3.0.0 | 559583805a514ae8a244b6ea4cb5124867064024 |

Для Core исходной базой служит commit
`9db360beec417b3798693026768262be2f7d3b18` выпуска Core v6.0.0.
В пилоте поверх него применяется
[локальная поправка необязательной оценки времени](tools/provider-patches/core-optional-time/README.md).
Она воспроизводит указанный в таблице commit и Git tree
`1bd2b3d51044c5582caf90f3ed03eaae7a541541` всего исходного репозитория.
База, SHA256 поправки, commit и tree записаны в
[манифесте восстановления](tools/provider-patches/core-optional-time/manifest.json).
Это локальная адаптация для пилота, а не новый опубликованный выпуск.
Исходники Presentation и Navigation в ней не изменены.

Core, Presentation и QRC устанавливаются в корне и пяти учебных частях.
Course Site находится в корне; Download и exporter — в tasks;
Navigation — в lectures, practice и tasks. Для провайдера `core`
сначала восстановите локальный commit по инструкции поправки:
получение SHA только с удалённого сервера не заменяет этот шаг.
Полные пакеты обновляются существующим интерфейсом providers:

```sh
python3 tools/sync-providers.py --providers-root /absolute/provider/repositories
```

Java использует `--release 25` без preview, официальный JUnit Console 1.14.1/Jupiter 5.14.1 и Platform runner. Профили java25-junit-v1/java25-mutation-v1 относятся к образу Java, fish-git-v1 — к отдельному образу fish. Ошибки компиляции и решения отделены от сбоев инфраструктуры. Полная контейнерная проверка охватывает всю выбранную поставку.

Исходный tag Platform v2.0.0 закреплён на `b971a61a2386fbf4ae7fcfc9394d307359bc7889`. Запуск публикации 38209253186 собирал образы из `5bf3a070ff2d21a5c6cebf636775a21bfab2641f`. Итоговый tag включает фактические pins; отдельная проверка содержимого Git, прав файлов и реестра подтвердила, что различие исходника сборки и финального tag ограничено утверждёнными метаданными и документацией, а контрольные суммы runner и библиотек сохранены. Точный финальный исходник прошёл независимую проверку. Java runner SHA256: `48f29c813ee923d50062774034f2c530da6a49588d2f891d51c4c9d9cc76f63b`.

| Среда исполнения | Опубликованный digest |
| --- | --- |
| java | `ghcr.io/afonenko-course-tools/java25-grader@sha256:f791e5590e6102961669e838cb2476f2dde91d888b0fb5a5292bc226cb2f127d` |
| fish | `ghcr.io/afonenko-course-tools/fish-git-grader@sha256:da7f44391f125ef6ffee838fbf5a47a96289e590eff7381448635639b6544b6a` |
| community | `ghcr.io/afonenko-course-tools/prairielearn-community-gateway@sha256:230b4eed786229922111da9a74e3b841d82ec66ff8ccfe82f309070c667e1aa2` |

Исходник Gateway v2.0.0: `ac7751fa11d0a93e2da3de64b849c44f30f3b37a`; образ `ghcr.io/afonenko-course-tools/moodle-prairielearn-gateway@sha256:9623f6693ae0c033de0115d6f05433d9442de982c865cfba29449415cfe92b17`. Схема БД — 3; конфигурация, delivery и results — v2. Прежние схемы отклоняются; перенос старой БД не предусмотрен.

OCI digests, исходник сборки, запись публикации и точный Platform tag закреплены в `CI/toolchain.json`. Доступ к образам подтверждён фактическим pull с узла проверки; публичная доступность не выводится из факта публикации. Для доступа CI к GHCR используется встроенный `GITHUB_TOKEN`; в задаче CI для проектов требуется `packages: read`; разрешение на чтение закрытых пакетов другой организации проверяется отдельно фактическим pull из Actions данного курса. Успешный локальный pull и локальный scope `read:packages` не доказывают такое разрешение. В CI закреплены Quarto 1.11.5, CUE 0.17.1 и Java 25. Порядок подготовки лабораторных описан в [текущей документации](docs/prairielearn-labs.md).
