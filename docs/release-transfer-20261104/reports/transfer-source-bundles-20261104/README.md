> Позднее Root успешно отправил актуальные Core и Site: PR71 иPR21, см. ../../REMOTE-REFS.md. Отказы ниже — сохранённая история. Эти bundles — резерв и отдельные archival WIP.

# Перенос двух веток Source на другое устройство

Прямой SSH push этих двух веток завершился закрытием соединения GitHub на порту 443. HTTPS для Site отказал из-за отсутствия `workflow` scope, для Core — TLS EOF. Этот комплект сохраняет точные коммиты без изменения исходников и без новой квалификации курса.

| Репозиторий | Ветка | HEAD | Сохранённая база истории |
| --- | --- | --- | --- |
| Afonenko-Course-Tools/quarto-course | codex/core-native-bridge-20261110 | 010ba917909fa7d44179d4a1a05291f88f061775 | a478d2cc0dfb19e0bebd99108815ac8ef1fe85a8 |
| Afonenko-Course-Tools/quarto-project-publish | codex/site-native-audience-20261110 | 2b397815cfdbf0e860405559de68067fe20204b4 | e8b345cec235c2c8438c479892f6bca2a4ce3ee7; дополнительная граница e0efd6b5428412500569d0da5a5e0b33d62a5663 |

Предполагаемая база будущих draft PR — фактический default `main`. Ветка Core на GitHub пока содержит 5f4590aa4eb524830715f8020c8ff3c504197f73; точный ref Site на GitHub отсутствует. PR70 относится к другой ветке и сохраняется отдельно.

## Файлы и восстановление

Для каждой ветки сохранены `.bundle`, `.shallow` и `.tar.gz` с общим префиксом. Архив создан штатным `git archive HEAD` и содержит только отслеживаемые файлы конкретного HEAD. Исходные репозитории shallow; поэтому `.bundle` требует соседний файл границы `.shallow`. Один bundle без него недостаточен: это установлено реальным отказом fetch в пустой bare репозиторий.

Пример восстановления Core из каталога комплекта:

```bash
git init --bare core-restored.git
cp core-native-bridge-010ba917-20261104.shallow core-restored.git/shallow
git -C core-restored.git fetch "$PWD/core-native-bridge-010ba917-20261104.bundle" refs/heads/codex/core-native-bridge-20261110:refs/heads/codex/core-native-bridge-20261110
git clone --branch codex/core-native-bridge-20261110 core-restored.git core-restored
```

Для Site используются префикс `site-native-audience-2b397815-20261104` и ветка `codex/site-native-audience-20261110`. Обе последовательности `init`, перенос границы, один `fetch` проверены в отдельных пустых bare репозиториях; восстановленные HEAD точно совпали с таблицей. Дополнительные Source tests, сборка и загрузка истории из сети не запускались. Недоступная ранняя история остаётся за сохранённой shallow границей.

Архивы `.tar.gz` позволяют независимо получить отслеживаемое дерево HEAD без Git. Содержимое архивов читается штатным tar; состояние installed providers, полный Native export Java/System Practice и production этим комплектом не подтверждаются.

После наблюдения координатором восстановления SSH-связи выполнена ещё одна явно разрешённая попытка для каждого ref с ConnectTimeout 12 секунд и полным timeout 30 секунд. Обе завершились exit128: `Connection closed by 20.233.83.149 port 443`. Последующих повторов не было; архивный комплект остаётся способом переноса этих двух HEAD.

## Архив прежних кандидатов WIP

Актуальные основные Source остаются Core `010ba917` и Site `2b397815` из первой таблицы. Следующие два комплекта сохраняют прежние отдельные направления со статусом **superseded NOT QUALIFIED**. Они не выбираются как основные refs и не подтверждают новую сборку.

| Префикс файлов | Ветка | HEAD | База checkpoint |
| --- | --- | --- | --- |
| core-superseded-wip-d8cf30e-20261104 | codex/java-release-20261110 | d8cf30edff7e924217858e83356c40cf19ec38b2 | 46b35909c9878196294f54b6be2d7505b336fe8d |
| site-sp-superseded-wip-dcb59dd-20261104 | codex/sp-native-render-20261110 | dcb59dd34c05479933cebd8eb3025a70ea573344 | e0efd6b5428412500569d0da5a5e0b33d62a5663 |

Для каждого префикса присутствуют `.bundle`, `.shallow`, `.tar.gz`. Восстановление выполняется как в примере выше с соответствующими префиксом и веткой. Для обоих комплектов проверены отдельный пустой bare репозиторий, перенос сохранённой shallow границы, один `git fetch` и точное совпадение HEAD. Архивы созданы штатным `git archive HEAD`; Source, индексы и refs исходных репозиториев не изменялись.
