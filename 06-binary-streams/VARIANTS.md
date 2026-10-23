# Варианты и объяснения

| № | Класс | Задание |
|---|---|---|
| 1 | `ChunkFileCopier` | Копирование блоками |
| 2 | `ByteAlternatingMerger` | Чередование блоков двух файлов |
| 3 | `PositionSplitter` | Разделение по чётности позиции |
| 4 | `ByteFilter` | Удаление запрещённых байтов |
| 5 | `ByteReplacer` | Замена значения байта |
| 6 | `ByteHalver` | Беззнаковое деление байта на два |
| 7 | `MultiKeyXorCipher` | XOR с циклическим ключом |
| 8 | `BitInverter` | Побитовое отрицание |
| 9 | `PositiveIntFilter` | Фильтрация отрицательных int |
| 10 | `DoubleAverageCalculator` | Среднее арифметическое double |
| 11 | `ByteDuplicator` | Дублирование байтов |
| 12 | `IndexMultiplier` | Умножение int на индекс |
| 13 | `ByteMinMaxFinder` | Минимальный и максимальный байт |
| 14 | `ZeroByteCounter` | Количество нулевых байтов |
| 15 | `EvenIndexByteRemover` | Удаление чётных позиций |
| 16 | `ByteReverser` | Обратный порядок байтов |
| 17 | `BytePairSwapper` | Перестановка соседних байтов |
| 18 | `ByteSorter` | Сортировка байтов |
| 19 | `ConsecutiveDuplicateRemover` | Удаление повторов в сериях |
| 20 | `RleEncoder` | Сжатие серий RLE |
| 21 | `AdjacentByteDifferencer` | Абсолютные разности соседних байтов |
| 22 | `IntArrayWriter` | Массив int с длиной |
| 23 | `IntArraySumReader` | Сумма последовательности int |
| 24 | `DoubleMinMaxReader` | Минимум и максимум double |
| 25 | `DoubleRangeFilter` | Фильтрация double по диапазону |
| 26 | `BytePairSorter` | Сортировка внутри пар |
| 27 | `CharCaseConverter` | Заглавные латинские буквы |
| 28 | `RunningMaximumWriter` | Текущий максимум int |
| 29 | `BooleanArrayWriter` | Массив boolean с длиной |
| 30 | `BooleanStatsReader` | Статистика true и false |
| 31 | `ShortToIntConverter` | Расширение short до int |
| 32 | `ByteCircularShifter` | Циклический сдвиг вправо |
| 33 | `SortedIntMerger` | Слияние отсортированных int |
| 34 | `IntEvenOddSplitter` | Разделение int на чётные и нечётные |
| 35 | `MatrixWriter` | Матрица int |
| 36 | `EvenByteFilter` | Фильтрация чётных значений байтов |
| 37 | `BitwiseAndMerger` | Побитовое AND |
| 38 | `BitwiseOrMerger` | Побитовое OR |
| 39 | `BinaryDiffWriter` | XOR двух файлов с остатком |
| 40 | `BinaryFileInserter` | Вставка файла по позиции |
| 41 | `ByteRangeRemover` | Удаление диапазона |
| 42 | `ByteRangeExtractor` | Извлечение диапазона |
| 43 | `BytePatternCounter` | Непересекающиеся вхождения образца |
| 44 | `LongestRunFinder` | Самая длинная серия |
| 45 | `StringArrayBinaryWriter` | Массив строк writeUTF/readUTF |
| 46 | `NthByteRemover` | Удаление каждого N-го байта |
| 47 | `CaesarByteCipher` | Шифр Цезаря по модулю 256 |
| 48 | `ByteAbsConverter` | Модуль знакового byte |
| 49 | `FilePartSplitter` | Разделение на N частей |
| 50 | `NormalizedHistogramWriter` | Нормированная гистограмма |

## 01. ChunkFileCopier

read возвращает фактическое число байтов. В write передаётся именно оно: последний неполный блок не дополняется мусором.

