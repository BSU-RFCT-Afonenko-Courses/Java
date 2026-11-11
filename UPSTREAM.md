# Происхождение технической поставки

Общие расширения устанавливаются штатными `quarto add/update` из
опубликованных версий. Порядок и каталоги описаны в
[инструкции](docs/provider-installation.md). Установленные `_extensions`
хранятся в Git, версии пакетов указаны в `_extension.yml`.

Исходные зависимости инструментов закреплены Git submodules в
[.gitmodules](.gitmodules). `git submodule update --init` получает выбранные
курсом commits. Platform отвечает за Native export и проверки;
Performance предоставляет CI bootstrap GNU mv.

Обычный CI выполняет `go-task build`: проверку служебных исходников,
student render и полный Native export. Готовые artifacts проверяются
отдельными declared/delivery jobs без повторной сборки. Verify получает
фактический Platform HEAD build job и проверяет его совпадение.
Результаты контейнерного оценивания устанавливаются этими отдельными
проверками; один успешный render их не подтверждает.

Runtime registry берётся из выбранной Platform. Версии системных инструментов
задаёт среда и штатные setup actions CI; требования Quarto находятся в
package descriptors. Собственный dependency manager курса не используется.

История прежних поставок доступна в
[точном снимке Git](https://github.com/BSU-RFCT-Afonenko-Courses/Java/tree/f5d97be630c92f187f78ac9903b3c48ec03f9c99/docs).
Её hashes, receipts и команды относятся к прежним этапам.
