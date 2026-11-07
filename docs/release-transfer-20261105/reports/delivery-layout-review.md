# Адресная рецензия новой дельты submission.layout

Итоговый вердикт по новой layout-дельте после исправления Platform `a65d7e910d06ab35de0c6a47ba4835456f076b6c` и при неизменном Gateway `44f5c2116470e37180050f1eba1ab230ab4794d7`: **готова в проверенном объёме**. Единственная Important, найденная на Platform `b76b82728ef5292f89a4293b2d2a8a6dca51516b`, закрыта адресной повторной рецензией. Critical и Minor в этой дельте не найдены.

Объём: новая upload-only optional `submission.layout` (`files`, `zip`, `files-or-zip`) в delivery v5; Platform `bridge/delivery.mjs`, `tools/delivery_verification.py`, их новые tests; Gateway `config.py` и новые tests. Изучены точные реальные файлы и дельта. Предыдущие исправления пакета D целиком не пересматривались. Product files, index и HEAD не изменялись рецензентом.

Рецензент — чат `01a1a8c4-d73d-7aa1-9329-d4826606af6f`. При возобновлении и перед этим вердиктом повторно проверен весь первичный модельный интервал от неизменного исходного turn `01a1a8c4-d7de-7bc2-b21d-46fb1a56d969`: два turn_context, верхние и nested model/effort `gpt-6.1-sol/high`, без Fast/Ultrafast override. PASS сохранён в [delivery-review-admission.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261105/reports/delivery-review-admission.json).

## Important

**Различие обязательного и добровольного wildcard теряется при проверке files-or-zip.** Platform `tools/delivery_verification.py:188` объединяет `file-patterns` и `optional-file-patterns` в одну `patterns`; условие `:199` принимает оба варианта как одинаковые. Прямой новый договор B требует именно `<pl-file-upload optional-file-patterns="*.java" optional-file-names="ARCHIVE">`. Если заменить `optional-file-patterns` на `file-patterns`, фактический Native control меняет required/optional семантику, но package_integrity возвращает success. Следовательно, существующий regression не различает согласованный вариант с отдельным ZIP и вариант с обязательным wildcard.

Воспроизведение: существующий actual package test `test_actual_free_and_zip_upload_controls_use_runtime_union_policy` выполнен на временном fixture с единственной заменой атрибута `optional-file-patterns="*.java"` → `file-patterns="*.java"`. Тест остался PASS. Журнал: [delivery-layout-required-pattern.log](delivery-layout-required-pattern.log). Это source/контрактное воспроизведение; фактический browser rendering/submit не утверждается.

Малая правка: держать required patterns и optional patterns раздельно; для `files-or-zip` требовать отсутствие required patterns и ровно `['*.java']` optional patterns, optional archive по реальному descriptor. Добавить отрицательный контроль с указанной заменой атрибута. Для `zip`/`files` patterns остаются запрещёнными. Общий manual free upload договор `file-patterns="*" optional-file-patterns="*"` сохраняется.

## Что подтверждено

- Все три consumers принимают три значения layout, неизвестное значение отклоняют; editor field запрещён. Отдельная closed workspace submission schema поля layout не допускает.
- Без layout прежние cardinality/optional-primary проверки остаются в отдельной ветви. Новый путь не меняет runtime union/API/required-primary policy.
- `zip` сверяет обязательное имя с реальным `additionalJavaSources.archive`, не с выдуманным `Main.java`/фиксированным именем архива. `files` сверяет fixed filename set с binding sources.
- Platform: 9 целевых Python tests PASS, 0.170 s (`delivery-layout-python.log`). Direct Node `node tests/delivery5.test.mjs`: 4 tests PASS, около 0.06 s. Запуск `node --test` дал только file-level summary, поэтому вывод о четырёх assertions основан на direct Node запуске.
- Gateway: `tests/test_delivery5.py` — 13 PASS, 0.14 s (`delivery-layout-gateway.log`); 5 прежних предупреждений о dependency/deprecation, ошибок нет.

## Исключено из этой рецензии

Предыдущая proposed `tree == delivery.files` проверка отклонена прямым ruling root: список files служит функциональному чтению/копированию, а не сертификатом полноты/неизменности дерева. Adversarial unlisted file без фактической утечки в B writer не доказывает нарушение роли/публичности. Эта прежняя finding не применяется как блокер; containment/symlink/regular-file/manual-no-private-tests остаются действующими проверками.

Новая дельта не требует заново принимать весь пакет, C runtime матрицу, все задачи/веса курса, установленный Native browser/DB, Docker либо production. Их PASS не заявляется и отсутствие таких новых прогонов не является замечанием к этой дельте. После исправления required/optional patterns достаточно проверить различающий контроль и затронутую layout ветвь.


## Закрытие находки на a65d7e9

Точный исправляющий коммит: `a65d7e910d06ab35de0c6a47ba4835456f076b6c`, только `tools/delivery_verification.py` и `tests/test_delivery5.py`. Проверены эти две дельты. В текущем HEAD `79abcf3` они совпадают с точным исправлением; посторонняя дельта C не рецензировалась повторно.

Required patterns и optional patterns теперь собираются раздельно. В ветви files-or-zip обязательные patterns запрещены, разрешены только optional `['*.java']`; реальное optional archive сверяется с descriptor. В zip/files обе группы patterns по-прежнему запрещены. Старое поведение manual free upload и отсутствие layout не изменено этой правкой.

Новый отрицательный контроль требует JobError при подмене optional-file-patterns на file-patterns. Авторский RED `delivery-layout-pattern-red.log` прочитан: `AssertionError: JobError not raised` на прежнем поведении. Рецензент самостоятельно исполнил актуальные 9 tests `test_delivery5.py`, включая этот отрицательный контроль и положительные files/files-or-zip/zip: **9 PASS, 0.156 s**, журнал [delivery-layout-fix-review.log](delivery-layout-fix-review.log). Новых находок в этой точной исправляющей дельте нет.

Перед итогом повторно проверен весь первичный модельный интервал от прежнего исходного turn: три turn_context, верхние и nested `gpt-6.1-sol/high`, PASS; запись `layout_fix_final_full_original_interval` в admission. Снятая root membership finding не восстановлена. Этот вердикт ограничен новой бизнес-дельтой layout и не расширяется до прежних пакетов либо production.
