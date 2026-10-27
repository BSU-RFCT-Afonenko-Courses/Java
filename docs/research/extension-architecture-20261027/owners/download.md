# Download: native context, отбор файлов и архивы

Исследован owner ref `Download 3.2.2` — кандидат 3.2.2, а не объявленный новый production release. Java baseline содержит 3.2.1 `Download 3.2.1`. Сравнение установленного `tasks/_extensions/Afonenko-Course-Tools/project-download` с кандидатом показывает четыре отличающихся файла: `_extension.yml`, `infrastructure/files.ts` и два observability bridges. `application/publish.ts`, `infrastructure/runtime.ts`, ownership и ZIP одинаковы. Поэтому batch/recovery ниже уже относятся и к выбранному Java baseline; это не новинка кандидата.

## Фактический маршрут

`entrypoints/pre.ts` вызывает `prepare`: native `quarto inspect`, ранняя проверка конфигурации, очистка только manifest-owned ZIP, своего receipt и request directory. Pandoc filter выполняет два AST walk: индекс видимых exercise Div, затем преобразование shortcode requests в ссылки. На документ пишется JSON заявки; повтор одной заявки внутри документа устраняется. View определяет starter/full, а closed/public selection остаётся ответственностью Core и последующих selectors.

`finish` всегда делает новый `quarto inspect`, даже при supplied `current`. Request ownership проверяет канонические источники, regular paths, разрешённые поля, имя SHA1(source) и SHA256 JSON. Затем `finish` читает request JSON повторно. При необходимости Core загружается по соседнему installed path: `resources.ts`, `artifacts/resolve.ts`, а в fallback — `native-run.ts`. Используется внутренний layout Core и структурный `any`, раннего общего capability gate нет.

Новый и установленный Download сначала проверяют наличие `withNativeArtifactBatch`. В batch callback фиксируются собственные ZIP bytes и receipt facts; публикационные проверки, cleanup и запись запускаются после завершения Core callback. Это осмысленная граница currentness: нельзя автоматически переносить запись внутрь proof batch ради меньшего числа проходов. Для старого Core остаётся fallback `loadNativeRun` + `resolveArtifact`. Supplied context проверяется по root/output/profiles; он не доказывает неизменность всех source bytes сам по себе.

`publish` дедуплицирует обычные ресурсы по ID до `resourceFiles`, но **каждую model artifact заявку разрешает до дедупликации**. Для повторного имени сравнивает `zip(previous)` и `zip(files)`, затем снова строит окончательный ZIP. Проверка неодинаковых payload обязательна; повторная сериализация и разрешение — кандидат на phase-local reuse, а не доказанная необходимость. Конечный набор записывается только после validation; manifest первым позволяет следующей сборке убрать собственную прерванную запись. Чужой output/receipt и symlink ведут к отказу.

## Гипотезы и стоимость

| Гипотеза | Свидетельство | Вывод и предел |
| --- | --- | --- |
| H31 | `runtime.ts`: prepare inspect и finish inspect; supplied current не отменяет finish inspect; Core batch уже есть | Повтор получения конфигурации подтверждён статически. Между pre и post идёт native render: без mutation model удалять post проверку нельзя. Передача принятого narrow context в одной фазе заслуживает опыта |
| H32 | `save` вычисляет SHA256(JSON.stringify(current.run)) в `sourceRunHash`, отдельно SHA256 каждого ZIP | Полный digest NativeRun существует; он связывает сериализованный run, а не заменяет currentness/ZIP integrity. Reader `sourceRunHash` в исследованных runtime-модулях Download/PL не найден. Нужность внешним release/evidence consumers остаётся неизвестной; удаление не предложено как готовая правка |
| H33 | `resourceFiles`: sorted directory walk, path checks, затем `gitIgnored` и чтение bytes | На непустой default selection — два Git child: isolated bare init и `check-ignore --no-index -z --stdin`. Один процесс check-ignore принимает весь список; это не процесс на файл. `gitignore:false` пропускает Git. Повторные artifacts могут заново выбрать тот же каталог; секунд и доли startup не измеряли |

Дополнительное source свидетельство H32: `tests/runtime-batch.ts:27` прямо проверяет exact `sourceRunHash` actual run, а `:31` проверяет, что receipt facts сняты до мутации borrowed run после callback. Это объясняет историческую regression-test гарантию. Оно не обнаруживает production reader и не доказывает, что полная сериализация NativeRun является минимальным достаточным предметом receipt.

`resourceFiles` обеспечивает настоящие Git semantics: вложенные `.gitignore`, отрицания и исключения каталогов. Замена на набор glob не является эквивалентным упрощением. ZIP STORE детерминированный: UTF-8 names, дата 1980-01-01, CRC32, ZIP32 limits; внешнего архиватора нет. Цена линейна по bytes плюс сортировка paths, но повтор ZIP увеличивает число проходов и временных buffers. Пиковая память неизвестна.

## Студенческое имя и граница ответственности

Сайт Download сейчас создаёт `exr-id-starter.zip`, `exr-id-full.zip`, `exr-id-conditions.zip`; обычный resource — `resource-id.zip`. Ссылка уже имеет `download=""`, но имени лабораторной в модели заявки нет. PrairieLearn отдельно копирует starter bytes в постоянный `starter.zip`; это другой presentation consumer.

Принятое требование — `<lab-or-group-id>--<exercise-id>.zip`, при отсутствии содержательной группы — `<exercise-id>.zip`. Его следует выражать presentation binding: logical artifact identity и hash остаются общими, а work/group задаёт download name. В одном вопросе, используемом несколькими works, нельзя записывать различающееся `question.html` без учёта dedup conflict. Возможны один canonical meaningful group либо имя на ссылке/assessment context; выбор должен быть проверен на actual browser Content-Disposition. Имена браузерных копий вроде `starter-1` не являются авторским ID и не наблюдались как строка генератора.

Короткий editor exercise не обязан показывать archive и README как обязательный шаг. Удаление ссылок из primary flow допустимо только при сохранении необходимых API/data; optional local kit остаётся отдельным действием. Текущая kit-модель сама по себе не задаёт будущую UX политику.

## Недостающие опыты

Одна очередь исследований должна содержать: supplied context против самостоятельного finish с mutation между фазами; repeated artifact memberships против unique artifacts при сохранении conflict negative case; повтор resource selection с `.gitignore` negation и sibling projects; receipt readers и digest subject; contextual filename в двух works при одном question. До этих опытов не заявляются проценты ускорения и не удаляются ownership/privacy/currentness guards.

проверочная сводка (сохранена локально) связывают все runtime rows Download с 13 предметными модулями. Ledger сохраняет 2444 rows: 889 runtime operation bindings, 1555 development/test/CI rows остаются unreviewed; 270 functions и 1908 callsites учтены механически. Модульная привязка не доказывает необходимость каждой инструкции: `semantic_justified=false`, `runtime_observed=false` и unresolved dispatch сохранены. проверочная сводка (сохранена локально).
