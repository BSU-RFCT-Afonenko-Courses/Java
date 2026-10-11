# Согласованный релиз инструментов и курсов — инструкция для новой сессии

Работай из текущего пустого каталога. Твоя задача — выполнить согласованный технический план и довести релиз инструментов и миграцию Java, Template и Cybersecurity до описанных ниже результатов. Обновляй рабочий план по фактическим проверкам; не заканчивай работу новым предложением плана. Не опирайся на прежнюю переписку, файлы `/tmp` или уже открытые рабочие каталоги.

Я разрешаю создавать рабочие ветки, коммиты и pushes, открывать и обновлять PR, проводить review, исправлять замечания, сливать готовые PR инструментов в их фактическую default branch (`main` или `master`), выпускать версионные релизы и устанавливать их в курсы. Повторное разрешение на эти действия не требуется. Нормальные PR merge разрешены. Никогда не удаляй и не переписывай `main`/`master`; не используй force push к ним. Удалять можно только ветки, все изменения которых доказанно слиты или интегрированы в фактическую default branch. Сохраняй и `main`, и `master`, если существуют обе; не переименовывай default branch ради формулировки плана.

Это новое разрешение отменяет исторические фразы «сейчас только планирование/исследование» в исходных документах. Остальные технические требования сохраняются. Не заменяй согласованные архитектурные решения новыми без существенной причины. Когда информации не хватает, продолжай независимую работу; спрашивай только о действительно необходимых пользовательских решениях.

## 1. Загрузка исходников и неизменяемая точка сравнения

Java baseline: `27f6fe0152d2d7e227c51e3542ca03f256e4ee35` в `BSU-RFCT-Afonenko-Courses/Java`. Он содержит зафиксированную текущую авторскую разметку, новые каталоги и документацию. Итоговая разметка должна развиваться вместе с инструментами; baseline нужен для сравнения и отката и не объявляет существующий синтаксис окончательным.

Запусти из пустого каталога:

```bash
set -euo pipefail
test -z "$(ls -A)"
command -v git
command -v gh
gh auth status
SESSION_ROOT="$(pwd -P)"
JAVA_BASELINE='27f6fe0152d2d7e227c51e3542ca03f256e4ee35'
gh repo clone BSU-RFCT-Afonenko-Courses/Java Java
cd Java
git fetch origin --tags
if ! git cat-file -e "${JAVA_BASELINE}^{commit}" 2>/dev/null; then
  git fetch origin "$JAVA_BASELINE"
fi
git cat-file -e "${JAVA_BASELINE}^{commit}"
git fetch origin refs/pull/7/head
START_REF="$(git rev-parse FETCH_HEAD)"
git merge-base --is-ancestor "$JAVA_BASELINE" "$START_REF"
mkdir -p "$SESSION_ROOT/release-work/spec"
git show "$START_REF:docs/release-requirements.md" > "$SESSION_ROOT/release-work/spec/release-requirements.md"
git show "$START_REF:docs/release-plan.md" > "$SESSION_ROOT/release-work/spec/release-plan.md"
printf '%s\n' "$START_REF" > "$SESSION_ROOT/release-work/spec/source-commit.txt"
git switch --detach "$JAVA_BASELINE"
test "$(git rev-parse HEAD)" = "$JAVA_BASELINE"
```

Baseline опубликован в GitHub в существующей PR-ветке Java. Если SHA недоступен, сообщи точное препятствие, не подменяй baseline другим HEAD.

Прочитай целиком сохранённые `release-work/spec/release-requirements.md` и `release-work/spec/release-plan.md` из зафиксированного START_REF; это спецификация R01–R11, технических задач, интерфейсов, схем и критериев завершения вместе с поручением о downstream PR. Разметку сравнивай с неизменяемым baseline, инструкции — с зафиксированным START_REF. Не читай прежнюю документацию baseline как более новое поручение. Также прочитай README, UPSTREAM, `providers.json`, `installed-packages.json`, относящиеся AGENTS.md и необходимые skills. Не считай указанные в документах локальные пути или установленные программы существующими в новой сессии.

Создай рядом каталоги `repos/` и `release-work/`; приватные runtime данные и результаты размещай вне публикуемого курса. Сохрани там исходную матрицу и текущий план, затем веди один актуальный отчёт. Секреты и персональные данные не добавляй в Git.

## 2. Инвентаризация репозиториев и актуальных PR

