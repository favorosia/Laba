@echo off
chcp 65001 > nul
setlocal
cd /d "%~dp0"
set "JDK_HOME=%JAVA_HOME%"
if not defined JDK_HOME for /d %%D in ("%ProgramFiles%\Java\jdk-*") do set "JDK_HOME=%%~fD"
if exist build rmdir /s /q build
mkdir build\classes
for /r src %%F in (*.java) do (
    if defined JDK_HOME ("%JDK_HOME%\bin\javac.exe" -encoding UTF-8 -d build\classes "%%F") else (javac -encoding UTF-8 -d build\classes "%%F")
    if errorlevel 1 exit /b 1
)
if errorlevel 1 exit /b 1
if defined JDK_HOME ("%JDK_HOME%\bin\jar.exe" --create --file game-characters.jar --main-class ru.laba.game.GameApplication -C build\classes .) else (jar --create --file game-characters.jar --main-class ru.laba.game.GameApplication -C build\classes .)
if errorlevel 1 exit /b 1
echo Готово: game-characters.jar
endlocal
