# Новые свидетельства выпуска и уточнение исследования

Срез публичных материалов подготовлен 27 октября 2026 года, Europe/Minsk.
Исследование ещё не запущено. Здесь прочитаны GitHub PR, исходники, release
metadata и существующие CI logs; новых tests/render/export/Docker запусков нет.
Чат «Выполни план публикации банков» на другом ПК ещё заканчивает работу.
Его итог, локальные failed/human/privacy reports и фактический server state
исследователь должен прочитать при старте. Эти сведения нельзя вывести из
названия коммита, наличия hash-поля или успешного CI другого состава.

Версии и immutable ссылки ниже идентифицируют **просмотренные свидетельства**.
Это не pins будущего исследования. T00 повторно выбирает фактический итог
предыдущей сессии и фиксирует соответствие каждого evidence его входам.

## Почему расширяется задача

Первоначальная постановка остаётся: понятное авторство, single source of truth,
малые публичные контракты и минимальное ожидание. Теперь к ней добавляется
объяснимый выпуск и восстановление после отказа. Публичная история показывает
дивергирующие release-линии, ошибки согласования схем и входных множеств,
неявные сроки жизни capture и отдельный временный допуск без квалификации.

Исследовать нужно не только стоимость успешного render, но и цену полного
рабочего цикла: подготовка → дешёвая проверка → render/export → qualification
→ допуск → публикация. Каждый переход должен иметь понятные входы, результат,
область доверия и состояние отказа. Этот документ не утверждает, что все
guards лишние, и не разрешает повторять исторический обход выпуска.

## 1. Номер версии не определяет линию изменений