Исходник: `src/task01/ChunkFileCopier.java`. Демонстрация: `src/task01/Demo.java`.

## 02. ByteAlternatingMerger

Чередуем блоки до N байтов. После конца одного файла продолжаем читать другой.

Исходник: `src/task02/ByteAlternatingMerger.java`. Демонстрация: `src/task02/Demo.java`.

## 03. PositionSplitter

Флаг чётности переключается после каждого байта и выбирает выходной поток.

Исходник: `src/task03/PositionSplitter.java`. Демонстрация: `src/task03/Demo.java`.

## 04. ByteFilter

Перебираем запрещённые значения. & 255 переводит byte в беззнаковый int. Записываем только разрешённые байты.

Исходник: `src/task04/ByteFilter.java`. Демонстрация: `src/task04/Demo.java`.

## 05. ByteReplacer

Сравниваем текущий байт с заменяемым и записываем новое либо исходное значение.

Исходник: `src/task05/ByteReplacer.java`. Демонстрация: `src/task05/Demo.java`.

## 06. ByteHalver

read возвращает 0..255, поэтому value / 2 даёт беззнаковое деление.

Исходник: `src/task06/ByteHalver.java`. Демонстрация: `src/task06/Demo.java`.

## 07. MultiKeyXorCipher

Индекс ключа идёт по кругу. Повторный XOR тем же ключом восстанавливает данные.

Исходник: `src/task07/MultiKeyXorCipher.java`. Демонстрация: `src/task07/Demo.java`.

## 08. BitInverter

~ инвертирует биты, write сохраняет младшие восемь бит.

Исходник: `src/task08/BitInverter.java`. Демонстрация: `src/task08/Demo.java`.

## 09. PositiveIntFilter

readInt читаем по четыре байта, сохраняем значения >= 0, включая ноль.

Исходник: `src/task09/PositiveIntFilter.java`. Демонстрация: `src/task09/Demo.java`.

## 10. DoubleAverageCalculator

Складываем double, делим на количество, один раз записываем результат после цикла.

Исходник: `src/task10/DoubleAverageCalculator.java`. Демонстрация: `src/task10/Demo.java`.

## 11. ByteDuplicator

Для каждого read выполняем два write с одним значением.

Исходник: `src/task11/ByteDuplicator.java`. Демонстрация: `src/task11/Demo.java`.

## 12. IndexMultiplier

Индекс i начинается с нуля. Умножаем число на индекс перед записью.

Исходник: `src/task12/IndexMultiplier.java`. Демонстрация: `src/task12/Demo.java`.

## 13. ByteMinMaxFinder

Обновляем минимум и максимум среди 0..255 и после чтения записываем два байта.

Исходник: `src/task13/ByteMinMaxFinder.java`. Демонстрация: `src/task13/Demo.java`.

## 14. ZeroByteCounter

При нуле увеличиваем счётчик, после цикла записываем один int.

Исходник: `src/task14/ZeroByteCounter.java`. Демонстрация: `src/task14/Demo.java`.

## 15. EvenIndexByteRemover

Флаг переключается после каждого байта; записываем только нечётные позиции с нуля.

Исходник: `src/task15/EvenIndexByteRemover.java`. Демонстрация: `src/task15/Demo.java`.

## 16. ByteReverser

Читаем массив с проверкой ограничения, затем идём с конца к началу.

Исходник: `src/task16/ByteReverser.java`. Демонстрация: `src/task16/Demo.java`.

## 17. BytePairSwapper

Читаем пару и записываем в обратном порядке; одиночный хвост сохраняем.

Исходник: `src/task17/BytePairSwapper.java`. Демонстрация: `src/task17/Demo.java`.

## 18. ByteSorter

Сортировка подсчётом: частоты 256 значений, затем вывод по возрастанию с учётом частот.

Исходник: `src/task18/ByteSorter.java`. Демонстрация: `src/task18/Demo.java`.

