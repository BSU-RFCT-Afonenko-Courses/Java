# References и происхождение решений

Официальные источники просмотрены 27 октября 2026 года. Native поведение сопоставлено с установленным Quarto 1.11.5, а не только с сайтом. Сходство с внешней системой не считается доказательством заимствования.

| Первичный источник | Применимый механизм | Граница сравнения |
| --- | --- | --- |
| [Quarto profiles](https://quarto.org/docs/projects/profiles.html) | Именованные views конфигурации проекта | Не заменяет materialization/checks contract. Profile arrays/hooks имеют native merge semantics, которые надо проверить перед заменой overlay |
| [Quarto scripts](https://quarto.org/docs/projects/scripts.html) | Input/output lists и render-all; пересчёт metadata после pre-render | Installed bundle подтверждает env в строках 137610–137623/137992. Эти facts могут сузить hook scope, но не доказывают currentness после произвольной внешней mutation |
| [Quarto includes](https://quarto.org/docs/authoring/includes.html) | Один канон включаемого текста | Relative paths считаются в контексте включающего документа; перенос условия/API требует fixture, а не механического копирования |
| [Quarto project options](https://quarto.org/docs/reference/projects/options.html) | Render/resources и preview watch-inputs | Полезны для canonical/workspace selection; не создают teacher projection, безопасный ZIP или qualification |
| [Pandoc Lua API](https://pandoc.org/lua-filters.html) | AST reader/writer внутри процесса | Кандидат для fragments вместо процесса на каждый choice. Quarto shortcodes и extended AST не приравниваются к bare Pandoc без equivalence |
| [Deno subprocess](https://docs.deno.com/api/deno/subprocess/) | Явные argv/cwd/env и child process | Устраняет необходимость shell evaluation; не обеспечивает межпакетную schema/API совместимость |
| [Ninja manual](https://ninja-build.org/manual.html) | DAG, prerequisites, depfiles, restat, pools | Сравнительный образец reuse/jobs, не выбранный build engine. Source closure/privacy остаются у владельца |
| [PrairieLearn Workspaces](https://docs.prairielearn.com/workspaces/) | Persistent remote workspace, images, initial files и gradedFiles | Возможность upstream не доказывает готовность pinned host. Полный source-level разбор и ограничения находятся в system-practice.md |
| [pl-workspace](https://docs.prairielearn.com/elements/pl-workspace/) | Штатный элемент открытия рабочей среды | Не заменяет grading transport и валидатор результата |
| [PrairieLearn external grading](https://docs.prairielearn.com/externalGrading/) | Отдельная отправка работы внешнему grader | Save редактора не равен submit/grade. Workspace image и grader image имеют разные роли |

## Внутреннее происхождение

Core NativeRun, source context и owned overlay прослежены по выбранному owner source и контрактам. Линия 8.3 продолжает 8.2.7; наличие batch Promise extraction проверено непосредственно. Поздний actual-writer fix другой 8.2.8 линии не переносится по номеру версии. Подробнее — owner matrix и core audit evidence.

Материализация/receipt/inventory связывают student/public inputs и закрытый teacher root. Эта полезная гарантия не оправдывает автоматически частоту всех FS/SHA/inspect операций. Caller lifetime рассматривается отдельно.

Platform assembly разрешает конкретные registry/lock derivations из exact source/assets. Host tools и OCI обновляются независимо. Это owner contract и проверенный tuple, а не требование одинаковых номеров пакетов.

Performance ABI 1 — проектный facade, а не обязательный runtime profiler каждого расширения. TypeScript Compiler API используется в диагностическом transform; Lua debug hooks принадлежат trace backend. Их существование не описывает обычный render.

Third-party реализации и vendor source mappings отмечены inventory. Новый copied upstream код в продукт не добавлялся. Заимствование конкретной реализации потребует отдельного выбора версии и проверки лицензии; здесь это не выполнялось.
