#!/bin/bash
set -euo pipefail
cd "$(dirname "$0")"
bash build.sh all
java -Dfile.encoding=UTF-8 -jar build/practice-04-all.jar all
mkdir -p build/tests
javac -encoding UTF-8 --release 17 -cp build/all/classes -d build/tests tests/EdgeCases.java
java -Dfile.encoding=UTF-8 -cp "build/all/classes:build/tests" EdgeCases
