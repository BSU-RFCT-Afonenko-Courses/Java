# Примеры записей и вариантов данных

Записи связывают компоненты значения с конструктором, чтением и равенством.
Программы создают значения, нормализуют параметры, изменяют разделяемый
массив, делают защитные копии и выбирают ветвь по типу результата.

Команды выполняются из каталога `handbook/examples/records` с JDK 25.
`javac` и `java` должны относиться к этому JDK. Для установленного отдельно
JDK можно использовать полный путь к его командам.

В [архиве записей](../records-examples.zip) исходники находятся в каталоге
`records/`. После распаковки перейдите в него; команды остаются теми же.

## Запуск отдельных опытов

```bash
javac -encoding UTF-8 -d out RecordDemo.java ValueClassDemo.java ConstructorDemo.java ArrayRecordsDemo.java EnumDemo.java SearchDemo.java SearchEvolutionDemo.java grade/Student.java grade/StudentDemo.java grade/ReflectionDemo.java
java -cp out RecordDemo
java -cp out ConstructorDemo
java -cp out ArrayRecordsDemo
```

Остальные классы запускаются той же командой `java -cp out` с именем
класса из таблицы. Объявления точек, массивных значений и результатов поиска
находятся внутри соответствующих классов запуска.

| Исходник | Класс запуска | Наблюдение |
|---|---|---|
| [RecordDemo.java](RecordDemo.java) | `RecordDemo` | Координаты `(2, 3)`: `equals` даёт `true`, `==` — `false`; разные типы записей не равны; `NaN` и знаки нуля |
| [ValueClassDemo.java](ValueClassDemo.java) | `ValueClassDemo` | Обычный класс с явно написанными `equals`, `hashCode` и `toString` |
| [ConstructorDemo.java](ConstructorDemo.java) | `ConstructorDemo` | Интервал включает `4`; обратные границы отвергаются; код `"  A-17  "` нормализуется |
| [ArrayRecordsDemo.java](ArrayRecordsDemo.java) | `ArrayRecordsDemo` | Чтение общего массива после изменений даёт `7` и `9`; защищённый `Scores` сохраняет `80`; равное восстановленное значение имеет такой же хеш и строку |
| [EnumDemo.java](EnumDemo.java) | `EnumDemo` | Именованное состояние, вес `10`, точное имя в `valueOf` |
| [SearchDemo.java](SearchDemo.java) | `SearchDemo` | Два варианта результата; `null` отвергается переключением |
| [SearchEvolutionDemo.java](SearchEvolutionDemo.java) | `SearchEvolutionDemo` | Третий вариант даёт `Matches: 3`; число `1` отвергается его конструктором |
| [grade/Student.java](grade/Student.java), [grade/StudentDemo.java](grade/StudentDemo.java) | `grade.StudentDemo` | Нормализация `id/name`, сравнение всех трёх компонентов, отказ неверного балла и разделителя |
| [grade/ReflectionDemo.java](grade/ReflectionDemo.java) | `grade.ReflectionDemo` | Компоненты `id:String`, `name:String`, `score:int`; имя `grade.Student` и описание примитивного типа |

`grade.Student` принимает ненулевые код и имя. После `strip()` они должны
быть непустыми и не содержать `;`, CR или LF. Балл принадлежит диапазону
0..100. Код сравнивается с учётом регистра. Конструктор проверяет одну
запись; уникальность кодов среди разных записей определяется в каталоге.

В конце `ArrayRecordsDemo` исходный и восстановленный `Scores` дают две
одинаковые строки `Scores[values=[80]]`; сравнение этих строк возвращает
`true`. `equals` для `null`, строки другого типа и записи с другим баллом
возвращает `false`. Представление строится по элементам через
`Arrays.toString`; формат файла или обмена задаётся отдельно.

## Снимок счётчика и контракт отправителя

[DispatchStatsDemo.java](course/examples/dispatch/DispatchStatsDemo.java)
использует реализации [отправителей](../interfaces/README.md).
Он находится в том же пакете `course.examples.dispatch`, что и
`CountingSender` и `Counted`, доступные внутри пакета.

Для этого опыта дополнительно скачайте [архив отправителей](../interfaces-examples.zip)
и распакуйте его рядом с `records/`. Нужны два соседних каталога:

```text
examples/
├── records/
│   └── course/examples/dispatch/DispatchStatsDemo.java, SenderContractDemo.java
└── interfaces/
    └── course/examples/dispatch/Sender.java и остальные исходники пакета
```

Из каталога `records/`:

```bash
javac -encoding UTF-8 -d out ../interfaces/course/examples/dispatch/*.java course/examples/dispatch/*.java
java -cp out course.examples.dispatch.DispatchStatsDemo
java -cp out course.examples.dispatch.SenderContractDemo
```

`DispatchStatsDemo` выводит две строки `3` и `2`. Отправитель обработал
третье сообщение после создания снимка; в компоненте `DispatchStats` сохранилось число `2`.
Оба объекта доступны через интерфейс `Counted` с методом `count()`.

[SenderContractDemo.java](course/examples/dispatch/SenderContractDemo.java)
показывает усиление предусловия. Общий клиент `sendEmpty(Sender)` передаёт
пустую строку, разрешённую контрактом `Sender`. `RecordingSender` сохраняет
один символ завершения строки; намеренно неверная реализация
`NonEmptySender` отвергает тот же вход. Вывод второго опыта:

```text
1
Пустая строка отвергнута
```

Это отдельное нарушение подстановки: отказ на прежнем допустимом входе.
В лекционном `SilentDocument` вызов был допустим, но возвращал запрещённый
результат.

## Ограничения, обнаруживаемые при компиляции

Файлы в `rejected` содержат отдельные недопустимые объявления.
Их компилируют по одному, чтобы увидеть причину отказа:

```bash
javac -encoding UTF-8 -d out rejected/ExtraState.java
javac -encoding UTF-8 -d out rejected/RecordSubclass.java
javac -encoding UTF-8 -d out rejected/ComponentAssignment.java
```

- [ExtraState.java](rejected/ExtraState.java): дополнительное поле экземпляра у записи.
- [RecordSubclass.java](rejected/RecordSubclass.java): попытка расширить неявно финальную запись.
- [ComponentAssignment.java](rejected/ComponentAssignment.java): присваивание компонентному полю в компактном конструкторе.

У `SearchEvolutionDemo` можно удалить ветвь `case Ambiguous` и повторить
компиляцию. Оставшиеся две ветви перестанут покрывать все разрешённые типы.

## Связанные программы лекции

[Опыты наследования](../inheritance/README.md) содержат документы,
перегрузку, сокрытие поля и статического метода, правила переопределения,
абстрактную площадь и ранний динамический вызов из конструктора.
[Опыты интерфейсов](../interfaces/README.md) содержат `Sender`, `Reminder`,
префикс, счётчик, `sendAll` и конфликты методов по умолчанию.
