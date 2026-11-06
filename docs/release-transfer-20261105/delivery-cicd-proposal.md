# Доставка курса через обычный CI/CD

Архитектурное предложение, 6 ноября 2026 года. В рамках этого отдельного обзора код не менялся и запуски не выполнялись. Реализация инфраструктурного репозитория и его фактические проверки описаны в основном отчёте. Текущая независимая квалификация Java 2.5.9 продолжается отдельно. Сегодня принимается локальный стенд, работа с VM запланирована на завтра; это предложение не является дополнительным условием сегодняшней приёмки.

**Последнее решение пользователя имеет приоритет:** сейчас в `prairielearn-local` выносится только инфраструктура, которая не зависит от будущей CI/CD. Доставка курса разрабатывается отдельно. Ни описанный ниже workflow, ни local import wrapper, ни course import state не входят в текущий новый репозиторий.

**Рекомендация для отдельной будущей работы:** сохранить существующие экспортёр, checker и native PrairieLearn sync, а последовательность перенести в один обычный GitHub Actions workflow: `export → verify → deploy`. Готовый production export передаётся между jobs штатным artifact; сервер и ноутбук потребляют этот export через Compose mounts и существующие Platform CLI. Отдельный orchestrator, реестр состояний доставки, очередь, язык конфигурации или новый manifest не нужны.

## Строгая граница нового репозитория сегодня

В новый repo входят Compose/services, healthchecks, TLS/secrets, постоянные volumes, узкая одноразовая инициализация первого native administrator и тестовая Moodle. Конфигурационные файлы Gateway/Bridge остаются в существующих форматах этих продуктов; заполнение связей с конкретным course/CI — внешняя задача доставки. Пустая БД не должна приводить к выдумыванию numeric IDs или к автоматическому созданию курса ради старта инфраструктуры.

| Файл/часть первоначального кандидата | Оставить сейчас | Отложить/исключить из нового repo |
| --- | --- | --- |
| `compose.yml` | PL, Moodle/PostgreSQL, proxy, volumes, healthchecks; generic определения Gateway/Bridge | `import-course`, зависимости старта от импорта, course-specific mount/ID/instance и генерацию bindings |
| `config/start-pl.sh`, `config/nginx.conf` | Запуск native support/migrate/start, TLS proxy и транспортные настройки | Автоматическую загрузку/экспорт/проверку курса |
| `scripts/init-config.py` | Создание ключей/TLS/directories, host paths/permissions, общей PL и Moodle конфигурации | `.env` parser; чтение delivery; `COURSE_*`; `courses.json`; native course bindings; `complete_gateway()` в той части, где он строит маршрут конкретного курса и требует import state |
| `scripts/import-course.mjs` | Только уже написанные `initialAdministrator()` и узкий native DB bootstrap actor можно извлечь в одноразовую infra-инициализацию | Сам local importer: `importSettings()`, `platformCLI()`, `main()` с bootstrap/sync курса, `course-import.json`, создание Bridge bindings |
| `moodle/*` | Воспроизводимая тестовая Moodle, accounts/registration fixture и её persistent data | Предполагаемый контракт управления course deployment из будущей CI/CD |
| `.env.example`, `.gitignore`, `.dockerignore` | Небольшие infrastructure defaults; исключение secrets/state из Git и build context | Course repository/commit/registry, course ID/instance, предполагаемые release selectors и обязательный Workspace socket базового запуска |
| `tests/import-course.test.mjs` | При необходимости существующие случаи initial-admin guard после выделения этого кода | Проверки local course import wrapper, который исключён из текущей области |
| `README.md`, `config/README.md` | Как запустить/остановить инфраструктуру, где её состояние, как передать существующие product config files | Инструкции ручной export/check/import цепочки, планы будущего CI/CD, обещание автоматического course provisioning |

Gateway/Bridge, которым ещё не передана реальная конфигурация native bindings, запускаются отдельным явным конфигурационным вариантом после её получения. Это обычное включение services/override с существующими config files, без bootstrap курса в base `up`. Выбор окончательного интерфейса будущего deploy сегодня не закрепляется. Существующие Platform native CLI можно документировать как внешнюю границу; новый local import wrapper для них сейчас не переносится.

## Что установлено по прочитанным исходникам

Ниже зафиксирован прочитанный candidate до сужения scope; это не утверждение, что перечисленные provisional local import файлы должны войти в итоговый инфраструктурный repo. Принадлежность текущему объёму задаёт таблица выше. В актуальном PR importer и его состояние уже исключены; осталась узкая native-инициализация администратора без собственного файла учёта личности.

