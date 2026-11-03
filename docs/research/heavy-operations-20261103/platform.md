# Карта лишней фиксации и тяжёлых операций Platform / Gateway / project scripts

3 ноября 2026 года. Только чтение Source и сохранённых исследований. Новые tests/builds/benchmarks/render/grading, обходы и хеширование установленного JDK или репозиториев не выполнялись. Выполнены адресное чтение кода/metadata, получение публичных исходников и разбор AST для сравнения названных функций двух ref. Это не проверка скорости и не приёмка runtime.

**Главное:** источник расходов шире прежних 7 полных verify Gradle+JDK. В official Java каждый `_run` с hardened Java окружён двумя `_grading_boundary()`: каждый читает/hash 173 750 940 bytes пяти core JDK files, обходит metadata complete JDK, `/proc` и запускает пять canary subprocess. Одни четыре version JVM двух native preflight дают ещё 8 boundary / 1 390 007 520 номинальных hash bytes / 40 subprocess. Это числа из существующего lock и прямой цепочки вызовов, не физическое чтение диска и не замер. Вынесение immutable installation work и удаление повторов — прямой кандидат; прежние guard-комментарии не превращены в неприкосновенную норму.

**Исходники и применимость:**

- Основной Platform Source: `865d01705f8e9acee59de09661d5c00c290f1583`, полная существующая копия R4. Java и SP закрепляют Platform `626b665d1c8d18b61480ce91c7bc8f7d41f99bdb` / 2.5.2 и Java image `sha256:17122d53f52500f817a5ca9e554bd92ca276b0284972e69f887be7e2cf9e1278`. Для `grade.py` оба Git tree указывают один blob `ea18441f038e8e8d61c793afa1271e6aa8f03b02`; ключевые `grading_job.py` функции структурно равны как AST при разных строках. Для прочих файлов совпадение/отличие Git blob указано в [platform-coverage.json](platform-coverage.json). Наличие Source механизма не доказывает его фактическую установленную конфигурацию: actual OCI contents и server ref **UNKNOWN**, контейнеры не читались.
- Java checkout: `dd5b76a0168133f0760ece6cb21f4bdf4f6203bb`; пользовательские деревья и untracked R0–R6 сохранены. SP: exact PR9 head `fcc5a2ac1eff3760d306a337a9c8871780365488`; полный публичный архив получен один раз, извлечён в `refs/System-Practice-fcc5a2ac1eff3760d306a337a9c8871780365488`. Хеш архива специально не вычислялся. Root разрешил binary codeload fallback после ограничения GitHubtools; обычный DNS был закрыт, штатная сетевая escalation одобрена.
- SP old installed pins: Core9.1.0 `04c189fccfaa56badfc079f7be46fea4969ccf37`, C9.0.1 `df8bd4aad274d618a782265b1db9c63e9b9a40f9`, Download3.3.1 `e4ce084d8d15381368def66c746b51c6ba810a7d`, Publisher5.3.0 `e0efd6b5428412500569d0da5a5e0b33d62a5663`, QRC3.2.0 `798f8c0d9f87fd5dcfe9ee85b79df57e647d2f3c`. Эти версии не приравниваются к Java Core9.3.4/C9.0.7/Download3.5.0/Publisher6.0.1; installed extension internals отдельно читают audit_core/audit_export по переданному полному SP пути.
- Gateway public main: `901624527dce2d37a62fedaa9b768cbbbfd1e366`, адресные копии `refs/gateway`. Server-installed Gateway/Moodle version **UNKNOWN**. Настоящие пользователи, ответы/оценки, credentials и DB содержимое не читались. В прочитанном Platform/Gateway source дампов/restore не найдено; stock Moodle upstream source и его сторонние плагины этим аудитом не покрыты.
- Предыдущие установленные 7×verify/двойная compile и проверка порядка API/JAR приняты из R4/native-build-cost-note.md и R4/cross-check-r2-r3.md без повторного опыта. Ссылки в этом отчёте ведут на точный ref и начальную строку; указанный scope отделяет response, request, export, qualification и release-only.

**Порядок разбора:** сначала P01–P04 для цены каждого Java ответа; P07–P11 для повторных full reads в course export/qualification/install; затем P16/P18/P19/P21/P23 и фоновой P14 по реальной частоте. Для любого пункта wall time и production volume остаются UNKNOWN, если не указана точная кодовая граница. Ниже 24 группы операций, не реестр grep-совпадений. Поле «требование» отделяет буквальную норму от реализации; нормативные противоречия сводит root.

**P01. ответ: official Java. Приоритет 1 — первым разбирать повтор неизменного.**

