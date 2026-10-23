#!/bin/bash
set -euo pipefail
cd "$(dirname "$0")"
choice="${1:-all}"
if [[ "$choice" != "all" ]]; then
    case "$choice" in
        ''|*[!0-9]*) echo "Нужен номер 1..50 или all" >&2; exit 1 ;;
    esac
    if [[ ${#choice} -gt 2 ]]; then echo "Номер должен быть 1..50" >&2; exit 1; fi
    number=$((10#$choice))
    if (( number < 1 || number > 50 )); then echo "Номер должен быть 1..50" >&2; exit 1; fi
    choice=$(printf '%02d' "$number")
fi
command -v javac >/dev/null || { echo "Установите JDK 17 или новее (см. README)." >&2; exit 1; }
mkdir -p "build/$choice/classes"
if [[ "$choice" == "all" ]]; then
    find src -name '*.java' | sort > "build/$choice/sources.txt"
    main=Main
    artifact=build/practice-04-all.jar
else
    find src/common "src/task$choice" -name '*.java' | sort > "build/$choice/sources.txt"
    main="task$choice.Demo"
    artifact="build/variant-$choice.jar"
fi
javac -encoding UTF-8 --release 17 -d "build/$choice/classes" @"build/$choice/sources.txt"
jar --create --file "$artifact" --main-class "$main" -C "build/$choice/classes" .
echo "Готово: $artifact"
echo "Запуск: java -Dfile.encoding=UTF-8 -jar $artifact"
