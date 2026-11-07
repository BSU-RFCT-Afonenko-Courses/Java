# Выпуск, отказы и восстановление

Предыдущий чат завершён. Публикация Java состоялась с явно отложенной квалификацией; это не полностью проверенный выпуск. Нынешнее исследование не возобновляет тот план, не продлевает исключительный допуск и не выполняет серверных действий.

Первичные решения, точные inputs и таблица попыток находятся в [хронологии R01](sections/release-history.md). [Матрица исходников](source-selection.md) разделяет release-линии. Глубокий аудит функций: [assembly](owners/platform-assembly.md), [admission](owners/platform-admission.md), [qualification и recovery](owners/platform-qualification.md).

## Проверено, отложено, неизвестно

| Слой | Последнее сохранённое состояние | Чего это не доказывает |
| --- | --- | --- |
| Course source | Java PR №26 merged; содержимое head и merge совпало | Соответствие старому Native по всем input bytes и новую qualification |
| Source releases | Core и PrairieLearn 8.3 опубликованы; отдельные поведенческие PR остаются open | Включённость всех fixes из дивергирующей 8.2.8 |
| Native Java | Final stage 9 exit 0; 128 questions, 97 works, 2790 файлов | Корректность reference/starter/negative solutions и обратной связи |
| Qualification Java | **0 из 672 final runtime scenarios исполнено**. Failed report: infrastructure-failure до runtime | Это не 672 неверных решения и не успешная qualification |
| Admission Java | package-integrity-user-deferred; **DEFERRED_BY_USER / NOT_VERIFIED** | Строгую verification receipt или автоматическое разрешение следующих поставок |
| Native System | 7 manual questions, 5 works; stage 9 exit 0, 110 файлов | Автоматические проверки: их qualification N/A для этой ручной поставки |
| Сервер | Сохранённые receipts: импорт 128 Java + 7 System, прежние ID/history/LTI сохранены; HTTPS 200, healthy PL | Реальный Moodle launch, сдачу, повторное открытие, feedback и начисление баллов |
| UX/оценивание | 118 Java auto enabled, 10 Java manual, 7 System manual; 34 известных upload ограничения | 118 qualified questions или пригодность UI для свободных классов/пакетов |

Исторический финальный Native Java произведён до поздних CI исправлений PR №26. Выбранный для исследования source уже содержит эти исправления. Между этими snapshots нельзя молча переносить PASS. Свежий публичный CI описан отдельно в [ci-current-state.md](ci-current-state.md).

## Хронология и изменение стратегии

В R01 прочитаны все 28 ходов предыдущего чата, а также настоящие human authorization, failed и privacy reports. SHA документа не заменяет его содержание. История включает таймауты 2702/10800 секунд, OOM при лимитах 2/4 GiB, поздние API/reference mappings, несовместимый allowlist `commonPathDocuments`, ошибки capture/model lifetime и отсутствующие physical common inputs. Не все эти ошибки относятся к одному source snapshot; исправленные случаи не объявлены новыми дефектами выбранной линии.

Пользователь 26 октября явно выбрал срочную публикацию: сначала части 1–5, затем 1–7 с отложенной проверкой качества. Identity/privacy требования сохранялись. Финальное поручение ограничило работу фиксацией и отправкой уже принятых изменений. Нынешний read-only scope не наследует полномочий того выпуска.

Policy связывала exact route, Native, payload, catalog, M3, assets/images и evidence. Она истекла 26 октября в 19:00 UTC (22:00 Минск). Последующая execution lease разрешала продолжить уже admitted экземпляр; она не была новым admission и не продлевала policy. Фальшивую strict receipt не создавали. Долг проверки остаётся открытым.

Отдельно обнаружено source-level расхождение с принятой политикой 8А: пользователь согласовал неограниченные попытки во время открытой работы и лучший результат; source `attempts: 3` / single variant превращается в `triesPerVariant: 3`. Позднего человеческого согласования ограничения в прочитанных 28 ходах не найдено. Реальное серверное поведение неизвестно; сообщение ассистента о лимите не заменяет согласие.

