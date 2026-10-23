#!/bin/bash
set -euo pipefail
cd "$(dirname "$0")"
if [[ $# -eq 0 ]]; then read -r -p "Номер варианта (1..50) или all: " choice; else choice="$1"; fi
bash build.sh "$choice"
if [[ "$choice" == "all" ]]; then
    java -Dfile.encoding=UTF-8 -jar build/practice-04-all.jar all
else
    number=$((10#$choice))
    artifact=$(printf 'build/variant-%02d.jar' "$number")
    java -Dfile.encoding=UTF-8 -jar "$artifact"
fi
