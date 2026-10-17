# Происхождение технической поставки Java

Активная связка использует выпущенные Core 7.2.0, PrairieLearn exporter 7.1.0
и Platform 2.2.0. `providers.json` задаёт точные published repository/tag/version/
commit/sourceZip; `installed-packages.json` закрепляет установленные bytes,
scopes и extension trees. Каждый bundle целиком воспроизводится через Git archive
того же released tag/commit. Новых локальных patches поверх этих выпусков нет.

| Владелец | Выпуск | Точный commit |
| --- | --- | --- |
| Afonenko-Course-Tools/quarto-course | v7.2.0 | `aebf6f4580c0bd5e9468498e840221619fc69e5f` |
| Afonenko-Course-Tools/quarto-course-prairielearn | v7.1.0 | `69924dbda56ed55fb556f2e0932565eafbdb6448` |
| Afonenko-Course-Tools/quarto-project-publish | v5.1.0 | `62f8c4a01a5fd9af11f5f66abba2bb48c2ef2631` |
| Afonenko-Course-Tools/quarto-reference-catalog | v3.1.0 | `3df2110e9f525f03f3ecb4926b5d55718e955c4c` |
| Afonenko-Course-Tools/quarto-project-download | v3.1.0 | `73797d3807e2433d33751d538746e1e9d82d6d57` |

[Core source release](https://github.com/Afonenko-Course-Tools/quarto-course/releases/tag/v7.2.0)
и [exporter source release](https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/releases/tag/v7.1.0)
сохраняют код и descriptors одного immutable ref. По сравнению с принятой
локальной связкой vendor code не изменился; актуализированы descriptors версий
и provenance. Прежние локальные поправки уже входят в выпущенные bytes.
`docs/local-extension-patches.json`, `tools/provider-patches/` и
`CI/local-candidate.json` сохраняют историю подготовки и не служат активными
production pins. Исторические candidate scripts относятся к прежнему снимку.

## Воспроизведение providers

Offline проверка установленного closure не обращается к GitHub:

```sh
python3 tools/materialize-providers.py --check
python3 tools/test-materialize-providers.py
```

Для переустановки подготовьте Git repositories с именами `checkout` из
`providers.json` в общем каталоге. В них должны быть точные published tags
и соответствующие objects. Материализатор проверяет tag → commit, descriptor
version и полный inventory до записи; неизвестные изменения и symlinks
останавливают операцию. Повторный запуск сохраняет те же bytes.

```sh
python3 tools/materialize-providers.py --providers-root /absolute/releases --write
python3 tools/materialize-providers.py --check
```

## Выпущенная Platform и runtime

[Platform source release v2.2.0](https://github.com/Afonenko-Course-Tools/prairielearn-platform/releases/tag/v2.2.0)
закреплён на `4579e7151e7107bbd9ef09852663d7771c2599db`.
Publisher run `39018028757` собирал runtime из
`b7fd40c42f3bbe03d0bb94111cde392c74c11b32`. Последующий metadata commit
обновил проверенные pins и документацию без изменения runtime bytes.
`runtime-image.json` и `published-runtime-profiles.json` опубликованы вместе
с source release; SHA256 registry:
`195eafb709cdd57589e0b626861bc8feb44d58a3c78a4520d0da04e620050650`.

| Образ | Неизменяемый repository digest |
| --- | --- |
| Java | `ghcr.io/afonenko-course-tools/java25-grader@sha256:6052b077f7d5fabaedbd2deb829eed15e9911beafaf78f29c90083ff917b8c17` |
| Community | `ghcr.io/afonenko-course-tools/prairielearn-community-gateway@sha256:55bdc17dec9ec969968ef0390ae316382b3c03730298d4a9cb1e65bce94eefcd` |
| fish | `ghcr.io/afonenko-course-tools/fish-git-grader@sha256:021adbbb5bb413f39b3e5e6c575dd989509998d89a8fe0cf470217e311a0c3d4` |

Java runner SHA256 —
`c270cac0932ecce0e01377cc8681a6cd7a6eb5b15f4e1acf9460b8c8c97eb92c`.
Оба Java profiles используют один опубликованный образ и точные library hashes.
Локальный config ID `sha256:3692af47c4bee8d371c68fbc98563482704b78fcf4589ea534da0f7a7826c89b`
фиксирует наблюдение Docker, а repository digest остаётся pin для export/grading.
Полные значения и publication evidence находятся в `CI/toolchain.json`.

## Проверка текущей поставки

Quarto 1.11.5, CUE 0.17.1, JDK 25 и Docker задаются явно. Внешний clean Platform
checkout должен иметь HEAD `4579e7151e7107bbd9ef09852663d7771c2599db`.
Выход checks хранится вне source checkout:

```sh
python3 CI/check-production-pins.py
bash CI/render.sh
PL_PLATFORM_ROOT=/absolute/released-platform CI_CHECK_OUTPUT=/absolute/fresh-checks bash CI/check-projects.sh
```

`CI/render.sh` выполняет student → full → student. Production checks снова
собирают student, получают owner inventory, экспортируют весь выбранный
instance `2026-2027-s1` и проверяют его полностью на host и container.
Этот внутренний instance сохраняет source course ID `java-semester-one`,
три работы numbers/control/arrays и прежние восемь выбранных задач (3/2/3).
Смена внутреннего instance не меняет учебные условия, required/scoring semantics
или внешние Gateway/Moodle входы.

Результаты конкретной operational поставки, exact source/native commits,
полные 45+45 scenarios и release receipt находятся в управляющем комплекте
`java-system-practice-work-20261016/operations/20261017-release-deploy/delivery`.
Для staging нужна именно complete successful container receipt, связанная
с окончательными delivery/source/inventory hashes и опубликованным registry.
Native Git commit и deliveryHash — разные pins. Private checks и полные
отчёты остаются вне native payload.

Восемь native задач не объявляются экспортом всех 103 проектов. Прежняя полная
regression учитывается отдельно при доказанной неизменности project/checker/
runtime bytes. Публикация source или image не заменяет текущую qualification,
hosted CI, server sync и live Moodle acceptance; прежний hosted CI waiver
не переносится на эту ветку. Действующие внешние URLs, ключи и IDs сохраняются.
