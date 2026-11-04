# Независимое review подготовки backend Performance

4 ноября 2026, root Sol6.1/high: **APPROVE**, база `94f32fb73778a86f20b7bb970e706cb5b4f0a775`. Один production файл `src/launchers/setup.ts` и текущая документация. [Отчёт](performance-backend-copy-20261104.md).

Удалена вся отдельная backend byte attestation: два чтения/hash выбранных деревьев, equality gate и соответствующий receipt. Состав копии, actual copied launch paths, real Deno/Pandoc, quoting, copy containment и Pandoc-init transformation/receipt сохранены. Consumer читает типизированный массив preparation; обязательного backend entry/его equality вне удалённого gate не найдено. Старые run.json не меняются, fake hash не подставлен.

Смысловое изменение раскрыто: прямой caller больше не получает отказ из-за старых destination файлов либо source/copy divergence; production callers создают fresh owned staging. Internal file links больше не отвергаются предшествующим hash, но остаются в существующем безопасном copy contract; external/special entries не разрешены. Старый double hash не был lock/atomic snapshot и не защищал последующую мутацию.

Affected offline compiler PASS0.622s после установленной коррекции лишнего manual режима; два существующих pure copy/metadata case PASS. Полный setup/native readiness и measured speedup не доказаны. Reviewer не повторял успешные cases; новых Source изменений нет. Отдельной новой qualification вместо удалённой не требуется.

Root primary interval от `01a1a4b3-8fd3-7be3-baf7-ce0cc5c60016`: contexts7160/7983/8022/9120/9348 top/nested Sol6.1/high PASS, globals unchanged.
