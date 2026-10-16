# Техническая поставка exr-j25-pipelines-price-transformation

Frozen `tasks/bank/pipelines.qmd` / `c9d05aea901fb819a5fcbb44bbf8a117caaeb9b2` / cardSHA `766a3dc3b1512990d55b335a4627545ed739e4d91bf8fc64fb9aec72d8d89b75`. Только student/ — public ZIP. Сначала student source compilation, затем ordinary teacher TaskBinding/JUnit compile, затем факты поведения. Study/Example/TaskBinding не являются API студента. Другой private/nested API без внешнего доступа требует ручной привязки/защиты без score; ошибки binding не превращаются в student behavior.

Primary и alternate используют разные package/instance формы; у distinct alternate обычный immutable класс вместо record, у price другое разрешённое HALF_EVEN, у primes массив вместо String. Binding только нормализует представление и реальные вызовы. Никаких private-field reflection/access suppression. Модельный typed array создаётся адаптером, затем сам массив передаётся решению без копирования — сохранность/first identity видимы.

Ручная часть: Представление/rounding выбирает решение и объясняет заранее. Primary BigDecimal HALF_UP; alternate BigDecimal HALF_EVEN, binding.expected policy отражает собственный выбор, не навязывает его студентам. Public/private tests и expected mutations в tests/scenarios.json; дополнительные wrong sources: tax-only, not-rounded. Нет новых out-of-domain inputs, форматов или helper obligations. Никакой регистрации/commit/Windows claim. Shared runner readonly.

