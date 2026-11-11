# Установка общих расширений

Опубликованные расширения устанавливаются штатными командами Quarto.
Установленные `_extensions` и их `_extension.yml` хранятся в Git;
render и CI используют эти файлы без сетевого обновления.

В каждом из шести самостоятельных проектов (`.`, `theory`, `tasks`,
`lectures`, `practice`, `handbook`) выполните:

```sh
quarto add Afonenko-Course-Tools/quarto-course@v9.4.0 --no-prompt
quarto add Afonenko-Course-Tools/quarto-reference-catalog@v3.3.0 --no-prompt
```

Core устанавливается целым bundle: `course-core`, `course-presentation`,
`course-navigation`, `course-theme`. Дополнительный Navigation в проекте сам по себе не
включает фильтр: активные расширения выбирает конфигурация Quarto.
Namespace установки — `_extensions/Afonenko-Course-Tools/`.
Команды выполняйте из каталога нужного проекта, включая вложенные проекты.

В корне курса установите Site и Download:

```sh
quarto add Afonenko-Course-Tools/quarto-project-publish@v6.1.0 --no-prompt
quarto add Afonenko-Course-Tools/quarto-project-download@v3.6.0 --no-prompt
```

Из `tasks` установите PrairieLearn и Download:

```sh
quarto add Afonenko-Course-Tools/quarto-course-prairielearn@v9.1.0 --no-prompt
quarto add Afonenko-Course-Tools/quarto-project-download@v3.6.0 --no-prompt
```

Для следующего выпуска используйте `quarto add` с выбранным тегом либо
`quarto update extension Afonenko-Course-Tools/quarto-course@v9.4.0`,
подставив нужный release. Команда без `@tag` получает ветку `main`. Перед обновлением сохраните рабочие изменения.
Проверьте Git diff, выполните `go-task build` и закоммитьте установленную
поставку. Каждый независимый проект обновляется явно; точный прежний subset
multi-package bundle собственным installer не восстанавливается.

## Инструменты сборки

Platform и Performance — обычные Git submodules: URL находятся в
[.gitmodules](../.gitmodules), commits — в gitlinks курса.

```sh
git submodule update --init
```

`go-task bootstrap` выполняет ту же команду. Для явного обновления инструмента
получите нужный Git ref в его каталоге, выполните checkout и закоммитьте
изменившийся gitlink. Platform по умолчанию расположен в `.platform`;
`PLATFORM_ROOT` позволяет явно выбрать developer checkout.
Performance используется CI на Ubuntu 24.04 для подготовки GNU mv с
`--no-copy --update=none-fail`; bootstrap сохраняет системные пакеты.
Локальная сборка использует подходящий `mv` из PATH.
