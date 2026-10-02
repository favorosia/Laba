@echo off
setlocal
cd /d "%~dp0"
set "JDK_HOME=%JAVA_HOME%"
if not defined JDK_HOME for /d %%D in ("%ProgramFiles%\Java\jdk-*") do set "JDK_HOME=%%~fD"
if exist build rmdir /s /q build
mkdir build\classes
if defined JDK_HOME (
  "%JDK_HOME%\bin\javac.exe" -encoding UTF-8 -d build\classes src\ru\laba\attendance\*.java
  if errorlevel 1 exit /b 1
  "%JDK_HOME%\bin\jar.exe" --create --file attendance-journal.jar --main-class ru.laba.attendance.AttendanceApplication -C build\classes .
) else (
  javac -encoding UTF-8 -d build\classes src\ru\laba\attendance\*.java
  if errorlevel 1 exit /b 1
  jar --create --file attendance-journal.jar --main-class ru.laba.attendance.AttendanceApplication -C build\classes .
)
if errorlevel 1 exit /b 1
echo Build complete: attendance-journal.jar
endlocal