Source: Platform, 865d017 (grade.py blob = 626b665d). [images/java25-grader/grade.py:286](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/java25-grader/grade.py#L286), [images/java25-grader/grade.py:354](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/java25-grader/grade.py#L354), [images/java25-grader/grade.py:463](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/java25-grader/grade.py#L463).

Цепочка: grade → preflight/_execute/_JobRuntime → _run → _grading_boundary (до/после) → _build_tree_state + _hardened_tool_state + _build_process_records + 5 canary _run_unchecked; native build/intact также вызывает boundary:2133,2173.

Что/объём: Каждая boundary: SHA256 пяти JDK core files = 173750940 bytes по уже существующему source lock; rglob+lstat всего BUILD_JDK, /proc status/stat, 5 subprocess runuser/Landlock/test. Не физические disk reads и не wall time.

Частота: 2 на каждый _run, содержащий hardened bin/java при official; ещё 1+6 boundary полного успешного native grade. Только 4 version JVM в двух preflight дают 8 boundary, 1390007520 hash bytes и 40 canary subprocess.

Назначение: Поймать изменение защищённых инструментов/ролей и доступ sbuser к complete build JDK.

Что убрать/перенести: Перенести полное содержимое JDK и проверку неизменной установки в installation/image admission; оставить проверки реально меняющейся границы при переходе build→grading. Повтор полного core hash и пяти canary вокруг каждого version/compile/Transform — самостоятельный кандидат на удаление.

Буквальное требование: Точное сохранение source/image pins и настоящий JDK25 требуются общими инструкциями; буквального требования читать 174MB и запускать пять canary вокруг КАЖДОЙ JVM не найдено. Эти guards — реализация.

**P02. ответ: Java preflight. Приоритет 1 — первым разбирать повтор неизменного.**

Source: Platform, 865d017 (тот же grade.py у 626b665d). [images/java25-grader/grade.py:548](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/java25-grader/grade.py#L548), [images/java25-grader/grade.py:2574](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/java25-grader/grade.py#L2574), [images/java25-grader/grade.py:2599](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/java25-grader/grade.py#L2599).

Цепочка: grade→preflight; native finish→verify→preflight повторно; preflight→runner hash + bundle rglob/hash + sharedFiles hash + helper verify + java/javac -version.

Что/объём: Весь runtime bundle, shared dependencies и runner; ещё две JVM на каждый preflight, каждая с P01.

Частота: 1 preflight для обычного grade; 2 для native grade, включая early finish. Внутренний public verification — отдельный grade.

Назначение: Сверка runtime closure, JDK25 runtime/compiler, helper.

Что убрать/перенести: Валидировать статический bundle и версии при построении/установке image, переиспользовать результат для заданного immutable image. Повторный finish preflight не добавляет новой предметной информации.

Буквальное требование: Настоящий JDK25 и отделение tool failure от student error обязательны; именно per-grade JVM version-команды и двойной hash closure не заданы.

**P03. ответ: Java подготовка trusted runtime. Приоритет 1 — первым разбирать повтор неизменного.**

Source: Platform, 865d017 (совпадающие runtime/helper blobs с 626b665d, кроме Assertions.java). [images/java25-grader/grade.py:925](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/java25-grader/grade.py#L925), [images/java25-grader/grade.py:978](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/java25-grader/grade.py#L978), [images/java25-grader/grade.py:1063](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/java25-grader/grade.py#L1063), [runtime/java25-junit-v1/feedback/Transform.java:220](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/runtime/java25-junit-v1/feedback/Transform.java#L220), [runtime/java25-junit-v1/feedback/module/org/prairielearn/feedback/internal/State.java:137](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/runtime/java25-junit-v1/feedback/module/org/prairielearn/feedback/internal/State.java#L137).

Цепочка: grade→новый _JobRuntime (copy JARs, пустые dirs); ensure_adapter→observerCompilation + transformerCompilation + runnerCompilation; prepare_observer→copy observer + 3 Java Transform; State.bind→hash JUnit/adapter/trusted classes.

Что/объём: 3 compile JVM для immutable helper sources каждый job; свежий compiler.jsa; 3 Transform JVM, проходы .class и hashes, policy; bind повторно читает JUnit и classes. Projection loop заново хеширует tests для каждого admissions.types:1138.

Частота: ensure_adapter один раз внутри данного _JobRuntime, но _JobRuntime создаётся заново для grade. prepare_observer один раз на implementation execution; State.bind один раз в JUnit JVM, не на каждый assertion.

Назначение: Доверенный feedback observer, защита trusted source/class identity и независимость от student code.

Что убрать/перенести: Собрать immutable adapter/observer/transformer и статические adapter/reader transform на image installation. Оставить работу над действительно текущими trusted test/student outputs. Вынести одинаковую tests hash-проверку из цикла projection admissions.

Буквальное требование: Предметное наблюдение assertions может быть нужно; запрет переиспользования helpers между jobs в комментарии compiler() — текущая реализация, не найденное прямое пользовательское требование.

**P04. ответ: native source/recipe/artifact. Приоритет 1 — первым разбирать повтор неизменного.**

Source: Platform, 865d017 (grade.py = 626b665d). [images/java25-grader/grade.py:2103](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/java25-grader/grade.py#L2103), [images/java25-grader/grade.py:2159](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/java25-grader/grade.py#L2159), [images/java25-grader/grade.py:2218](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/java25-grader/grade.py#L2218), [images/java25-grader/grade.py:2255](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/java25-grader/grade.py#L2255), [images/java25-grader/grade.py:2331](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/java25-grader/grade.py#L2331).

Цепочка: native build→canary checks + source/descriptor/tests/recipe hashes→копия src/recipe в свежий workspace→копия inventory→intact до/после; verify→intact+artifact_state. 7 full toolchain verify уже установлены R4, здесь повторно не проверялись.

Что/объём: Повторные reads/hash выбранных sources, tests/config, recipe; обходы copied work; JAR читается/hash в artifact_state, копируется, снова readback/hash, затем каждый verify hash JAR. Fresh Gradle caches/tmp, --offline --no-daemon --no-build-cache clean jar.

Частота: intact 6 раз плюс initial capture на успешном native grade; artifact verify 4 раза через closure. Source JAR build отдельно от предварительного javac student compile.

Назначение: Связать artifact с inputs и не допустить mutation private inputs/build recipe.

Что убрать/перенести: Recipe/static installation checks убрать из каждого intact; сократить повторные hash одного private immutable input и JAR до действительных переходов владения. Удалить двойную production compilation после согласования API/JAR semantics; действующий JUnit classpath использует stage/student, а не этот JAR.

Буквальное требование: Соответствие проверенного артефакта задаче важно. Именно 6 intact, постоянный clean/new caches и повторная компиляция не являются буквальной нормой; R4/native-build-cost-note.md прежний совет про guards не ограничивает новый прямой запрос пользователя.

**P05. qualification job; ответ: additional sources. Приоритет 2 — по владельцу/частоте.**

Source: Platform, 865d017; у 626b665d функции те же, строки -18 в grading_job.py. [tools/grading_job.py:730](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/grading_job.py#L730), [tools/grading_job.py:894](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/grading_job.py#L894), [tools/grading_job.py:980](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/grading_job.py#L980), [images/java25-grader/grade.py:2398](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/java25-grader/grade.py#L2398), [images/java25-grader/grade.py:2631](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/java25-grader/grade.py#L2631).

Цепочка: run_job native→_prepared_build_input_hash до/после→_additional_inventory (тот же ZIP admission); grade→_additional_inventory ещё раз→write temporary extracted source files.

Что/объём: Архив читается/hash, ZIP members проверяются/распаковываются в память, Java files hash; затем actual grade повторяет чтение и пишет sources. Плюс primary source hash до/после.

Частота: Для native verification с additional archive: 2 operator admission прохода +1 grader admission на каждый run_job, public recursion повторяет.

Назначение: Независимо связать returned proof с реально материализованными inputs.

Что убрать/перенести: На одном доверенном qualified execution сократить повторное admission неизменного private archive; использовать один результат существующего admission, не добавлять новый parser/validator. Реальную admission чужого submission оставить.

Буквальное требование: Безопасное принятие внешнего ZIP и предметный additional-source договор нужны; три одинаковых admission на один verification grade не заданы.

**P06. qualification курса, не обычный web ответ. Приоритет 2 — по владельцу/частоте.**

Source: Platform, 865d017 (run_job AST = 626b665d:852; verify_results:1153). [tools/check-course.py:16](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/check-course.py#L16), [tools/check-course.py:33](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/check-course.py#L33), [tools/check-course.py:35](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/check-course.py#L35), [tools/grading_job.py:319](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/grading_job.py#L319), [tools/grading_job.py:870](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/grading_job.py#L870), [tools/grading_job.py:988](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/grading_job.py#L988).

Цепочка: check-course→scenario_inventory→по каждому project/scenario prepare_job→run_job→docker run/host grade; public-verification создаёт copy student и рекурсивный run_job со своим grade; verify_results.

Что/объём: inventory(manifest) и его normalization hashes на каждый prepare_job, повторное чтение selected sources/tests/fixtures, materialization job; отдельный container и compilation/build на каждый scenario и public suite.

Частота: P×S выбранных jobs, плюс public job для каждого подготовленного public-verification при infrastructure complete. Число P/S из конкретного manifest; здесь не исполнялось.

Назначение: Проверить эталоны, стартеры, отрицательные сценарии, public/full suite и coverage.

Что убрать/перенести: Нормализовать manifest/selection один раз на check-course; не повторять static dependency/source processing между scenarios. При одинаковом production input разделить reusable compilation/build и действительно отличающиеся suite runs. Не превращать каждую повторную suite в полный cold build.

Буквальное требование: Содержательная проверка эталонов/негативов и полного принятого объёма может быть нормативной; container/Gradle/closure replay на каждый совпадающий input — выбор реализации.

**P07. qualification/receipt, повтор по scenario и question. Приоритет 1 — первым разбирать повтор неизменного.**

Source: Platform, 865d017; runtime_evidence AST = 626b665d. [tools/grading_job.py:152](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/grading_job.py#L152), [tools/grading_job.py:984](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/grading_job.py#L984), [tools/grading_job.py:1042](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/grading_job.py#L1042), [tools/grading_job.py:1210](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/grading_job.py#L1210), [tools/grading_job.py:1559](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/grading_job.py#L1559).

Цепочка: run_job→runtime_evidence для native build observation и executionEvidence; verify_results→runtime_evidence внутри result loop; _validate_automatic_release_receipt→runtime_evidence внутри question loop.

Что/объём: Каждый вызов сканирует bundle и заново читает/hash все его files, runner и sharedFiles; отдельный canonical metadata digest.

Частота: Native successful run_job ≥2; verify_results 1 на result; release validator 1 на automatic question. Несколько вопросов одного runtime не объединяются; public recursion добавляет run_job.

Назначение: Сверить фактически исполненный dependency closure с source registry и вопросами.

Что убрать/перенести: В рамках неизменного qualified runtime один раз получить closure по runtime, передать в уже существующие этапы; убрать O(question_count) полные reads одного bundle при receipt чтении.

Буквальное требование: Правильная runtime identity нужна; rehash одних immutable bundle bytes на каждую строку результата/вопрос не требуется буквально.

**P08. export/qualification/receipt. Приоритет 1 — первым разбирать повтор неизменного.**

Source: Platform, 865d017; package verifier тот же в 626b665d, receipt AST тот же. [tools/build-course.py:297](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/build-course.py#L297), [tools/build-course.py:307](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/build-course.py#L307), [tools/build-course.py:338](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/build-course.py#L338), [tools/delivery_verification.py:88](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/delivery_verification.py#L88), [tools/delivery_verification.py:222](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/delivery_verification.py#L222), [tools/grading_job.py:1588](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/grading_job.py#L1588), [tools/grading_job.py:1612](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/grading_job.py#L1612).

Цепочка: build_course→tree(native)→package_integrity→provenance hashes→final tree comparison. check-course delivery→package_integrity→delivery_receipt→package_integrity→validate_release_receipt→package_integrity→automatic receipt tail вновь hash delivery.files.

Что/объём: Полный native payload читается/hash несколькими слоями; package_integrity также перечитывает JSON/HTML и сканирует question folder. Schema dependencies хешируются на native_schema вызов.

Частота: Один успешный полный automatic delivery check имеет как минимум 4 loops чтения/hash delivery.files по указанной цепочке; manual/native-only без automatic tail — 3. Export отдельно читает весь payload и перечитывает перед publication.

Назначение: Целостность native package, metadata/forms/controls и соответствие receipt.

Что убрать/перенести: Один package_integrity результат в пределах защищённой операции; валидировать изменившийся/внешний пакет при следующем trust boundary, убрать прямую вложенную повторную проверку receipt, который только что построен из того же пакета.

Буквальное требование: Связь receipt с поставкой и полнота актуального выпуска существенны; число полных loops и повторное чтение immutable native schema не предписаны.

**P09. export/staging installation, весь Platform source. Приоритет 1 — первым разбирать повтор неизменного.**

Source: Platform, 865d017 (release_assembly.py blob = 626b665d). [tools/release_assembly.py:35](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/release_assembly.py#L35), [tools/release_assembly.py:61](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/release_assembly.py#L61), [tools/release_assembly.py:91](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/release_assembly.py#L91), [tools/release_assembly.py:132](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/release_assembly.py#L132), [tools/build-course.py:281](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/build-course.py#L281), [tools/build-course.py:321](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/build-course.py#L321), [tools/build-course.py:337](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/build-course.py#L337).

Цепочка: Context→GitSource→scan .git + ls-tree + для каждого blob отдельный git cat-file и SHA256→physical walk/read/hash всех source files. recheck() строит новый Context целиком.

Что/объём: Всё released Platform code tree, каждый Git blob + физическая копия, Git index/status/modes/metadata и 4 assets; O(files) git subprocess на Context.

Частота: build_course assembly: 1 initial Context +2 full recheck, то есть три таких прохода. Staging имеет вложенные recheck на registry/course/checkout/publish, см. P11.

Назначение: Аутентификация exact released code + двух external overlays, currentness во время export.

Что убрать/перенести: Не восстанавливать всю source qualification на каждом currentness: install/admit assembly один раз, сохранять уже принятую source identity и проверять только значимые mutable inputs. Хотя бы git cat-file batch убирает subprocess на файл. Убрать последующие глобальные полные recheck неизменного checkout.

Буквальное требование: Точные source commits/image digests требуются AGENTS. Это не буквальное требование полного bytes/tree verification несколько раз в одной операции или запрета обычных managed worktrees.

**P10. freeze private course source; export. Приоритет 1 — первым разбирать повтор неизменного.**

Source: Platform, 865d017 (prepare-private-source.py blob = 626b665d). [tools/prepare-private-source.py:38](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/prepare-private-source.py#L38), [tools/prepare-private-source.py:50](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/prepare-private-source.py#L50), [tools/prepare-private-source.py:120](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/prepare-private-source.py#L120), [tools/prepare-private-source.py:129](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/prepare-private-source.py#L129), [tools/prepare-private-source.py:150](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/prepare-private-source.py#L150), [tools/prepare-private-source.py:224](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/prepare-private-source.py#L224), [tools/prepare-private-source.py:241](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/prepare-private-source.py#L241), [tools/build-course.py:268](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/build-course.py#L268), [tools/build-course.py:318](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/build-course.py#L318).

Цепочка: prepare→public git blobs по одному→check_checkout reads→Core/execution inventories→physical_state public/execution/core before/after→copy→git hash-object/update-index на каждый file→validate. build_course validate before/after→runtime_inventory.

Что/объём: Целые public/execution/Core trees, nested educational .git; ignored runtime files тоже. Буквальный дубль validate:134 inventory(source), затем runtime_inventory:127 снова inventory(source) и фильтр только после полного hash.

Частота: Freeze одна operator операция; build_course два validate и ещё отдельный runtime_inventory:319. Точные count full trees зависят distinct/public/execution/allow_runtime, без запуска.

Назначение: Приватная материализация teacher/public/common, сохранность исходников и seed identity.

Что убрать/перенести: Убрать двойной inventory внутри validate непосредственно; runtime_inventory обходить только три объявленных runtime root. Убрать полный public/execution/Core replay там, где writer exclusive и bytes уже captured; заменить per-file Git processes штатным bulk Git доступом.

Буквальное требование: Сохранность пользовательских данных и teacher/public граница существенны. Именно полный repository snapshot/ignored hash и частота его повторов не установлены прямым требованием.

**P11. установка native course. Приоритет 1 — первым разбирать повтор неизменного.**

Source: Platform, 865d017 (stage_courses.py blob = 626b665d). [tools/stage_courses.py:72](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/stage_courses.py#L72), [tools/stage_courses.py:102](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/stage_courses.py#L102), [tools/stage_courses.py:119](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/stage_courses.py#L119), [tools/stage_courses.py:127](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/stage_courses.py#L127), [tools/stage_courses.py:150](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/stage_courses.py#L150), [tools/stage_courses.py:183](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/stage_courses.py#L183).

Цепочка: stage_registry→Context→stage_course→assembly.recheck→validate_checkout→validate_release_receipt→walk/provenance hashes→validate_pinned_native→assembly.recheck→validate_pinned_native; fresh stage ещё recheck+validate_pinned_native перед rename.

Что/объём: Каждый validate_pinned_native делает полный payload walk, git ls-tree, live read каждого file и отдельный git cat-file; повторяет HEAD/status. Между двумя validate_pinned_native нет записи в course, только recheck другого Platform source.

Частота: Existing checkout: validate_pinned_native два раза на checkout при assembly; fresh checkout: три раза. Плюс provenance/package/receipt hashes и несколько полных Context P09.

Назначение: Exact native Git payload/receipt identity перед readonly mount.

Что убрать/перенести: Убрать буквально соседний повтор validate_pinned_native; один доверенный admission перед атомарным publish и дальнейшая ссылка на immutable checkout. Не делать повторный весь Git/blob read при unchanged повторном stage_registry.

Буквальное требование: Exact source/data preservation обязательны; две/три полные сверки одного checkout подряд не предписаны.

**P12. operator bootstrap/sync, DB. Приоритет 2 — по владельцу/частоте.**

Source: Platform, 865d017 (bootstrap/sync/payload blobs = 626b665d). [tools/native-course-payload.mjs:8](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/native-course-payload.mjs#L8), [tools/bootstrap-native-course.mjs:24](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/bootstrap-native-course.mjs#L24), [tools/sync-native-course.mjs:27](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/sync-native-course.mjs#L27), [tools/sync-native-course.mjs:59](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/sync-native-course.mjs#L59), [tools/institution-bootstrap.mjs:37](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/institution-bootstrap.mjs#L37), [tools/institution-bootstrap.mjs:55](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/institution-bootstrap.mjs#L55).

Цепочка: bootstrap→readNativeCoursePayload→hash всех delivery.files+ancestor lstat; затем named locks и DB create. sync-native-course независимо повторяет files hash, selects/authorizes current route, syncDiskToSql.

Что/объём: Весь native course payload + lstat каждого пути; настоящая native DB sync из upstream PrairieLearn, объём внутри syncDiskToSql без source upstream здесь UNKNOWN. Institution/course DB locks отдельные.

Частота: На явную operator bootstrap/sync команду. Не на каждый ответ, document или Gateway status.

Назначение: Импорт immutable поставки и безопасное связывание institution/course/route.

Что убрать/перенести: Переиспользовать verified immutable installation при последовательных bootstrap/sync, убрать идентичную local payload реализацию в двух местах. DB locks согласуют реальные конкурентные записи; данных о дороговизне/дублировании этих locks нет.

Буквальное требование: Правильный DB target/сохранность данных важны; механизм полного hash перед каждой отдельной operator стадией не предписан. Дампов/restore в прочитанном исполняемом source не найдено.

**P13. server startup + request context. Приоритет 2 — по владельцу/частоте.**

Source: Platform + Gateway, Platform865d017/626b665d; Gateway901624527dce2d37a62fedaa9b768cbbbfd1e366. [bridge/bindings.mjs:6](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/bridge/bindings.mjs#L6), [bridge/server.mjs:54](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/bridge/server.mjs#L54), [bridge/server.mjs:99](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/bridge/server.mjs#L99), [src/gateway/config.py:108](https://github.com/Afonenko-Course-Tools/moodle-prairielearn-gateway/blob/901624527dce2d37a62fedaa9b768cbbbfd1e366/src/gateway/config.py#L108), [src/gateway/app.py:82](https://github.com/Afonenko-Course-Tools/moodle-prairielearn-gateway/blob/901624527dce2d37a62fedaa9b768cbbbfd1e366/src/gateway/app.py#L82), [src/gateway/courses.py:38](https://github.com/Afonenko-Course-Tools/moodle-prairielearn-gateway/blob/901624527dce2d37a62fedaa9b768cbbbfd1e366/src/gateway/courses.py#L38).

Цепочка: Node startup→loadVerifiedBindings hash всего native delivery для каждого binding; у626 это inline server:55–74. Python create_app→load_delivery также realpath/read/hash всего payload; create_courses_app делает отдельный app на course. Node context на request читает infoCourseInstance.json.

Что/объём: Два сервиса независимо читают/hash одну поставку на startup; на request только маленький instance metadata read+DB lookups, не полный source walk.

Частота: Полный hash на каждый service/process start и binding/course; маленький read на context request. Actual число workers/restarts на сервере UNKNOWN.

Назначение: Delivery identity при старте и UUID routing при обращении.

Что убрать/перенести: Перенести полную проверку immutable mounted delivery на installation; использовать уже принятую pinned поставку между Node/Python. Кешировать проверенный infoCourseInstance на lifetime verified binding, убрать file read с каждого request.

Буквальное требование: Соответствие маршрута и delivery важно; отдельный full hash каждым сервисом/процессом и постоянный JSON read не являются буквальными нормами.

**P14. постоянный background runtime. Приоритет 2 — по владельцу/частоте.**

Source: Gateway, 901624527dce2d37a62fedaa9b768cbbbfd1e366 (public main, установленный server ref UNKNOWN). [src/gateway/config.py:78](https://github.com/Afonenko-Course-Tools/moodle-prairielearn-gateway/blob/901624527dce2d37a62fedaa9b768cbbbfd1e366/src/gateway/config.py#L78), [src/gateway/app.py:97](https://github.com/Afonenko-Course-Tools/moodle-prairielearn-gateway/blob/901624527dce2d37a62fedaa9b768cbbbfd1e366/src/gateway/app.py#L97), [src/gateway/grades.py:150](https://github.com/Afonenko-Course-Tools/moodle-prairielearn-gateway/blob/901624527dce2d37a62fedaa9b768cbbbfd1e366/src/gateway/grades.py#L150), [src/gateway/grades.py:192](https://github.com/Afonenko-Course-Tools/moodle-prairielearn-gateway/blob/901624527dce2d37a62fedaa9b768cbbbfd1e366/src/gateway/grades.py#L192).

Цепочка: worker while True→assignments.reconcile_all→grades.poll→все active assignments→bridge.results; все activity_bindings→каждая lab bridge.work_results; drain проходит outbox, проверяет binding/latest observation/native attempt и шлёт AGS.

Что/объём: O(active assignments + всех bindings + pending outbox) SQL/HTTP на цикл, запросы идут последовательно. Это не file/hash inventory.

Частота: pollSeconds default15, допускается1..3600; реальные настройки/число активных rows UNKNOWN. Для каждого named course свой worker.

Назначение: Подтянуть grading/ACL и доставить актуальные оценки в Moodle через LTI/AGS.

Что убрать/перенести: Прекратить polling заведомо неизменных завершённых строк, выбирать только изменившиеся/ожидающие сущности и переиспользовать уже известные актуальные результаты. Существующий outbox уже задаёт retry/backoff; не нужен новый DSL/receipt framework.

Буквальное требование: Корректная синхронизация оценок/назначений нужна; обход всех rows каждые15s, включая completed, не задан буквальной нормой. Настоящие данные не читались.

**P15. startup/transactions, не тяжёлый source snapshot. Приоритет 3 — низкий/предметный, нет установленного лишнего большого повтора.**

Source: Gateway, 901624527dce2d37a62fedaa9b768cbbbfd1e366. [src/gateway/store.py:41](https://github.com/Afonenko-Course-Tools/moodle-prairielearn-gateway/blob/901624527dce2d37a62fedaa9b768cbbbfd1e366/src/gateway/store.py#L41), [src/gateway/store.py:62](https://github.com/Afonenko-Course-Tools/moodle-prairielearn-gateway/blob/901624527dce2d37a62fedaa9b768cbbbfd1e366/src/gateway/store.py#L62), [src/gateway/store.py:86](https://github.com/Afonenko-Course-Tools/moodle-prairielearn-gateway/blob/901624527dce2d37a62fedaa9b768cbbbfd1e366/src/gateway/store.py#L86), [src/gateway/store.py:93](https://github.com/Afonenko-Course-Tools/moodle-prairielearn-gateway/blob/901624527dce2d37a62fedaa9b768cbbbfd1e366/src/gateway/store.py#L93).

Цепочка: Store init→startup-lock flock→read-only schema checks→connect/WAL; WAL busy retry≤15s с sleep≤10ms. transaction→BEGIN IMMEDIATE; request/session/idempotency hashes — маленькие record hashes.

Что/объём: SQLite schema metadata и SQL locks; полного DB dump/hash/backup в этом source нет.

Частота: startup-lock один раз на создание Store; транзакционный lock на mutation; WAL retries только при реальном SQLITE_BUSY/LOCKED.

Назначение: Конкурентное создание DB и атомарные user operations/idempotency.

Что убрать/перенести: Не выдавать эти locks за аналог глобального file fixation. Удаление без установленной проблемы не следует из анализа; при user demand убрать лишние checks источник требует отличать реальные atomicity от repeated immutable bytes checks.

Буквальное требование: Сохранность реальных DB данных обязательна. Конкретный flock/WAL mechanism — implementation, но здесь нет найденного дублирования большого объёма.

**P16. Workspace launch/restart. Приоритет 2 — по владельцу/частоте.**

Source: Platform, 865d017 (Workspace entrypoint blob = 626b665d). [images/sp-git-workspace/entrypoint.sh:18](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/sp-git-workspace/entrypoint.sh#L18), [images/sp-git-workspace/entrypoint.sh:26](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/sp-git-workspace/entrypoint.sh#L26), [images/sp-git-workspace/entrypoint.sh:44](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/sp-git-workspace/entrypoint.sh#L44), [images/sp-git-workspace/entrypoint.sh:71](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/sp-git-workspace/entrypoint.sh#L71).

Цепочка: entrypoint→manifest parse→read/hash seed.zip; если work есть, сравнивает identity и продолжает. Первый launch→unzip+каждый member hash→git fsck/full→symbolic-ref/rev-list/status→сравнить reachable и all objects→rename work.

Что/объём: Весь compressed seed при каждом restart; initial≤4096 files, ≤16MiB expanded + full Git object closure.

Частота: archive hash каждый entrypoint независимо от уже существующего work; unzip/Git только первая инициализация. Restart намеренно не вызывает Git.

Назначение: Не сбросить работу, не принять другой seed и не выдать object базы с готовым решением.

Что убрать/перенести: Проверить immutable seed при export/installation один раз; restart сравнивает сохранённую small identity, не читает весь archive. Сохранность существующего work оставить; fsck/all-objects installation/export seed тоже можно вынести до каждого пользователя.

Буквальное требование: Сохранение student work и отсутствие готового решения в seed существенны; per-restart SHA256 и fsck при первом старте каждого пользователя не предписаны.

**P17. ответ: fish. Приоритет 3 — низкий/предметный, нет установленного лишнего большого повтора.**

Source: Platform, 865d017 (fish grade/supervisor blobs = 626b665d). [images/fish-git-grader/grade.py:28](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/fish-git-grader/grade.py#L28), [images/fish-git-grader/grade.py:42](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/fish-git-grader/grade.py#L42), [images/fish-git-grader/grade.py:68](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/fish-git-grader/grade.py#L68), [images/fish-git-grader/grade.py:76](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/images/fish-git-grader/grade.py#L76).

Цепочка: grade→bundle+runner/shared closure hash→compile/exec trusted Python checker→fish syntax subprocess→до16 cases: fresh dir, copy answer, write fixtures, chown recursive, fish subprocess.

Что/объём: Весь small fish runtime closure на ответ; источник≤1MiB, fixture text≤64KiB каждый. На case новая copy/fixtures и process; полных JDK hashes нет.

Частота: preflight один на fish grade; syntax один; 1..16 case launches до первого failure/time budget.

Назначение: Проверить versioned runtime и выполнить независимые предметные cases.

Что убрать/перенести: Static runtime closure проверить на installation. Выбор разных fixture случаев и isolation — полезная предметная работа; не объявлять её лишней просто по grep. Уменьшение копий может использовать уже фиксированный read-only answer, если isolation сохраняется.

Буквальное требование: Предметные случаи/оболочка нужны по принятому fish договору; именно full closure на каждый ответ не задан.

**P18. provider installation/check, project scripts. Приоритет 2 — по владельцу/частоте.**

Source: Java + System-Practice, Java dd5b76a0168133f0760ece6cb21f4bdf4f6203bb; SP fcc5a2ac1eff3760d306a337a9c8871780365488. [tools/materialize-providers.py:16](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/tools/materialize-providers.py#L16), [tools/materialize-providers.py:39](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/tools/materialize-providers.py#L39), [tools/materialize-providers.py:100](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/tools/materialize-providers.py#L100), [tools/materialize-providers.py:128](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/tools/materialize-providers.py#L128), [tools/sync-providers.py:22](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/tools/sync-providers.py#L22).

Цепочка: --check→для каждого package×scope inventory(rglob/read/hash/stat). --write→git archive full _extensions→staged inventory→current target inventory→ещё prewrite inventory→copy/chmod→postwrite inventory. Java legacy sync→git archive ZIP→quarto add→ещё inventory/copy/check.

Что/объём: Все installed extension copies, часто один package в root/theory/lectures/practice/tasks/handbook и других scopes; одинаковые package bytes считаются по каждой копии.

Частота: На каждый explicit materializer check/write; вызывается project CI и render. Legacy sync не штатный текущий candidate маршрут и не вызывается runtime.

Назначение: Pin provenance/vendor closure и сохранение локальных правок при установке.

Что убрать/перенести: Проверять при installation/change vendor copies, не при каждой несвязанной сборке/статусе. Свести writable target к одному владельцу и убрать повторные inventories одного target без mutation. Legacy archive→quarto add→copy путь не нужен текущему pinned materializer.

Буквальное требование: Точные provider versions и сохранение локальных правок важны; полное hash каждой installed copy на каждый render/check не задано.

**P19. CI whole course, explicit candidate qualification. Приоритет 2 — по владельцу/частоте.**

Source: Java, dd5b76a0168133f0760ece6cb21f4bdf4f6203bb. [.github/workflows/check.yml:1](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/.github/workflows/check.yml#L1), [CI/render.sh:7](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/CI/render.sh#L7), [CI/render.sh:10](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/CI/render.sh#L10), [CI/check-projects.sh:10](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/CI/check-projects.sh#L10), [CI/check-projects-candidate.sh:22](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/CI/check-projects-candidate.sh#L22).

Цепочка: CI render job disposable clone→materializer --check→quarto --version→student/full/student whole render; отдельный providers job снова materializer --check. Candidate script→student render→Platform export→check-course host+container.

Что/объём: Три render всего проекта, дублированный vendor hash в разных jobs. Candidate запускает и host, и container grading одного manifest; результаты не равны qualification сами по себе.

Частота: push master, PR, workflow_dispatch и weekly schedule; default render triplet. Candidate только явная команда, не обычный CI current entrypoint.

Назначение: Публичная сборка/profile isolation + source pins; candidate diagnostic/full qualification.

Что убрать/перенести: Обычный unchanged/irrelevant CI не должен повторять whole student/full/student и vendor hash. Сохранить адресный profile-transition regression там, где менялась такая семантика; выбирать affected course/input вместо fixed triple every run. Не запускать host и container одинаковые cold builds без отдельной цели.

Буквальное требование: Student/full correctness и финальная точная приёмка могут требоваться; everypush/weekly triple render и duplicate vendor check — implementation.

**P20. source pins preflight, optional assembly/image metadata. Приоритет 2 — по владельцу/частоте.**

Source: Java + System-Practice, Java dd5b76a; SP fcc5a2ac. [CI/check-production-pins.py:23](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/CI/check-production-pins.py#L23), [CI/check-production-pins.py:45](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/CI/check-production-pins.py#L45), [CI/check-production-pins.py:65](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/CI/check-production-pins.py#L65), [CI/check-production-pins.py:96](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/CI/check-production-pins.py#L96), [CI/check-production-pins.py:116](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/CI/check-production-pins.py#L116), [CI/check-local-candidate.py:8](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/dd5b76a0168133f0760ece6cb21f4bdf4f6203bb/CI/check-local-candidate.py#L8).

Цепочка: main→pins JSON checks. Только если PL_PLATFORM_ROOT+PL_RUNTIME_REGISTRY→assembly: selected module bytes versus git show + assets hash→Context→Context.recheck. _published_image_config умеет GHCR token/manifest/config, но main его НЕ вызывает. Local candidate→runtimeFiles hash+materializer check+docker image inspect.

Что/объём: Default pins preflight — маленькие metadata JSON без registry network или full source walk. Optional assembly наследует P09 дважды; metadata helper может делать3 HTTP на image только при другом явном caller.

Частота: CI check-projects вызывает main по одному разу; assembly только с paired env. Default network frequency0. Candidate inspect на explicit candidate preflight.

Назначение: Проверка непротиворечивости production pins и optional authenticated installation.

Что убрать/перенести: Не добавлять полный source/hash/network к простому metadata preflight. Для admitted assembly не повторять Context.recheck полного tree. Удалить/не вызывать неиспользуемую metadata machinery на каждую operation.

Буквальное требование: Pins нужны; helper presence не доказывает сетевой вызов или runtime cost. Новый proof/validator сюда не нужен.

**P21. CI material generation + whole render. Приоритет 2 — по владельцу/частоте.**

Source: System-Practice, fcc5a2ac1eff3760d306a337a9c8871780365488. [.github/workflows/ci.yml:22](https://github.com/BSU-RFCT-Afonenko-Courses/System-Practice/blob/fcc5a2ac1eff3760d306a337a9c8871780365488/.github/workflows/ci.yml#L22), [tools/build_materials.py:57](https://github.com/BSU-RFCT-Afonenko-Courses/System-Practice/blob/fcc5a2ac1eff3760d306a337a9c8871780365488/tools/build_materials.py#L57), [tools/build_git_materials.py:43](https://github.com/BSU-RFCT-Afonenko-Courses/System-Practice/blob/fcc5a2ac1eff3760d306a337a9c8871780365488/tools/build_git_materials.py#L43), [tools/build_maintenance_materials.py:16](https://github.com/BSU-RFCT-Afonenko-Courses/System-Practice/blob/fcc5a2ac1eff3760d306a337a9c8871780365488/tools/build_maintenance_materials.py#L16), [tools/materialize_public_materials.py:11](https://github.com/BSU-RFCT-Afonenko-Courses/System-Practice/blob/fcc5a2ac1eff3760d306a337a9c8871780365488/tools/materialize_public_materials.py#L11).

Цепочка: CI→provider check→apt fish→5 materials --check→tests→whole student/full/student. --check генераторы реально заново строят expected ZIP bytes; maintenance generate создаёт Git histories, objects/refs, shared bare copy, diff, prune, recursive read и ZIP; потом hashes existing archives.

Что/объём: Все объявленные учебные ZIP и публичные copies; Git fixture construction даже в --check. build_git_materials обычный seed generate сейчас только packs text; старые initialize/commit helpers сами из generate не вызываются.

Частота: Каждый push main/PR. Три whole renders на run; все materials generators на run независимо от changed files.

Назначение: Детерминизм материалов и view transition correctness.

Что убрать/перенести: Материалы строить/сверять при изменении seed/packer/checker; не восстанавливать Git историю на любой QMD/README правке. Разделить static check metadata и реальную генерацию. Повторный full profile triplet оставить адресной profile regression, не blanket gate.

Буквальное требование: Учебные материалы должны соответствовать условиям; точный Git reconstruction/ZIP rehash каждый CI и fixed triple render не заданы.

**P22. explicit local archive checker / tests, не автоматический PL runtime всех задач. Приоритет 2 — по владельцу/частоте.**

Source: System-Practice, fcc5a2ac1eff3760d306a337a9c8871780365488. [tools/system_support.py:34](https://github.com/BSU-RFCT-Afonenko-Courses/System-Practice/blob/fcc5a2ac1eff3760d306a337a9c8871780365488/tools/system_support.py#L34), [tools/system_support.py:70](https://github.com/BSU-RFCT-Afonenko-Courses/System-Practice/blob/fcc5a2ac1eff3760d306a337a9c8871780365488/tools/system_support.py#L70), [tools/check_version_control.py:31](https://github.com/BSU-RFCT-Afonenko-Courses/System-Practice/blob/fcc5a2ac1eff3760d306a337a9c8871780365488/tools/check_version_control.py#L31), [tools/check_version_control.py:194](https://github.com/BSU-RFCT-Afonenko-Courses/System-Practice/blob/fcc5a2ac1eff3760d306a337a9c8871780365488/tools/check_version_control.py#L194), [tools/check_version_control.py:235](https://github.com/BSU-RFCT-Afonenko-Courses/System-Practice/blob/fcc5a2ac1eff3760d306a337a9c8871780365488/tools/check_version_control.py#L235).

Цепочка: check_archive→bounded ZIP/tar read→копия двух .git в safe bare view→tree/index/work reads→fsck --full на каждый repo→_history: все commits→каждый tree и parents→cat-file blob на каждый file→ancestors rev-list cache.

Что/объём: Archive default≤1000 entries/8MiB; history≤256 commits. O(commits×files) Git subprocess; возможно повторное чтение одного blob между trees/index/project/verification. Bare helper copy объектов/refs также есть при явном вызове.

Частота: На explicit invocation checker/соответствующий test. Не следует переносить это на каждый студентский ответ: SP tasks grading остаётся manual, source автоматического вызова этого checker в Platform fish grade не установлен.

Назначение: Разобрать предметную структуру сдачи и Git историю без запуска supplied hooks/config; внешний SSH/процесс восстановления всё равно manual.

Что убрать/перенести: Один bulk read объектов и cached blob/tree results вместо subprocess для каждого одинакового blob; не запускать полный этот checker для несвязанных проектов. Сохранить предметную историю и bounded foreign archive admission.

Буквальное требование: Проверка предметной истории нужна этому заданию; fsck twice и per-blob processes не буквальная норма. Нельзя считать их global fixation без связи с caller.

**P23. CI/release-only. Приоритет 2 — по владельцу/частоте.**

Source: Platform + Gateway, Platform865d017; Gateway901624527dce2d37a62fedaa9b768cbbbfd1e366. [.github/workflows/check.yml:66](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/.github/workflows/check.yml#L66), [tools/prepare_native_toolchain.py:225](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/prepare_native_toolchain.py#L225), [tools/prepare_native_toolchain.py:265](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/prepare_native_toolchain.py#L265), [tools/prepare_native_toolchain.py:296](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/prepare_native_toolchain.py#L296), [tools/prepare_native_jdk.py:192](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/prepare_native_jdk.py#L192), [tools/publish-receipt.py:48](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/tools/publish-receipt.py#L48), [.github/workflows/publish-runtime.yml:100](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/.github/workflows/publish-runtime.yml#L100), [.github/workflows/release.yml:55](https://github.com/Afonenko-Course-Tools/moodle-prairielearn-gateway/blob/901624527dce2d37a62fedaa9b768cbbbfd1e366/.github/workflows/release.yml#L55).

Цепочка: Platform check push/PR: prepare --fetch→--verify-context→--write-build-context (ещё verify before/after)→Docker no-cache network-none build→--verify-context→image smoke. prepare validates public closure/cache repeatedly; JDK normalize snapshot before/after, copy, normalized twice. Publisher вновь verify_context twice, builds4 images, pushes then pulls each digest; Gateway release pip/tests/wheel/build-push/pull + wheel hash.

Что/объём: Pinned Gradle ZIP, JDK package/components/installations и public source selection + TAR; несколько full reads больших fixed inputs. Docker pull/build/container/test setup отдельно от learner response.

Частота: Platform check.yml на push/PR/dispatch без paths filter; publish-runtime explicit workflow dispatch/release. Gateway release explicit existing tag, не per answer.

Назначение: Создать и опубликовать реальные qualified immutable runtimes.

Что убрать/перенести: Построить один reusable admitted runtime на relevant source/toolchain change и переиспользовать exact image. Убрать постоянные одинаковые --verify-context из nested calls; не запускать no-cache native toolchain build на любую несвязанную source правку и повторно ради того же release.

Буквальное требование: Реальный image и pin нужны. Статические Source фиксации и 4+ повторных verify на неизменных bytes/всеpush без path discrimination — mechanism choices, не прямые нормы.

**P24. deployment installation-only. Приоритет 3 — низкий/предметный, нет установленного лишнего большого повтора.**

Source: Platform, 865d017 (Ansible source blob = 626b665d). [ansible/roles/platform/tasks/main.yml:88](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/ansible/roles/platform/tasks/main.yml#L88), [ansible/roles/platform/tasks/main.yml:104](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/ansible/roles/platform/tasks/main.yml#L104), [ansible/roles/platform/tasks/main.yml:114](https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/865d01705f8e9acee59de09661d5c00c290f1583/ansible/roles/platform/tasks/main.yml#L114).

Цепочка: Ansible→recurse owner private gateway directory→docker image inspect для каждого fixed image→copy/load archive только если missing→compose operations.

Что/объём: Metadata inspect per image, recursive chown/copies; archive transfer/load installation cost. Никаких действий этого playbook в аудите не выполнялось.

Частота: На explicit deploy playbook. Image archive copy/load conditional on absent image.

Назначение: Доставить installation artifacts и владельцев.

Что убрать/перенести: Это правильная граница для дорогостоящей image/source validation; не переносить сюда per-answer checks обратно. Уже условный missing-image путь не считать постоянным runtime scan.

Буквальное требование: Deployment consistency и данные важны; конкретный metadata inspect small и не объясняет grade latency.

**Границы вывода:** предложены места удаления или переноса работы, не новая DSL/proof/validator система. Реальные изменения ещё не внесены. Предметные test cases, DB atomicity и сохранность student work не смешаны с многократной проверкой неизменной установки. Полнота установленных расширений SP/Java объединяется с отдельными картами audit_core/audit_export; цена stock Moodle и фактического deployment не выдумана.

