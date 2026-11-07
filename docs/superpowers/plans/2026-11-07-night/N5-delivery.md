# Типизация выдачи и прогресс Download

По прямому уточнению пользователя от 7 ноября разрешены только `gpt-6.1-sol` и `gpt-6.1-terra`; роли и усилия задаёт обновлённая матрица. Учебный автор и независимый рецензент — разные Sol/high; ключевая архитектура и её независимая рецензия — разные Sol/max. Standard сохраняется.

Вы — N5, `gpt-6.1-sol/high`. Прочитайте [общее поручение](common.md). Владеете тремя каноническими repo: `quarto-project-download`, `quarto-reference-catalog`, `quarto-project-publish`, включая их собственные tools/tests/config. Старые локальные copies не использовать как актуальную базу; refs выдаёт N0.

## Контракт

Core artifact/AST/public types производит N3, PL contracts — N4. Вы производите ZIP/README/link catalog/site; установленные копии, pins и course configs меняет N0. Performance bridge исправляется у N3 в каноническом шаблоне. Старые URL после source-переноса не сохраняются; новые адреса должны быть согласованы во всех consumers.

## Задачи

1. Инвентаризировать весь собственный поддерживаемый active source/tests/tools/entrypoints трёх repo; выбрать строгие проверки по реально используемым языкам. Vendor/imported не редактировать; generated менять через источник.
2. Строго типизировать bytes ownership, audience/public-full, paths/targets, ресурсы и link destinations на общих публичных contracts. Известный `project-download/application/package-artifact.ts` уже использует `unknown → ArtifactFilesSchema.parse`; сохранить эту реальную границу и runtime-защиты.
3. Удалить неиспользуемые маршруты после проверки прямых/косвенных callers/config и перейти вместе с поддерживаемыми consumers на новый API без compatibility. Не создавать второй формат артефакта или собственный HTML parser.
4. Добавить в существующий logger Download стадии чтения/подготовки, готовые страницы, собранные архивы и запись файлов. stdout остаётся предметным выводом, progress — stderr; quiet работает. Счётчики возрастают после завершения операции; ранние стадии показывают начало/конец/ошибку. Проценты времени и дополнительные render/inspect для счётчика не вводить.
5. Получить от N3 семантику «Открытые примеры» и сохранить один раскрытый заголовок в README/HTML; PL-output согласовать с N4. Содержимое тестов и ручные задачи сохраняются.
6. Через N0 проверить ZIP contents, README/resources/links, safe paths и отсутствие преподавательских files/bytes в student, в том числе после перемещения страниц и новой типизации.
7. Заказать N0 штатные checks по свежим refs: Download `bash tools/check.sh`; QRC contracts/native-linking/browser; Site navigation/native-pipeline/paths/profiles/browser. Пакетировать по различающимся случаям, не повторять полный исторический набор на каждую аннотацию.

## Приёмка

Отдельный независимый Sol проверяет код и producer → consumer границы; ключевую межрепозиторную архитектуру — отдельный Sol/max. Передать принятые SHA, полный inventory/coverage, выводы проверок и остаток. Прогресс подтверждает наблюдаемость работы, а ускорение требует сопоставимого измерения всей цепочки и не обещается выводом logger.
