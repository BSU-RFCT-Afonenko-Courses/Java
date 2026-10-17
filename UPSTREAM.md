# Происхождение технической поставки Java

Текущая локальная интеграция 17 октября 2026 года использует проверенную
candidate-связку. Полная содержательная, визуальная и внешняя приёмка курса
выполняются отдельными этапами. Предыдущее описание сохранено в
[истории](docs/provider-history/UPSTREAM-before-20261016.md); его статусы
относятся к прежней редакции.

`providers.json` сохраняет published repository/tag/version/commit/sourceZip
как исходную базу. Отдельный `candidate` задаёт точный локальный commit,
baseCommit, tracked patch и SHA256. `installed-packages.json` содержит
фактически установленные bytes и scopes; `extensionTree` относится к базовому
пакету, а `candidate` описывает применённый локальный diff. Descriptor version
остается номером исходного release, а не объявлением нового выпуска.

| Владелец | Опубликованная база | Локальный candidate |
| --- | --- | --- |
| Afonenko-Course-Tools/quarto-course | v7.1.0 / `04a6295569f5dcc0fc1b991dc076bafc050dbd49` | `0ff120b1d5bce80c730f46fe2c413142c23baeba` |
| Afonenko-Course-Tools/quarto-project-publish | v5.1.0 / `62f8c4a01a5fd9af11f5f66abba2bb48c2ef2631` | Без локальных изменений |
| Afonenko-Course-Tools/quarto-reference-catalog | v3.1.0 / `3df2110e9f525f03f3ecb4926b5d55718e955c4c` | Без локальных изменений |
| Afonenko-Course-Tools/quarto-course-prairielearn | v7.0.0 / `a13008aaf23e0616eac4b18f1dabbf77a77b56f3` | `1221c8c073d77d80d825ee906e9c244a75ff4a39` |
| Afonenko-Course-Tools/quarto-project-download | v3.1.0 / `73797d3807e2433d33751d538746e1e9d82d6d57` | Без локальных изменений |

Полные patches и hashes установленных отличий находятся в
[манифесте](docs/local-extension-patches.json). Core candidate сохраняет
опубликованную optional time semantics: отсутствие оценки допустимо;
заданное значение остаётся положительным целым; неполная оценка скрывает
общую сводку. Навигация к hash-цели сохраняет начало структурного SECTION;
диагностика называет владельца проектом. API-precheck — необязательное узкое
расширение, которое само не создаёт обязательных API задания.

## Проверка и воспроизведение

Обычный checkout уже содержит vendored packages. Offline проверка ничего
не переустанавливает и не обращается к GitHub:

```sh
python3 tools/materialize-providers.py --check
python3 tools/test-materialize-providers.py
```

Для воспроизведения подготовьте repositories с именами `checkout` из
`providers.json` в одном каталоге. Достаточно published tag objects и blobs;
непушенный candidate SHA получать из GitHub не требуется. Материализатор
проверяет tag → exact SHA, descriptor version, tracked patch SHA256 и все
candidate package bytes. Пример:

```sh
python3 tools/materialize-providers.py --providers-root /absolute/release-checkouts --write
python3 tools/materialize-providers.py --check
```

Данные берутся из Git archive точного published commit и tracked patch,
без использования working-tree правок владельца. Перед первой записью
проверяется весь target inventory всех scopes. Неизвестная локальная правка,
extra file или symlink останавливает поставку. Повторный запуск не меняет
bytes/inventory. Посторонние темы сохраняются. Материализация предполагает
единоличное владение vendor scopes на время проверки и записи.

Прежние16 локальных поправок сверены с release/base и фактическими bytes до
интеграции. Все16 старых diff обратимо применимы поверх нового candidate:
[receipt](docs/provider-history/local16-carry-verification.json). Шесть collect
files теперь также содержат API поля; это дополнительный локальный diff,
не потеря прежней диагностики. Старые providers/installed/local patch manifests
сохранены в `docs/provider-history/`. Legacy sync-providers.py отказывается
заменять candidate опубликованной базой.

## Runtime и внешняя граница

