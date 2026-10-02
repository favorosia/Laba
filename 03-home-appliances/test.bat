@echo off
setlocal
cd /d "%~dp0"
set "JDK_HOME=%JAVA_HOME%"
if not defined JDK_HOME for /d %%D in ("%ProgramFiles%\Java\jdk-*") do set "JDK_HOME=%%~fD"
set "JUNIT_JAR=tools\junit-platform-console-standalone-1.11.4.jar"
if not exist tools mkdir tools
if not exist "%JUNIT_JAR%" curl.exe --fail --location --output "%JUNIT_JAR%" https://repo.maven.apache.org/maven2/org/junit/platform/junit-platform-console-standalone/1.11.4/junit-platform-console-standalone-1.11.4.jar
if errorlevel 1 exit /b 1
if exist build\test-classes rmdir /s /q build\test-classes
mkdir build\test-classes
if defined JDK_HOME (
  "%JDK_HOME%\bin\javac.exe" --release 17 -encoding UTF-8 -cp "%JUNIT_JAR%" -d build\test-classes src\ru\laba\appliances\*.java test\ru\laba\appliances\*.java
  if errorlevel 1 exit /b 1
  "%JDK_HOME%\bin\java.exe" -jar "%JUNIT_JAR%" execute --class-path build\test-classes --scan-class-path
) else (
  javac --release 17 -encoding UTF-8 -cp "%JUNIT_JAR%" -d build\test-classes src\ru\laba\appliances\*.java test\ru\laba\appliances\*.java
  if errorlevel 1 exit /b 1
  java -jar "%JUNIT_JAR%" execute --class-path build\test-classes --scan-class-path
)
if errorlevel 1 exit /b 1
endlocal
