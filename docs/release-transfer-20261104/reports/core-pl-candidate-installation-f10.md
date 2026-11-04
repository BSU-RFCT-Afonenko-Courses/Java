# Адресная установка Core f10

3 ноября 2026. Root разрешил установку после независимого ACCEPT в `core-registered-shortcode-review.md` для332ea585..f10c4e65. Один вызов существующего `core-pl-candidate-installation.py --core-commit f10c4e6527b1834651d59a95a0ef4373357cd8f0 --write` завершился exit0; wall tool0.137464551s. Отдельный preflight не запускался. Скрипт не менялся.

## Собственный допуск

Назначение Sol6.1/medium, Standard. Собственный chat01a1a253-e067-7583-bf86-ecc79bd9b33f, path `/root/install_shortcode_candidate`, исходный ход01a1a253-e09d-7bd0-b06a-09436efd3df6. Первичный rollout указан в `core-pl-candidate-installation-f10-model-evidence.json`: session_meta строка1 и turn_context строка8; верхние и nested поля Sol6.1/medium согласованы. Service tier отсутствует; Standard назначен root, скрытая серверная маршрутизация не аттестуется. Глобальные настройки и нормативные документы моделей не менялись.

## Установка и проверка

Core installed332ea585d7b09f07c3f3886943d58a97a528078a → f10c4e6527b1834651d59a95a0ef4373357cd8f0. PL exact3a6c1c14254f1ca547c3bc2577d80007c83bdcf0 сохранён; live dirty PL Source не читался. Published base/version/tag/sourceZip сохранены.

Продуктовая дельта состоит ровно из `document/assignment-producer.ts` и `native-ts/early-context.lua` в `_extensions/Afonenko-Course-Tools/course-core/` каждого scope: корень `.`, `theory`, `tasks`, `lectures`, `practice`, `handbook` обоих курсов. Всего24 адресных атомарных замены. В каждом курсе также изменены только `providers.json`, `installed-packages.json`, `tools/provider-patches/core-release-20261110.patch`:15файлов на курс. Java UI file rows и bytes сохранены, общая candidate metadata синхронизирована по существующему контракту. SP core-ui не менялся. PL files/patch/manifest/lock и прочие providers не изменились.

Existing script проверил bytes/mode затронутых targets до и после записи; неизменённые vendor files не обходились. Concrete manifest/lock diff прочитан. Git diff-check и cached diff-check исключая cumulative patch PASS. Patch exact bytes совпали с Git diff published-base→f10 в обоих курсах; исторические blank context строки unified diff сохранены. Actual unstaged/staged path set точно совпал с разрешёнными15files перед commit.

- Java installation commit: `38cb70176c3f4c1abfcbcc974fe03e90c4198ba7` поверхbbd92a82ff73ac4594daa0aaa513eeae6269393a.
- SP installation commit: `e579e0e0bfe20403f6b07df2f04a64e5e6c5d7d8` поверх66c4c5110b03e7ac9e9132eff6b9f0bfff0b6190.

Сообщение обоих commits: «Установлен Core f10c4e65 с допуском зарегистрированных shortcode». Root release-cycle не stage/commit. Java pipeline, hash/inventory/rebuild, дополнительный render/inspect, push и PR не запускались. Машинный результат: `core-pl-candidate-installation-f10-result.json`. Прежние отчёт/JSON сохранены как `core-pl-candidate-installation-332-history.md` и `core-pl-candidate-installation-332-history-result.json`.

Один разрешённый SP native attempt фиксируется отдельно в `sp-native-f10-execution.json` и `sp-native-f10.log`; он определяет фактическую native приёмку, installation success её не заменяет.

После единственного SP attempt оба git status clean; native результат exit1 за28.143593671s, отказ финализации OPERATION_CHANGED/labs, описан в `sp-native-f10-acceptance.md`. Перед передачей повторно проверен весь исходный model interval: единственный turn_context8 Sol6.1/medium согласован.
