@echo off
setlocal
cd /d "%~dp0"
set "JDK_HOME=%JAVA_HOME%"
if not defined JDK_HOME for /d %%D in ("%ProgramFiles%\Java\jdk-*") do set "JDK_HOME=%%~fD"
if exist build rmdir /s /q build
mkdir build\classes
if defined JDK_HOME (
  "%JDK_HOME%\bin\javac.exe" --release 17 -encoding UTF-8 -d build\classes src\ru\laba\appliances\*.java
  if errorlevel 1 exit /b 1
  "%JDK_HOME%\bin\jar.exe" --create --file home-appliances.jar --main-class ru.laba.appliances.ApplianceApplication -C build\classes .
) else (
  javac --release 17 -encoding UTF-8 -d build\classes src\ru\laba\appliances\*.java
  if errorlevel 1 exit /b 1
  jar --create --file home-appliances.jar --main-class ru.laba.appliances.ApplianceApplication -C build\classes .
)
if errorlevel 1 exit /b 1
echo Built home-appliances.jar
endlocal
