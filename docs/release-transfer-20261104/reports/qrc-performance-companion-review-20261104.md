# Независимое review QRC/Performance companion

4 ноября 2026. Root `gpt-6.1-sol/high`: **APPROVE**. База Performance `10810e482dcecde1eecf2e697cac0e3d628f05fe`; соседняя Event партия независимо принята в `e88c82c`. Прочитаны весь comparator, diff трёх offline tools, current docs и существующий strict consumer `scaling.ts`; [отчёт исполнителя](qrc-performance-companion-implementation-20261104.md).

Удалена сама церемониальная гарантия: private QRC blob execution/receipt и Core HTML normalizer, их специальный integration test и normalization-only cases. Нового replay/flag/parser/framework нет. Java policy больше не сравнивает HTML content; наличие каждого HTML всё ещё проверяется до пропуска. Non-HTML и ZIP entries/распакованные bytes остаются прежними. Инструменты сообщают `checkedContentEqual` и `htmlBytesCompared:false`; `recheck-repeat` явно убирает прежнюю общую `equivalent` из унаследованного результата и сохраняет исходный файл. Strict diagnostic сохраняет буквальную эквивалентность и своего реального consumer.

Это намеренное сужение собственной гарантии, прямо разрешённое исследованием необходимости обвязки. Изменённые HTML ID/href/text этим gate не обнаруживаются; docs и тест не называют их семантически равными. Предметные QRC/owner privacy/role проверки не заменены comparator. Historical reports/manifests сохранены; поддержка старого исполняемого replay не является условием их сохранности. Legacy positional control workspace остаётся на прежнем месте, unused normalizer argument описан.

Показаны affected offline compiler PASS и 4 предметных filesystem/ZIP cases PASS, system Deno2.9.7. Новые case сначала выявили старую гарантию, затем fixture исправлен после `zip -z` failure; unchanged retry не было. Суммарно3.4162s в cap45. Reviewer не повторял зелёные tests: новый вопрос разрешён Source review. Quarto render/fullCI/course speedup и полная HTML/privacy equivalence не заявлены.

Root admission: полный текущий origin interval contexts7160/7983/8022/9120/9348 top/nested Sol6.1/high PASS; глобальные настройки не менялись.
