# SP Core047 + PLf9: Download post внутри незавершённого capture child

3 ноября 2026. Только read-only диагноз actual attempt из `sp-native-047-acceptance.md`, `sp-native-047.log`, `sp-native-047-execution.json`. Единственный actual run принадлежит install owner: 25.647282166s, exit1, retry0. Здесь render/inspect/hash/build/тесты и новая SP попытка не запускались. Core/Download Source, installed copies и учебные данные не менялись. Прочитан exact опубликованный Download3.5 commit `c81df7364dd1fbbd07a0e54d67714b3d66f9695e` через `git show`/`git grep` в no-checkout `provider-sources/quarto-project-download`, а также одна installed copy `system-practice/tasks/_extensions/Afonenko-Course-Tools/project-download`. Ни checkout, ни загрузки версии не было.

## Конкретная причина

Это цикл ожидания двух разных фаз, а не недостаток данных семи документов и не новый SourceChanged.

1. PL `application/native-entry.ts:225–305` открывает `withNativeCaptureScope`, получает prepared lease и **ожидает** `lease.captureInputs` с одним actual owner Quarto render. Внутри Quarto исполняются hooks из `tasks/_quarto.yml:22–29`: Core pre, Download pre; затем QRC post, Site collect, Core post, Download post.
2. Core `runtime/operation.ts:1873–1904` при существующей reservation сохраняет результат только как `child-<token>.json` и отмечает `posted=true`. Сообщение «7 текущих native-ts author документов» означает успешную локальную финализацию child; оно не обещает scope complete. В ordinary render без reservation этот же участок пишет `complete.json` сразу.
3. Download post вызывает `finish`; native requests выбирают native маршрут даже без CLI `--native`. Exact Download `infrastructure/runtime.ts:633` вызывает `Core.withCurrentNativeOperation`. Этот API читает **только** `complete.json` (`operation.ts:2264–2275`) и затем требует reservation phase `sealed`. Отсутствие файла даёт наблюдённое `OPERATION_INCOMPLETE: Операция ещё не завершена`.
4. Однако `captureInputs` ещё ожидает завершения Quarto (`operation.ts:744`). Лишь после успешного возврата `runNative`, проверки posted child, завершения prepared lease и всего callback `withNativeCaptureScope` вызывает `sealCaptureScope` (`operation.ts:870–884`). Seal объединяет children, выполняет существующие проверки и пишет `complete.json`/`sealed` (`operation.ts:2043–2064`). До успешного Download post Quarto не вернётся; до возврата Quarto seal невозможен.
5. Ошибка Download делает Quarto exit1. Existing runner честно выдаёт `Native capture child failed: 1`, outer scope сохраняет reservation failed. Поэтому complete не появляется и после ошибки. Подменить child публичной завершённой операцией означало бы обойти lifetime/full coverage/final fences; для устранения данного отказа этого не требуется.

## Сохранённые actual артефакты

`tasks/_generated/course-spec/native-ts/active.json` указывает на generation `649ab4bb-d0ad-42d3-bd6d-50e552198406`. В ней:

- `child-501a3c23-7c1f-47f9-bc51-d46e803a347e.json`: documents7, catalog documents7, outputs7, releaseDocuments7; identity profiles `["full"]`, projection `author`; наблюдённых runtime implementationFiles15.
- `reservation.json`: единственный child с тем же token, inputs всех семи actual QMD, `used=true`, `posted=true`; итоговый status `failed` после outer error.
- `complete.json` отсутствует. Сохранены семь комплектов ранних/поздних/native observations. Эти файлы не являются публичным complete и не использовались для продолжения pipeline.
- В existing `_generated/project-download/requests` семь request sidecars: все `native=true`, `courseProcessed=true`. Четыре bank sources требуют14 site archives: по `conditions` и `full` для семи упражнений. Это только requests: до упаковки и receipt Download не дошёл.

Это прямые чтения уже сохранённых JSON/имен файлов, без нового source/runtime обхода, hash или inference о неизвестном успешном результате.

## Нужен ли этот Download post PL

Для нынешнего PL native assignment delivery — нет. Он создавал бы отдельный набор **сайтовых** ZIP/receipt из всех bank requests, внутри author/full capture. После seal PL открывает настоящий complete через `withCurrentNativeOperation` (`native-entry.ts:323`), строит выбранные assignment deliveries и пакует их собственный student inventory: `native-materialize.ts:155–220` вызывает Core `materializeProject` и затем Download `packageArtifact(final)`; workspace использует `adapter.workspaceArtifact(..., zip: Download.zip)`. Результат передаётся exporter как `studentArtifact`, а не считывается из site `_downloads` или `artifacts-receipt.json` (`native-course.ts:486,556`). Native route явно отвергает `publicationConditions`/staging receipt как отдельные ещё не связанные зависимости (`native-course.ts:261–270`).