Каждый следующий shell-фрагмент выполняй из исходного SESSION_ROOT, передавая workdir явно. Переменные предыдущего вызова инструмента не считаются сохранёнными. Получи фактический список репозиториев через GitHub; например:

```bash
set -euo pipefail
SESSION_ROOT="$(pwd -P)"
test -d "$SESSION_ROOT/Java/.git"
mkdir -p "$SESSION_ROOT/repos" "$SESSION_ROOT/release-work"
gh repo list Afonenko-Course-Tools --limit 1000 \
  --json nameWithOwner,url,defaultBranchRef,isArchived \
  > "$SESSION_ROOT/release-work/tools-repositories.json"
gh repo list BSU-RFCT-Afonenko-Courses --limit 1000 \
  --json nameWithOwner,url,defaultBranchRef,isArchived \
  > "$SESSION_ROOT/release-work/course-repositories.json"
```

Разреши actual repository names/URLs по этим данным, `providers.json` и ссылкам спецификации. Обязательный инвентарь: Core/Presentation/Navigation (`quarto-course`), Download, PrairieLearn exporter, PrairieLearn platform, Moodle gateway, Template (`quarto-template-course`), Java, Cybersecurity; также проверь затронутые QRC и Course Site. Не угадывай имя gateway и не создавай дубликат существующего репозитория.

Для каждого владельца и трёх курсов проверь latest-created и latest-updated PR, их содержимое, base/head branches, точный head SHA, состояние, draft, checks и reviews. Можно получить полный snapshot командой `gh api --paginate 'repos/OWNER/REPO/pulls?state=all&sort=updated&direction=desc&per_page=100' --jq '.[] | {number,title,state,draft,created_at,updated_at,merged_at,base:.base.ref,head:.head.ref,headSha:.head.sha,url:.html_url}'`. Подставляй обнаруженные OWNER/REPO; анализируй также порядок создания, а не только обновления. При нескольких PR выбирай релевантные по содержимому и зависимости, не автоматически самый большой номер.

Известные ссылки служат отправной точкой, не актуальным snapshot: exporter PR 11; platform PR 1; Core PR 27; Download PR 6. У Java фактическая default branch — `master`: текущий PR 7 (`feat/prairielearn-java25-20261008`) основан на `codex/native-slide-sections`, а prerequisite PR 6 этой ветки направлен в `master`. У Cybersecurity default branch — `master`, текущий PR 4 — `feat/course-contract-20261006` → `master`, проверенный исходный head `b6b085cf0541785d11159bd9a106cd131dfabaf0`. Перепроверь эти данные. Для gateway на момент baseline PR отсутствовал — повторно проверь и, если это всё ещё так, начинай первый функциональный PR от актуальной default branch.

Клонируй обнаруженные репозитории в `repos/`, fetch branches/tags/PR refs. Открытый релевантный PR — база разработки по зафиксированному head SHA. Для слитого PR используй актуального потомка в фактической default branch, сохранив изменения опубликованных tags; при squash/cherry-pick проверь фактическую интеграцию. Не возвращайся к старому PR SHA ради номера. Сохрани snapshot до правок и обновляй его при появлении новых upstream изменений.

Работай в изолированных `codex/` ветках или worktrees, сохраняя уже существующие имена активных PR-веток. Не сбрасывай чужие изменения. Для Java fetch точного текущего head PR 7, проверь наличие baseline в его истории. Сначала проведи review, исправления и безопасный обычный merge prerequisite PR 6 в `master`; затем retarget PR 7 на `master` и согласуй его историю обычным merge, либо необходимым rebase с сохранением всех commits/изменений и проверкой before/after. PR 7 должен остаться открытым. Не переписывай его историю только ради очистки и не удаляй stacked base до доказательства интеграции.

## 3. План, роли и порядок поставки

Сначала запиши конкретный граф зависимостей, PR bases, пакеты/версии, проверки и критерии завершения. При необходимости уточни план спецификации под реальные текущие PR, сохраняя принятые контракты. Используй субагентов для независимых задач и отдельного review. Координатор: reasoning effort `high`; исполнители кода: `medium`; авторская разметка, рекомендации и документация: `ultra`, если поддерживается выбранной моделью, иначе `xhigh`. Не меняй модель без необходимости; фиксируй фактически применённые настройки.

Последовательность:

