# Native knitr cache: независимый review evidence

Root: **APPROVE SCOPED**. Прочитаны все три адаптированных existing drivers,
сохранённый outer result, оба render/completed markers, оба actual inventory
results/README и ZIP verification. Новый render/проверка ZIP не запускались.

Stage создаётся заново, extensions извлекаются из exact Core4a/Download944,
до запуска fixture кешей не копируют. Оба captures используют тот же QMD,
Native `freeze:false/cache:true`, owner/output/profile. Счётчик R находится вне
owner и увеличивается в единственной cached ячейке. Saved markers подтверждают
ledger1 после обоих renders и разные operation UUID. Поэтому это реальное
повторное потребление engine cache, а не повторное чтение одного старого Core
artifact. Возвращение обоих operation callbacks и отдельные completed records
подтверждают поздний consumer/closing lifecycle.

В обоих README сохранены отдельные inline42, таблица и PNG URL. Inventory содержит
starter, README и реальную PNG15196B; driver связывает HTML PNG с inventory bytes,
а сохранённая Python проверка подтверждает обе ZIP по CRC/entry set/bytes.
Проверяются прежние private block/reference sentinels. Разные response UUID в
README допустимы и не превращаются в новую гарантию равных bytes между поколениями.

Единственный outer result: exit0, timeout=false,15.503752s<60,retry0. Обе
Performance записи dataValid=true/coverageComplete=false; initial hash и A1
copy остаются расходами, не объявлены устранёнными. Это подтверждает обычный
последовательный unchanged-input knitr-cache corridor. Project freeze,
межпрофильная свежесть/приватность произвольного текста, external data, Jupyter,
конкурентная запись cache и весь курс этим опытом не приняты. Нового cachekey,
engine или обязательного runtime validator не появилось.
