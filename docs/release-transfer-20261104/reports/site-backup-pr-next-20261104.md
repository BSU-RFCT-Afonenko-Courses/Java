# Site850: локальный backup/PR план без запуска CI

Source-only. `site-java` clean, HEAD `85080c730ad54c8fd81fa4a3c7c806a8771c4f68`, local branch `codex/java-release-20261110`; upstream не задан. Origin URL `https://github.com/Afonenko-Course-Tools/quarto-project-publish.git` для fetch/push. В local bare common store нет remote-tracking refs/refs/pull и remote.origin.fetch mapping; есть local codex branches Java850, isolated audience850 и old SP e0efd6. Отсутствие tracking refs **не** доказывает отсутствие remote branch/PR.

Сохранённый root state сообщает remote backup предыдущего d16, но в прочитанных saved reports нет точного remote codex ref/readback или Site PR URL/номера. Поэтому remote branch current HEAD/open PR **UNKNOWN**, не «PR нет». Новых network/gh/ls-remote/fetch/status запросов не делалось.

## Что автоматически запускается

Единственный tracked workflow `.github/workflows/ci.yml:2–6`:

- `pull_request` без paths/types/draft exclusion;
- `push` только branches `[main]`;
- `workflow_dispatch`.

Следовательно создание draft PR тоже не освобождено Source условием от CI, а push в существующую codex branch при уже открытом PR может вызвать synchronize. Простой non-main branch push без связанного PR по этому workflow не задаёт push run. Поскольку существующий PR неизвестен, безопасно не обещать отсутствие CI для push текущей ветки.

Job широкий, хотя Quarto matrix имеет единственный1.11.5: npm ci + Chromium install/browser controls, performance checks, Native planning/pipeline/public-operation/owner-release loops, composition/profiles/paths/collection/formats/domains/preview. Scope job не ограничен четырьмя файлами Site850; общего job timeout в Source нет. Draft сам по себе не является дешёвой рецензией.

Текущие pins старые: Core `c9f23ae0537280e1a94bf2ecc21d6fcbb6766b7c`, QRC `798f8c0d9f87fd5dcfe9ee85b79df57e647d2f3c`, Download `b478893595dee945be3202a57daf982d281017f2`, PL `df8bd4aad274d618a782265b1db9c63e9b9a40f9`. Checkout/assert companions должны меняться согласованно в будущей отдельной integration партии; этот проход pins не трогал и совместимость такого старого graph с Site850 не объявляет.

## Минимальный порядок после решения root

1. Для immediate remote backup без qualification — отдельный **новый** non-main backup ref, не привязанный к PR, ровно на850; например `codex/site-audience-backup-20261104-85080c7`. Перед записью root должен установить отсутствие этого ref/PR по saved exact evidence либо одному адресному metadata lookup. Запись только create-if-absent/без overwrite existing ref; никаких force updates/main/tag/publish. Здесь backup не выполнялся.
2. Draft PR пока подготовить как текст/локальный diff. Если root найдёт уже существующий Site PR — обновлять его после проверки head, не создавать duplicate. Само открытие/обновление PR отложить до готовности потратить один широкий CI run.
3. Перед единственным согласованным PR/CI integration согласовать exact accepted Core/PL/Download/QRC composition и только existing checkout/assert pins. Не вводить вторую CI suite, disable-workflow, skip-CI commit или новый trigger workaround. После review объединить нужный pin companion и Site850 в один planned push/PR action. Qualification/result отдельно; Site не установлен/не опубликован автоматически.

Предлагаемый PR смысл: «Проверять только активную аудиторию Site». Root+children сохраняют active audience/selected Native/physical/output/private guards, убираются шесть alternate-audience inspect; существующие Java/SP profile configs уже разделяют outputs. Сохранность неактивной выдачи при совпадающих/вложенных paths теперь ответственность конфигурации. Existing addressed checks Source850 PASS; whole course/production/new paired CI этим не заявляются. Public URLs/installed pins/реальные outputs не менялись.

Никаких Source edits, pins changes, commit/push/PR/network/render/inspect/hash/CI запусков в этом проходе нет. Mainstate root-owned.

## Последующее действие root

Root адресно прочитал open PR metadata для exact head `Afonenko-Course-Tools:codex/java-release-20261110`: ответ GitHub `[]`. После этого обычный SSH push без force успешно продвинул существующий remote ref `d16f232..85080c7`; новый backup ref не понадобился. Первая sandbox попытка остановилась до соединения на доступе к системному SSH config, разрешённый повтор вне sandbox завершился успешно. Credentials/global config не менялись. Новый PR, workflow dispatch, install и публикация не выполнялись; CI результата для850 этим не заявляется.