1. **Контракты и baseline.** Зафиксируй исходные IDs/body hashes/назначения/project paths и реальный состав банка; ожидаемый baseline — 242 задания и отдельная non-bank demonstration. Согласуй owner schemas и общую матрицу совместимости. Это сравнение авторской модели, не обход runnable каталогов.
2. **Инструменты.** Развивай актуальные PR owners: Core defaults/назначения/visibility/artifacts и project-check collector → Download и полный PL exporter → единый Platform runner/local verification и Community bridge → Moodle gateway. После фиксации Core API независимые владельцы могут работать параллельно. Во время разработки проверяй candidate packages в изолированном Java worktree, совместно корректируй будущую разметку и интерфейсы, фиксируй найденные пробелы у owners. Для формальной миграции курса используй затем выпущенные и проверенные tags; baseline не ограничивает итоговый authoring.
3. **Review и релизы owners.** Вместе с кодом обновляй связанные схемы, спецификации, примеры, рекомендации и owner docs. Открой/обнови PR, проведи независимый review, исправь замечания, запусти обязательные checks и слей готовый PR в фактическую default branch. Затем выпусти подходящие versioned tags/releases; новый контракт с breaking changes требует соответствующей версии. Quarto-пакеты должны устанавливаться штатным `quarto add OWNER/REPO@TAG`, включая выбор пакета там, где репозиторий содержит несколько расширений. Проверь установку из опубликованного tag в чистом fixture, состав, bytes/modes/hashes и фактическое поведение установленного пакета. Для Platform/Gateway закрепи версии CLI/schema, image digests и минимальные зависимости. Не представляй будущий tag/digest существующим.
4. **Java после tools.** Обнови целые пакеты и provenance manifests через штатный interface; не редактируй vendored `_extensions` по файлам. Первое изменение миграции курса — отдельным коммитом удалить ручные пять файлов `prairielearn/` по R01, сохранив pilot QMD и проекты. Затем мигрируй авторскую разметку, README, grading suites, CI и свежую документацию по Tasks 7–11. Обновляй текущий PR 7 курса, проведи review и исправления. Сохрани в Java `master` и активную ветку PR 7, а также `main`, если она существует; PR 7 остаётся открытым и готовым к рассмотрению.
5. **Template после Java.** По работающей итоговой разметке Java открой отдельный PR Template для обновления authoring; если это PR уже создан в текущей сессии, продолжай его вместо дубликата. Обнови руководство автора, согласованные примеры и рекомендации установки/локальной проверки. Документы описывают опубликованные версии и реально проверенные команды; не копируй весь курс Java в Template. Проведи review и исправления, оставь PR готовым к рассмотрению.
6. **Cybersecurity после Template.** Повторно проверь текущий PR Cybersecurity, продолжи его от точного актуального head. Мигрируй разметку и целые расширения по текущей спецификации и обновлённому Template, закреплённому точным head SHA authoring PR (либо его merge SHA, если он уже слит); согласуй manifests, README, рекомендации и CI. Сохрани учебное содержание и существующие пользовательские изменения. Проведи релевантные проверки, review и исправления; оставь обновлённый PR готовым к рассмотрению.

## 4. Обязательные смысловые условия

- Core один раз вычисляет effective properties после Quarto metadata inheritance. Отличный explicit `target` под общим default — ошибка, совпадающий допустим; individual visibility законно переопределяет default. `false` явно отменяет inherited default там, где разрешено схемой. Проверка до audience projection охватывает скрытые ветви; неизвестные поля — ошибка. `.task-items` наследует requirement/work-mode; stage принадлежит блоку.
- Общий платформенно нейтральный `project-check`/`project-checks` принят. Проверяются только явно подключённые проекты, включая унаследованный default: **автоматическое обнаружение Java по filesystem запрещено**. Manual demonstrations с явным check проверяются в declared scope независимо от PL export. Наличие Java/reference/tests не подключает проект. PL export требует check; incomplete selected delivery блокирует выпуск. Обычный HTML render не запускает Java/Docker.
- Download получает разрешённый моделью комплект по exercise ID. Одна контекстная ссылка: обычная задача student → starter, full → full; открытая `course-role=demonstration` → полный проект в обоих. Это намеренно распространяется на три Unicode demo exercises, остающихся вне банка. Conditions не содержит решений. README автономны; ZIP детерминированны; приватные материалы обычных задач не раскрываются.
- Нативный PL course/instance/assessment/questions и payload генерирует установленный exporter. Запрещены ручные native shell/binding maps, курсный substitute exporter, собственный grader/runtime и правки generated output. Сохрани устойчивые учебные ID; однократные изменения UUID/оценивания фиксируй как явную миграцию.
- Один Platform-owned Java 25/JUnit runner используется локально и в PL. Student build.gradle/public tests не определяют официальный grade. Положительные эталоны, starters и объявленные negative contract fixtures проверяются отдельно. Нет универсального требования starter score=0; verification success, реализуемость, покрытие tests и scoring — разные выводы. Исправь доказанные empty-examples/no-op private coverage gaps. Numbers lab оценивает **только код sum/mean**; объяснение — учебная рекомендация, отсутствующая в PL submission/scoring/Moodle result.
- Используй **Community PrairieLearn + gateway**, Moodle LTI 1.3/AGS и принятый приватный identity/ACL bridge. Не переходи к Enterprise. Runtime назначения/keys/оценки остаются вне авторской QMD/Git. Настоящий Student имеет серверный доступ только к назначенной защите, включая прямые URL; assignment/self-selection/approval, retries/replay/reassign и единый результат Moodle проверяются по R09/Task 6/Task 10.
- Сохрани структуру тематических проектов, банк, порядок назначений и время. Авторские audience wrappers удаляются; преподавательские инструкции размещаются в full-only страницах/README. CI — тонкий запуск owner CLI в `CI/`, без копии бизнес-логики. Fresh docs: стартовая инструкция, требования, текущий план и один актуальный release report; история — Git, без нового `docs/history`.

