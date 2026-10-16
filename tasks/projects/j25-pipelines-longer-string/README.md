# Техническая поставка exr-j25-pipelines-longer-string

Frozen `tasks/bank/pipelines.qmd` / `c9d05aea901fb819a5fcbb44bbf8a117caaeb9b2` / cardSHA `32341188f1bdcafdba1d867b9f868c452fa92ea59d763d4235019b1de4f9cac9`. Только student/ — public ZIP. Сначала student source compilation, затем ordinary teacher TaskBinding/JUnit compile, затем факты поведения. Study/Example/TaskBinding не являются API студента. Другой private/nested API без внешнего доступа требует ручной привязки/защиты без score; ошибки binding не превращаются в student behavior.

Primary и alternate используют разные package/instance формы; у distinct alternate обычный immutable класс вместо record, у price другое разрешённое HALF_EVEN, у primes массив вместо String. Binding только нормализует представление и реальные вызовы. Никаких private-field reflection/access suppression. Модельный typed array создаётся адаптером, затем сам массив передаётся решению без копирования — сохранность/first identity видимы.

Ручная часть: Собственный функциональный интерфейс двух String→String, реализация лямбдой. UTF16 char length; при равенстве значение первой строки, identity не требуется. Public/private tests и expected mutations в tests/scenarios.json; дополнительные wrong sources: second-tie, codepoint-length. Нет новых out-of-domain inputs, форматов или helper obligations. Никакой регистрации/commit/Windows claim. Shared runner readonly.

