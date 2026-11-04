# Ordinary publication: ограниченная поведенческая приёмка

**Пока не запускалась.** Следующий конкретный шаг root после независимого Source review и PASS изменённого общего compiler graph. Это не full-course qualification, не benchmark и не production.

Используется ровно существующий `site-native-audience/tests/site-owner-release.ts root-pl`, одна top-level invocation. Fixture заранее создаёт два независимых владельца: root c active PL без Download и tasks c active PL+Download. Конфигурации фиксированы до render, capture между ветвями не подменяется. Проверяются real owner/course/source coverage, конечный ZIP/README, mounted URL, anchors/resource suffix и отсутствие student solution/author files. Это не отдельная матрица ветвей.

Все входы — собственные временные авторские fixtures; не читать DB, live config/mounts/studentfiles. Actual Source: Core `core-native-bridge`, PL `prairielearn`, Download `download`, QRC `qrc`, Site `site-native-audience`; никакой установки в Java/SP или production. Fixture copies extensions только в собственный tempdir. До исполнения сохранить actual heads + scoped dirty paths, не хешировать деревья. Historical captures остаются.

Общий hard cap60s включая завершение, retry0: process group TERM после57s, KILL после3s при необходимости; полные stdout/stderr и elapsed/exit/timeout сохранить. Новая общая supervision библиотека не создаётся. QUARTO_RUN_NO_NETWORK=true, existing Quarto cache; никаких install/network/CI/grading/seed/JDK/build/full Java/SP. Результат Native берётся из actual fixture assertion/exit, внешний timeout не выдаётся за Native failure. Сигнальные Performance бюджеты этим не возобновляются, незавершённый Performance runner не используется.

При первом отказе/timeout сохранить причину и остановить опыт без автоматического retry/разбиения на подматрицы. Измеренное время относится к этому fixture; ускорение курса из него не выводится. Source/API полная публикация и actual runtime этой узкой ветви отмечаются отдельно. NoPL/inactive/course-export/Platform/Gateway/cache freshness/закрытие при ошибке данным единственным запуском не квалифицируются.

## Фактический результат первого и единственного запуска

**BLOCKED / runtime NOT QUALIFIED.** Exit1, elapsed5.729529888s, timeoutfalse, без TERM/KILL и повторов. Source-independent reviews APPROVE и affected compiler PASS сохраняют отдельный смысл; они не доказывают поведение публикации.

Fixture directory сохранён: `/tmp/site-b3-owner-f08801cdb7dd7130`. Полные [stdout](native-publication-bounded-acceptance-20261104.stdout.log), [stderr](native-publication-bounded-acceptance-20261104.stderr.log), [Native log](native-publication-bounded-acceptance-20261104.native.log), [machine result](native-publication-bounded-acceptance-20261104.json), [heads/dirtypaths](native-publication-bounded-acceptance-20261104-source.json).

Root и child planning прошли, Core child pre выбрал2inputs. Первый child index.qmd остановился в штатном Quarto Sass cache: `Deno.openKv → sassCache → compileWithCache`, `unable to open database file`. До новых ordinary publication/ZIP assertions выполнение не дошло. Причина отказа файла точно не установлена Nativeerrorсообщением; это не установленный дефект нового publication API.

Адресное чтение actual `/opt/quarto/bin/quarto.js`80222–80229/87867–87869/36573–36575 подтвердило persistent cache branch `$XDG_CACHE_HOME/quarto/sass`, default `$HOME/.cache/quarto/sass`. XDG_CACHE_HOME не задан; `/home/tolya/.cache/quarto/sass` существует, но лежит вне writable roots текущего sandbox. Поэтому невозможность записи KV в этом окружении — обоснованная гипотеза. Не выполнялись probe writes, смена прав, очистка/перенос существующих caches, повторный render или расширение Native budget. Session32240 завершена, незавершённых дочерних операций после обычного отказа fixture не заявлено.

Следующее возможное ограниченное действие — согласовать среду будущего Native acceptance с доступным writable XDG cache; это отдельная явно назначенная попытка со своим лимитом, без выдачи первого отказа за успех и без переписывания реальных авторских cache/freeze policy. Этот пакет не создаёт общего cache механизма.

## Отдельно назначенный corrected-context запуск

Root исправляет собственный пропуск XDG_CACHE_HOME по уже принятому SP evidence (`sp-shared-site6-stage-integration-20261104.md`:45): existing `/home/tolya/JavaCodex/.cache`, без очистки/новой cache policy. Source остаётся тем же принятым local candidate. Отдельный changed-context запуск той же existing root-pl invocation получает только остаток общего cap60: 54.270470111994655s включая3sgrace; prior5.729529888s сохранён, budget не сбрасывается. Это не unchanged retry; после этого запуска автоматических повторов нет. Performance signals budget не меняется.