## Независимые компоненты и composition tuple

Runtime 2.4.0, host tools 2.4.1, source Core/Exporter 8.3, raw release assets и OCI — разные компоненты. Текущий gateway HTTPS PR не пересобирает OCI автоматически. Assembly допускает ровно проверенные derivations registry/lock из exact inputs; произвольный гибрид веток недопустим. Raw assets, их hash, assembled tree и API capability проверяют разные гарантии.

В `Context` повторно читаются Git blobs и проверяется физическая сборка. Статическая формула одного контекста — 216 отдельных `git cat-file` и ещё 9 Git-команд; три создания дают 675 процессов, если ветви исполняются полностью. Это **формула по коду**, не замер bottleneck. Fresh recheck на внешней границе нужен; повтор внутри неизменённой фазы — кандидат на сокращение только после mutation control. Внутри grading_job найден узкий непосредственный повтор SHA неизменённого DTO; он не оправдывает удаление всех проверок целостности.

Строгий маршрут требует настоящую qualification receipt и её привязку к input tuple. Deferred маршрут сохраняет NOT_VERIFIED и отдельное решение. Package integrity, semantic qualification, admission и фактический server import не сводятся к одному булеву ready.

## Входные множества и повторная работа

Bank exercises, canonical QMD, workspace QMD, checks, works, ZIP и scenarios считаются отдельно. Историческая CI-цепочка PR26: 177 QMD вместо 128 canonical из-за 49 workspace QMD / 48 duplicate identities; затем course.json удалялся следующей фазой; после этого source inventory не находил common student files. Текущие fixes: ci-checks selection, явное сохранение модели и материализация common inputs. Они не доказывают scope обычного preview.

Stock PrairieLearn exporter один раз собирает native model и передаёт его в collectProjectChecks. Тезис о полном render для каждой работы неверен. Повторные обработки одного question по memberships и переходы между отдельными CLI-фазами остаются отдельными кандидатами. Source view и assembled view не должны случайно конкурировать за роль исходника.

Platform CLI не имеет общего корректного resume. Завершённая фаза может быть переиспользована только при совпадении её input closure и контракта: изменение teacher/reference, relevant grader/runtime, Native, schema либо source обесценивает зависимые результаты. Сохранённый файл или старый PASS сами по себе не checkpoint. Предложение не требует нового workflow engine.

## Предложение закрытия долга

Это план будущего отдельного продуктового поручения, а не выполняемые сейчас действия.

1. Выбрать новый точный course/teacher/runtime/host tuple. Сохранить опубликованные материалы и историю. Проверить capabilities по source lineage и actual installed consumers.
2. До дорогой работы проверить canonical/workspace selection, model/common-input lifetime, embedded/standalone schema и digit-leading instance. Infrastructure failure получает собственный статус, а не ноль студенту.
3. На малом наборе различить free name, nested ZIP, fixed API и data/JAR случаи из 34 upload ограничений. Затем выполнить согласованный полный automatic inventory на точном tuple: reference, starter, negative cases, public checks и failure classes. Малый PASS не заменяет 672 scenarios.
4. Получить новую строгую receipt из реальных результатов. При partial/failure показать долг и зависимые результаты; не использовать истёкшую policy. Повторить только необходимую фазу с проверкой fresh inputs.
5. Отдельно проверить разрешённые реальные Moodle sessions двух пользователей: launch, скачивание, save/reopen, сдачу, auto/manual points, attempts/best, choice/completion, teacher UI и privacy feedback. Запись в DB не заменяет signed LTI launch.
6. Новый выпуск или откат потребует отдельного решения с backup/history preservation и проверкой доступности. В этом исследовании staging, publisher, import и rollback не запускались.

Стоимость recovery разделяется на source preparation, hashing/transport, render/export, qualification, assembly и activation. Старые неуспехи сохраняются; неизвестные длительности остаются неизвестными. Full Java и Cybersecurity matrix ради уверенности повторять не предлагается.
