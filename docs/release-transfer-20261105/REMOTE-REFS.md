# Опубликованные результаты 5 ноября 2026

Новые прямые указания пользователя: сохранить все результаты в PR и подготовить начало обсуждения в другой сессии. Код не дорабатывался ради зелёного статуса; прежние dirty изменения зафиксированы как незавершённые. Все PR — черновики, без слияния.

| Пакет | PR | Точный коммит | Статус |
| --- | --- | --- | --- |
| Java, весь учебный пакет и передача | Ветка `codex/course-labs-20261105`, PR создаётся вместе с этим пакетом | Учебный HEAD `859c5f7499bb2e03b3504e5ec35e41d3dd79e368`; текущий HEAD смотрите в PR | Полный экспорт на `8e9d618`, отдельная Download-дельта проверена; общая приёмка открыта |
| Core | [PR72](https://github.com/Afonenko-Course-Tools/quarto-course/pull/72) | `d21ddfdc9e4fde58145275751dccfc1c997777df` | Независимая техническая приёмка |
| PL | [PR37](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/pull/37) | `5862e66dcc96e2a6920ba4fe19f1df2bf0746a94` | Основной `fcd3320` принят; последний коммит сохраняет незавершённые CI fixtures |
| Download, основная работа | [PR20](https://github.com/Afonenko-Course-Tools/quarto-project-download/pull/20) | `de1f3aa859a3d949223b7230a6377a4fc0c3572d` | Технические проверки/рецензии; прежний CI красный |
| Download, дополнение CI | [PR21](https://github.com/Afonenko-Course-Tools/quarto-project-download/pull/21) | `5b1541025157ad4b8f8b01dced1fb78a0ba2eb9b` | Содержит pins `c96e53f` и сохранённый WIP тест; приёмка не выполнена |
| SP | [PR11](https://github.com/BSU-RFCT-Afonenko-Courses/System-Practice/pull/11) | `599c9847bbd3119c19e7f3f4dbdd97b805e3e123` | Миграция путей принята; student CI и Native UI остаются открыты |
| Performance | [PR8](https://github.com/Afonenko-Course-Tools/quarto-course-performance/pull/8) | `86f762e317b237320657a3319c70701d03c72a2e` | Типизация/адресные проверки/рецензия; нет доказательства ускорения курса |
| Platform, интегрированные C и D | [PR36](https://github.com/Afonenko-Course-Tools/prairielearn-platform/pull/36) | `728830780925ac10519db4325065b85152a94b7a` | Технический пакет принят; Native UI ещё открыт |
| Gateway | [PR11](https://github.com/Afonenko-Course-Tools/moodle-prairielearn-gateway/pull/11) | `44f5c2116470e37180050f1eba1ab230ab4794d7` | Технический пакет принят; не общая приёмка Moodle входа |
| QRC | [Исходный PR22](https://github.com/Afonenko-Course-Tools/quarto-reference-catalog/pull/22) | `541a44836cf5ec934a101059cbee77c9ff47d134` | Без новых изменений; совместимость прямых вызовов просмотрена |
| Site | [Исходный PR21](https://github.com/Afonenko-Course-Tools/quarto-project-publish/pull/21) | `2b397815cfdbf0e860405559de68067fe20204b4` | Без новых изменений; Site collect участвовал в полном экспорте |

## Связь с исходной точкой

Java основан на `c2e35e7ed3ec4ca2412dd66c9d71d9ce94e6290c`, [PR33](https://github.com/BSU-RFCT-Afonenko-Courses/Java/pull/33), ветка `codex/release-transfer-20261104`. Старый PR сохраняется. Новый Java PR направлен на эту ветку.

Platform PR36 направлен на сохранённую ветку PR35 `codex/java-release-20261110`, коммит `6616222206d3e0843ebc560af38c2dc3519557f0`. Он включает C feedback `43684896240cd46b6b860300e38ccfb181123107` в истории D; отдельная конкурирующая Platform ветка не нужна для интеграции.

Gateway PR11 основан на `main` / `901624527dce2d37a62fedaa9b768cbbbfd1e366`. Download PR21 — дополнение к ветке PR20, а не замена прежнего PR. Не путать его WIP с принятой функциональной дельтой `de1f3aa`.

## Локальные тестовые образы

- Четыре подтверждённых UI случая: source `b79291184a1c52bd1b2e59247b01d19171b16d0c`, `codex/java-feedback-ui:20261105`, image `sha256:8e9cd1119bd189e7d6723e03fd017b795e6e6e979f6012a181e0ebec57331fc5`.
- Квалифицированный Gradle для настоящего B/D пакета: `codex/java-feedback-gradle:20261105`, image `sha256:474e1845dc16e9a99e416896a83c1dc46a60b3fdc914603bc59067cc02e8f477`.
- Финальная UI-проекция, пока без окончательной UI-приёмки: source `43684896240cd46b6b860300e38ccfb181123107`, `codex/java-feedback-final-ui:20261105`, image `sha256:1f2f4c5ea37b2eab7c7a69ec325802ac9d3dad927af7c767a32f9107d272eadb`.
- Workspace candidate: `codex/sp-workspace-delivery:20261105`, image `sha256:d7eaa4558c866b495371ffd7e478a0a4e1d172f66f8a5eda7f532a0d5aaead34`. Проверен seed/stop/start, не вся Native цепочка.

Это штатные локальные Docker ID, а не новые собственные proof. Образы не публиковались. В Source не подменялись production OCI pins ради локальной приёмки.