Поздний actual child AST действительно содержит14 ссылок `../_downloads/*-{conditions,full}.zip` с class `project-download`. Они не требуют изготовления author ZIP ради student Body: existing Core `body-export/producer.ts:72–81` удаляет только contextual Download Link classes из public condition; `publicCondition` применяет эту partition (`:307`), а introduction — её же (`:198`). Full ZIP никогда не становится public question resource. Child public resources не содержат `_downloads`. Это вывод по существующему коду и actual AST, не новый выполненный Body acceptance; дальнейший PL этап ещё не достигнут.

Полезная роль Download сохраняется: registered shortcode handler/late filter определяет реальные literal requests, текст и href; ordinary website publication получает ZIP по этим ссылкам. Download pre очищает свои прежние output manifest/receipt/request sidecars перед новым render (`runtime.ts:93–134`), поэтому его удаление здесь не предлагается. Прямые `packageArtifact`/`zip` остаются полезны PL и не вызывают `finishDownloads`/site publication. Ordinary render без capture reservation имеет завершённый Core state уже после Core post, поэтому последующий Download post находится в правильной фазе. External Publisher может отдельно отложить post и вызвать `finishDownloads` после завершения producer; published test `tests/project-contract-consumer.ts:52–80` уже использует такой порядок. Его обычный native release validation/project contracts/resource/currentness путь менять не требуется.

## Один ограниченный предлагаемый diff

Владелец — Core orchestrator **частного prepared assignment render**, файл `_extensions/course-core/runtime/assignment-execution.ts`, existing `assignmentChild` после exact render/token qualification и сборки `fixed` env (`:198–214`). Только при уже вычисленном `render === true` Core устанавливает для собственного child:

```ts
if (render) fixed.COURSE_SITE_DEFER_DOWNLOADS = "1";
```

Это конкретное предложение, строка **не реализована** в этом read-only задании. Signal уже существует в exact Download `entrypoints/post.ts:3`: он завершает только site publication hook до `finish`. Новый Download API/profile/registry/receipt, новая caller env опция и изменение Quarto hook config не нужны. Core знает actual assignment intent и управляет закрытым command environment; PL не должен получать arbitrary env override. Добавление после existing env validation не открывает caller возможность менять остальные переменные. Условие `render` здесь доступно только внутри assignment execution с actual live capture token и exact owner argv; general Core commands/обычный render вне этого пути flag от Core не получают. Inspect/Pandoc probes тоже не получают новый flag.

Смысл: private capture производит current native documents; обычная публикация сайта в нём не запрашивается. Отменяется именно лишний siteZIP finalizer, а не его refusal и не Core gate. Не переносить seal внутрь Core post, не читать child через complete API, не catch-and-ignore OPERATION_INCOMPLETE, не создавать fake complete и не повторять Download finish после capture: PL уже имеет свой отдельный selected student packaging. Guards correlation/lifetime/full coverage/private/resource/role/currentness и настоящая последующая sealed операция остаются прежними.

Если root примет эту семантику, достаточная адресная проверка diff: pure assertion, что exact prepared render получает existing defer flag, inspect/Pandoc его не приобретают, caller environment остаётся закрытым; небольшой regression existing Body partition на genuine late Link class может проверить удаление site Download links при сохранении обычных ресурсов. Это не новая qualification matrix. Actual продолжение остаётся единственным отдельно назначенным root SP run после независимого review; успех seal/PL/ZIP/Platform на основании данного диагноза не заявляется.

Отдельная соседняя граница, не предлагаемая в этом diff: generic Download post без `--author` ожидает student projection (`entrypoints/post.ts:6`), тогда как этот private child author/full. Если просто изготовить complete пораньше, это не сделает обычный site finalizer корректным assignment consumer; следующим был бы projection binding. Обычный author сайт по опубликованному Download contract вызывает `--native --author`; исправление ordinary full-site hook конфигурации — отдельный scope.

## Собственный допуск и сохранность

Исходный primary turn `01a1a21a-f52f-73a3-85e9-758306144504` не заменён. В собственном rollout полный интервал содержит7 turn_context records /6 уникальных turns: строки8,215,597,710,878,1016,1032; две последние повторяют current turn `01a1a265-29fb-74d0-8610-ad9514d71851`. Все top model/effort и nested collaboration settings согласованы `gpt-6.1-sol/high`. Standard назначен root, `service_tier` отсутствует; серверный tier отдельно не утверждается. Globals/normative документы untouched. Source implementation, installs, provider refs, owner captures/logs, student data не менялись; новый файл — только этот diagnosis report.

## Последующая разрешённая реализация

После принятия этого read-only диагноза root отдельно разрешил ровно предложенную Core дельту. Она реализована в Source commit `830381697beea4106655965e10605cc13987199b` поверх `0478458a71f3acf34ad6d9983ac11023783dde3d`: два production lines (комментарий и qualified-render-only env flag) плюс один pure behavior regression. RED1.103s/GREEN1.186s; exact diff check PASS, Core Source clean. Полный implementation report — последний раздел `core-native-delivery.md`. Диагностические выводы выше сохраняют свой исходный read-only статус; новых render/inspect/hash/actual попыток и установки не было. Диапазон передан root для независимого review.
