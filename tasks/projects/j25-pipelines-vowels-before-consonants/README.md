# Техническая поставка exr-j25-pipelines-vowels-before-consonants

Frozen `tasks/bank/pipelines.qmd` / `c9d05aea901fb819a5fcbb44bbf8a117caaeb9b2` / cardSHA `2d9a0b4e1999315fc078a9fb37f3dff20b7bf4b644e802edbc02493347833f8c`. Только student/ — public ZIP. Сначала student source compilation, затем ordinary teacher TaskBinding/JUnit compile, затем факты поведения. Study/Example/TaskBinding не являются API студента. Другой private/nested API без внешнего доступа требует ручной привязки/защиты без score; ошибки binding не превращаются в student behavior.

Primary и alternate используют разные package/instance формы; у distinct alternate обычный immutable класс вместо record, у price другое разрешённое HALF_EVEN, у primes массив вместо String. Binding только нормализует представление и реальные вызовы. Никаких private-field reflection/access suppression. Модельный typed array создаётся адаптером, затем сам массив передаётся решению без копирования — сохранность/first identity видимы.

Ручная часть: Stream API; только lower-case Latin input, vowels a/e/i/o/u, каждая группа сортируется, повторы остаются. Public/private tests и expected mutations в tests/scenarios.json; дополнительные wrong sources: global-alphabetic, lost-duplicates. Нет новых out-of-domain inputs, форматов или helper obligations. Никакой регистрации/commit/Windows claim. Shared runner readonly.

