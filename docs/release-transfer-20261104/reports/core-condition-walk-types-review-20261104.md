# Core condition walk — независимый review

Root: **APPROVE scoped**. Прочитаны весь condition.ts, changed diff и сохранённый
writer report/check outcome. Source reviewer не писал; новых запусков нет.

visit принимает unknown. Tag/property checks и индексирование сохраняют
потребляемое c[2][0] для объектов/массивов/строк, включая прежний inherited-property
доступ; functions не превращены в фиктивную Native модель. Непустой string URL
по-прежнему обязателен для Link/Image. Остальные unknown узлы рекурсивно обходятся
в том же порядке через unknown-valued local views; закрытого Pandoc union и
casts не появилось.

Остальные bound/decode/clone/freeze/sort/capability действия неизменны. Это не
переобоснование их необходимости: этот технический долг остаётся отдельным.
Valid Native семантика/roles/выдача не меняются. Прямые Proxy/prototype side effects
не заявлены универсальным эквивалентным входом JSON corridor.

Existing pure condition-selection-a2 PASS0.3655s и один affected bundled compiler
PASS3.1365s, total3.5025s/cap10/retry0. Новых cases, Native/whole suite/benchmark
нет. Блокирующих Source findings нет; full Core coverage и новый full CI этим
срезом не объявлены пройденными.
