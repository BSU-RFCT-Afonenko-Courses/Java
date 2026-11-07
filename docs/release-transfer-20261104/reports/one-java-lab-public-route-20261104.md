Одну настоящую Java лабораторную существующий публичный CLI выбирает для выдачи, но **не ограничивает ею предварительный capture/render**. Поэтому ограниченный запуск `sec-lab-numbers` сейчас не подготовлен: он снова затронет всю книгу. Ничего не запускалось, не копировалось и не изменялось в Source. Native стоимость сохранена: 18.833749249/40 s; остаток 21.166250751 s.

У `export-course.ts` нет флага `--work`: он выбирает instance с десятью labs. Отдельный публичный `export.ts` принимает позиционно `COURSE_ROOT BOOK WORK BINDING OUTPUT [FUNCTIONAL_PROFILE...]`; для этой работы WORK — `sec-lab-numbers`. Это выбор assessment, а не Quarto input. Точный исполнимый argv ограниченного экспорта дать нельзя: соответствующего публичного selector нет; standalone binding также нельзя заменять выдуманным JSON.

Порядок существующего пути: Core snapshot всего owner → наблюдение owner → все `observed.files.input` становятся `expectedSources` → выбирается assessment → lease вызывает `render --output-dir _generated/prairielearn-native-render` без списка документов. `full` меняет проекцию, а не chapters; существующий `ci-checks` задаёт широкий glob. Поэтому ожидается весь configured book из сохранённой карты 128 QMD, включая `labs/numbers.qmd`, `bank/numbers.qmd`, остальные labs/banks/units/essays. Ровно два входа «работа+банк» публичный route не задаёт. Фактический разрешённый список без нового inspect не утверждается. Старый 900-секундный отказ не переизмерялся.

Нужны реальные installed Core/PL/QRC/Download, navigation/presentation/Site hooks и сохранённый внешний QRC catalog. Переменные CORE/PL сами не переключают installed lookup. Прежний контекст: QUARTO=/opt/quarto/bin/quarto, его bin в PATH; canonical DENO_DIR/XDG_CACHE_HOME; QUARTO_RUN_NO_NETWORK=true, DENO_NO_PACKAGE_JSON=1. Для автоматических Java вопросов нужен существующий runtime registry из прежней карты. Эти зависимости не устраняют отсутствие ограничения capture. Private builder, subset-книга, новая API или изменение hooks не предлагаются. Собственный исходный Sol6.1/medium interval продолжен, PASS.

Источники:

- [Публичный standalone argv](../../prairielearn/_extensions/course-prairielearn/entrypoints/export.ts#L5).
- [Все owner inputs перед выбором assessment](../../prairielearn/_extensions/course-prairielearn/application/native-entry.ts#L96).
- [Фактический whole-owner child argv](../../prairielearn/_extensions/course-prairielearn/application/native-entry.ts#L286).
- [Snapshot до наблюдения/selection](../../core-native-bridge/_extensions/course-core/runtime/assignment-admission.ts#L196).
- [Настоящая book mapping](../../java/tasks/_quarto.yml#L63).
- [Существующая карта входов/providers/registry](java-native-readiness.md).