[Core 8.3.0](https://github.com/Afonenko-Course-Tools/quarto-course/releases/tag/v8.3.0)
продолжает опубликованный 8.2.7, а не объединённую линию 8.2.8.
[PR48](https://github.com/Afonenko-Course-Tools/quarto-course/pull/48) и
[Exporter PR28](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/pull/28)
явно описывают изолированную линию выпуска. Их source Releases опубликованы,
хотя PR остаются draft/open. Статусы PR, source tag и готовности курса различны.
[Сравнение историй Core](https://github.com/Afonenko-Course-Tools/quarto-course/compare/v8.2.8...v8.3.0).

В 8.2.8 объединены линии наблюдения и пакетной оптимизации, исправлен настоящий
JSON export с несколькими hooks и локальным `format: html`. Из большего номера
8.3.0 не следует наличие этого fix или тех же фаз Performance. Новое
воспроизведение такого отказа на нынешнем Java здесь не выполнялось.

В T00 нужна матрица «линия → capability/fix → consumer → проверенное evidence».
`latest`, main и максимум SemVer не выбирают подходящий набор. Нельзя молча
собрать новый гибрид или обновить vendor в production ради исследования.

## 2. Новый контракт ответа: optional primary и ZIP

Core/Exporter/Platform согласовали `primary-required:false`: обязательность
исходных имён starter не навязывается сдаче; берётся непустая совокупность
реально присланных Java-файлов. Отсутствующие файлы не подставляются из
starter/reference. Отсутствие поля и `true` сохраняют legacy-семантику.
Это **не** разрешение пустого starter inventory и не отмена fixed API задачи.
[Контракт Core](https://github.com/Afonenko-Course-Tools/quarto-course/blob/v8.3.0/docs/project-checks.md#L139),
[контракт exporter](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/blob/v8.3.0/docs/authoring.md#L554).

Opt-in относится к upload, editor отклоняется; nested имена и дополнительные
Java передаются внутри bounded ZIP с путями. Source selection, опубликованный
API, public verification, reference inventory и native UI остаются отдельными
контрактами. Их согласованность следует проверять на малой задаче со свободными
именами/пакетами и на fixed-API задаче, а не выводить из schema PASS.

Новый пример цены точного pin: отдельный
[commit exporter](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/commit/11a4fd3d877c7c9c6124a2d05beb0a8c8c2833b5)
меняет только literal Core SHA в installed-overlay test. Это усиливает H47:
воспроизводимость пары и проверка совместимого producer должны различаться.

## 3. Platform runtime и host-tools — разные компоненты

[Runtime 2.4.0](https://github.com/Afonenko-Course-Tools/prairielearn-platform/releases/tag/v2.4.0)
имеет успешный [publisher run](https://github.com/Afonenko-Course-Tools/prairielearn-platform/actions/runs/40286956980),
три raw assets и pull-наблюдения трёх OCI images. Это опубликованная runtime
поставка, не автоматическая квалификация Java-банка. Suite publisher завершился
с 515 tests и 52 skips; skips не превращаются в прохождение пропущенных проверок.
Наличие/размеры/digests assets просмотрены через API; новое скачивание и
сравнение их bytes с Actions artifact этим обновлением не выполнялось.

[Host-tools 2.4.1](https://github.com/Afonenko-Course-Tools/prairielearn-platform/releases/tag/v2.4.1)
выпущены отдельно, без новых OCI и assets. Они сохраняют runtime/registry/lock
и исходный M3 Context 2.4.0, добавляя новый host validator и `--assembly-root`.
Это конкретный случай независимого обновления компонента, который нельзя
снова связывать правилом «все номера и source SHA обязаны совпасть».
[Контракт host validation](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/59130bab53d0ec2a03fdf025f97bb17930420e8a/docs/host-validation.md#L3).

Published assembly намеренно содержит два допустимых overlay: runtime registry
и generated lock. Они выводятся из exact source и raw assets; raw checkout
сохраняется отдельно. Отличие от checked-in prepared metadata не доказывает
обход. Нужно отдельно оценить необходимость этого двухступенчатого выпуска,
минимальную identity и понятный способ восстановления без ручного смешения.
[Assembly derivation](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/tools/release_assembly.py#L91).

При этом README/releasing текущего host ref ещё называют runtime неопубликованным
кандидатом, а source lock хранит прежнюю canonical основу. Первое — рассинхрон
статуса документации, второе может быть частью штатного derivation и требует
разбора. `/releases/latest` при чтении возвращал 2.3.2. Release API Java pin
также различает content-addressed OCI и `immutable:false` объекта GitHub Release:
точная проверка bytes не равна предположению неизменяемости всех объектов.

## 4. Отложенный допуск не является verification PASS

[Platform PR23](https://github.com/Afonenko-Course-Tools/prairielearn-platform/pull/23)
вводит явный paired opt-in `--admission-policy` и `--admission-policy-sha256`.
Он разрешает package-integrity admission со статусом
`DEFERRED_BY_USER / NOT_VERIFIED`; verification receipt при этом не создаётся.
Без policy сохраняется строгая receipt-ветка, другие routes не получают
такого допуска автоматически.

Публичный код подтверждает наличие механизма. Его фактическое применение,
результат staging и серверный импорт нужно установить по доступному local
evidence; по одному PR эти события не объявляются выполненными.

Закрытая policy связывает route, Native repository/commit/instance, payload,
counts, builder/images и hashes human/privacy/failed-report документов;
при staging требуется `expiresAt` позже текущего времени и не далее 24 часов.
Это не доказывает дату выдачи policy или автоматическое снятие уже
размещённого курса после expiry. Helper проверяет поля hashes, но
не читает сами документы решений. Их смысл, происхождение, срок и полномочия
нужно восстановить по локальному evidence и чату.
[Policy validator](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/59130bab53d0ec2a03fdf025f97bb17930420e8a/tools/stage_courses.py#L33),
[развилка deferred/strict](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/59130bab53d0ec2a03fdf025f97bb17930420e8a/tools/stage_courses.py#L90).

Исследование должно выяснить: какой именно gate не удалось пройти и почему;
что фактически проверили вместо него; как состояние видно оператору и студенту;
какие проверки отложены; что происходит при expiry, повторном staging и смене
source; как закрывается задолженность по qualification и выполняется rollback.
Не следует ни выдавать этот путь за полную квалификацию, ни объявлять его
несогласованным без чтения человеческого решения на другом ПК.

Это исторический факт. Поручение исследования не разрешает новое admission,
продление policy, создание decision hashes или изменение сервера. Истечение
исторической policy не мешает анализировать её сохранённые свидетельства.

## 5. Ошибки контрактов и ложный отрицательный контроль

[Platform PR21](https://github.com/Afonenko-Course-Tools/prairielearn-platform/pull/21)
исправляет actual `prepare_job`: embedded schema отставала от standalone по
допустимым `feedback/publicCases`. Это новый случай позднего рассогласования
декларативных контрактов, а не новая потребность в per-render проверке всех версий.

Следующий publisher остановился на `test_stale_baked_runner_is_refused`:
старый literal SHA prefix больше не встречался, поэтому мутация не меняла
вход, но тест ожидал отказ. [PR22](https://github.com/Afonenko-Course-Tools/prairielearn-platform/pull/22)
изменяет фактический digest и утверждает различие bytes. Этот отказ не
доказывал ошибку студенческого grader; отсутствие изменения входа — дефект
самого отрицательного контроля.
[Неуспешный publisher job](https://github.com/Afonenko-Course-Tools/prairielearn-platform/actions/runs/40279965307/job/121430895230).

Host-tools также исправляет digit-first instance `2026-2027-s1`. Разные
грамматики одного ID и копии одной schema нужно ловить до долгого выпуска.
Существующий зелёный Platform main CI при этом использует историческую
интеграционную пару и сам по себе не квалифицирует новую Java/M3 композицию.

## 6. Java: три разных отказа public CI

Просмотрены [PR25](https://github.com/BSU-RFCT-Afonenko-Courses/Java/pull/25)
и [PR26](https://github.com/BSU-RFCT-Afonenko-Courses/Java/pull/26).
PR26 остаётся draft; между их просмотренными heads три CI-коммита и три
изменённых файла. BANK304 7/7, 1049 student files и parity 128 ZIP заявлены
описанием PR25; соответствующие локальные receipts здесь не прочитаны.

| Сохранённый запуск | Наблюдавшийся отказ inventory |
| --- | --- |
| [40295901020](https://github.com/BSU-RFCT-Afonenko-Courses/Java/actions/runs/40295901020) | После 177 QMD: неоднозначная captured project/source identity |
| [40319095984](https://github.com/BSU-RFCT-Afonenko-Courses/Java/actions/runs/40319095984) | После 128 QMD и 118 checks: отсутствует `tasks/_generated/course-spec/course.json` |
| [40356830541](https://github.com/BSU-RFCT-Afonenko-Courses/Java/actions/runs/40356830541) | После 128 QMD и 118 checks: отсутствуют обязательные public source/student inputs |

У всех трёх отдельный render job успешен. Это не успех inventory либо выпуска.
В последнем просмотренном [run40401766940](https://github.com/BSU-RFCT-Afonenko-Courses/Java/actions/runs/40401766940)
на `60e50f8` итог ещё не был получен. Его актуальное состояние следует прочитать
при запуске; текущий пакет не переобъявляет его PASS.

В исправлениях появился отдельный `ci-checks` profile, исключающий workspace
QMD; модель сохраняется вне owner перед следующим native pass; common student
файлы материализуются в disposable checkout перед inventory и удаляются с
проверкой владения. [Текущий CI-код](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/60e50f8304c8c7097047f23e6a4488f2c330c48c/CI/check-projects.sh#L18),
[новая materialization-фаза](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/60e50f8304c8c7097047f23e6a4488f2c330c48c/CI/check-projects.sh#L55).

Нужны отдельные множества: задачи банка, canonical QMD, workspace QMD,
active checks, выбранные вопросы и сценарии. Совпадение двух чисел 128 не
означает тождество сущностей; 118 checks не следует автоматически назвать
неполным банком. Причины разницы надо установить. Исправление только CI
selection также не доказывает согласованность обычного author/preview пути.

Это вопросы ownership и lifetime: почему следующий helper удаляет ещё нужный
результат; почему source inventory требует физически собранных common файлов;
можно ли дать единый разрешённый input view без дополнительного authoring
runtime. До ответа нельзя оценивать такой цикл только как «медленный render».

Публичный inventory по-прежнему сообщает `private-input-not-provided / not-verified`.
UPSTREAM ещё описывает старые версии, тогда как providers/toolchain закрепляют
новые. Это расхождение документов, не основание подменить фактические pins.

## 7. Performance уже имеет применимые числовые данные

[Принятый PR5 Performance](https://github.com/Afonenko-Course-Tools/quarto-course-performance/pull/5)
добавил отчёт Cybersecurity с Core 8.2.8, 27 новыми случаями, 24 пригодными
записями Performance и 21 строгим сравнением outputs. Прежний отчёт с отказом
Body 8.2.6 сохранён исторически, а не является итогом новой матрицы.
[Полный отчёт](https://github.com/Afonenko-Course-Tools/quarto-course-performance/blob/883977b809dc5bb4ca17ff34b494f159a7ff3168/docs/releases/2026-10-25-core-8.2.8-cybersecurity.md).

Медианы student/full: 278,584/308,518 секунды в исходной серии и
459,900/487,010 в новой. Это +65,1%/+57,9% для **совокупной** смены четырёх
владельцев, не причинная оценка одного Core. По три измерения, порядок без
рандомизации; одиночные native/summary/cpu случаи не измеряют overhead.
Raw traces/CPU/teacher Body остаются локальными, доступные публичные JSON
сводки уже пригодны для постановки различающих вопросов.

Не повторять эту матрицу ради нового PASS. Сначала сопоставить lineage,
hardware/toolchain, функциональный scope и activation instrumentation с
будущей базой. Cybersecurity без программных project checks не доказывает
время Java qualification или IDE-подготовки.

## 8. Проверяемые кандидаты затрат

Новые и прежние кодовые свидетельства дают кандидатов, но не измеренные
bottlenecks: последовательные qualification scenarios; дополнительный public
Docker job; повторные dependency/payload hashes; полные обходы JDK/Gradle;
fresh Gradle caches и `clean jar`; `Context.recheck`, повторяющий source Git
blob и physical-tree inventory. Нужна декомпозиция полезной работы, защиты,
подготовки и наблюдения с их разными trust intervals.
[Qualification loop](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/tools/check-course.py#L34),
[public job](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/tools/grading_job.py#L311),
[Gradle setup](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/images/java25-grader/grade.py#L772),
[assembly recheck](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/tools/release_assembly.py#L124).

## Что меняется в будущем исследовании

Добавлены B01–B16 и задачи R01–R04: хронология сбоев/решений; lineage и
компоненты выпуска; ordinary/exceptional admission и recovery; lifecycle/CI
и стоимость повторной подготовки. Они используют те же ledgers и единую
очередь, не создают второй runtime или конкурирующий inventory.

Первый отчёт после T00 и авторского этапа должен отдельно показать текущее
состояние выпуска и существенные неизвестные. Нельзя ждать конца исследования,
чтобы обнаружить, что «успешная поставка» в исходных ожиданиях означала только
source Release, public inventory или временный NOT_VERIFIED admission.