Опубликованные Platform2.1.0 commit/digests в `CI/toolchain.json` сохранены.
Новый local candidate Platform source —
`72f24f2c7fe7f07461b3d348a0dd25465ef95b1c`, config ID —
`sha256:130e05e77ab1d7764dd126ed81fac73fc915530a071f73a0944b061a5f84107f`.
Exact source/runtime hashes и требуемое будущее внешнее действие находятся в
`CI/local-candidate.json`. Этот config ID не выдаётся за published digest.

```sh
export PL_PLATFORM_ROOT=/absolute/local/platform
export PL_LOCAL_IMAGE_ID=sha256:130e05e77ab1d7764dd126ed81fac73fc915530a071f73a0944b061a5f84107f
bash CI/check-projects-candidate.sh --preflight-only
# Полный локальный цикл после отдельной авторской приёмки:
CI_CHECK_OUTPUT=/absolute/outside-course bash CI/check-projects-candidate.sh
```

Flow проверяет exact clean source SHA, runtime hashes и локальный linux/amd64
image через inspect, без clone/pull/push. Затем строит отдельный runtime
registry с явным local candidate и выполняет полную выбранную delivery на
host/container. На этом этапе проверен только preflight; полный текущий
course render/delivery этим документом не объявляется готовым.

Production grading CI останавливается до получения образов, пока provider
содержит local candidate. Для возобновления нужен отдельно разрешённый выпуск
Platform/image и обновление published pins; render CI проверяет tracked vendor
closure без получения непушенных SHA. Реальные Moodle/Gateway параметры и
внешние входы сохранены; действующий сервис не изменялся.

Текущий candidate дополнен bounded optional Java source ZIP и compiled type discovery. Публичные version/digests не изменены. Навигация предоставляет вызов штатного Reveal Notes.open на широком экране и в меню узкого экрана; самостоятельная inline-панель не служит доказательством работы отдельного окна. Actual speaker-window verification ведётся в отдельном visual receipt. Прежние результаты render/native остаются историческими до новых проверок точных текущих SHA.

Локальная поправка Presentation разрешает перенос длинных URL библиографии Reveal и внутреннюю прокрутку таблиц на экранах до 600px; правило таблиц исключает desktop, print media и Reveal print-pdf. Проверка клавиатурного доступа к прокрутке не заявлена. Исходные таблицы и учебный текст сохранены.

## Локальная техническая поправка 17 октября 2026 года

Исходник Core добавляет `course.assessment-time: false`: настройка отключает
автоматическую вставку сводки времени в HTML. Она включена в конфигурациях
корня Java и всех пяти учебных форм для student/full. Отсутствие настройки
и `true` сохраняют прежнее поведение расширения. Данные `time`, `theory-time`,
расчёты и транспорт Body сохранены. Суммарная поправка поверх опубликованной
базы, её SHA256 и точные байты пакетов учтены в `providers.json`,
`installed-packages.json` и `docs/local-extension-patches.json`.
Расширения установлены штатным материализатором из поправки исходника.

Локальный образ Java пересобран из точного исходного снимка Platform
с прежними SHA256 файлов среды исполнения и закреплённым digest базового образа.
Новый config ID указан выше и в
`CI/local-candidate.json`; поле `imageRebuild` сохраняет происхождение сборки
и отсутствующий предыдущий ID. Это новая локальная сборка, а не восстановление
прежнего образа и не опубликованный digest. Опубликованные привязки сохранены.

Узкий native-тест настройки, независимая техническая рецензия, проверки
воспроизведения vendor и контейнерные API/grader/nested-JVM тесты выполнены.
Первоначальные ограничения Jupyter, localhost socket и Ansible RPC устранены
настройками локального окружения с адресным повтором затронутых фаз.
Итоговая сборка, визуальная проверка и проверка поставки изменённого курса выполняются
после стабильного авторского снимка; внешние Moodle/Gateway не изменялись.

Русские страницы используют штатный `language.skip-to-content`. Presentation
локализует точные штатные подписи поиска, ссылок на разделы и пустой панели
заметок; английский интерфейс и заданные автором подписи сохраняются.