- Установленный `course-prairielearn/entrypoints/export-course.ts` уже читает курс через действующий Quarto/Core native capture и создаёт готовый native PL каталог с `delivery.json`. Рядом записывается отдельный checks JSON. Экспорт не запускает JUnit qualification. Выход должен быть свежим, checks нельзя поместить внутрь native output; оба результата публикуются после подготовки через rename.
- Checks содержат настоящие выбранные bytes тестов, эталонов и других входов в base64. Это преподавательские данные. Готовый **production native export тоже не является student-дистрибутивом**: PL должен получить серверные grading/answer inputs. Публичные страницы и starter-файлы выделяет уже существующая student-проекция; архив всего native курса нельзя объявлять публичным учебником.
- `check-course.py verify --scope delivery` читает эту же поставку и checks, проверяет целостность структуры и исполняет заявленные сценарии. Exit code `1` обозначает дефект/неполную проверку, `2` — сбой инфраструктуры. Проверка не должна превращать инфраструктурную ошибку в нулевую оценку.
- `bootstrap-native-course.mjs` создаёт native institution/course через существующие модели. Он требует существующего native administrator. `sync-native-course.mjs` уже поддерживает subsequent update **без registry**: получает native numeric course ID, путь и administrator UID, затем вызывает native `syncDiskToSql`.
- В `institution-bootstrap.mjs` поля `repository` и `commit` обязательны для `pl-courses-v2`, но `insertCourse` записывает `repository: null`, `branch: 'master'`. Здесь Git checkout не выполняется. Это действительный guard для прежнего Git staging, перенесённый в операцию, которая его не использует. Придумывать эти значения для готового локального export не нужно.
- В первоначальном, впоследствии отклонённом кандидате `prairielearn-local` был узкий `import-course` service, который использует Platform bootstrap/sync, сохраняет native ID/UUID и формирует Bridge bindings. Начальный administrator создаётся native `selectOrInsertUserByUid` и существующим SQL `administratorAdmins.insert_admin_by_user_uid` с native audit. Повторный запуск с другим UID не выдаёт ему administrator.
- В том первоначальном кандидате local import каждый раз передавал registry и снова вызывал bootstrap; `init-config.py` требует repository/commit, переписывает `courses.json` и самостоятельно разбирает `.env`. Его парсер не реализует семантику Compose interpolation. `.env.example` выставляет десятки настроек, включая выводимые из export и host данные.
- Native sync сначала меняет `course.path`, затем вызывает `syncDiskToSql`; при ошибке общего отката в этом скрипте нет. Поэтому нынешний deploy не является транзакционной заменой всей поставки. При одном mutable mount `.../current` сравнение с previous payload само по себе тоже не сохраняет старые bytes; local import дополнительно держит UUID в state.
- В первоначальном base `compose.yml` переменная `WORKSPACE_DOCKER_SOCKET` была обязательна даже внутри выключенного profile. В нынешнем PR это устранено отдельным явным override. В upstream compose-go interpolation выполняется при чтении YAML, а применение profiles — позднее. Следовательно выключенный Workspace profile не защищает основной запуск от ошибки этой переменной. Это подтверждено чтением upstream source, без запуска Docker. [Исходник compose-go](https://raw.githubusercontent.com/compose-spec/compose-go/main/loader/loader.go), [правила interpolation](https://docs.docker.com/reference/compose-file/interpolation/).

## Предлагаемая схема

```text
Авторский Git ref + установленный exporter + выбранный runtime release
                         │
                    export job
                         ├── native export + delivery.json ──┐
                         └── checks: отдельно, приватно ─────┤
                                                           ▼
                                                       verify job
                                                           │ success
                                                 production approval
                                                           │
                                                       deploy job
                                                           │
                                             Compose + Platform CLI
                                                           │
                                            native bootstrap ИЛИ sync
                                                           │
                                            native IDs → product config
```

Быстрый результат доступен после `export`, не после всех 385 проверок. Длительная qualification идёт отдельным job. Deploy получает **тот же** native artifact, который проверял `verify`; после проверки экспорт не запускается заново. Публикация runtime images остаётся отдельным Platform release workflow: изменение текста задания не должно пересобирать PrairieLearn, Gateway, JDK или Workspace.

Предлагаю один workflow с двумя обычными режимами запуска:

1. Push/PR — только быстрый export, как обратная связь автору; текущая публичная student-сборка остаётся отдельным существующим job.
2. `Run workflow` на выбранном разрешённом ref — export, полная verify и, если запрошена доставка, production job после approval. Автор один раз запускает цель «подготовить/обновить», а не копирует три команды. Если нужна только qualification, deploy пропускается.

Это сознательно допускает новый export при отдельном запуске release workflow после более раннего preview. Сегодня финальный export исходника `081` занял **21 мин 44 с под нагрузкой**; это наблюдение, а не постоянная цена. Выигрыш простой схемы — отсутствие маршрутизации между чужими runs. При этом внутри release pipeline и при повторе упавшего verify/deploy повторного export нет. Для уже идущей сегодняшней квалификации этот маршрут не запускается и её результаты не пересоздаются. Если повтор preview действительно станет существенной стоимостью, предусмотрена первая альтернатива ниже.

GitHub уже предоставляет jobs/`needs`, неизменяемые artifacts и передачу их между jobs; собственный receipt успешности вместо статуса job не требуется. [Workflow artifacts](https://docs.github.com/en/actions/tutorials/store-and-share-data). Production environment даёт required reviewer и ограничение разрешённых refs; доступность reviewer rules зависит от тарифа и видимости репозитория. [Environments](https://docs.github.com/en/actions/reference/workflows-and-actions/deployments-and-environments).

**Граница доступа:** production export, checks и полный checker snapshot размещаются только там, где имеющиеся permissions допускают преподавательские данные. GitHub artifact доступен читателям репозитория и сам по себе ничего не скрывает. [Доступ к artifacts](https://docs.github.com/en/actions/how-tos/manage-workflow-runs/download-workflow-artifacts). Если публичный course repository не подходит по этой модели, выполнение проходит в существующем приватном course/deployment repository, а общий reusable workflow может оставаться публичным. Публичный `student-site` публикуется отдельно существующим packaging. Называть artifact `private` или добавлять hash недостаточно. Нового секретного формата не предлагается.

## Какие действия остаются у человека

| Кто | Обычное действие после перехода | Какие значения действительно выбирает |
| --- | --- | --- |
| Автор | Меняет декларативный Quarto-контент, делает push; для полной подготовки нажимает `Run workflow` | Git ref штатным селектором; instance только если надо выбрать один из нескольких |
| Принимающий | Смотрит отчёт нужного run и разрешает production job | Approval для конкретной поставки |
| Первый запуск ноутбука сейчас | Запускает Compose; служебный config-init готовит конфигурацию | Особые host paths/порты только при необходимости; никакого course repository/commit |
| Загрузка/обновление курса на ноутбуке в будущем | Передаёт готовый export будущей отдельной доставке | Каталог поставки; instance только при неоднозначности |
| Обновление сервера в будущем | Разрешает deploy job; job загружает artifact и вызывает существующий Platform native sync через Compose | Целевой environment, если их несколько |

Фактический базовый запуск инфраструктуры в текущем PR уже использует одну команду:

```sh
docker compose up -d --build --wait
```

Initializer выполняется служебным контейнером Compose и готовит обычные конфигурации, ключи/TLS и host paths. Он не читает курс, не запускает exporter/checker/sync и не управляет жизненным циклом контейнеров. Python и OpenSSL на хосте для этого не нужны; `.env` разбирает Compose. При повторе сохраняются ключи и аккаунты, обновляются только вычисляемые транспортные настройки. Внешние product configs не перезаписываются. Системное доверие тестовому CA остаётся отдельным явным действием пользователя. Фактический уровень приёмки установки указан в основном отчёте.

Предлагаемый будущий update UX — одна цель deployment job: получить готовый export, вызвать существующий Platform native sync и перечитать product config при его изменении. Внутри job допустимы несколько обычных CLI шагов; пользователь их не копирует. У этого предложения ещё нет принятого laptop launcher/CLI, и он не создаётся сейчас. Для обновления самих инфраструктурных образов уже подходят обычные `docker compose pull` и `docker compose up -d --wait`, с сохранением volumes.

### Минимальные параметры

- `COURSE_DIR` — реальный каталог готового export будущей доставки. Это неизбежный выбор входа, если он не загружается выбранным CI run автоматически; базовый infrastructure repo сейчас его не требует.
- `COURSE_KEY` выводится из `delivery.courseId`; instance — из `delivery.instances`, если он единственный. При нескольких экземплярах человек выбирает один, либо deployment настраивается на все. Отдельный Java-specific default запрещён.
- Native numeric course/institution/instance IDs выдаёт БД. UUID берутся из native файлов. Их нельзя вписывать руками в README или копировать из чужой инсталляции.
- Exporter определяется установленным расширением выбранной книги; course root — checkout job; native/checks/report paths — служебные временные каталоги job. `EXPORTER`, `COURSE_ROOT`, `EXPORT_DIR`, `CHECKS`, `RUNTIMES` исчезают из пользовательской инструкции, хотя внутри job нормальные пути остаются.
- Book/instance — конфигурация данного курса, runtime release — выбор сопровождающего в workflow, а не пять переменных перед каждым запуском. Published runtime profiles берутся из выбранного существующего Platform release; один и тот же файл используется export и verify. OCI digests уже содержатся в нём. Не заменять выбор версии автоматическим `latest`.
- `HOST_JOBS_DIR`, локальные UID/GID и state path вычисляются один раз host initializer из рабочего каталога и `id`; это не учебные параметры. Для удалённого Docker daemon или особого размещения требуется явный host path override: путь принадлежит Docker host, а не обязательно ноутбуку с CLI.
- Служебный local bootstrap actor и hostnames/ports имеют generic defaults. Домен, TLS и существующий native administrator сервера — реальные свойства установки; их один раз настраивает оператор. Institution относится к созданию курса средствами Platform, а не к обязательной анкете base infra. Секреты генерируются один раз и сохраняются вне Git. Повторный `up` их не ротирует.
- Workspace — осознанная дополнительная возможность с действительно необходимым отдельным daemon/socket. Это не обязательный параметр базового стенда. Выбор grader/Workspace session image уже находится в поставке; local repo не дублирует его Java-specific настройкой.

Не все внутренние значения следует делать пользовательскими env-переключателями. Зафиксированные версии образов, default DB имена, service ports и технические filenames лучше хранить как обычную конфигурацию repo; override добавлять там, где есть реальная потребность.

## Готовый artifact, source capture и отдельные expectations

Действующий Quarto/Core capture имеет внутренние operation/request/`active.json` и capture witness. `nativeEntry` сейчас поддерживает только новую операцию; consume-existing явно не реализован. Это существующая часть source qualification, а не интерфейс переноса поставки. Её не следует превращать в новый CI протокол или переносить между runners.

Между jobs передаются только законченный native каталог, существующий checks manifest и выбранные published runtime profiles. Checks несут selected bytes и относительные names/targets; `bookRoot` формируется из `options.book`. Checker использует эти bytes, а не прежний абсолютный source root/capture workspace. В нынешних `scenario_inventory`/`prepare_job` аргумент snapshot не читается; в примере ниже он не нужен. Не надо сохранять одинаковые абсолютные пути runners, повторно вызывать capture или перепривязывать operation ради проверки готового artifact. Изменение исходников/AST остаётся новым source export; проверка уже созданной поставки — отдельной операцией checker.

Случай `starter expected=0, actual=0.2`, когда один hygiene JUnit проходит, а остальные сценарии ожидаемо TODO, показывает ещё одну границу. `check.verification-expectations` — существующие поля **private checks manifest**, не grading descriptor. В `prepare_job` expectations выбираются отдельно; при `scope=delivery` grader descriptor и selected test/starter bytes сопоставляются с native файлами существующей проверкой. Поэтому исправление принятого operational expectation само по себе не требует изменения native PL payload или grader inputs.

**Будущее предложение:** qualification job должен принимать checks как отдельный стандартный artifact и позволять передать исправленную, рецензированную редакцию **того же native-checks JSON**, сохраняя прежний native artifact. Исправляется точное существующее поле нужного qualified ID; новое `expectations.yml`, patch DSL, второй delivery manifest и hashes не нужны. Авторская правка также фиксируется в источнике обычным Git commit. Нельзя автоматически подгонять expected к наблюдённому actual; это изменение проверочного контракта, требующее содержательной приёмки. Существующий checker затем повторно проверяет выбранную поставку с новым manifest и сохраняет новый отчёт; старый отчёт не переписывается и сам по себе не становится новым SUCCESS.

**Сегодня:** штатного CLI checks-only нет; `export-course.ts` принимает только нынешние book/instance/checks-output/runtime-registry/candidate-image options, consume-existing не является обходом. Если принятый процесс требует именно регенерации checks из исправленного Quarto source, честный сегодняшний путь может включать полный повтор export/render. Описанное разделение job inputs — будущая отдельная работа, не обещание уже доступного режима. Это не повод сегодня менять native payload или останавливать независимую квалификацию.

## Предлагаемые фрагменты стандартной конфигурации

Ниже **проект workflow, не выполненная конфигурация**. Это существенная часть после стандартных checkout/setup шагов: установлен Quarto нужной версии; в `.ci/platform` получены инструменты выбранного Platform release, рядом лежит его существующий `published-runtime-profiles.json`. Эти подготовительные шаги должны быть явными обычными Actions шагами. В рабочем файле actions закрепляются принятой в репозитории политикой версий. Выполнение — в репозитории с подходящим доступом к преподавательским данным.

```yaml
name: Подготовить и доставить курс
on:
  push:
    branches: [master]
  pull_request:
  workflow_dispatch:
    inputs:
      deploy:
        description: Доставить после полной проверки и приёмки
        type: boolean
        default: false
permissions:
  contents: read
env:
  # Выбор курса, один раз в его workflow; generic local repo этого не знает.
  COURSE_INSTANCE: 2026-2027-s1
jobs:
  export:
    runs-on: ubuntu-24.04
    outputs:
      native-id: ${{ steps.native.outputs.artifact-id }}
      checks-id: ${{ steps.checks.outputs.artifact-id }}
    steps:
      # Обычные checkout/setup и получение выбранного runtime release.
      - name: Экспортировать установленным exporter
        run: |
          quarto run tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/entrypoints/export-course.ts \
            "$GITHUB_WORKSPACE" "$RUNNER_TEMP/native" --book tasks \
            --instance "$COURSE_INSTANCE" --checks-output "$RUNNER_TEMP/checks.json" \
            --runtime-registry "$GITHUB_WORKSPACE/.ci/platform/published-runtime-profiles.json"
      - name: Подготовить отдельный checker artifact
        run: |
          mkdir "$RUNNER_TEMP/checker-inputs"
          cp "$RUNNER_TEMP/checks.json" "$RUNNER_TEMP/checker-inputs/checks.json"
          cp .ci/platform/published-runtime-profiles.json "$RUNNER_TEMP/checker-inputs/"
      - uses: actions/upload-artifact@v4
        id: native
        with:
          name: native-course-${{ github.run_attempt }}
          path: ${{ runner.temp }}/native
          include-hidden-files: true
          if-no-files-found: error
      - uses: actions/upload-artifact@v4
        id: checks
        with:
          name: checker-inputs-${{ github.run_attempt }}
          path: ${{ runner.temp }}/checker-inputs
          if-no-files-found: error
  verify:
    if: github.event_name == 'workflow_dispatch'
    needs: export
    runs-on: ubuntu-24.04
    steps:
      # Обычные checkout того же github.sha и tools/setup.
      - uses: actions/download-artifact@v5
        with:
          artifact-ids: ${{ needs.export.outputs.native-id }}
          path: native
          merge-multiple: true
      - uses: actions/download-artifact@v5
        with:
          artifact-ids: ${{ needs.export.outputs.checks-id }}
          path: checker
          merge-multiple: true
      - name: Проверить именно готовую поставку
        run: |
          python3 .ci/platform/tools/check-course.py verify \
            --manifest checker/checks.json \
            --scope delivery --delivery native/delivery.json --backend container \
            --runtime-registry checker/published-runtime-profiles.json \
            --output report/check-report.json
      - uses: actions/upload-artifact@v4
        if: always()
        with:
          name: qualification-report-${{ github.run_attempt }}
          path: report/
          if-no-files-found: warn
  deploy:
    if: inputs.deploy
    needs: [export, verify]
    environment: production
    concurrency:
      group: deploy-production-course
      cancel-in-progress: false
    runs-on: [self-hosted, course-deploy]
    steps:
      - uses: actions/download-artifact@v5
        with:
          artifact-ids: ${{ needs.export.outputs.native-id }}
          path: native
          merge-multiple: true
      # Обычная передача в новый release-каталог, затем существующий Platform CLI
      # внутри PL через docker compose exec. Checks на сервер не скачивать.
      # Первый bootstrap и subsequent sync — разные существующие операции.
```

В download steps используются artifact IDs из outputs export job. Нельзя собирать имя из текущего `run_attempt` потребителя: при `Re-run failed jobs` успешный export может оставаться от предыдущей попытки. Checker inputs сначала складываются в один staging directory: смешивание runner temp и hidden `.ci` paths изменило бы layout и могло исключить runtime profiles. Native upload явно включает скрытые файлы только свежего выбранного output: там есть требуемый `workspace/.pl-seed`; весь workspace таким способом не загружается. При необходимости сохранить Unix file modes штатная альтернатива — tar внутри artifact, а не собственная упаковка. [Официальный upload action](https://github.com/actions/upload-artifact). Конкретное развёртывание runner/SSH на VM выбирается в отдельной CI/CD работе; приведённый self-hosted вариант — пример уже установленного доверенного deployment runner. PR код не должен исполняться на нём и не получает production secrets.

Один reusable workflow уместен, когда второй курс реально потребует ту же цепочку. Тогда переносится этот YAML с небольшими `workflow_call` inputs, а не создаётся composite action/новый CLI для каждого шага. Пока достаточно одного workflow. [Reuse workflows](https://docs.github.com/en/actions/how-tos/reuse-automations/reuse-workflows).

Для infra одноразовую инициализацию administrator можно выразить стандартным one-shot dependency; она не читает курс и не вызывает course bootstrap/sync. Это предлагаемое выделение уже написанного native admin кода:

```yaml
# Предлагаемый infra-фрагмент; course delivery сюда не входит.
services:
  initial-administrator:
    # Извлечённый существующий native user/admin bootstrap, без course input.
    depends_on:
      pl: {condition: service_healthy}
    restart: 'no'
```

`service_healthy` ждёт готовности PL; при необходимости downstream one-shot шаги используют `service_completed_successfully`. `depends_on` не является постоянным контроллером здоровья и не откатывает уже работавшие сервисы. [Startup order](https://docs.docker.com/compose/how-tos/startup-order/). Замена bytes внутри прежнего bind-каталога не является событием обновления Compose. Обычный `up` сохраняет mounted volumes и пересоздаёт изменившиеся services. [Compose up](https://docs.docker.com/reference/cli/docker/compose/up/).

Workspace host и его `${WORKSPACE_DOCKER_SOCKET:?...}` предлагается целиком перенести в отдельный `compose.workspace.yml`, который читается только по явному выбору:

```sh
# Предложение; базовый up вообще не читает этот файл и socket-параметр.
docker compose -f compose.yml -f compose.workspace.yml up -d --wait
```

Не подставлять фиктивный socket и не отменять требование отдельного daemon ради прохождения interpolation. Отдельный override — стандартное средство Compose, без нового слоя управления.

## Дополнение: единица публикации и начатые попытки

После архитектурного обзора локальное обновление выявило отдельное ограничение native PrairieLearn. Изменение весов assessment не обновляет `instance_questions.current_value` уже начатой работы. Новая ручная часть при старом нулевом current value даёт отрицательную доступную автоматическую стоимость; по исходникам это влияет и на правильный новый ответ. Сохранение строк истории не равно сохранению корректного дальнейшего оценивания. Native regrading не предоставляет общей автоматической миграции изменённых весов без повторной проверки ответов.

Единица native sync — весь курс. Текущий exporter выбирает один instance и выдаёт только его каталог; sync помечает отсутствующие в поставке instances удалёнными. Поэтому схема CI/CD должна публиковать полный согласованный course tree, а не считать single-instance export добавлением нового экземпляра. Если согласуется сохранение старой шкалы в прежнем CI и новая шкала в новом CI, поставка обязана содержать оба с действительными рубриками и ссылками. Это отдельная необходимая доработка публикации, а не повод добавить wrapper в инфраструктурный repo. Решение о политике старых попыток вынесено пользователю; сегодня оно не реализовано автоматически.

Verify job также обязан явно учитывать отрицательные контроли: нынешние 385 охватывают стартеры и корректные авторские варианты, но не 58 изменённых wrong/initializer наборов. Зелёный check-course job с текущим inventory не является полной приёмкой этих отрицательных случаев. Их состав и ожидаемая предметная причина остаются частью реальной проверки, без введения нового формата публичных тестов.

## Bootstrap, обновление, история и сбои

**Infrastructure bootstrap сейчас** создаёт local administrator только в действительно пустой local DB. На существующем сервере используется уже проверенный native administrator; повторный запуск не создаёт нового всемогущего пользователя. `administratorEmail: null` не заменяется выдачей прав произвольному вошедшему через Moodle UID. Он не создаёт institution/course/CI и не формирует bindings.

**Первый course bootstrap в будущей доставке** — отдельный вызов существующего Platform CLI, затем native sync. Подтверждённый numeric ID уже хранится в native DB; из CLI он передаётся следующему шагу штатным job output. Повтор после неуспешного первого sync должен разрешать тот же native course по тому же immutable пути и действительным identity/institution, а не создавать второй курс из-за отсутствия нового local receipt. Текущий portable wrapper пишет `course-import.json` только после sync; его удаление из scope избавляет от необходимости закреплять этот дополнительный state contract. Если native привязку нельзя однозначно восстановить, требуется оператор, а не создание курса «на всякий случай».

**Subsequent update в будущей доставке** получает ID из действительной native/product configuration, подтверждает существующий course в native DB и выполняет sync того же курса. Registry для этого уже необязателен. Course/CI UUID, numeric IDs, history и принадлежность institution сохраняются. Существующая Bridge configuration — конфигурация маршрута с проверкой native parent/UUID, не новый receipt допуска; отдельный `course-import.json` в infra не нужен.

На сервере следует сохранять предыдущие готовые exports обычными release-каталогами, монтировать их общий parent read-only в PL и передавать новый абсолютный native путь `/course/<courseId>/<release>`. Название release берётся из GitHub run/Git metadata. Не требуется содержательный hash имени. Старые каталоги не редактируются; `current` symlink не используется — он конфликтует с существующими guards против symlink traversal. Этот course mount задаёт будущий deployment override, не обязательный base infra contract. CLI должен работать с тем же mount, который видит запущенный PL: подмена mount только у отдельного `compose run` не обновляет mount PL. Bridge читает bindings при старте; после изменения его product config deployment явно перезапускает Bridge. Одного `compose run` для всего обновления недостаточно.

**Rollback** означает выбор ранее сохранённого совместимого native export и повторный native sync того же course/CI UUID с теми же volumes. Это откат содержимого, не восстановление БД и не отмена студенческих событий. Native history/attempts не очищаются. Изменения состава questions/assessments и обратимость конкретной версии проверяются существующими native правилами. Автоматического полного rollback после частичного sync сейчас нет; резервное копирование/восстановление БД остаётся вне текущего объёма.

| Сбой | Что делает стандартная цепочка | Что остаётся оператору |
| --- | --- | --- |
| Export не завершился | Job красный; verify/deploy не запускаются | Исправить source/tool failure, повторить export |
| Verify вернул `1` или `2` | Deploy заблокирован; отчёт сохраняется через `always()` | Исправить причину; повторить failed job на тех же artifacts, если вход не менялся |
| Artifact уже удалён retention policy | Повтор job не получает исходный вход и завершается ошибкой | Выполнить новый полный pipeline; не подменять его текущим каталогом |
| Platform CLI отказал до DB mutation | Нет успешного downstream запуска | Исправить mount/config/admin/identity; повторить операцию |
| Первый bootstrap успешен, первый sync упал | Native course уже существует | Разрешить тот же native course, продолжить sync; при неоднозначности восстановить привязку оператором. Не создать второй курс |
| Native sync упал после начала mutation | Job красный, состояние может быть частичным | Повторить native sync той же поставки; при необходимости осознанно resync предыдущей. Не объявлять старую версию автоматически работающей |
| Два обновления одновременно | Production job concurrency сериализует CI обращения | Не запускать параллельный ручной sync в обход принятого deployment пути |

Для deploy не применять `cancel-in-progress: true`: прерывание native mutation ухудшает восстановление. Actions concurrency действует внутри репозитория и не координирует разные репозитории/ручные команды. По умолчанию новая pending работа может вытеснить предыдущую pending; это политика «оставить последнюю», а не надёжная очередь всех выпусков. [Concurrency](https://docs.github.com/en/actions/how-tos/write-workflows/choose-when-workflows-run/control-workflow-concurrency). Для данного одиночного курса достаточно одного разрешённого deploy за раз, без требования выполнить каждый промежуточный push. Никакого собственного queue service для этого не требуется.

## Что удалить или объединить

| Текущее место | Предлагаемое сокращение |
| --- | --- |
| Пользовательский runbook | Удалить ручную последовательность export/check/sync и пять служебных env-путей. Оставить цель workflow, location готовой поставки и просмотр отчёта |
| `prairielearn-local/.env.example` | Убрать обязательные `COURSE_REPOSITORY`, `COURSE_COMMIT`, дублирование `COURSE_KEY`/единственного instance, numeric IDs и host-derived UID/jobs. Остальные технические defaults не рекламировать как обязательную анкету |
| `scripts/init-config.py` | Удалить собственный `env_file()` parser, Git-origin/commit validation для готового export и генерацию local `courses.json`. Сохранить реальные config/secrets/TLS/permissions, одноразовость identity и полезные diagnostics |
| `scripts/import-course.mjs` | Исключить local course import wrapper из нового repo. Сохранить только извлечённый initial native admin guard как инфраструктуру. В будущей отдельной CI/CD вызывать existing Platform CLI; не делать универсальный импорт произвольных PL repositories |
| `institution-bootstrap.mjs` и bootstrap CLI | Отделить прямой bootstrap готового production payload от прежнего Git registry selector. Принять существующий institution descriptor/key непосредственно, course identity взять из payload. Не добавлять новый manifest/registry schema. `selectRegistryCourse` остаётся только реальным Git staging callers, пока они нужны |
| `sync-native-course.mjs` | Для portable пути использовать уже имеющийся режим без registry; не снимать administrator, identity, parent/course scoping и production guards |
| `compose.yml` | Вынести Workspace socket requirement в явно подключаемый override. Сохранить infra one-shot init, volumes и healthchecks; удалить зависимости от course import. Не добавлять daemon, watcher или launcher поверх Compose |
| `build-course.py`, `stage_courses.py`, `check_registry.py` | Не включать эту прежнюю Git staging цепочку в новый portable repo. `build-course.py` не должен становиться ещё одной обязательной командой перед установленным exporter; workflow выбирает один путь экспорта. Удаление этих инструментов из Platform целиком — только после проверки остальных callers; сегодня они ещё имеют реальные ссылки |
| `publish-receipt.py` / runtime publisher | Не смешивать с публикацией курса. Его обычные runtime profiles/OCI references уже решают выбор runtime; не создавать второй course receipt и не пересобирать images при каждом course update |

Удаление redundant validation не означает удаление настоящих защит: production/`devMode=false`/`hasShib=true`, отказ candidate, contained regular files, запрет symlink/path traversal, совпадение course ID и UUID, CI UUID/history и parent scoping, native administrator и audit остаются обязательными. Для student-публикации сохраняется существующая public projection; приватные grading/check/reference bytes туда не направляются.

## Две осмысленные альтернативы

**Разделить build и promotion по GitHub run ID.** Первый workflow один раз экспортирует и проверяет; второй `workflow_dispatch` получает стандартный run ID, скачивает его native artifact и доставляет. Это убирает повтор preview export и позволяет «доставить уже проверенное». Цена — два workflow, `actions: read` для cross-run download, проверка происхождения/успешности разрешённого run через стандартную GitHub API и retention policy. Не нужно переносить checks на сервер. Этот вариант предпочтительнее, если повторный долгий export регулярно мешает; пока он не обязателен для простого первого перехода. Cross-run download требует token и run identifier. [Artifact download](https://docs.github.com/en/actions/tutorials/store-and-share-data).

**Один private OCI image готового курса.** CI кладёт уже экспортированное native дерево в минимальный data image и после verify публикует immutable tag/digest; deployment выбирает этот image и получает файлы штатными Docker средствами. Плюсы — готовый registry retention, привычный pull и идентификатор версии. Минусы — ещё один image build/push, private registry permissions, извлечение/доступ PL к тем же файлам и дополнительная упаковка при наличии уже работающего artifact+bind mount. Сам digest не подтверждает успешность qualification и не заменяет native guards. Для одного laptop/server deployment сейчас это лишняя обязательная операция; оставляю альтернативой при реальной потребности в registry delivery.

## Короткий переход

1. **Сегодня:** завершить текущую local acceptance и независимую qualification. В новый repo перенести только stable infrastructure из первой таблицы: без course wrapper/state/registry и без реализации CI/CD. Workspace — отдельный явно подключаемый override. Отчёт не объявляет proposed интерфейсы уже доступными.
2. **Завтра VM:** использовать существующие проверенные средства установки в согласованном объёме. Не делать готовность будущего workflow условием завтрашней VM. Локальные volumes/студенческие данные не переносить побочным эффектом установки.
3. **Отдельная следующая работа — CI/CD:** согласовать простой interface получения production export и native bindings, убрать неиспользуемую Git-зависимость direct bootstrap, добавить три обычных jobs. Checks/expectations отделить от native artifact; проверить доступ к преподавательским входам до upload. Никаких новых delivery manifests.
4. После первого принятого CI/CD обновления удалить заменённые инструкции/параметры. Уже проверенный course artifact не пересобирать ради переноса. Лишь при появлении второго consumer вынести YAML в reusable workflow; отдельный SDK, orchestrator или deployment platform для этого не нужен.

Критерий упрощения: автор выбирает учебный ref/instance, local пользователь — готовый export, оператор — целевой environment и approval. Остальное либо следует из существующей поставки/native DB, либо один раз настроено обычными средствами CI/Compose.

## Приложение: первичные места в исходниках

Проверены Java `081b6463` и Platform `857e8f2`; у portable repo анализировались текущие рабочие файлы S2/S3/S5. Исторические specs/plans не использовались как требование сохранить прежнюю Git registry схему.

- [Установленный export CLI](/home/tolya/JavaCodex/java-evening-20261105/integration-java/tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/entrypoints/export-course.ts:1), [production/checks outputs и публикация](/home/tolya/JavaCodex/java-evening-20261105/integration-java/tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/application/export-course.ts:789).
- [Private selected checks bytes](/home/tolya/JavaCodex/java-evening-20261105/integration-java/tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/application/native-check-inventory.ts:1), [server grading inputs](/home/tolya/JavaCodex/java-evening-20261105/integration-java/tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/application/export-course.ts:537), [runtime registry resolution](/home/tolya/JavaCodex/java-evening-20261105/integration-java/tasks/_extensions/Afonenko-Course-Tools/course-prairielearn/application/native-course.ts:367).
- [Checker и exit status](/home/tolya/JavaCodex/java-morning-20261106/root-platform-release-259/tools/check-course.py:1), [payload file guards](/home/tolya/JavaCodex/java-morning-20261106/root-platform-release-259/tools/native-course-payload.mjs:1), [administrator/identity/CI guards](/home/tolya/JavaCodex/java-morning-20261106/root-platform-release-259/tools/course-sync-policy.mjs:1).
- [Registry validation и repository:null](/home/tolya/JavaCodex/java-morning-20261106/root-platform-release-259/tools/institution-bootstrap.mjs:19), [native sync порядок операций](/home/tolya/JavaCodex/java-morning-20261106/root-platform-release-259/tools/sync-native-course.mjs:1), [Bridge parent bindings](/home/tolya/JavaCodex/java-morning-20261106/root-platform-release-259/bridge/server.mjs:62).
- [Текущий узкий native admin bootstrap](/home/tolya/JavaCodex/java-morning-20261106/prairielearn-local/scripts/bootstrap-admin.mjs:1), [initializer](/home/tolya/JavaCodex/java-morning-20261106/prairielearn-local/scripts/init-config.py:1), [Compose](/home/tolya/JavaCodex/java-morning-20261106/prairielearn-local/compose.yml:1), [текущие параметры](/home/tolya/JavaCodex/java-morning-20261106/prairielearn-local/.env.example:1).
- [Существующий course CI](/home/tolya/JavaCodex/java-evening-20261105/integration-java/.github/workflows/check.yml:1), [Platform publisher](/home/tolya/JavaCodex/java-morning-20261106/root-platform-release-259/.github/workflows/publish-runtime.yml:1), [обычные runtime assets](/home/tolya/JavaCodex/java-morning-20261106/root-platform-release-259/tools/publish-receipt.py:1).

Дополнение по прямому решению пользователя: вопрос переноса старых попыток отложен для отдельного обсуждения. Политика пересчёта, нового экземпляра или иной миграции сегодня не выбрана и не реализуется. Свежая локальная приёмка продолжает проверять новый состав; её результат не закрывает старую историю.