## 19. ConsecutiveDuplicateRemover

Предыдущее значение начинается с -1. Записываем только байт, отличающийся от предыдущего.

Исходник: `src/task19/ConsecutiveDuplicateRemover.java`. Демонстрация: `src/task19/Demo.java`.

## 20. RleEncoder

Считаем длину серии. При смене значения пишем byte значения и int длины. После цикла сохраняем последнюю серию. decode читает пары и разворачивает их.

Исходник: `src/task20/RleEncoder.java`. Демонстрация: `src/task20/Demo.java`.

## 21. AdjacentByteDifferencer

Храним предыдущий байт и для каждого следующего записываем модуль разности. Для непустого входа выход на байт короче.

Исходник: `src/task21/AdjacentByteDifferencer.java`. Демонстрация: `src/task21/Demo.java`.

## 22. IntArrayWriter

Сначала число элементов, затем значения. При чтении получаем длину, выделяем массив и заполняем его.

Исходник: `src/task22/IntArrayWriter.java`. Демонстрация: `src/task22/Demo.java`.

## 23. IntArraySumReader

Складываем все int; после цикла записываем одну сумму.

Исходник: `src/task23/IntArraySumReader.java`. Демонстрация: `src/task23/Demo.java`.

## 24. DoubleMinMaxReader

После каждого double обновляем минимум и максимум, затем записываем их в таком порядке.

Исходник: `src/task24/DoubleMinMaxReader.java`. Демонстрация: `src/task24/Demo.java`.

## 25. DoubleRangeFilter

Сохраняем значение, если оно одновременно >= min и <= max. Границы включены.

Исходник: `src/task25/DoubleRangeFilter.java`. Демонстрация: `src/task25/Demo.java`.

## 26. BytePairSorter

Читаем пару, пишем минимум и максимум; одиночный хвост сохраняем.

Исходник: `src/task26/BytePairSorter.java`. Демонстрация: `src/task26/Demo.java`.

## 27. CharCaseConverter

readChar читает два байта. Только 'a'..'z' сдвигаются на разность 'A' - 'a'. Кириллица сохраняется.

Исходник: `src/task27/CharCaseConverter.java`. Демонстрация: `src/task27/Demo.java`.

## 28. RunningMaximumWriter

Начинаем с Integer.MIN_VALUE, обновляем и записываем максимум после каждого числа.

Исходник: `src/task28/RunningMaximumWriter.java`. Демонстрация: `src/task28/Demo.java`.

## 29. BooleanArrayWriter

Writer записывает длину и writeBoolean. Отдельный Reader создаёт массив и читает readBoolean.

Исходник: `src/task29/BooleanArrayWriter.java`. Демонстрация: `src/task29/Demo.java`.

## 30. BooleanStatsReader

По значению boolean увеличиваем один из двух счётчиков. Записываем сначала true, затем false.

Исходник: `src/task30/BooleanStatsReader.java`. Демонстрация: `src/task30/Demo.java`.

## 31. ShortToIntConverter

readShort возвращает знаковый short. При передаче в writeInt он расширяется до int с сохранением знака.

Исходник: `src/task31/ShortToIntConverter.java`. Демонстрация: `src/task31/Demo.java`.

## 32. ByteCircularShifter

Приводим сдвиг по модулю длины. Сначала записываем хвост длины offset, затем начало массива.

Исходник: `src/task32/ByteCircularShifter.java`. Демонстрация: `src/task32/Demo.java`.

## 33. SortedIntMerger

Храним по текущему числу из двух потоков. Записываем меньшее, продвигаем только его поток. Входы должны быть отсортированы.

Исходник: `src/task33/SortedIntMerger.java`. Демонстрация: `src/task33/Demo.java`.

## 34. IntEvenOddSplitter

value % 2 == 0 выбирает поток чётных чисел, иначе поток нечётных.

Исходник: `src/task34/IntEvenOddSplitter.java`. Демонстрация: `src/task34/Demo.java`.

