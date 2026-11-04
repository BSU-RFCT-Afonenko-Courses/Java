# Official preflight: Source candidate, isolated control PASS

База Platform `9b82e29bffd77cc462ffdef23cd3b96ca041db92`, `/home/tolya/JavaCodex/java-release-20261110/platform`, до edits clean. Прямой GO root расширил scope до трёх prod companions. Candidate не является image/production acceptance; commit/push/mainstate отсутствуют.

## Минимальная дельта

- `images/java25-grader/grade.py`: прежний второй JVM process `com.sun.tools.javac.Main -version` и его diagnostic выполняются только вне `PL_CONTAINMENT=official`. Первый `java -version`, проверка JDK25 runtime и возвращаемый `output.strip()` сохранены. Host compiler diagnostic не менялся.
- `tools/prepare_native_toolchain.py`: только существующий `RUNNER` pin.
- `runtime-profiles.json`: только две существующие Java `runnerSha256`; native third profile создаётся existing baked view от Java profile. Image digests, published history и fish profile не изменены.
- `tests/test_gradle_review_fixes.py`: адаптирован один существующий `RecipeMembershipTests.test_complete_native_pins_and_plain_profiles_remain_admissible`, без нового framework/cases. Existing три профиля проверяют прежний return через mocked official `_run`, который допускает только первую JVM command; host `javac 24` должен дать прежний controlled refusal. `_verify_landlock_helper` mocked только в official unit branch.

Ровно один grade.py SHA вычислен для согласования существующих pins: `5023a63140a721f0b72dcd0eb04d3f2818f6907c58ddecaf0a49543e44e83e98`. Это compatibility coupling текущего release Source, не обоснование вечной необходимости hash admission. Existing per-job runner/bundle/shared byte hashing сохранены и остаются под вопросом по [карте](pattern-pass-platform-20261104.md). Sole compilation/scoring/source selection/student files/sandbox roles/grading boundaries не изменены. Нижний слой и пределы installer evidence описаны в [Source plan](jdk-gradle-next-slice-20261104.md); новых guarantees не вводилось.

Exact diff: [patch](jdk-official-preflight-20261104.patch). Предварительный `git diff --check` PASS. После первого отказа production Source не менялся; ниже отражено отдельно разрешённое test-only исправление fixture.

## Единственный разрешённый control

Команда: `PYTHONPATH=tests PYTHONDONTWRITEBYTECODE=1 python3 -m unittest test_gradle_review_fixes.RecipeMembershipTests.test_complete_native_pins_and_plain_profiles_remain_admissible -v`.

**ERROR**, elapsed **0.2417 s**, cap15 s/retry0. До входа в preflight `registry()` вызывает existing `preparation._fish_view(ROOT)`; `read_regular` → `safe_parent('/')` отклоняет filesystem identity: `ValueError: Unsafe parent identity: /`. Это shared fixture admission на данном host, а не FAIL нового official/host behavior. Оба behavioral assertions не достигнуты; успех не заявляется. Existing helper/guards не ослаблялись, alternative probe и retry не запускались.

[Command/elapsed/exit JSON](jdk-official-preflight-check-20261104.json), [полный первый log](jdk-official-preflight-20261104.log). Реальные JDK/Gradle/Docker/render/network/build/wholeJDK hashes не запускались. Existing fixture registry делает свои обычные runtime bundle/shared digests; нового hash-pass механизма не добавлено.

Root отдельно разрешил значимое test-only исправление existing fixture и один changed control≤5 s: в пятом файле `tests/gradle_unit_fixtures.py` synthetic `registry()` вызывает существующий `_fish_view(ROOT, read=lambda path: path.read_bytes())`. Раньше synthetic fixture включал реальную owner-check границу host filesystem, не относящуюся к проверяемому preflight behavior. Read port уже поддержан API, bytes читаются те же; production `read_regular`/`safe_parent`, installer и Native image admission не менялись. Нет нового flag, copied registry или monkeypatch owner guard.

После этой Source-дельты тот же единственный case **PASS**, elapsed **0.1933 s**, cap5 s/retry0; official допускает только первый version process и сохраняет toolchain return, host `javac 24` даёт прежний refusal. [Final command/elapsed JSON](jdk-official-preflight-final-check-20261104.json), [final log](jdk-official-preflight-final-20261104.log). Первый filesystem ERROR выше и его evidence сохранены. Итого две разрешённые изменённые итерации 0.4350 s, не unchanged retry. Production grade.py SHA после test-only fix не пересчитывался. Final `git diff --check` PASS.

Все пять файлов заморожены после PASS, exact patch обновлён. Candidate готов для independent Source review; это isolated synthetic behavior evidence, не actual image/production acceptance.

## Own origin

[Admission](jdk-official-preflight-admission-20261104.json): исходные chat `01a1a54c-ab39-7a83-808d-a190428ff628` / turn `01a1a54c-ab6a-7412-894c-270be9b9aac3` сохранены; все 18 первичных turn_context top/nested согласованы `gpt-6.1-sol/medium`. Standard назначен, hidden routing не утверждается; globals не менялись.
