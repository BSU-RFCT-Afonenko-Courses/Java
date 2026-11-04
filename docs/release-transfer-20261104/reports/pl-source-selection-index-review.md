# Независимый обзор sourceRows index

Координатор `/root` не писал implementation. Прочитаны полный diff `5a04576e..35496cdd547d6ad2263fe171e451fb251c793c83`, исходный per-file caller и report исполнителя.

Соответствие и качество: принято, actionable замечаний нет. Production создаёт закрытый Map один раз в projectNativeChecks и передаёт отдельные file/submission inputs в closure. Standalone nativeRawSourceSelection сохраняет прежний массивный контракт. Duplicate/unsafe sourceRows проверяются на подготовке; каждый file по-прежнему проходит schema/path/size/hash/membership и v1/v2 shared-origin правила. Map не опубликован и не даёт Core authority; global cache/нового resolver нет.

Фактическое устранение повтора — F×S → Q×S + F lookup, не обещание одного индекса на весь курс или измеренного ускорения. Raw-origin/reference/typecheck результаты исполнителя переиспользованы, не выполнялись повторно. Пока в Java установлен прежний принятый PL5a04576e; обновление candidate на35496cdd — отдельная адресная установка.