## 5. Приёмка, завершение и очистка веток

Проведи цепочку из спецификации: owner tests → installation из tags → student/full/student с privacy/search/QRC/hrefs и проверкой всех ZIP в браузере → fresh full native export → host и **тот же exact image** container verification → настоящий PL sync/editor/attempts → synthetic gateway fixtures → настоящий локальный Moodle Student/teacher round trip. Прежние исследовательские результаты не заменяют новую приёмку. Планируемые CLI из baseline сначала реализуются у владельца; не называй их существующими до проверки.

Проверь prerequisite версии/ресурсы фактической машины, pin Quarto/CUE/JDK/PL/Moodle и container digests; не удаляй чужие volumes/images ради места. Fresh receipts должны совпадать по source/delivery/tests/runner/image hashes; успешный healthcheck или контейнерный JSON не заменяет Moodle round trip. После final releases повтори затронутые проверки на установленных финальных pins и конечные интеграционные gates. Для Cybersecurity проверяй применимые возможности его курса без искусственного подключения Java-заданий.

После merge каждого owner PR очисти доказанно интегрированные локальные и remote ветки. Предпочитай merge commit, чтобы head оставался предком default branch. Для удаления фиксируй exact head SHA и проверяй `git merge-base --is-ancestor HEAD_SHA origin/DEFAULT`; после squash/rebase merge нужны merged PR, совпадение branch HEAD с recorded PR head и доказательство отсутствия новых неинтегрированных изменений. Не применяй безусловный `git branch -D` или массовое удаление refs. Перед удалением повторно проверь remote head и состояние worktrees. В tools оставь фактическую default branch и ветки автоматических Playwright suggestions; обнаружи их по фактическим PR/назначению, не удаляй по догадке. Проверенный пример — `quarto-course: dependabot/npm_and_yarn/playwright-1.63.0`; номер версии перепроверь. Незавершённые/неинтегрированные изменения сохраняй и явно отмечай. Для Java целевое состояние — `master` и активная ветка PR 7. `main`/`master` всегда сохраняются, если существуют. **`gh-pages` сохраняется, если её коммиты не интегрированы в default branch: условие безопасности важнее буквального «удалить все остальные ветки»**, а штатная публикация owner docs требует этой deployment branch. Не удаляй чужие непроверенные worktrees. Ветки Template/Cybersecurity с обновляемыми открытыми PR сохраняются.

В одном кратком отчёте оставь:

- repository/PR URL, base/head/merge SHA и итоговый status каждого компонента;
- release/tag URL, версия, commit, CLI/schema и image digest, минимальные зависимости и проверенная команда установки;
- Java baseline и final PR head, Template/Cybersecurity final heads, необходимые rollback refs;
- реально выполненные проверки с exit codes и связанными hashes, устранённые дефекты, оставшиеся ограничения;
- удалённые ветки с доказательством интеграции и оставшиеся ветки с причиной сохранения.

Не объявляй релиз завершённым при непроверенной установленной версии, неполной selected delivery, ручной правке native artifact или невыполненном обязательном Moodle round trip. При внешнем препятствии сохрани выполненную работу и конкретно укажи незавершённый gate.
