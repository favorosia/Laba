@echo off
setlocal
cd /d "%~dp0"
set "JDK_HOME=%JAVA_HOME%"
if not defined JDK_HOME for /d %%D in ("%ProgramFiles%\Java\jdk-*") do set "JDK_HOME=%%~fD"
if not exist lib mkdir lib
if not exist lib\postgresql-42.7.13.jar curl.exe --fail --location --output lib\postgresql-42.7.13.jar https://repo.maven.apache.org/maven2/org/postgresql/postgresql/42.7.13/postgresql-42.7.13.jar
if errorlevel 1 exit /b 1
if exist build rmdir /s /q build
mkdir build\classes
if defined JDK_HOME (
  "%JDK_HOME%\bin\javac.exe" --release 17 -encoding UTF-8 -d build\classes src\ru\laba\films\config\*.java src\ru\laba\films\domain\*.java src\ru\laba\films\data\*.java src\ru\laba\films\service\*.java src\ru\laba\films\presentation\*.java
) else (
  javac --release 17 -encoding UTF-8 -d build\classes src\ru\laba\films\config\*.java src\ru\laba\films\domain\*.java src\ru\laba\films\data\*.java src\ru\laba\films\service\*.java src\ru\laba\films\presentation\*.java
)
if errorlevel 1 exit /b 1
echo Main-Class: ru.laba.films.presentation.FilmDatabaseApplication> build\MANIFEST.MF
echo Class-Path: lib/postgresql-42.7.13.jar>> build\MANIFEST.MF
if defined JDK_HOME ("%JDK_HOME%\bin\jar.exe" --create --file film-database.jar --manifest build\MANIFEST.MF -C build\classes .) else (jar --create --file film-database.jar --manifest build\MANIFEST.MF -C build\classes .)
if errorlevel 1 exit /b 1
echo Built film-database.jar
endlocal