## 35. MatrixWriter

Сначала строки и столбцы, затем элементы построчно. Чтение создаёт массив и заполняет его двумя вложенными циклами.

Исходник: `src/task35/MatrixWriter.java`. Демонстрация: `src/task35/Demo.java`.

## 36. EvenByteFilter

Сохраняем значения с нулевым остатком от деления на два. Позиция не имеет значения.

Исходник: `src/task36/EvenByteFilter.java`. Демонстрация: `src/task36/Demo.java`.

## 37. BitwiseAndMerger

Читаем по байту из двух потоков и пишем x & y. Сначала проверяем равенство размеров.

Исходник: `src/task37/BitwiseAndMerger.java`. Демонстрация: `src/task37/Demo.java`.

## 38. BitwiseOrMerger

Читаем по байту из двух потоков и пишем x | y. Сначала проверяем равенство размеров.

Исходник: `src/task38/BitwiseOrMerger.java`. Демонстрация: `src/task38/Demo.java`.

## 39. BinaryDiffWriter

Для двух существующих байтов пишем XOR, остаток одного потока копируем без изменений.

Исходник: `src/task39/BinaryDiffWriter.java`. Демонстрация: `src/task39/Demo.java`.

## 40. BinaryFileInserter

Копируем префикс до позиции, вставляемый файл, затем остаток исходного файла.

Исходник: `src/task40/BinaryFileInserter.java`. Демонстрация: `src/task40/Demo.java`.

## 41. ByteRangeRemover

Сохраняем байты вне включительного диапазона.

Исходник: `src/task41/ByteRangeRemover.java`. Демонстрация: `src/task41/Demo.java`.

## 42. ByteRangeExtractor

Сохраняем только байты внутри включительного диапазона.

Исходник: `src/task42/ByteRangeExtractor.java`. Демонстрация: `src/task42/Demo.java`.

## 43. BytePatternCounter

Окно длины образца сбрасываем после совпадения. Иначе сдвигаем на один байт, чтобы не пропустить частичный префикс.

Исходник: `src/task43/BytePatternCounter.java`. Демонстрация: `src/task43/Demo.java`.

## 44. LongestRunFinder

Храним длину текущей серии и лучший ответ. Обновляем при строгом улучшении, поэтому при равенстве остаётся первая серия.

Исходник: `src/task44/LongestRunFinder.java`. Демонстрация: `src/task44/Demo.java`.

## 45. StringArrayBinaryWriter

Сначала число строк, затем writeUTF каждой строки; readUTF восстанавливает строку по внутренней длине.

Исходник: `src/task45/StringArrayBinaryWriter.java`. Демонстрация: `src/task45/Demo.java`.

## 46. NthByteRemover

Счётчик идёт от 1 до N. На N пропускаем байт и сбрасываем счётчик.

Исходник: `src/task46/NthByteRemover.java`. Демонстрация: `src/task46/Demo.java`.

## 47. CaesarByteCipher

encrypt прибавляет сдвиг, decrypt вычитает. & 255 оставляет младшие 8 бит (модуль 256).

Исходник: `src/task47/CaesarByteCipher.java`. Демонстрация: `src/task47/Demo.java`.

## 48. ByteAbsConverter

Приводим к знаковому byte, считаем модуль как int и снова приводим к byte. Поэтому -128 остаётся -128.

Исходник: `src/task48/ByteAbsConverter.java`. Демонстрация: `src/task48/Demo.java`.

## 49. FilePartSplitter

Части получают size/N байтов, первые size%N частей ещё по одному. Ровно N файлов с разницей размеров максимум в байт.

Исходник: `src/task49/FilePartSplitter.java`. Демонстрация: `src/task49/Demo.java`.

## 50. NormalizedHistogramWriter

Считаем частоты long[256], делим каждую на число байтов, пишем 256 float.

Исходник: `src/task50/NormalizedHistogramWriter.java`. Демонстрация: `src/task50/Demo.java`.
